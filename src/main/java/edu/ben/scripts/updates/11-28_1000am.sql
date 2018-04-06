-- -----------------------------------------------------
-- Table `HoneyComb`.`POST_IMAGE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `HoneyComb`.`POST_IMAGE` (
  `imageID` INT NOT NULL AUTO_INCREMENT,
  `postID` INT NOT NULL,
  `file_extension` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`imageID`),
  INDEX `post_image_post_link_idx` (`postID` ASC),
  CONSTRAINT `post_image_post_link`
    FOREIGN KEY (`postID`)
    REFERENCES `HoneyComb`.`POST` (`postID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

INSERT INTO `honeycomb`.`post_image` (`imageID`, `postID`, `file_extension`) VALUES ('1', '1', '.png');
INSERT INTO `honeycomb`.`post_image` (`imageID`, `postID`, `file_extension`) VALUES ('2', '2', '.png');
INSERT INTO `honeycomb`.`post_image` (`imageID`, `postID`, `file_extension`) VALUES ('3', '3', '.png');

alter table honeycomb.post drop column image_path;
