/**
 * Created by federicoberon on 30/11/2019.
 */
'use strict';

var singleUploadForm = document.querySelector('#singleUploadForm') || document.querySelector('#singleUploadFormMap') ;
var singleFileUploadInput = document.querySelector('#singleFileUploadInput') || document.querySelector('#singleFileUploadInputMap');

var multipleUploadForm = document.querySelector('#multipleUploadForm') || document.querySelector('#multipleUploadFormMap');
var multipleFileUploadInput = document.querySelector('#multipleFileUploadInput') || document.querySelector('#multipleFileUploadInputMap');

var uploadResponse = document.querySelector('#upload-response');


function uploadSingleFile (file){
    var url = document.getElementById("fileApiUrl").innerHTML+'/file';
    var idEmpresa = document.querySelector("#idEmpresa").value;
    var idUbicacion = document.querySelector("#idUbicacion").value;
    var originFileName = file.name;
    var extension = file.name.split('.').pop();
    var fileName;
    var pathsArray = getAllFilesPathOfParam(url , idEmpresa , 'fotos_map');

    if(pathsArray.length !== 0){
        pathsArray = filterFilesPath(pathsArray, idUbicacion);

        if(pathsArray.length !== 0){
            var fileNameArray = getFileNames(pathsArray);
            var numbersDuplicateArray = resolveFileName(fileNameArray);

            var number = numbersDuplicateArray.reduce(function (accumulator, current) {
                return accumulator > parseInt(current) ? accumulator : parseInt(current);
            }, 0);

            if(number === 0){
                fileName = idUbicacion + "(1)." + extension;
            }else{
                fileName = idUbicacion + '(' + (number + 1) + ').' + extension;
            }
        }else{
            fileName = idUbicacion + '.' + extension;
        }

    }else{
        fileName = idUbicacion + '.' + extension;
    }

    var formData = new FormData();
    formData.append("archivo", file , fileName);

    var xhr = new XMLHttpRequest();

    xhr.open("POST", url + '/' + idEmpresa + '/' + fileName);

    xhr.onload = function() {
        console.log(xhr.responseText);

        if(xhr.status === 200 || xhr.status === 201 ) {
            var successResponse = document.createElement("p");
            successResponse.innerHTML = "<i class='far fa-check-circle' style='color:green'></i> Archivo subido exitosamente: " + originFileName;
            uploadResponse.appendChild(successResponse);
            uploadResponse.style.display = "block";
        } else {
            console.log("error: ", originFileName);
            var errorResponse = document.createElement("p");
            errorResponse.innerHTML = "<p><i class='far fa-times-circle' style='color:darkred'></i> Hubo un error: "+ originFileName +"</p>";
            uploadResponse.appendChild(errorResponse);
            uploadResponse.style.display = 'block';
        }
    };

    xhr.send(formData);
}

function resolveAfter3Seconds() {
    return new Promise(resolve => {
        setTimeout(() => {
            resolve('resolved');
        }, 3000);
    });
}

function resolveFileName(fileNameArray){
    /* The group on this regex matches the number found between two parentheses in case the file name already exists*/
    var regexGetDuplicateNumber = /[1-9]+[\s]{0,2}[(]{0,1}(\w*)[)]{0,1}/;

    /* Return number found if exists else return zero*/
    return fileNameArray.map(function (name) {
        return name.match(regexGetDuplicateNumber)[1] ? name.match(regexGetDuplicateNumber)[1] : 0;
    });
}

async function uploadMultipleFiles(files) {

    $('#modal-loader').show();
    $('#loader-block').show();

    for(var index = 0; index < files.length; index++) {
        uploadSingleFile(files[index]);
        const result = await resolveAfter3Seconds();
    }

    $('#modal-loader').hide();
    $('#loader-block').hide();
}

function clearResponseLabel(){
    uploadResponse.style.display = "none";
    uploadResponse.innerHTML = '';
}

singleUploadForm.addEventListener('submit', function(event){
    var files = singleFileUploadInput.files;

    uploadSingleFile(files[0]);
    event.preventDefault();
}, true);


multipleUploadForm.addEventListener('submit', function(event){
    var files = multipleFileUploadInput.files;

    uploadMultipleFiles(files);
    event.preventDefault();
}, true);