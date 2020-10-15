import { newProfile } from './data.js'

const menuBtn = document.getElementById('menu').addEventListener('click', openMenu);
const dropDownMenu = document.querySelector('#dropDownMenu');

const user = [];

console.log(user);

// Start
if (document.querySelector("body").dataset.title === "home") {
    document.querySelector('#start').addEventListener('click', openNextTab)

}

////////////////////////////////////////

// Change Password Or Profile
if (document.querySelector("body").dataset.title === "change-password-login") {

    document.querySelector('.change-wrapper').addEventListener('submit', e => {
        e.preventDefault();
        let newLogin = document.querySelector('#newLogin').value;
        let newPassword = document.querySelector('#newPass').value;
        let currentEmail = document.querySelector('#currentEmail').value;
        let confirmPassword = document.querySelector('#confirmPass').value;
        // Check if password match
        if (newPassword !== confirmPassword) {
            showError('Passwords do not match', document.querySelector('#confirm'));
        } else {
            // Updated user's info

            let userUpdated = {
                login: newLogin,
                password: newPassword
            };

            // Check if user exist 
            let user = {
                    email: currentEmail
                }
                // Post to find the user by email
            fetch('https://ifo-communalka.herokuapp.com/email_confirmation', {
                    method: 'POST',
                    headers: {
                        "Content-type": "application/json; charset=UTF-8"
                    },
                    body: JSON.stringify(user),
                })
                .then(response => {
                    console.log(response.status);
                    if (response.status === 200) {
                        return response.json();
                    } else {
                        throw response;
                    }
                })
                .then(data => {
                    console.log('Success:', data);
                    // If successful we update the user's data

                    // ask Pavel about correct URL
                    return fetch('https://ifo-communalka.herokuapp.com/email_confirmation', {
                            method: 'PUT',
                            headers: {
                                "Content-type": "application/json; charset=UTF-8"
                            },
                            body: JSON.stringify(userUpdated),
                        })
                        .then(response => {
                            console.log(response.status);
                            if (response.status === 200) {
                                return response.json()
                            } else {
                                throw response;
                            }
                        })
                        .then(data => {
                            console.log('Success:', data);
                            // if successful, inform that e-mail has successful sent
                            document.querySelector('.showcase-wrapper').innerHTML =
                                `   <div id="change_login">
                                <h2 class="confirmation-info">Thanks, we have sent you an e-mail with the confirmation.</h2>
                            </div>
                        `
                        })
                        .catch((error) => {
                            console.error('Error:', error);
                        });
                })
                .catch((error) => {
                    console.error('Error:', error);
                });
            console.log(userUpdated);
        }
    })
}

////////////////////////////////////

// Create Account

