DROP TABLE IF EXISTS `coupon`, `coupon_stock`;
-- -----------------------------------------------------
-- Table `coupon_stack`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `coupon_stock` (
    `id` BIGINT NOT NULL,
    `name` VARCHAR(45) NULL,
    `discount_ratio` INT NULL,
    `discount_value` INT NULL,
    `started_at` DATETIME NOT NULL,
    `finished_at` DATETIME NOT NULL,
    `min_product_price` INT NOT NULL,
    `total_quantity` INT NOT NULL,
    `left_quantity` INT NOT NULL,
    PRIMARY KEY (`id`))
    ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `coupon`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `coupon` (
    `id` BIGINT NOT NULL,
    `user_id` BIGINT NOT NULL,
    `coupon_id` BIGINT NOT NULL,
    `issued_at` DATETIME NOT NULL,
    `expired_at` DATETIME NOT NULL,
    `used_at` DATETIME NOT NULL,
    `state` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_coupon_id_idx` (`coupon_id` ASC) VISIBLE,
    CONSTRAINT `fk_coupon_id`
    FOREIGN KEY (`coupon_id`)
    REFERENCES `coupon_db`.`coupon_stack` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;
