import Cookies from './js.cookie.js';

// from create_password.html
const saveNewUserBtn = document.getElementById('saveNewUserBtn');
saveNewUserBtn.addEventListener('click', saveUserHandler);
/***********************/


function saveUserHandler(event) {
    const usrdata = Cookies.get('usr');
    const userObj = JSON.parse(usrdata);

    userObj.usrname = userObj.usr;
    delete userObj.usr;

    userObj.pwd = event.target.form.password.value;

    saveNewUser(userObj);
}

async function saveNewUser(staff) {

    let url = 'http://ifo-communalka.herokuapp.com/';

    url = 'http://localhost:8080/';

    const path = 'save_user';

    const res = await
    fetch(`${url}${path}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(staff)
    });

    if (!res.ok || res.status !== 200) {
        throw new Error('response failed');
    }
    const data = await res.json();
    if (data.usr !== null)
        location.href = "./login";
    else { alert('user not') }
}