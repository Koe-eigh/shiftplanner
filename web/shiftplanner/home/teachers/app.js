const storage = sessionStorage;
const host = 'localhost';

window.addEventListener('DOMContentLoaded', async (e) => {

    document.querySelector('.new-teacher-button').addEventListener('click', (e) => {
        window.location.href = `../teacher_registration/index.html`;
    })

    const teachers = await fetch(`/backend/user/${storage.getItem('userId')}/teachers`, {
        headers: {
            Authorization: `Bearer ${storage.getItem('jwt')}`
        }
    })
    .then(res => res.json())
    .catch(err => console.error(err));

    teachers.forEach(teacher => {
        const card = createTeacherCard(teacher);
        document.querySelector('.teacher-list').appendChild(card);
    })
});

function createTeacherCard(teacher) {

    const DOM_div = document.createElement('div');
    DOM_div.className = 'teacher-card';
    DOM_div.id = `${teacher.id}`
    const h2 = document.createElement('h2');
    h2.innerText = `${teacher.name}`;
    DOM_div.appendChild(h2);
    const p = document.createElement('p');
    p.innerText = `${teacher.skills.toString()}`;
    DOM_div.appendChild(p);
    const delete_button = document.createElement('button');
    delete_button.className = 'delete-button';
    delete_button.innerText = '削除';
    delete_button.addEventListener('click', async (e) => {
        e.preventDefault();
        console.log('clicked');
        const teacherIds = [];
        teacherIds.push(teacher.id);
        await fetch(`/backend/user/${storage.getItem('userId')}/teachers`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${storage.getItem('jwt')}`
            },
            body: JSON.stringify(teacherIds)
        })
        .catch(err => console.error(err));

        window.location.reload();
    })
    DOM_div.appendChild(delete_button);

    return DOM_div;
}