create database mySqlDB;
use mySqlDB

CREATE USER sqluser IDENTIFIED BY 'sqluserpw'; 

grant usage on *.* to sqluser@localhost identified by 'sqluserpw'; 
grant all privileges on mySqlDB.* to sqluser@localhost; 

CREATE TABLE NODE_INFO ( id INT AUTO_INCREMENT PRIMARY KEY, CORE1_CPU varchar(20), CORE2_CPU varchar(10), FREE_MEMORY varchar(20), USED_MEMORY varchar(10), REQUEST_ID varchar(100));

INSERT INTO NODE_INFO VALUES(100,'80','44','1234M','1.2G','123456');
INSERT INTO NODE_INFO VALUES(101,'70','55','1234M','1.2G','123456');

INSERT INTO NODE_INFO (CORE1_CPU,CORE2_CPU,FREE_MEMORY,USED_MEMORY, REQUEST_ID) VALUES ('80','44','1234M','1.2G','12345');
INSERT INTO NODE_INFO (CORE1_CPU,CORE2_CPU,FREE_MEMORY,USED_MEMORY, REQUEST_ID) VALUES ('70','55','1234M','1.2G','32323');

commit;