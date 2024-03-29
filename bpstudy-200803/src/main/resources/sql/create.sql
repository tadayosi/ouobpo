drop table pay_slip if exists;
drop table time_record if exists;
drop table employee if exists;

create table employee (
	employee_id    identity,
	name           varchar(100) not null,
	job_type       varchar(10) not null,
	rank           int not null,
	rent_allowance boolean not null,
	rent           int
);

create table time_record (
	id             identity,
	employee_id    int not null,
	target_year    int not null,
	target_month   int not null,
	overtime_hours int not null,
	unique (employee_id, target_year, target_month),
	foreign key (employee_id) references employee (employee_id)
);

create table pay_slip (
	id                 identity,
	employee_id        int not null,
	target_year        int not null,
	target_month       int not null,
	base_salary        int not null,
	overtime_allowance int not null,
	rent_allowance     int not null,
	total_amount       int not null,
	unique (employee_id, target_year, target_month),
	foreign key (employee_id) references employee (employee_id)
);

insert into employee (
	name,
	job_type,
	rank,
	rent_allowance,
	rent
) values (
	'Taro Yamada',
	'SE',
	2,
	true,
	80000
);

insert into time_record (
	employee_id,
	target_year,
	target_month,
	overtime_hours
) values (
	0,
	2008,
	3,
	30
);
