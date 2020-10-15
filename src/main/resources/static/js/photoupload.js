import { newProfile } from "./data.js";

const fileInput = document.getElementById('upload');
fileInput.addEventListener('change', function() { fileInputHandler(this) });

const photoPlaceholder = document.querySelector('.profile-foto');

const savePhotoBtn = document.getElementById('savePhotoBtn');
savePhotoBtn.addEventListener('click', savePhoto);

let croppieInstance = null;

function fileInputHandler(input) {

    if (input.files && input.files[0]) {
        const reader = new FileReader();

        reader.onload = function(e) {

            document.getElementById("uploadPhotoBtn").style.display = "none";
            const photoHandlersBtns = document.getElementsByClassName("photo-handlers");

            photoHandlersBtns.disabled = false;
            photoHandlersBtns[0].style.cursor = photoHandlersBtns[1].style.cursor = "pointer";

            croppieInstance = new Croppie(photoPlaceholder, {
                enableExif: true,
                viewport: {
                    width: 179,
                    height: 266,
                    type: 'square'
                },
                boundary: {
                    width: 179,
                    height: 266
                }
            });

            croppieInstance.bind({
                url: e.target.result
            })
        }
        reader.readAsDataURL(input.files[0]);
    } else {
        console.log("Sorry - you're browser doesn't support the FileReader API");
    }

}

const addUserpicToCanvas = (res) => {
    // const userpicImage = document.getElementById('userpic');
    //  userpicImage.src = res;

    // document.querySelector('.cr-slider-wrap').style.display = "none";
}

const sendUserpicToRemoteStorage = (res) => {
    console.log(res);
};

function savePhoto() {
    croppieInstance.result({
        type: 'canvas',
        size: 'viewport'
    }).then(
        res => {
            addUserpicToCanvas(res);
        });

}