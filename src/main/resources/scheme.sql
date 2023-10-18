DROP TABLE IF EXISTS `coupon`, `user_coupon`, `user`;

-- -----------------------------------------------------
-- Table `coupon`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `coupon` (
    `coupon_id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NULL,
    `discount_type` VARCHAR(45) NOT NULL,
    `discount_rate` DECIMAL(3,2) NULL,
    `discount_price` INT NULL,
    `total_quantity` INT NOT NULL,
    `left_quantity` INT NOT NULL,
    `started_at` DATETIME NOT NULL,
    `finished_at` DATETIME NOT NULL,
    `usage_hours` INT NOT NULL,
    `min_product_price` INT NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`coupon_id`))
    ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `user_coupon`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user_coupon` (
    `user_coupon_id` BIGINT NOT NULL AUTO_INCREMENT,
    `coupon_id` BIGINT NOT NULL,
    `user_id` BIGINT NOT NULL,
    `issued_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `expired_at` DATETIME NOT NULL,
    `used_at` DATETIME NULL,
    `status` VARCHAR(50) NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`user_coupon_id`),
    INDEX `fk_coupon_id_idx` (`coupon_id` ASC) VISIBLE,
    CONSTRAINT `fk_coupon_coupon`
        FOREIGN KEY (`coupon_id`)
            REFERENCES `coupon` (`coupon_id`))
    ENGINE = InnoDB;

ALTER TABLE user_coupon
    ADD CONSTRAINT user_coupon_id_and_user_id_unique UNIQUE(coupon_id, user_id);

-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user` (
    `user_id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NULL,
    `status` VARCHAR(50) NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`user_id`))
    ENGINE = InnoDB;

