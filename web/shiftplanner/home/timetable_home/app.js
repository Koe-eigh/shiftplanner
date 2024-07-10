const storage = sessionStorage;

const evenOrOdd = ['even', 'odd'];
const days = ['日', '月', '火', '水', '木', '金', '土']
const screenModeEnum = {
    teacher: 'TEACHER',
    student: 'STUDENT'
};
let screenMode = screenModeEnum.teacher;
let timetable;
let index = 0;

window.addEventListener('DOMContentLoaded', async (e) => {
    timetable = await fetch(`/backend/user/${storage.getItem('userId')}/timetables/${storage.getItem('projectId')}`, {
        headers: {
            Authorization: `Bearer ${storage.getItem('jwt')}`
        }
    })
        .then(res => {
            return res.json();
        })
        .catch(err => {
            console.error(err);
        });

    console.log(timetable);

    const timeslots = timetable.timeslots;
    const calendar = document.querySelector('#calendar');
    const dayrow = document.createElement('tr');
    const blank = document.createElement('th');
    dayrow.appendChild(blank);
    days.forEach(day => {
        const dayelem = document.createElement('th');
        dayelem.textContent = day;
        dayelem.className = 'day';
        dayelem.colSpan = '2';
        dayrow.appendChild(dayelem);
    })
    calendar.appendChild(dayrow);

    for (let i = 0; i < timeslots.length; i = i + 7) {
        if ((i / 7) % 6 === 0) {
            const daterow = document.createElement('tr');
            const time = document.createElement('td');
            time.textContent = '時間';
            time.className = 'time';
            daterow.appendChild(time);
            for (let j = 0; j < 7; j++) {
                const td = document.createElement('td');
                td.textContent = `${timeslots[i + j].slot.slice(-14, -9).replace('-', '/')}`;
                td.className = `date`;
                td.colSpan = '2';
                daterow.appendChild(td);
            }
            calendar.appendChild(daterow);
        }

        const slotrow = document.createElement('tr');
        slotrow.className = `slot${(i / 7) % 6}`;
        const time = document.createElement('td');
        time.textContent = `${timeslots[i].slot.slice(-8, -3)}`;
        time.className = 'time';
        slotrow.appendChild(time);
        for (let j = 0; j < 14; j++) {
            const td = document.createElement('td');
            // td.id = `${timeslots[i + (Math.floor(j/2))].date}T${timeslots[i + (Math.floor(j/2))].startTime}`;
            td.className = `slot D${timeslots[i + (Math.floor(j / 2))].slot.replaceAll('-', '_').replaceAll(':', 't')} ${evenOrOdd[j % 2]} ${timeslots[i + (Math.floor(j / 2))].closed === true ? 'close' : 'open'}`;
            slotrow.appendChild(td);
        }
        calendar.appendChild(slotrow);
    }

    createSchedule(timetable, index);

});


