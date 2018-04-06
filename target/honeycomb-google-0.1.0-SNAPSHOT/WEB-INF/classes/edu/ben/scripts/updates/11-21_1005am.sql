ALTER TABLE comment DROP COLUMN userID;
alter table honeycomb.user modify column userID INT auto_increment;
ALTER TABLE comment ADD COLUMN userID INT, add constraint foreign key (userID) references user(userID);

DROP TABLE USER;
-- -----------------------------------------------------
-- Table `HoneyComb`.`USER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `HoneyComb`.`USER` (
  `userID` INT NOT NULL auto_increment,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `phone_number` INT NOT NULL,
  `username` VARCHAR(60) NOT NULL,
  `bio` VARCHAR(400) NULL,
  `profile_image_path` VARCHAR(45) NULL,
  `active` TINYINT(1) NOT NULL,
  `login_attempts` INT NOT NULL,
  `created_on` TIMESTAMP NOT NULL,
  PRIMARY KEY (`userID`))
  
ENGINE = InnoDB;