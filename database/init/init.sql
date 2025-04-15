USE solver-db;

CREATE TABLE IF NOT EXISTS roles (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  authority VARCHAR(36) NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
  id VARCHAR(255) PRIMARY KEY,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS grades (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  grade_id INT NOT NULL,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS subjects (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);



INSERT INTO roles (authority) VALUES ('ADMIN');
INSERT INTO roles (authority) VALUES ('USER');

INSERT INTO users (id, username, password) VALUES ('ac13f1be-d29f-4bea-813a-e6733c6661a6', 'test', '$2a$10$sIPiCZMFF630lMyENi70aOb0oZcnQBN0AztEyhsvoJqI6t7VhUjLW'); -- username: test, password: password

INSERT INTO grades (grade_id, name) VALUES (1, 'PRIMARY_1');
INSERT INTO grades (grade_id, name) VALUES (2, 'PRIMARY_2');
INSERT INTO grades (grade_id, name) VALUES (3, 'PRIMARY_3');
INSERT INTO grades (grade_id, name) VALUES (4, 'PRIMARY_4');
INSERT INTO grades (grade_id, name) VALUES (5, 'PRIMARY_5');
INSERT INTO grades (grade_id, name) VALUES (6, 'PRIMARY_6');
INSERT INTO grades (grade_id, name) VALUES (7, 'JUNIOR_1');
INSERT INTO grades (grade_id, name) VALUES (8, 'JUNIOR_2');
INSERT INTO grades (grade_id, name) VALUES (9, 'JUNIOR_3');
INSERT INTO grades (grade_id, name) VALUES (10, 'HIGH_1');
INSERT INTO grades (grade_id, name) VALUES (11, 'HIGH_2');
INSERT INTO grades (grade_id, name) VALUES (12, 'HIGH_3');
INSERT INTO grades (grade_id, name) VALUES (13, 'OTHER');


INSERT INTO subjects (id, name) VALUES (1, 'PRIMARY_MATH');
INSERT INTO subjects (id, name) VALUES (2, 'PRIMARY_JAPANESE');
INSERT INTO subjects (id, name) VALUES (3, 'J_EXAM_MATH');
INSERT INTO subjects (id, name) VALUES (4, 'J_EXAM_JAPANESE');
INSERT INTO subjects (id, name) VALUES (5, 'J_EXAM_SOCIAL_STUDIES');
INSERT INTO subjects (id, name) VALUES (6, 'J_EXAM_SCIENCE');
INSERT INTO subjects (id, name) VALUES (7, 'JUNIOR_MATH');
INSERT INTO subjects (id, name) VALUES (8, 'JUNIOR_JAPANESE');
INSERT INTO subjects (id, name) VALUES (9, 'JUNIOR_ENGLISH');
INSERT INTO subjects (id, name) VALUES (10, 'JUNIOR_SOCIAL_STUDIES');
INSERT INTO subjects (id, name) VALUES (11, 'JUNIOR_SCIENCE');
INSERT INTO subjects (id, name) VALUES (12, 'H_EXAM_MATH');
INSERT INTO subjects (id, name) VALUES (13, 'H_EXAM_JAPANESE');
INSERT INTO subjects (id, name) VALUES (14, 'H_EXAM_ENGLISH');
INSERT INTO subjects (id, name) VALUES (15, 'H_EXAM_SOCIAL_STUDIES');
INSERT INTO subjects (id, name) VALUES (16, 'H_EXAM_SCIENCE');
INSERT INTO subjects (id, name) VALUES (17, 'HIGH_MATH_IA');
INSERT INTO subjects (id, name) VALUES (18, 'HIGH_MATH_IIB');
INSERT INTO subjects (id, name) VALUES (19, 'HIGH_MATH_III');
INSERT INTO subjects (id, name) VALUES (20, 'HIGH_JAPANESE');
INSERT INTO subjects (id, name) VALUES (21, 'HIGH_CLASSICAL_JAPANESE');
INSERT INTO subjects (id, name) VALUES (22, 'HIGH_ENGLISH');
INSERT INTO subjects (id, name) VALUES (23, 'UNI_EXAM_MATH_IA');
INSERT INTO subjects (id, name) VALUES (24, 'UNI_EXAM_MATH_IIB');
INSERT INTO subjects (id, name) VALUES (25, 'UNI_EXAM_MATH_III');
INSERT INTO subjects (id, name) VALUES (26, 'UNI_EXAM_JAPANESE');
INSERT INTO subjects (id, name) VALUES (27, 'UNI_EXAM_CLASSICAL_JAPANESE');
INSERT INTO subjects (id, name) VALUES (28, 'UNI_EXAM_ENGLISH');
INSERT INTO subjects (id, name) VALUES (29, 'UNI_EXAM_JAPANESE_HISTORY');
INSERT INTO subjects (id, name) VALUES (30, 'UNI_EXAM_WORLD_HISTORY');
INSERT INTO subjects (id, name) VALUES (31, 'UNI_EXAM_PHYSICS');
INSERT INTO subjects (id, name) VALUES (32, 'UNI_EXAM_CHEMISTRY');
INSERT INTO subjects (id, name) VALUES (33, 'UNI_EXAM_BIOLOGY');
