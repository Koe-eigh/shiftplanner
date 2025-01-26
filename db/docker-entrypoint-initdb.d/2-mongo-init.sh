mongoimport -u solver -p password --db shiftplanner_solver --collection role --file /docker-entrypoint-initdb.d/role.json --jsonArray
mongoimport -u solver -p password --db shiftplanner_solver --collection user --file /docker-entrypoint-initdb.d/user.json --jsonArray
mongoimport -u solver -p password --db shiftplanner_solver --collection user_to_student --file /docker-entrypoint-initdb.d/user_to_student_relation.json --jsonArray
mongoimport -u solver -p password --db shiftplanner_solver --collection students --file /docker-entrypoint-initdb.d/students.json --jsonArray
mongoimport -u solver -p password --db shiftplanner_solver --collection user_to_teacher --file /docker-entrypoint-initdb.d/user_to_teacher_relation.json --jsonArray
mongoimport -u solver -p password --db shiftplanner_solver --collection teachers --file /docker-entrypoint-initdb.d/teachers.json --jsonArray
mongoimport -u solver -p password --db shiftplanner_solver --collection timetables --file /docker-entrypoint-initdb.d/timetables.json --jsonArray
mongoimport -u solver -p password --db shiftplanner_solver --collection user_to_timetable --file /docker-entrypoint-initdb.d/user_to_timetable_relation.json --jsonArray