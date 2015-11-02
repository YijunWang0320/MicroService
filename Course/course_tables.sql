.open course_service.db

CREATE TABLE main.Course( course_id INT PRIMARY KEY, course_name VARCHAR (50));

CREATE TABLE main.Enrollment(course_id INT,student_id INT,student_name VARCHAR,PRIMARY KEY (course_id, student_id));