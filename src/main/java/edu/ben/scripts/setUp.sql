-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema HoneyComb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema HoneyComb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `HoneyComb` DEFAULT CHARACTER SET utf8 ;
USE `HoneyComb` ;

-- -----------------------------------------------------
-- Table `HoneyComb`.`USER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `HoneyComb`.`USER` (
  `userID` INT NOT NULL AUTO_INCREMENT,
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


-- -----------------------------------------------------
-- Table `HoneyComb`.`POST`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `HoneyComb`.`POST` (
  `postID` INT NOT NULL AUTO_INCREMENT,
  `userID` INT NOT NULL,
  `title` VARCHAR(60) NOT NULL,
  `description` VARCHAR(300) NOT NULL,
  `link` VARCHAR(100) NULL,
  `active` TINYINT(1) NOT NULL,
  `draft` TINYINT(1) NOT NULL,
  `tack_count` INT NOT NULL DEFAULT 0,
  `like_count` INT NOT NULL DEFAULT 0,
  `view_count` INT NOT NULL DEFAULT 0,
  `created_on` TIMESTAMP NOT NULL,
  `image_path` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`postID`),
  INDEX `user_table_link_idx` (`userID` ASC),
  CONSTRAINT `post_user_link`
    FOREIGN KEY (`userID`)
    REFERENCES `HoneyComb`.`USER` (`userID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `HoneyComb`.`USER_TACK`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `HoneyComb`.`USER_TACK` (
  `userID` INT NOT NULL,
  `postID` INT NOT NULL,
  PRIMARY KEY (`userID`, `postID`),
  INDEX `user_tack_post_link_idx` (`postID` ASC),
  CONSTRAINT `user_tack_user_link`
    FOREIGN KEY (`userID`)
    REFERENCES `HoneyComb`.`USER` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `user_tack_post_link`
    FOREIGN KEY (`postID`)
    REFERENCES `HoneyComb`.`POST` (`postID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `HoneyComb`.`TAG`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `HoneyComb`.`TAG` (
  `tag` VARCHAR(30) NOT NULL,
  `similar_tag` VARCHAR(30) NOT NULL,
  `count` INT NOT NULL DEFAULT 1,
  `last_incremented` TIMESTAMP NOT NULL DEFAULT CURRENT_TIME,
  PRIMARY KEY (`tag`, `similar_tag`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `HoneyComb`.`USER_TAG`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `HoneyComb`.`USER_TAG` (
  `userID` INT NOT NULL,
  `tag` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`userID`, `tag`),
  INDEX `user_interest_interest_link_idx` (`tag` ASC),
  CONSTRAINT `user_interest_interest_link`
    FOREIGN KEY (`tag`)
    REFERENCES `HoneyComb`.`TAG` (`tag`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `user_interest_user_link`
    FOREIGN KEY (`userID`)
    REFERENCES `HoneyComb`.`USER` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `HoneyComb`.`POST_TAG`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `HoneyComb`.`POST_TAG` (
  `postID` INT NOT NULL,
  `tag` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`postID`, `tag`),
  INDEX `post_tag_tag_link_idx` (`tag` ASC),
  CONSTRAINT `post_tag_post_link`
    FOREIGN KEY (`postID`)
    REFERENCES `HoneyComb`.`POST` (`postID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `post_tag_tag_link`
    FOREIGN KEY (`tag`)
    REFERENCES `HoneyComb`.`TAG` (`tag`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `HoneyComb`.`COMMENT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `HoneyComb`.`COMMENT` (
  `commentID` INT NOT NULL AUTO_INCREMENT,
  `userID` INT NOT NULL,
  `postID` INT NOT NULL,
  `message` VARCHAR(200) NOT NULL,
  `like_count` INT NOT NULL DEFAULT 0,
  `created_on` TIMESTAMP NOT NULL,
  PRIMARY KEY (`commentID`),
  INDEX `comment_user_link_idx` (`userID` ASC),
  INDEX `comment_post_link_idx` (`postID` ASC),
  CONSTRAINT `comment_user_link`
    FOREIGN KEY (`userID`)
    REFERENCES `HoneyComb`.`USER` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `comment_post_link`
    FOREIGN KEY (`postID`)
    REFERENCES `HoneyComb`.`POST` (`postID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `HoneyComb`.`GROUP`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `HoneyComb`.`GROUP` (
  `groupID` INT NOT NULL,
  `group_name` VARCHAR(45) NOT NULL,
  `created_on` TIMESTAMP NOT NULL,
  `created_by` INT NOT NULL,
  PRIMARY KEY (`groupID`),
  INDEX `group_user_link_idx` (`created_by` ASC),
  CONSTRAINT `group_user_link`
    FOREIGN KEY (`created_by`)
    REFERENCES `HoneyComb`.`USER` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `HoneyComb`.`GROUP_USER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `HoneyComb`.`GROUP_USER` (
  `groupID` INT NOT NULL,
  `userID` INT NOT NULL,
  `type` INT NOT NULL,
  `created_on` TIMESTAMP NOT NULL,
  PRIMARY KEY (`groupID`, `userID`),
  INDEX `group_user_user_link_idx` (`userID` ASC),
  CONSTRAINT `group_user_user_link`
    FOREIGN KEY (`userID`)
    REFERENCES `HoneyComb`.`USER` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `group_user_group_link`
    FOREIGN KEY (`groupID`)
    REFERENCES `HoneyComb`.`GROUP` (`groupID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `HoneyComb`.`GROUP_POST`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `HoneyComb`.`GROUP_POST` (
  `groupID` INT NOT NULL,
  `postID` INT NOT NULL,
  PRIMARY KEY (`groupID`, `postID`),
  UNIQUE INDEX `postID_UNIQUE` (`postID` ASC),
  CONSTRAINT `group_post_post_link`
    FOREIGN KEY (`groupID`)
    REFERENCES `HoneyComb`.`POST` (`postID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `group_post_group_link`
    FOREIGN KEY (`groupID`)
    REFERENCES `HoneyComb`.`GROUP` (`groupID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `HoneyComb`.`USER_FOLLOWS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `HoneyComb`.`USER_FOLLOWS` (
  `userID` INT NOT NULL,
  `followed_userID` INT NOT NULL,
  `created_on` TIMESTAMP NOT NULL,
  PRIMARY KEY (`userID`, `followed_userID`),
  CONSTRAINT `user_follows_user_link`
    FOREIGN KEY (`userID` , `followed_userID`)
    REFERENCES `HoneyComb`.`USER` (`userID` , `userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `HoneyComb`.`USER_LIKE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `HoneyComb`.`USER_LIKE` (
  `userID` INT NOT NULL,
  `postID` INT NOT NULL,
  PRIMARY KEY (`userID`, `postID`),
  INDEX `user_like_post_link_idx` (`postID` ASC),
  CONSTRAINT `user_like_post_link`
    FOREIGN KEY (`postID`)
    REFERENCES `HoneyComb`.`POST` (`postID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `user_like_user_link`
    FOREIGN KEY (`userID`)
    REFERENCES `HoneyComb`.`USER` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
