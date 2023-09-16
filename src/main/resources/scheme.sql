DROP TABLE IF EXISTS `coupon`, `coupon_stock`, `coupon_rule`;

-- -----------------------------------------------------
-- Table `coupon_rule`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `coupon_rule` (
    `id` BIGINT NOT NULL,
    `name` VARCHAR(45) NULL,
    `discount_type` VARCHAR(45) NOT NULL,
    `discount_rate` INT NULL,
    `discount_value` INT NULL,
    `started_at` DATETIME NOT NULL,
    `finished_at` DATETIME NOT NULL,
    `usage_hours` INT NOT NULL,
    `min_product_price` INT NOT NULL,
    PRIMARY KEY (`id`))
    ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `coupon_stock`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `coupon_stock` (
    `id` BIGINT NOT NULL,
    `coupon_rule_id` BIGINT NOT NULL,
    `total_quantity` INT NOT NULL,
    `left_quantity` INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_coupon_id_idx` (`coupon_rule_id` ASC) VISIBLE,
    CONSTRAINT `fk_coupon_rule_coupon_stock`
    FOREIGN KEY (`coupon_rule_id`)
    REFERENCES `coupon_db`.`coupon_rule` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `coupon`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `coupon` (
    `id` BIGINT NOT NULL,
    `user_id` BIGINT NOT NULL,
    `coupon_stock_id` BIGINT NOT NULL,
    `coupon_rule_id` BIGINT NOT NULL,
    `issued_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `expired_at` DATETIME NOT NULL,
    `used_at` DATETIME NULL,
    `status` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_coupon_rule_id_idx` (`coupon_rule_id` ASC) VISIBLE,
    INDEX `fk_coupon_stock_id_idx` (`coupon_stock_id` ASC) VISIBLE,
    CONSTRAINT `fk_coupon_rule_coupon`
        FOREIGN KEY (`coupon_rule_id`)
            REFERENCES `coupon_db`.`coupon_rule` (`id`),
    CONSTRAINT `fk_coupon_stock_coupon`
        FOREIGN KEY (`coupon_stock_id`)
            REFERENCES `coupon_db`.`coupon_stock` (`id`))
    ENGINE = InnoDB;
