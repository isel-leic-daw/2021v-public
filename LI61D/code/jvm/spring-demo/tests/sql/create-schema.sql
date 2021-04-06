create schema dbo;

create table dbo.Students(
  id int generated always as identity primary key,
  number int unique,
  name VARCHAR(100)
);
