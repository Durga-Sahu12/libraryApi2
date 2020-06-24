create table bookitems (
	bcode integer primary key, 
	bname varchar(45) not null,
	author varchar(45) not null,
	
	issuedate date not null
);