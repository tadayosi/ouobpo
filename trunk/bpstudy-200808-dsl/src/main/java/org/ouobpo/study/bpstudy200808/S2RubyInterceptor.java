package org.ouobpo.study.bpstudy200808;

import static org.apache.commons.io.IOUtils.*;
import static org.jruby.javasupport.JavaEmbedUtils.*;

import java.io.IOException;
import java.util.Arrays;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.io.IOUtils;
import org.jruby.Ruby;
import org.jruby.runtime.GlobalVariable;
import org.jruby.runtime.builtin.IRubyObject;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class S2RubyInterceptor extends AbstractInterceptor {
  private static final Logger LOGGER = LoggerFactory.getLogger(S2RubyInterceptor.class);

  public Object invoke(MethodInvocation invocation) throws Throwable {
    String script = findRubyScript(invocation);
    return execute(script, invocation);
  }

  private String findRubyScript(MethodInvocation invocation) throws IOException {
    Class<?> targetClass = getTargetClass(invocation);
    String filename = targetClass.getSimpleName()
        + "_"
        + invocation.getMethod().getName()
        + ".rb";
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("executing '" + filename + "'");
    }
    return IOUtils.toString(targetClass.getResourceAsStream(filename));
  }

  private Object execute(String script, MethodInvocation invocation) {
    Ruby ruby = initialize(Arrays.asList());
    prepareVariables(ruby, invocation);
    IRubyObject rubyResult = ruby.evalScriptlet(wrapWithRescue(script));
    Object ret = rubyToJava(
        ruby,
        rubyResult,
        invocation.getMethod().getReturnType());
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("returned '" + ret + "'");
    }
    return ret;
  }

  private static void prepareVariables(Ruby ruby, MethodInvocation invocation) {
    // logger
    ruby.defineGlobalConstant("LOGGER", javaToRuby(ruby, LOGGER));
    // method arguments
    String[] names = invocation.getMethod().getAnnotation(Arguments.class).value();
    Object[] args = invocation.getArguments();
    for (int i = 0; i < args.length; i++) {
      ruby.defineVariable(new GlobalVariable(ruby, "$" + names[i], javaToRuby(
          ruby,
          args[i])));
    }
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
}
