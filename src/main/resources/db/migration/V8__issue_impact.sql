START TRANSACTION;
ALTER TABLE `issue`
ADD `impact` VARCHAR(10) NOT NULL DEFAULT 'NORMAL';

ALTER TABLE `issue`
ADD `urgency` VARCHAR(20) NOT NULL DEFAULT 'NORMAL';
COMMIT;