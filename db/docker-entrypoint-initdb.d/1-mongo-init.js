let solver = {
    user: "solver",
    pwd: "password",
    roles: [
        {
            role: "readWrite",
            db: "shiftplanner_solver"
        }
    ]
};

db.createUser(solver);
db.createCollection("role");
db.createCollection("user");
db.createCollection("user_to_student");
db.createCollection("students");
db.createCollection("user_to_teacher");
db.createCollection("teachers");
db.createCollection("timetables");
db.createCollection("user_to_timetable");