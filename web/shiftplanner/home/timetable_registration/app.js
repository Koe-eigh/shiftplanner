const submit = document.querySelector('.submit');
const start = document.querySelector('#start');
const end = document.querySelector('#end');
const id = document.querySelector('#id');

const host = 'localhost';
const storage = sessionStorage;

window.addEventListener('DOMContentLoaded', async (e) => {
    await fetch('/backend/user/', {
        headers: {
            Authorization: `Bearer ${storage.getItem('jwt')}`
        }
    })
    .catch(err => {
        window.location.href = `../../error/unauthorized.html`;
    });

});

submit.addEventListener('click', (e) => {
    e.preventDefault();
    const projectId = id.value;
    createCalendar(start.valueAsDate, end.valueAsDate);
    setEventListener(projectId);
});

function createCalendar(startDate, endDate){
    if (isNaN(startDate) || isNaN(endDate) || startDate > endDate) {
        alert('開始日と終了日を正しく入力してください。');
        return;
    }

    deleleteAllElements();

    const head = document.createElement('h2');
    head.textContent = '休館日を設定してください';
    document.querySelector('.elem_container').appendChild(head);

    const calendar = document.createElement('table');
    calendar.id = 'calendar';
    document.querySelector('.elem_container').appendChild(calendar);

    // 開始日をその週の日曜日に設定
    if (startDate.getDay() !== 0) { // 0は日曜日
        startDate.setDate(startDate.getDate() - startDate.getDay());
    }

    const weekdays = ['日', '月', '火', '水', '木', '金', '土'];
    const timeslots = ['12:40', '14:10', '15:40', '17:20', '18:50', '20:20'];

    const dayrow = document.createElement('tr');
    const blank = document.createElement('th');
    dayrow.appendChild(blank);
    for(let i = 0; i < 7; i++){
        const dayElement = document.createElement('th');
        dayElement.className = 'day'
        dayElement.innerText = weekdays[i];
        dayrow.appendChild(dayElement);
    }
    calendar.appendChild(dayrow);

    let currentDate = new Date(startDate);
    while (currentDate <= endDate) {
        const daterow = document.createElement('tr');
        const timeElement = document.createElement('td');
        timeElement.className = 'time';
        timeElement.textContent = '時間';
        daterow.appendChild(timeElement);
        for(let i = 0; i < 7; i++){
            const dateElement = document.createElement('td');
            dateElement.className = 'date';
            dateElement.textContent = (currentDate.getMonth() + 1) + '/' + currentDate.getDate();
            daterow.appendChild(dateElement);
            currentDate.setDate(currentDate.getDate() + 1);
        }
        currentDate.setDate(currentDate.getDate() - 7);//currentDateをtdダグのid属性を決めるために7日前に戻す
        calendar.appendChild(daterow);
        for(let i = 0; i < 6; i++){
            const slotrow = document.createElement('tr');
            slotrow.className = `slot${i}`;
            const time = document.createElement('td');
            time.className = 'time';
            time.textContent = timeslots[i];
            slotrow.appendChild(time);
            for(let j = 0; j < 7; j++){
                const slotElement = document.createElement('td');
                slotElement.className = 'slot open';
                slotElement.id = currentDate.getFullYear() + '-' + ('0' + (currentDate.getMonth() + 1)).slice(-2) + '-' + ('0' + currentDate.getDate()).slice(-2) + 'T' + timeslots[i] + ':00';
                slotrow.appendChild(slotElement);
                currentDate.setDate(currentDate.getDate() + 1);
            }
            calendar.appendChild(slotrow); 
            currentDate.setDate(currentDate.getDate() - 7);
        }
        currentDate.setDate(currentDate.getDate() + 7);
        
    }

    const button = document.createElement('button');
    button.textContent = '登録';
    button.className = 'submit';
    document.querySelector('.elem_container').appendChild(button);
}

function deleleteAllElements(){
    document.querySelector('.elem_container').innerHTML = '';
}

function setEventListener(projectId){

    const slots = document.querySelectorAll('.slot');
    slots.forEach(slot => {
        slot.addEventListener('click', (e) => {
            if(e.target.classList.contains('open')){
                e.target.classList.remove('open');
                e.target.classList.add('close');
            }else{
                e.target.classList.remove('close');
                e.target.classList.add('open');
            }
        });
    });

    const button = document.querySelector('.submit');
    button.addEventListener('click', (e) => {
        e.preventDefault();

        const timeslots = [];
        const slots = document.querySelectorAll('.slot');
        slots.forEach(slot => {
            const date = new Date(slot.id);
            const timeslot = {
                slot: `${date.getFullYear()}-${('0' + (date.getMonth() + 1)).slice(-2)}-${('0' + date.getDate()).slice(-2)}T${('0' + date.getHours()).slice(-2)}:${('0' + date.getMinutes()).slice(-2)}:${('0' + date.getSeconds()).slice(-2)}`,
                closed: slot.classList[1] === 'close' ? true : false
            };
            timeslots.push(timeslot);
        });
        console.log(timeslots)
        setTimeslots(timeslots, projectId);
    });
}


async function setTimeslots(timeslots, projectId) {
    const timetable = await fetch(`/backend/user/${storage.getItem('userId')}/timetables`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${storage.getItem('jwt')}`
        },
        body: `${projectId}`
    })
    .then(res => res.json())
    .catch(err => {
        alert('ERROR', err);
        console.log(err);
    });

    console.log(timetable);

    await fetch(`/backend/user/${storage.getItem('userId')}/timetables/${timetable.id}/timeslots`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${storage.getItem('jwt')}`
        },
        body: JSON.stringify(timeslots)
    })
    .then(res => {
        storage.removeItem('projectId');
        storage.setItem('projectId', `${timetable.id}`)
        window.location.href = `./teacher_schedule/index.html`;
    })
    .catch(err => {
        console.log(err);
    })
}