const solverButton = document.querySelector('#solve');
solverButton.addEventListener('click', async (e) => {
    await fetch(`/backend/solver/solve`, {
        method: 'POST',
        headers: {
            Authorization: `Bearer ${storage.getItem('jwt')}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(timetable)

    })
        .then(res => {
            console.log(res);
            setTimeout(() => {
                datafetch();
            }, 1000);
        })
        .catch(err => {
            alert('error', err);
            console.error(err);
        })
})




let studentIndex = 0;
const byStudents = document.querySelector('#switch');
byStudents.addEventListener('click', (e) => {
    switchScreen();
});

const prev = document.querySelector('.prev');
prev.addEventListener('click', (e) => {
    // decrement(timetable, index);
    if (screenMode === 'TEACHER') {
        if (index !== 0) {
            index--;
        }
        deleteSchedule();
        createSchedule(timetable, index);
    } else {
        if (studentIndex !== 0) {
            studentIndex--;
        }
        deleteSchedule();
        createScheduleByStudents(timetable, studentIndex);
    }


    console.log(index);
})

const next = document.querySelector('.next');
next.addEventListener('click', (e) => {
    // increment(timetable, index);
    if (screenMode === 'TEACHER') {
        if (index !== timetable.teacherSchedules.length - 1) {
            index++;
        }
        deleteSchedule();
        createSchedule(timetable, index);
    } else {
        const studentNames = timetable.lessons.map(lesson => lesson.studentSchedule.student.name).filter((name, index, self) => self.indexOf(name) === index);
        console.log(studentNames);
        if (studentIndex !== studentNames.length - 1) {
            studentIndex++;
        }
        deleteSchedule();
        createScheduleByStudents(timetable, studentIndex);
    }

})


function datafetch() {
    fetch(`/backend/user/${storage.getItem('userId')}/timetables/${storage.getItem('projectId')}`, {
        method: 'GET',
        headers: {
            Authorization: `Bearer ${storage.getItem('jwt')}`,
        }
    })
        .then(res => {
            console.log(res);
            return res.json();
        })
        .then(timetable => {
            deleteSchedule();
            createSchedule(timetable, index);
            if (timetable.solverStatus === 'SOLVING_ACTIVE') {
                setTimeout(() => {
                    datafetch();
                }, 1000);
            }

        })
        .catch(err => {
            alert('error', err);
        })
}



function switchScreen() {
    document.querySelector('.name').textContent = '';
    deleteSchedule();
    if (screenMode === 'TEACHER') {
        createScheduleByStudents(timetable, studentIndex);
        screenMode = screenModeEnum.student;
    } else if (screenMode === 'STUDENT') {
        createSchedule(timetable, index);
        screenMode = screenModeEnum.teacher;
    } else {
        throw new Error('IllegalStateException');
    }
}

function createSchedule(timetable, index) {
    const teacher = timetable.teacherSchedules[index];
    const lessonsByTeacher = Array.from(timetable.lessons).filter(lesson => lesson.teacherSchedule.teacher.id === teacher.teacher.id);
    teacher.dayOffReqs.forEach(offslot => {
        const slot = document.querySelectorAll(`.D${offslot.slot.replaceAll('-', '_').replaceAll(':', 't')}`);
        slot.forEach(slot => {
            slot.classList.add('dayOff');
        });
    });
    console.log(teacher);
    console.log(lessonsByTeacher);
    const name = document.querySelector('.name');
    name.textContent = `${teacher.teacher.name}`;
    lessonsByTeacher.forEach(lesson => {

        const timeslots = document.querySelectorAll(`.D${lesson.timeslot.slot.replaceAll('-', '_').replaceAll(':', 't')}`);
        const container = document.createElement('div');
        container.className = 'lesson-container';
        container.innerHTML = `${lesson.subject}<br>${lesson.studentSchedule.student.name}`;
        Array.from(timeslots).filter(timeslot => !timeslot.hasChildNodes())
            .forEach(timeslot => timeslot.appendChild(container));
    });
}

function deleteSchedule() {
    document.querySelectorAll('.slot').forEach(slot => {
        slot.innerHTML = '';
        slot.classList.remove('dayOff');
    });
}

function createScheduleByStudents(timetable, studentIndex) {
    const arr = timetable.lessons.map(lesson => lesson.studentSchedule.student.name);
    const studentNames = arr.filter((name, index, self) => self.indexOf(name) === index);
    console.log(arr);
    console.log(studentNames);
    const lessonByStudent = timetable.lessons.filter(lesson => lesson.studentSchedule.student.name === studentNames[studentIndex]);
    const studentsArr = timetable.lessons.map(lesson => lesson.studentSchedule);
    const students = studentsArr.filter((student, index, self) => arr.indexOf(student.student.name) === index);//？？？
    console.log(studentsArr);
    console.log(students);
    const student = students[studentIndex];
    student.dayOffReqs.forEach(offslot => {
        const slot = document.querySelectorAll(`.D${offslot.slot.replaceAll('-', '_').replaceAll(':', 't')}`);
        slot.forEach(slot => {
            slot.classList.add('dayOff');
        })
    });
    const name = document.querySelector('.name');
    name.textContent = `${studentNames[studentIndex]}`;
    lessonByStudent.forEach(lesson => {
        const timeslots = document.querySelectorAll(`.D${lesson.timeslot.slot.replaceAll('-', '_').replaceAll(':', 't')}`);
        const container = document.createElement('div');
        container.className = 'lesson-container';
        container.innerHTML = `${lesson.subject}<br>${lesson.teacherSchedule.teacher.name}`;
        Array.from(timeslots).filter(timeslot => !timeslot.hasChildNodes()).forEach(timeslot => timeslot.appendChild(container));
    });
}

function increment(timetable, index) {
    if (index != timetable.teacherSchedules.length - 1) {
        index++;
    } else {
        index = 0;
    }
}

function decrement(timetable, index) {
    if (index != 0) {
        index--;
    } else {
        index = timetable.teacherSchedules.length - 1;
    }
}

document.querySelector('#pdf').addEventListener('click', generatePDF);


async function generatePDF() {
    const { jsPDF } = window.jspdf;

    // HTMLのテーブル要素を取得
    const calendar = document.querySelector('#calendar');

    const header = document.querySelector('header');



    const pdf = new jsPDF('p', 'mm', 'a4');
    const pdfWidth = pdf.internal.pageSize.getWidth();
    const pdfHeight = pdf.internal.pageSize.getHeight();

    mergeAndCaptureElements(header, calendar, combinedCanvas => {
        const imgData = combinedCanvas.toDataURL('image/png');
        const imgWidth = combinedCanvas.width;
        const imgHeight = combinedCanvas.height;
        const ratio = imgWidth / pdfWidth;

        let position = 0; // 名前の下から描画開始
        let remainingHeight = imgHeight;

        while (remainingHeight > 0) {
            const heightToPrint = Math.min(remainingHeight, pdfHeight * ratio - position);
            const sourceY = imgHeight - remainingHeight;

            const pageCanvas = document.createElement('canvas');
            pageCanvas.width = imgWidth;
            pageCanvas.height = heightToPrint;

            const ctx = pageCanvas.getContext('2d');
            ctx.drawImage(combinedCanvas, 0, sourceY, imgWidth, heightToPrint, 0, 0, imgWidth, heightToPrint);

            const pageData = pageCanvas.toDataURL('image/png');
            pdf.addImage(pageData, 'PNG', 0, position, pdfWidth, heightToPrint / ratio);

            remainingHeight -= heightToPrint;
            if (remainingHeight > 0) {
                pdf.addPage();
                position = 0; // 新しいページでは上から描画
            }
        }
        const name = document.querySelector('.name').innerText;
        pdf.save(`${name}_schedule.pdf`);
    });

}

function mergeAndCaptureElements(element1, element2, callback) {
    html2canvas(element1).then(canvas1 => {
        html2canvas(element2).then(canvas2 => {
            const combinedCanvas = document.createElement('canvas');
            combinedCanvas.width = Math.max(canvas1.width, canvas2.width);
            combinedCanvas.height = canvas1.height + canvas2.height;

            const ctx = combinedCanvas.getContext('2d');
            ctx.drawImage(canvas1, 0, 0);
            ctx.drawImage(canvas2, 0, canvas1.height);

            callback(combinedCanvas);
        });
    });
}