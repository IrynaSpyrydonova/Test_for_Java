export class newProfile {

    constructor() {
        this.fullname,
            this.gender,
            this.country,
            this.city,
            this.gsm,
            this.email,
            this.userpic,
            this.occupations = [],
            this.interests = [],
            this.attachments = [],
            this.description
    }

    fillUserInfo = () => {
        const profileName = document.querySelector('#profileName').value;
        this.fullname = profileName;
        const gender = document.querySelector('.profile-content').elements.gender.value;
        this.gender = gender;
        const country = document.querySelector('#profileCountry').value;
        this.country = country;
        const town = document.querySelector('#profileCity').value;
        this.city = town;
        const phone = document.querySelector('#phone').value;
        this.gsm = phone;
        const email = document.querySelector('#profileEmail').value;
        this.email = email;

        if (
            this.fullname === "" || this.gender === "" || this.country === "" || this.city === "" || this.gsm === "" || this.email === "") {
            document.querySelector('.fill-all-fields').style.display = "block";
        } else if (!this.isValidEmail(this.email)) {
            document.querySelector('.fill-all-fields').innerHTML = "Please enter a valid E-Mail address"
            document.querySelector('.fill-all-fields').style.display = "block";
        } else {
            document.querySelector('.profileDiv').style.display = "none";
            document.querySelector('.interest_profile').style.display = "grid";
            // fetch('../static/json/professions.json')
            fetch('/json/professions.json')
                .then(response => response.json())
                .then(data => data.forEach(item => {
                    const values = (Object.values(item)).join().replace(/ /g, "");

                    let speciality = document.createElement('div');
                    speciality.className = "speciality-item";
                    speciality.innerHTML = `
                    <input class="chk-btn" type="checkbox"  value=${values} name="speciality" id=${values}> 
                    <label for=${values}>${Object.values(item)}</label>
                    `;
                    speciality.addEventListener('click', e => {
                        if (!e.target.matches('input')) return;
                        if (speciality.children[0].checked) {
                            let value = speciality.children[1].innerHTML;
                            document.querySelector('.output-1').innerHTML += `<p>${value}</p>`;
                            this.occupations.push(value);
                        }
                    })
                    document.querySelector('.speciality-items').appendChild(speciality);
                }))
                .catch((error) => {
                    console.error('Error:', error);
                });

            const allInterests = document.querySelectorAll('[name="interests"]');
            allInterests.forEach(item => {
                item.addEventListener('click', e => {
                    if (item.checked) {
                        let value2 = item.parentElement.children[1].innerHTML;
                        document.querySelector('.output-2').innerHTML += `<p>${value2}</p>`;
                        this.interests.push(value2)
                    }
                })
            })


            document.getElementById('typeSpeciality').addEventListener('keypress', e => {
                if (e.keyCode == 13) {
                    document.querySelector('.output-1').innerHTML += `<p>${e.target.value}</p>`;
                    this.occupations.push(e.target.value);
                    e.target.value = '';
                }
            })

            document.getElementById('typeInterest').addEventListener('keypress', e => {
                if (e.keyCode == 13) {
                    document.querySelector('.output-2').innerHTML += `<p>${e.target.value}</p>`;
                    this.occupations.push(e.target.value);
                    e.target.value = '';
                }
            })
        }
    }

    // Check email is valid

    isValidEmail = (email) => {
        const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(String(email).toLowerCase());
    }

    getUserPicture = () => {
        // Pavel should change where he stores user's pic
        let profilePhoto = document.getElementById('userpic');
        profilePhoto = document.getElementsByClassName('cr-image')[0];

        this.userpic = profilePhoto.src;
    }

    getUserInfo = () => {
        const userText = document.getElementById('addUserInfoInput');
        this.description = userText.value;

    }



}

export class newUser {
    constructor() {
        this.username,
            this.password,
            this.email
    }

    getUserPassword = () => {
        const createPass = document.getElementById('password1');
        const confirmPass = document.getElementById('password2');
        const passMessage = document.querySelector('.password-requirements2').children[0];
        createPass.addEventListener('input', e => {
            if (createPass.value === "") {
                passMessage.innerHTML = '';
            }
        });

        if (createPass.value !== confirmPass.value) {
            passMessage.innerHTML = 'Passwords do not match';
            passMessage.className = 'passError';

            console.log(document.querySelector('.password-requirements2').children[0]);
        } else {
            return this.password = confirmPass.value;
        }
    }


}