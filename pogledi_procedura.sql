use mydb;
create view VehicleView as
select * from vehicle inner join model using (idModel) inner join brandname using (idBrandName) inner join vehicletype using (idVehicleType);
select * from VehicleView;

create view SalesManView as
select * from Account inner join Person using (idAccount) inner join SalesMan using (idAccount);
select * from SalesManView;

DELIMITER $$
create procedure buyCar (in pIdVehicle INT, in pIdAccount INT)
BEGIN
	INSERT INTO buy (idVehicle, idAccount, buy.date) VALUES (pIdVehicle, pIdAccount, CURDATE());
     UPDATE vehicle SET vehicle.removed='1' WHERE (vehicle.idVehicle = pIdVehicle);
END$$
DELIMITER ;

-- drop trigger Vehicle_BEFORE_UPDATE;
-- drop procedure buyCar;
-- call buyCar(3, 4);