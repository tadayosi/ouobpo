package org.ouobpo.study.bpstudy200808.drools;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatelessSession;
import org.drools.compiler.DroolsParserException;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;

public class Client {
  public static void main(String[] args) throws Exception {
    List<Employee> employees = Arrays.asList(
        new Employee("ABC001", "佐藤", 29),
        new Employee("ABC002", "山田", 35),
        new Employee("ABC003", "田中", 25));

    // drl
    RuleBase ruleBaseDrl = newRuleBase(newPackage("Employee.drl"));
    StatelessSession sessionDrl = ruleBaseDrl.newStatelessSession();
    sessionDrl.execute(employees);

    // dsl
    RuleBase ruleBaseDsl = newRuleBase(newPackageFromDsl(
        "Employee.dslr",
        "employee.dsl"));
    StatelessSession sessionDsl = ruleBaseDsl.newStatelessSession();
    sessionDsl.execute(employees);
  }

  private static Package newPackage(String drl)
      throws DroolsParserException, IOException {
    PackageBuilder builder = new PackageBuilder();
    builder.addPackageFromDrl(reader(drl));
    return builder.getPackage();
  }

  private static Package newPackageFromDsl(String dslr, String dsl)
      throws DroolsParserException, IOException {
    PackageBuilder builder = new PackageBuilder();
    builder.addPackageFromDrl(reader(dslr), reader(dsl));
    return builder.getPackage();
  }

  private static Reader reader(String resource) {
    return new InputStreamReader(Client.class.getResourceAsStream(resource));
  }

  private static RuleBase newRuleBase(Package pkg) throws Exception {
    RuleBase ruleBase = RuleBaseFactory.newRuleBase();
    ruleBase.addPackage(pkg);
    return ruleBase;
  }
}