if (document.querySelector("body").dataset.title === "create-account") {
    let username = document.querySelector("#name");
    let userEmail = document.querySelector('#email')

    username.addEventListener('change', checkUsrNameHandler);
    userEmail.addEventListener('change', checkEmailHandler);


    document.querySelector("#close").addEventListener('click', closeForm)
    document.querySelector('.accountDiv').addEventListener('submit', e => {
        e.preventDefault();
        checkLength(username, 3, 15);
        const data = {
            usr: username.value,
            email: userEmail.value
        };

        user.push(data);
        console.log(data);

        // fetch('https://ifo-communalka.herokuapp.com/email_confirmation', {
        fetch('/email_confirmation', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data),
            })
            .then(response => {
                console.log(response.status);
                if (response.status === 200) {
                    return response.json();
                } else {
                    throw response;
                }
            })
            .then(data => {
                console.log('Success:', data);
                showEnvelope();
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    });

    let code = document.querySelector("#pass");

    code.addEventListener('input', e => {
        console.log(code.value);
        if (code.value.length > 0) {
            document.querySelector('#enter').classList.remove('enter')
        } else {
            document.querySelector('#enter').classList.add('enter');
            document.querySelector('#errorCode').classList.remove('error');
        }
    })

    document.querySelector('.codeDiv').addEventListener('submit', e => {
        e.preventDefault();
        let userCode = code.value;
        let link = "";
        console.log(userCode.length);
        const reg = /^([a-zA-Z0-9]*-[a-zA-Z0-9]*){4}$/;
        const reg2 = /^[0-9A-F]+$/;
        if (!reg.test(userCode) && !reg2.test(userCode)) {
            showErrorCode(`You've entered a wrong code. Click  ${link} and we will send you a new one to your email.`);
        }

        const data = {
            code: userCode
        }

        console.log(data);

        fetch('https://ifo-communalka.herokuapp.com/confirm_my_login', {
                method: 'POST',
                headers: {
                    "Content-type": "application/json; charset=UTF-8"
                },
                body: JSON.stringify(data),
            })
            .then(response => {
                if (response.status === 200) {
                    return response.json();
                } else {
                    throw response;
                }
            })
            .then(data => {
                console.log('Success:', data.response);
                if (data.response === "Confirmation string is not valid") {
                    showErrorCode(`You've entered a wrong code. Click  ${link} and we will send you a new one to your email.`)
                } else if (data.response === "Confirmation string was already used") {
                    showErrorCode(`You've entered a wrong code. Click  ${link} and we will send you a new one to your email.`)
                } else {
                    showWelcome();
                    document.getElementById("start").addEventListener('click', openTab);
                }
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    });
}


//////////////////////////////////////

// Filling profile

if (document.querySelector("body").dataset.title === "profileForm") {
    let profile = new newProfile();

    fetch('https://restcountries.eu/rest/v2/all')
        .then(response => response.json())
        .then(data => {
            data.forEach(item => {
                const countyList = document.querySelector('#profileCountry');
                let country = document.createElement('option');
                country.value = `${item.name}`;
                let countryText = document.createTextNode(`${item.name}`);
                country.appendChild(countryText);
                countyList.appendChild(country);

            });
        })
        .catch((error) => {
            console.error('Error:', error);
        });
    const next = document.querySelector('#arrow1');
    next.addEventListener('click', fillProfile);

    const previous = document.querySelector('#arrow2');
    previous.addEventListener('click', backToProfile);

    function fillProfile() {
        profile.fillUserInfo();
        console.log(profile);
    }

    const next4 = document.querySelector('#arrow3');
    next4.addEventListener('click', fillProfileInterests);


    function fillProfileInterests(e) {
        e.preventDefault();
        if(profile.interests.length === 0 || profile.occupations.length === 0){
            document.querySelector('.fill-all-interests').style.display="block";
        } else {
        document.querySelector('.interest_profile').style.display = "none";
        document.querySelector('.main-foto').style.display = "grid";
        console.log(profile);
        const previous2 = document.querySelector('#arrow');
        previous2.addEventListener('click', backToInterests);
        }
    }

    const next5 = document.querySelector('#arrow5');
    next5.addEventListener('click', fillProfilePicture);

    function fillProfilePicture(e) {
        e.preventDefault();
        document.querySelector('.main-foto').style.display = "none";
        console.log(document.querySelector('.addUserInfo'));
        document.querySelector('.addUserInfo').style.display = "flex";
        profile.getUserPicture();
        console.log(profile);
    }

    document.querySelector('#submit').addEventListener('click', sendUserProfile)

    function sendUserProfile(e) {
        e.preventDefault();
        profile.getUserInfo();

        // Ask Pavel to put correct link
        fetch('/save_profile', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(profile),
            })
            .then(response => {
                if (response.status === 200) {
                    return response.json()
                } else {
                    throw response;
                }
            })
            .then(data => {
                document.querySelector('.addUserInfo').style.display = "none";
                document.querySelector('.letter-info').style.display = "flex";

                console.log('Success:', data);
            })
            .catch((error) => {
                console.error('Error:', error);
            });


    }
}

// Dropdown Menu
function openMenu() {
    let currentValue = getComputedStyle(dropDownMenu, null).display;
    if (currentValue == "none") {
        dropDownMenu.style.display = "block";
    } else {
        dropDownMenu.style.display = "none";
    }
}

// Open Login tab
function openNextTab() {
    location.href = "./login.html";
}

// Close Create account Form
function closeForm() {
    location.href = "./index.html";
}


// Open Profile form
function openTab() {
    location.href = "./profileForm.html";
}


// Check input lenght
function checkLength(input, min, max) {
    if (input.value.length < min) {
        showError(`${getFieldName(input)} must be at least ${min} characters`, document.querySelector("#smallName"));
    }
    if (input.value.length > max) {
        showError(`${getFieldName(input)} must be less than ${max} characters`, document.querySelector("#smallName"));
    }
}

// Get fieldName
function getFieldName(input) {
    return input.id.charAt(0).toUpperCase() + input.id.slice(1);
}

// Show input error message
function showError(message, type = '') {

    let error = document.querySelector("#smallName");
    error.className = 'error animate';
    error.innerText = message;
    error.style.visibility = "visible";

}


function showEnvelope() {
    document.querySelector('.accountDiv').style.display = "none";

    const envelope = document.querySelector('.email-wrapper');
    envelope.style.display = 'block';

    const closeBtn = envelope.querySelector('.btn-close');
    closeBtn.onclick = showEmailForm;
};

const showEmailForm = (event) => {

    document.querySelector('.email-wrapper').style.display = 'none';

    const confirmationForm = document.querySelector('.codeDiv');
    confirmationForm.style.display = "block";

    document.querySelector("body").dataset.title = 'user-code';
    confirmationForm.onsubmit = codeConfirmation;

}

const codeConfirmation = (event) => {
    event.preventDefault();
    if (document.querySelector("body").dataset.title === "user-code") {

        let code = document.querySelector("#pass");

        code.addEventListener('input', e => {
            if (code.value.length > 0) {
                document.querySelector('#enter').classList.remove('enter')
            } else {
                document.querySelector('#enter').classList.add('enter')
            }
        });

        let userCode = code.value;
        let link;
        console.log(userCode.length);
        const reg = /^([a-zA-Z0-9]*-[a-zA-Z0-9]*){4}$/;
        const reg2 = /^[0-9A-F]+$/;
        if (!reg.test(userCode) && !reg2.test(userCode)) {
            showErrorCode(`
            You 've entered a wrong code. Click  ${link} and we will send you a new one to your email.`);
        }

        let data = {
            code: userCode
        };

        fetch('/confirm_my_login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data)
            })
            .then(response => {
                if (response.status === 200) {

                    if (response === "Confirmation string is not valid") {
                        showErrorCode(`You've entered a wrong code. Click  ${link} and we will send you a new one to your email.`)
                    } else if (response === "Confirmation string was already used") {
                        showErrorCode(`You've entered a wrong code. Click  ${link} and we will send you a new one to your email.`)
                    } else {
                        return response.json()
                    }

                } else {
                    throw response;
                }
            })

        .then(data => {
            console.log('Success:', data);
            document.cookie = `usr=${JSON.stringify(user[0])}`;
            location.href = "./create_password";
        }).catch(error => {
            console.error('Error:', error);
            showErrorCode(`You've entered a wrong code. Click  ${link} and we will send you a new one to your email.`);
        })

    }
}

