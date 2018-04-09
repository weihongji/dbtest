/*
USE Test
GO

IF NOT EXISTS(select * from sys.tables where name = 'Customer') BEGIN
	create table dbo.Customer(
		Id int IDENTITY(1,1) NOT NULL,
		Name varchar(100) NOT NULL,
		Gender smallint NOT NULL,
		Birthdate smalldatetime,
		DateStamp datetime NOT NULL CONSTRAINT DF_Customer_DateStamp DEFAULT (getdate()),
		CONSTRAINT PK_Customer PRIMARY KEY CLUSTERED
		(
			Id ASC
		)
	)
END
GO

--drop table Customer
--truncate table

INSERT INTO Customer(Name, Gender, Birthdate) VALUES ('Jack', 1, '19800102')
*/

select * from Customer

