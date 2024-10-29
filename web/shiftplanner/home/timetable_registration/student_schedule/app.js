const storage = sessionStorage;
const host = 'localhost';

window.addEventListener('DOMContentLoaded', async (e) => {
    const timetable = await fetch(`/backend/user/${storage.getItem('userId')}/timetables/${storage.getItem('projectId')}`, {
        headers: {
            Authorization: `Bearer ${storage.getItem('jwt')}`
        }
    })
    .then(res => res.json())
    .catch(err => console.error(err));

    console.log(timetable);

    const studentSchedules = timetable.lessons.map(lesson => lesson.studentSchedule).filter((element, index, self) => self.findIndex(e => e.student.id === element.student.id) === index);
    console.log(studentSchedules);
    studentSchedules.forEach(studentSchedule => {
        const card = createCard(studentSchedule);
        document.querySelector('.student-list').appendChild(card);
    })

    const students = await fetch(`/backend/user/${storage.getItem('userId')}/students`, {
        headers: {
            Authorization: `Bearer ${storage.getItem('jwt')}`
        }
    })
    .then(res => res.json())
    .catch(err => console.error(err))

    students.forEach(student => {
        const option = document.createElement('option');
        option.value = student.name;
        option.innerText = student.name;
        option.id = student.id;
        document.querySelector('#name').appendChild(option);
    })


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

    document.querySelector('#add').addEventListener('click', (e) => {
        e.preventDefault();
        const subject = document.querySelector('#subject').selectedOptions[0].value;
        const qty = document.querySelector('#qty').selectedOptions[0].value;
        const DOM_li = document.createElement('li');
        DOM_li.className = 'course';
        DOM_li.innerText = subject + ' ' + qty;
        document.querySelector('.course-list').appendChild(DOM_li);
    })

    document.querySelector('#submit').addEventListener('click', async (e) => {

        e.preventDefault();
        const student = students.filter(student => student.id === document.querySelector('#name').selectedOptions[0].id)[0];
        const dayOffReqs = collectDayOffReqs();
        const courseMap = new Map();
        document.querySelectorAll('.course').forEach(DOM_li => {
            const course = DOM_li.innerText.split(' ');
            courseMap.set(course[0], Number(course[1]));
        })

        console.log(courseMap);
        

        const studentSchedule = {
            student: student,
            courseMap: Object.fromEntries(courseMap),
            dayOffReqs: dayOffReqs
        }

        const request_body = [];
        request_body.push(studentSchedule);

        console.log(request_body);
        console.log(JSON.stringify(request_body));

        await fetch(`/backend/user/${storage.getItem('userId')}/timetables/${storage.getItem('projectId')}/studentSchedules`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${storage.getItem('jwt')}`
            },
            body: JSON.stringify(request_body)
        })
        .then(res => location.reload())
        .catch(err => console.error(err));
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
    card.className = 'student-card';
    card.id = obj.id;
    const h2 = document.createElement('h2');
    h2.innerText = obj.student.name;
    card.appendChild(h2);
    const delete_button = document.createElement('button');
    delete_button.className = 'delete-button';
    delete_button.innerText = '削除'
    delete_button.addEventListener('click', async (e) => {
        e.preventDefault();
        const ids = [];
        ids.push(obj.id);
        await fetch(`/api/user/${storage.getItem('userId')}/timetables/${storage.getItem('projectId')}/studentSchedules`, {
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