// Back to profile Info
function backToProfile(){
    debugger;
    document.querySelector('.profileDiv').style.display="grid";
    document.querySelector('.interest_profile').style.display="none";
}

// Back to profile Info
function backToInterests(e){
    e.preventDefault();
    document.querySelector('.interest_profile').style.display="grid";
    document.querySelector('.main-foto').style.display="none";
}

// Show Welcome page
function showWelcome() {
    document.querySelector('#showcase').style.display = "none";
    document.querySelector('#greeting').style.display = "block";
}

function checkUsrNameHandler(event) {
    const usrname = event.target.value;
    checkCredentials(usrname, 'usrname');
}

function checkEmailHandler(event) {
    const usremail = event.target.value;
    usremail.parentElement;
    checkCredentials(usremail, 'usremail');
}

async function checkCredentials(credential, type) {

    let path = '';

    if (type === 'usrname')
        path = '/usr_exists';
    else path = '/email_exists';

    const res = await
    fetch(`${path}?${type}=${credential}`, {
        method: 'GET'
    });

    if (!res.ok || res.status !== 200) {
        throw new Error('response failed');
    }
    const data = await res.json();
    if (data) {
        showError(`${credential} already exists. Please, try with another`);
        document.querySelector('.btn-join').disabled = true;
        return;
    };

    document.querySelector('.btn-join').disabled = false;

}