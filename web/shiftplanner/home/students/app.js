const storage = sessionStorage;
const host = 'localhost';

window.addEventListener('DOMContentLoaded', async (e) => {

    document.querySelector('.new-student-button').addEventListener('click', (e) => {
        window.location.href = `../student_registration/index.html`;
    })


    const students = await fetch(`/backend/user/${storage.getItem('userId')}/students`, {
        headers: {
            Authorization: `Bearer ${storage.getItem('jwt')}`
        }
    })
    .then(res => res.json())
    .catch(err => console.error(err));

    students.forEach(student => {
        const card = createStudentCard(student);
        document.querySelector('.student-list').appendChild(card);
    })
})

function createStudentCard(student) {

    const DOM_div = document.createElement('div');
    DOM_div.className = 'student-card';
    DOM_div.id = `${student.id}`
    const h2 = document.createElement('h2');
    h2.innerText = `${student.name}`;
    DOM_div.appendChild(h2);
    const p = document.createElement('p');
    p.innerText = `${student.grade}`;
    DOM_div.appendChild(p);
    const delete_button = document.createElement('button');
    delete_button.className = 'delete-button';
    delete_button.innerText = '削除';
    delete_button.addEventListener('click', async (e) => {
        e.preventDefault();
        console.log('clicked');
        const studentIds = [];
        studentIds.push(student.id);
        await fetch(`/backend/user/${storage.getItem('userId')}/students`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${storage.getItem('jwt')}`
            },
            body: JSON.stringify(studentIds)
        })
        .catch(err => console.error(err));

        window.location.reload();
    })
    DOM_div.appendChild(delete_button);

    return DOM_div;
}