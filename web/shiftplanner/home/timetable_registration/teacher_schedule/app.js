const storage = sessionStorage;

window.addEventListener('DOMContentLoaded', async (e) => {
    const timetable = await fetch(`/backend/user/${storage.getItem('userId')}/timetables/${storage.getItem('projectId')}`, {
        headers: {
            Authorization: `Bearer ${storage.getItem('jwt')}`
        }
    })
        .then(res => res.json())
        .catch(err => console.error(err));

    // const teacherSchedules = timetable.teacherSchedules;
    // teacherSchedules.forEach(teacherSchedule => {
    //     const DOM_li = document.createElement('li');
    //     DOM_li.className = 'teacher_item';
    //     DOM_li.id = `${teacherSchedule.teacher.id}`;
    //     DOM_li.innerText = `${teacherSchedule.teacher.name}`;
    //     document.querySelector('.teacher_list').appendChild(DOM_li);
    // })


    const teachers = await fetch(`/backend/user/${storage.getItem('userId')}/teachers`, {
        headers: {
            Authorization: `Bearer ${storage.getItem('jwt')}`
        }
    })
    .then(res => res.json())
    .catch(err => console.error(err));

    const DOM_select = document.querySelector('#name');
    teachers.forEach(teacher => {
        const teacher_name = teacher.name;
        const DOM_option = document.createElement('option');
        DOM_option.innerText = teacher_name;
        DOM_option.id = teacher.id;
        DOM_select.appendChild(DOM_option);
    })

    timetable.teacherSchedules.forEach(teacherSchedule => {
        const card = createCard(teacherSchedule);
        document.querySelector('.teacher-list').appendChild(card);
    });

    const slots = timetable.timeslots;

    const weekdays = ['日', '月', '火', '水', '木', '金', '土'];

    const calendar = document.querySelector('#calendar');
    const dayrow = document.createElement('tr');
    const blank = document.createElement('th');
    dayrow.appendChild(blank);
    for (let i = 0; i < 7; i++) {
        const day = document.createElement('th');
        day.textContent = `${weekdays[i]}`;
        day.className = 'day';
        dayrow.appendChild(day);
    }

    calendar.appendChild(dayrow);
    for (let i = 0; i < slots.length; i = i + 7) {
        if ((i / 7) % 6 === 0) {
            const daterow = document.createElement('tr');
            const time = document.createElement('td');
            time.textContent = '時間';
            daterow.appendChild(time);
            for (let j = 0; j < 7; j++) {
                const td = document.createElement('td');
                td.textContent = `${timetable.timeslots[i + j].slot.slice(5, 10).replace('-', '/')}`;
                td.className = `date`;
                daterow.appendChild(td);
            }
            calendar.appendChild(daterow);
        }


        const tr = document.createElement('tr');
        tr.className = `slot${(i / 7) % 6}`;
        const td = document.createElement('td');
        td.textContent = `${timetable.timeslots[i].slot.slice(-8, -3)}`;
        td.className = `time`;
        tr.appendChild(td);
        for (let j = 0; j < 7; j++) {
            const td = document.createElement('td');
            td.id = `${timetable.timeslots[i + j].slot}`;
            td.className = `slot ${timetable.timeslots[i + j].closed === true ? 'close' : 'open'}`;
            tr.appendChild(td);
        }
        calendar.appendChild(tr);

    }

    const tds = document.querySelectorAll('td');
    tds.forEach(td => {
        td.addEventListener('click', (e) => {
            e.preventDefault();
            if (!e.target.classList.contains('close')) {
                if (e.target.classList.contains('selected')) {
                    e.target.classList.remove('selected');
                } else {
                    e.target.classList.add('selected');
                }
            }

        })
    });

    document.querySelector('#add').addEventListener('click', async (e) => {
        e.preventDefault();
        const id = document.querySelector('#name').selectedOptions[0].id;
        const dayOffReqs = collectDayOffReqs();
        const teacherSchedule = [{
            // teacher: timetable.teacherSchedules.filter(teacherSchedule => id === teacherSchedule.teacher.id)[0],
            teacher: teachers.filter(teacher => id === teacher.id)[0],
            dayOffReqs: dayOffReqs
        }];

        await fetch(`/backend/user/${storage.getItem('userId')}/timetables/${storage.getItem('projectId')}/teacherSchedules`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${storage.getItem('jwt')}`
            },
            body: JSON.stringify(teacherSchedule)
        })
            .then(res => {
                window.location.reload();
            })
            .catch(err => {
                alert('ERROR', err);
            })
    })
});

function collectDayOffReqs() {
    const dayOffReqs = document.querySelectorAll('.selected');
    const timeslots = [];
    dayOffReqs.forEach(dayOffReq => {
        const timeslot = {
            slot: dayOffReq.id,
            closed: dayOffReq.classList[1] === 'close' ? true : false
        }
        timeslots.push(timeslot);
    })
    console.log(timeslots);
    return timeslots;
}

function createCard(obj) {
    const card = document.createElement('div');
    card.className = 'teacher-card';
    card.id = obj.id;
    const h2 = document.createElement('h2');
    h2.innerText = obj.teacher.name;
    card.appendChild(h2);
    const delete_button = document.createElement('button');
    delete_button.className = 'delete-button';
    delete_button.innerText = '削除'
    delete_button.addEventListener('click', async (e) => {
        e.preventDefault();
        const ids = [];
        ids.push(obj.id);
        await fetch(`/backend/user/${storage.getItem('userId')}/timetables/${storage.getItem('projectId')}/teacherSchedules`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${storage.getItem('jwt')}`
            },
            body: JSON.stringify(ids)
        })
        .then(res => window.location.reload())
        .catch(err => console.error(err));
    })
    card.appendChild(delete_button);
    return card;
}