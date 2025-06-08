ALTER TABLE user_details DROP COLUMN authorities;

ALTER TABLE user_details ADD role enum ('VIEWER','CREATOR','ADMIN');
