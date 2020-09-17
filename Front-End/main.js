var bluetoothSvg = '<svg aria-hidden="true" focusable="false" data-prefix="fab" data-icon="bluetooth-b" class="svg-inline--fa fa-bluetooth-b fa-w-10" role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 320 512"><path fill="currentColor" d="M196.48 260.023l92.626-103.333L143.125 0v206.33l-86.111-86.111-31.406 31.405 108.061 108.399L25.608 368.422l31.406 31.405 86.111-86.111L145.84 512l148.552-148.644-97.912-103.333zm40.86-102.996l-49.977 49.978-.338-100.295 50.315 50.317zM187.363 313.04l49.977 49.978-50.315 50.316.338-100.294z"></path></svg>'
var spinner = '<svg class="spinner" width="32px" height="32px" viewBox="0 0 66 66" xmlns="http://www.w3.org/2000/svg"><circle class="circle" fill="none" stroke-width="6" stroke-linecap="round" cx="33" cy="33" r="30"></circle></svg>';
var table = document.getElementById('table');
var result = document.getElementById('result');
var search = document.getElementById('search');

window.onload = function () {
    processDevices([]);
}
search.addEventListener('click', searchDevices, false);

function searchDevices() {
    showSpinner();
    var xhr = new XMLHttpRequest();
    xhr.onload = function (ev) {
        if (ev.target && ev.target.readyState === 4 && (ev.target.status === 200 || ev.target.status === 400)) {
            clearResult();
            processResponse(ev.target.response); // -> Aqui esta la respuesta en formato JSON
        }
    }
    xhr.onerror = function (ev) {
        var msg = ev.target && ev.target.readyState === 4 && ev.target.status === 0 ? 'No se pudo conectar con el servicio' : 'Ocurrio un error al buscar'
        showMessage(msg)
    }
    xhr.open('GET', 'http://localhost:9092/search', true);
    xhr.send();
}

function showMessage(msg) {
    var p = document.createElement('p');
    p.innerHTML = msg;
    clearResult();
    result.appendChild(p);
}

function showSpinner() {
    result.innerHTML = spinner;
}

function clearResult() {
    result.innerHTML = '';
}

/**
    La respuesta siempre viene con esta estructura
    {
        error: boolean,
        response: any,
        message: string
    }
*/
function processResponse(response) {
    var data = JSON.parse(response); // -> JSON.parse combierte el JSON a Objeto

    if (data.error) {
        showMessage(data.message);
    } else {
        processDevices(data.response);
    }
}

function processDevices(devices) {
    var thead = '<thead><tr>'
        + '<th>' + bluetoothSvg + '</th>'
        + '<th>Name</th>'
        + '<th>Address</th>'
        + '<th>Authenticated</th>'
        + '<th>Encrypted</th>'
        + '<th>Trusted Device</th>'
        + '</tr></thead>';
    var tbody = '<tbody>';

    if (Array.isArray(devices) && devices.length) {
        devices.forEach(function (device) {
            var tr = '<tr>';
            tr += '<td>' + bluetoothSvg + '</td>';
            tr += '<td>' + prop(device.name) + '</td>';
            tr += '<td>' + prop(device.bluetoothAddress) + '</td>';
            tr += '<td>' + prop(device.authenticated) + '</td>';
            tr += '<td>' + prop(device.encrypted) + '</td>';
            tr += '<td>' + prop(device.trustedDevice) + '</td>';
            tr += '</tr>';
            tbody += tr;
        })
    } else {
        tbody += '<tr><td colspan="6" class="text-center p-10">No se encontraron registros</td></tr>'
    }

    tbody += '</tbody>';
    table.innerHTML = thead + tbody;
}

function prop(prop) {
    return prop !== undefined && prop !== null ? prop : '';
}