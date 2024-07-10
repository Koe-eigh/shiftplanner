//TODO pathを/backend/に書き換える

const host = 'localhost';
const storage = sessionStorage;

window.addEventListener('DOMContentLoaded', async (e) => {
    await fetch('/backend/user/', {
        headers: {
            Authorization: `Bearer ${storage.getItem('jwt')}`
        }
    })
        .then(res => {
            if (!res.ok) throw new Error(res.statusText);
        })
        .catch(err => {
            window.location.href = `../error/unauthorized.html`;
        });

    const timetables = await fetch(`/backend/user/${storage.getItem('userId')}/timetables`, {
        headers: {
            Authorization: `Bearer ${storage.getItem('jwt')}`
        }
    })
        .then(res => res.json())
        .catch(err => console.error(err));

    timetables.forEach(timetable => {
        const DOM_li = document.createElement('li');
        DOM_li.className = 'project-item';
        DOM_li.id = `${timetable.projectId}`;
        DOM_li.innerText = `${timetable.name}`;
        DOM_li.addEventListener('click', async (e) => {
            e.preventDefault();
            storage.removeItem('projectId');
            storage.setItem('projectId', e.target.id);
            window.location.href = `./timetable_home/index.html`;
        })
        document.querySelector('.project-list').appendChild(DOM_li);
    });

});

document.querySelector('.new-project-btn').addEventListener('click', async (e) => {
    e.preventDefault();
    window.location.href = `./timetable_registration/index.html`;
})

function verify(userId) {

}