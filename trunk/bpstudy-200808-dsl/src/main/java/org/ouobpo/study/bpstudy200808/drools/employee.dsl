[condition][]age over {age}=emp : Employee(age > {age})
[condition][]employeeNo {employeeNo}=emp : Employee(employeeNo == {employeeNo})
[condition][]name {name}=emp : Employee(name == {name})
[consequence][]out {str}=System.out.println({str} + "> " + emp);
