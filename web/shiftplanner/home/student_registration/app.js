const storage = sessionStorage;
const host = 'localhost';

const subject_teacher_map = new Map();
let teachers;

window.addEventListener('DOMContentLoaded', async (e) => {
    teachers = await fetch(`/backend/user/${storage.getItem('userId')}/teachers`, {
        headers: {
            Authorization: `Bearer ${storage.getItem('jwt')}`
        }
    })
    .then(res => res.json())
    .catch(err => console.error(err));

    teachers.forEach(teacher => {
        const DOM_option = document.createElement('option');
        DOM_option.id = teacher.id
        DOM_option.value = teacher.name
        DOM_option.innerText = teacher.name;
        document.querySelector('#teacher-options').appendChild(DOM_option);
    })

})

document.createElement('option')

document.querySelector('.add').addEventListener('click', (e) => {
    e.preventDefault();
    const subject = document.querySelector('#subject').value;
    const teacher_id = document.querySelector('#teacher-options').selectedOptions[0].id;
    console.log(teacher_id);
    teacher_name = document.querySelector('#teacher-options').value;
    const DOM_li = document.createElement('li');
    DOM_li.innerText = `${subject} ${teacher_name}`;
    console.log(teachers.filter(teacher => teacher.id === teacher_id)[0]);
    subject_teacher_map.set(subject, teachers.filter(teacher => teacher.id === teacher_id)[0]);
    document.querySelector('.teacher-list').appendChild(DOM_li);
})

document.querySelector('#registration').addEventListener('click', async (e) => {

    const student_name = document.querySelector('#name').value;
    const student_grade = document.querySelector('#grade').value;
    const student = {
        name: student_name,
        grade: student_grade,
        subjectTeacherMap: Object.fromEntries(subject_teacher_map)
    }

    console.log(student);

    const request_body = [];
    request_body.push(student);

    await fetch(`/backend/user/${storage.getItem('userId')}/students`, {
        method: 'POST',
        headers: {
            Authorization: `Bearer ${storage.getItem('jwt')}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(request_body)
    })
    .then(res => {
        window.location.reload();
    })
    .catch(err => console.error(err));

})