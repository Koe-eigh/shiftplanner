const storage = sessionStorage;
const host = 'localhost'

document.querySelector('#submit').addEventListener('click', async (e) => {
    // console.log(document.querySelector('#name').value);
    // console.log(document.querySelector('#subjects').selectedOptions)

    const DOM_collections = document.querySelector('#subjects').selectedOptions;
    const skills = [];
    for(let i = 0; i < DOM_collections.length; i++) {
        skills.push(DOM_collections[i].value);
    }

    const teacher = {
        name: document.querySelector('#name').value,
        skills: skills
    }

    console.log(teacher);
    const teachers = [];
    teachers.push(teacher);

    console.log(JSON.stringify(teachers));

    await fetch(`/backend/user/${storage.getItem('userId')}/teachers`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${storage.getItem('jwt')}`
        },
        body: JSON.stringify(teachers)
    })

    window.location.href = `../teachers/index.html`;
})