const host = 'localhost';
const storage = sessionStorage;

document.querySelector('button').addEventListener('click', async (e) => {
    e.preventDefault();
    const username = document.querySelector('#username');
    const password = document.querySelector('#password');
    const userInfo = {
        username: username.value,
        password: password.value
    }
    console.log(username);
    console.log(password);
    console.log(userInfo);
    try {
        const responseDTO = await fetch(`/backend/auth/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userInfo)
        })
        .then(res => {
            return res.json();
        })
        .catch(err => {
            window.alert('Authorizaion did not succeed');
            throw new Error();
        })

        storage.setItem('userId', responseDTO.user.userId);
        storage.setItem('jwt', responseDTO.jwt); //jwtをsessionStorageに保存していいの？
        window.location.href = `../home/index.html`;
    } catch (err) {
        console.log(err);
    }
    

})