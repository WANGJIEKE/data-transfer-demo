let mymap = L.map('mapid').setView([51.505, -0.09], 12);

L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
    maxZoom: 18,
    id: 'mapbox/streets-v11',
    accessToken: 'pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw'
}).addTo(mymap);

function getDataPoints() {
    fetch(`/dataPoints?count=${1000}`)
        .then(response => response.json(), reason => alert(reason))
        .then(data => data['latlons'].map(latlon => L.circleMarker(latlon, { radius: 5 }).addTo(mymap)), reason => alert(reason));
}

document.getElementById('get-data-button').addEventListener('click', getDataPoints);
