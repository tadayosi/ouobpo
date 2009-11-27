drop table data if exists;

create table data (
	id        identity,
	filename  varchar(100),
	content   varchar(255),
	timestamp timestamp
);