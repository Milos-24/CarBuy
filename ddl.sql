-- MySQL Script generated by MySQL Workbench
-- Thu Sep 22 11:16:06 2022
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Account` (
  `idAccount` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(15) NOT NULL,
  `password` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`idAccount`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Person`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Person` (
  `idAccount` INT NOT NULL,
  `name` VARCHAR(20) NOT NULL,
  `lastName` VARCHAR(20) NOT NULL,
  `sex` TINYINT(1) UNSIGNED NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idAccount`),
  INDEX `fk_Person_Account1_idx` (`idAccount` ASC) ,
  CONSTRAINT `fk_Person_Account1`
    FOREIGN KEY (`idAccount`)
    REFERENCES `mydb`.`Account` (`idAccount`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`PhoneNumber`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`PhoneNumber` (
  `idAccount` INT NOT NULL,
  `phoneNumber` VARCHAR(15) NOT NULL,
  UNIQUE INDEX `phoneNumber_UNIQUE` (`phoneNumber` ASC) ,
  PRIMARY KEY (`idAccount`, `phoneNumber`),
  CONSTRAINT `fk_PhoneNumber_Account1`
    FOREIGN KEY (`idAccount`)
    REFERENCES `mydb`.`Account` (`idAccount`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`SalesBody`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`SalesBody` (
  `idAccount` INT NOT NULL,
  PRIMARY KEY (`idAccount`),
  CONSTRAINT `fk_SalesBody_Account1`
    FOREIGN KEY (`idAccount`)
    REFERENCES `mydb`.`Account` (`idAccount`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`SalesMan`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`SalesMan` (
  `idAccount` INT NOT NULL,
  PRIMARY KEY (`idAccount`),
  INDEX `fk_SalesMan_Person1_idx` (`idAccount` ASC) ,
  CONSTRAINT `fk_SalesMan_Person1`
    FOREIGN KEY (`idAccount`)
    REFERENCES `mydb`.`Person` (`idAccount`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_SalesMan_SalesBody1`
    FOREIGN KEY (`idAccount`)
    REFERENCES `mydb`.`SalesBody` (`idAccount`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Dealership`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Dealership` (
  `idAccount` INT NOT NULL,
  `name` VARCHAR(15) NOT NULL,
  `location` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`idAccount`),
  CONSTRAINT `fk_Dealership_SalesBody1`
    FOREIGN KEY (`idAccount`)
    REFERENCES `mydb`.`SalesBody` (`idAccount`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Buyer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Buyer` (
  `idAccount` INT NOT NULL,
  PRIMARY KEY (`idAccount`),
  INDEX `fk_Buyer_Person1_idx` (`idAccount` ASC) ,
  CONSTRAINT `fk_Buyer_Person1`
    FOREIGN KEY (`idAccount`)
    REFERENCES `mydb`.`Person` (`idAccount`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`BrandName`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`BrandName` (
  `idBrandName` INT NOT NULL AUTO_INCREMENT,
  `brandName` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idBrandName`),
  UNIQUE INDEX `brandName_UNIQUE` (`brandName` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Model`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Model` (
  `idModel` INT NOT NULL AUTO_INCREMENT,
  `idBrandName` INT NOT NULL,
  `modelName` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idModel`),
  UNIQUE INDEX `modelName_UNIQUE` (`modelName` ASC) ,
  INDEX `fk_Model_BrandName1_idx` (`idBrandName` ASC) ,
  CONSTRAINT `fk_Model_BrandName1`
    FOREIGN KEY (`idBrandName`)
    REFERENCES `mydb`.`BrandName` (`idBrandName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`VehicleType`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`VehicleType` (
  `idVehicleType` INT NOT NULL AUTO_INCREMENT,
  `vehicleType` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`idVehicleType`),
  UNIQUE INDEX `carVehicle_UNIQUE` (`vehicleType` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Vehicle`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Vehicle` (
  `idVehicle` INT NOT NULL AUTO_INCREMENT,
  `idAccount` INT NOT NULL,
  `idVehicleType` INT NOT NULL,
  `idModel` INT NOT NULL,
  `horsePower` INT UNSIGNED NOT NULL,
  `numberOfDoors` INT UNSIGNED NOT NULL,
  `transmission` VARCHAR(10) NOT NULL,
  `fuel` VARCHAR(10) NOT NULL,
  `seatNumber` INT UNSIGNED NOT NULL,
  `registrationNumber` VARCHAR(10) NULL,
  `makeYear` INT UNSIGNED NOT NULL,
  `load` INT UNSIGNED NOT NULL,
  `removed` TINYINT(1) NULL,
  PRIMARY KEY (`idVehicle`),
  UNIQUE INDEX `idVehicle_UNIQUE` (`idVehicle` ASC) ,
  UNIQUE INDEX `registrationNumber_UNIQUE` (`registrationNumber` ASC) ,
  INDEX `fk_Vehicle_Model1_idx` (`idModel` ASC) ,
  INDEX `fk_Vehicle_VehicleType1_idx` (`idVehicleType` ASC) ,
  INDEX `fk_Vehicle_SalesBody1_idx` (`idAccount` ASC) ,
  CONSTRAINT `fk_Vehicle_Model1`
    FOREIGN KEY (`idModel`)
    REFERENCES `mydb`.`Model` (`idModel`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Vehicle_VehicleType1`
    FOREIGN KEY (`idVehicleType`)
    REFERENCES `mydb`.`VehicleType` (`idVehicleType`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Vehicle_SalesBody1`
    FOREIGN KEY (`idAccount`)
    REFERENCES `mydb`.`SalesBody` (`idAccount`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Buy`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Buy` (
  `idBuy` INT NOT NULL AUTO_INCREMENT,
  `idAccount` INT NOT NULL,
  `idVehicle` INT NOT NULL,
  `date` DATE NOT NULL,
  PRIMARY KEY (`idBuy`),
  INDEX `fk_Vehicle_has_Buyer_Vehicle1_idx` (`idVehicle` ASC) ,
  INDEX `fk_Buy_Buyer1_idx` (`idAccount` ASC) ,
  CONSTRAINT `fk_Vehicle_has_Buyer_Vehicle1`
    FOREIGN KEY (`idVehicle`)
    REFERENCES `mydb`.`Vehicle` (`idVehicle`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Buy_Buyer1`
    FOREIGN KEY (`idAccount`)
    REFERENCES `mydb`.`Buyer` (`idAccount`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Waranty`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Waranty` (
  `idWaranty` INT NOT NULL AUTO_INCREMENT,
  `duration` INT NOT NULL,
  `date` DATE NOT NULL,
  PRIMARY KEY (`idWaranty`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Sale`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Sale` (
  `idSale` INT NOT NULL AUTO_INCREMENT,
  `idWaranty` INT NULL,
  `idAccount` INT NOT NULL,
  `idVehicle` INT NOT NULL,
  `date` DATE NOT NULL,
  PRIMARY KEY (`idSale`),
  INDEX `fk_SalesBody_has_Vehicle_Vehicle1_idx` (`idVehicle` ASC) ,
  INDEX `fk_SalesBody_has_Vehicle_SalesBody1_idx` (`idAccount` ASC) ,
  INDEX `fk_Sale_Waranty1_idx` (`idWaranty` ASC) ,
  CONSTRAINT `fk_SalesBody_has_Vehicle_SalesBody1`
    FOREIGN KEY (`idAccount`)
    REFERENCES `mydb`.`SalesBody` (`idAccount`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_SalesBody_has_Vehicle_Vehicle1`
    FOREIGN KEY (`idVehicle`)
    REFERENCES `mydb`.`Vehicle` (`idVehicle`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Sale_Waranty1`
    FOREIGN KEY (`idWaranty`)
    REFERENCES `mydb`.`Waranty` (`idWaranty`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `mydb`;

DELIMITER $$
USE `mydb`$$
CREATE DEFINER = CURRENT_USER TRIGGER `mydb`.`Vehicle_BEFORE_INSERT` BEFORE INSERT ON `Vehicle` FOR EACH ROW
BEGIN
	IF (makeYear < 1900) THEN 
		SIGNAL SQLSTATE '45000'
        SET message_text = "Unos nije validan";
    
    END IF;
END;$$

USE `mydb`$$
CREATE DEFINER = CURRENT_USER TRIGGER `mydb`.`Vehicle_BEFORE_UPDATE` BEFORE UPDATE ON `Vehicle` FOR EACH ROW
BEGIN
	IF (makeYear < 1900) THEN 
		SIGNAL SQLSTATE '45000'
        SET message_text = "Unos nije validan";
    
    END IF;
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
