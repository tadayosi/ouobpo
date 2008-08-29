package org.ouobpo.study.bpstudy200808.jruby;

import static org.apache.commons.io.IOUtils.*;
import static org.apache.commons.lang.StringUtils.*;
import static org.jruby.javasupport.JavaEmbedUtils.*;
import static org.seasar.framework.container.InstanceDef.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.io.IOUtils;
import org.jruby.Ruby;
import org.jruby.internal.runtime.methods.DynamicMethod;
import org.jruby.runtime.GlobalVariable;
import org.jruby.runtime.builtin.IRubyObject;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class S2RubyInterceptor extends AbstractInterceptor {
  private static final Logger LOGGER          = LoggerFactory.getLogger(S2RubyInterceptor.class);

  private static final String RUBY_EXTENSION  = ".rb";

  private S2Container         fRootContainer;

  private Ruby                fRuby           = null;

  private Set<Method>         fDefinedMethods = null;

  public void init() {
    fRuby = initialize(Arrays.asList());
    // logger
    fRuby.defineGlobalConstant("LOGGER", javaToRuby(fRuby, LOGGER));
    // container components
    defineGlobalVariables(fRootContainer);

    fDefinedMethods = new HashSet<Method>();
  }

  private void defineGlobalVariables(S2Container container) {
    // define variables
    for (int i = 0; i < container.getComponentDefSize(); i++) {
      ComponentDef compDef = container.getComponentDef(i);
      if (!isEmpty(compDef.getComponentName())
          && SINGLETON_NAME.equals(compDef.getInstanceDef().getName())
          || APPLICATION_NAME.equals(compDef.getInstanceDef().getName())) {
        fRuby.defineVariable(new GlobalVariable(fRuby, "$"
            + compDef.getComponentName(), javaToRuby(
            fRuby,
            compDef.getComponent())));
      }
    }
    // child containers
    for (int i = 0; i < container.getChildSize(); i++) {
      defineGlobalVariables(container.getChild(i));
    }
  }

  public void destroy() {
    terminate(fRuby);
    fDefinedMethods.clear();
    fDefinedMethods = null;
  }

  public Object invoke(MethodInvocation invocation) throws Throwable {
    String script = findRubyScript(invocation);
    return execute(script, invocation);
  }

  private String findRubyScript(MethodInvocation invocation) throws IOException {
    String filename = scriptName(invocation) + RUBY_EXTENSION;
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("executing '{}'", filename);
    }
    return IOUtils.toString(getTargetClass(invocation).getResourceAsStream(
        filename));
  }

  private Object execute(String script, MethodInvocation invocation) {
    if (!fDefinedMethods.contains(invocation.getMethod())) {
      fRuby.evalScriptlet(toMethod(wrapWithRescue(script), invocation));
      fDefinedMethods.add(invocation.getMethod());
    }
    String methodName = methodName(invocation);
    DynamicMethod method = fRuby.getObject().getMethods().get(methodName);
    IRubyObject rubyResult = method.call(
        fRuby.getCurrentContext(),
        fRuby.getObject(),
        null,
        methodName,
        arguments(invocation));
    return rubyToJava(fRuby, rubyResult, invocation.getMethod().getReturnType());
  }

  private IRubyObject[] arguments(MethodInvocation invocation) {
    IRubyObject[] ret = new IRubyObject[invocation.getArguments().length];
    for (int i = 0; i < ret.length; i++) {
      ret[i] = javaToRuby(fRuby, invocation.getArguments()[i]);
    }
    return ret;
  }

  private static String wrapWithRescue(String script) {
    StringBuilder builder = new StringBuilder();
    builder.append("begin").append(LINE_SEPARATOR);
    builder.append(script).append(LINE_SEPARATOR);
    builder.append("rescue => e").append(LINE_SEPARATOR);
    builder.append("  LOGGER.error e.to_s").append(LINE_SEPARATOR);
    builder.append("end");
    return builder.toString();
  }

  private String toMethod(String script, MethodInvocation invocation) {
    StringBuilder builder = new StringBuilder();
    builder.append("def ").append(methodName(invocation));
    boolean first = true;
    for (String name : argumentNames(invocation)) {
      if (first) {
        first = false;
      } else {
        builder.append(",");
      }
      builder.append(" ").append(name);
    }
    builder.append(LINE_SEPARATOR);
    builder.append(script).append(LINE_SEPARATOR);
    builder.append("end");
    String method = builder.toString();
    if (LOGGER.isTraceEnabled()) {
      LOGGER.trace("defined method is:{}{}", LINE_SEPARATOR, method);
    }
    return method;
  }

  private static String[] argumentNames(MethodInvocation invocation) {
    Arguments args = invocation.getMethod().getAnnotation(Arguments.class);
    if (args == null) {
      return new String[0];
    } else {
      return args.value();
    }
  }

  private String scriptName(MethodInvocation invocation) {
    return String.format(
        "%s_%s",
        getTargetClass(invocation).getSimpleName(),
        invocation.getMethod().getName());
  }

  private String methodName(MethodInvocation invocation) {
    return uncapitalize(scriptName(invocation));
  }

  //----------------------------------------------------------------------------
  // Setter
  //----------------------------------------------------------------------------

  public void setContainer(S2Container container) {
    fRootContainer = container.getRoot();
  }
}
