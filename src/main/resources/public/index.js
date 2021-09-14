let input_name = document.getElementById('name');
let input_country = document.getElementById('country');
let create_room = document.getElementById('create_button');
let table = document.getElementById('table');
let forbidden_no_room = document.querySelector('.forbidden');
let hasAccess = document.querySelector('.hasAccess');
let room_name = document.getElementById('name_room')
let country_name = document.getElementById('name_country')
let lamp = document.querySelector('.lamp');
let button = document.getElementById('button')

let cur_users_location;
let cur_id;

$.getJSON('https://api.db-ip.com/v2/free/self', function (data) {
    cur_users_location = data.countryName;
});


create_room.addEventListener('click', () => {

    if (input_name.value.toString().trim() === '') {
        create_room.textContent = `Name can't be empty`
        setTimeout(() => {
            create_room.textContent = `Create Room`
        }, 2000)
        return;
    }

    let room = {
        country: input_country.value,
        name: input_name.value
    };
    let link = 'http://localhost:8085/create';

    fetch(link, {
        method: 'POST',
        body: JSON.stringify(room),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(response => {
        if (!response.ok)
            throw new Error("Status code  : " + response.status);
        return response.text();
    }).then(text => {

        if (text === 'exist') {
            create_room.textContent = `Already exist`
            setTimeout(() => {
                create_room.textContent = `Create Room`
            }, 2000)
        } else {
            create_room.textContent = `Success`
            input_name.value = ''
            setTimeout(() => {
                create_room.textContent = `Create Room`
            }, 2000)

            room.id = text
            insertNewRoom(room);
        }

    }).catch(reason => {
        console.log(reason)
    })

}, false)


let link = 'http://localhost:8085/rooms';
fetch(link, {
    method: 'GET'
}).then(response => {
    if (!response.ok)
        throw new Error('Status code  : ' + response.status)
    return response.json();
}).then(jsons => {
    jsons.forEach(json => {
        insertNewRoom(json);
    })
}).catch(reason => {
    console.log(reason)
})


var insertNewRoom = (room) => {

    var row = ` <tr>
                    <td>${room.name}</td>
                    <td>${room.country}</td>
                    <td>
                        <button id="${room.id}">
                            Visit
                        </button>
                    </td>

                </tr>
       `
    table.insertAdjacentHTML('beforeend', row)

}

table.addEventListener('click', event => {

    let id = event.target.id;

    let link = 'http://localhost:8085/room/' + id;

    fetch(link, {
        method: 'GET'
    }).then(response => {
        if (!response.ok)
            throw new Error('Status code : ' + response.status)
        return response.json();
    }).then(room => {

        if (room.country !== cur_users_location) {
            hasAccess.style.display = 'none'
            forbidden_no_room.textContent = 'Forbidden'
            forbidden_no_room.style.visibility = 'visible'
            setTimeout(() => {
                forbidden_no_room.textContent = 'No room selected'
            }, 2000)
            return
        }

        hasAccess.style.display = 'block'
        forbidden_no_room.style.visibility = 'hidden'
        room_name.textContent = room.name;
        country_name.textContent = room.country;
        cur_id = event.target.id;
        console.log('Clicked...  : ')
        console.log(room.lamp)
        console.log(event.target.id)
        if (room.lamp)
            lamp.style.backgroundColor = 'greenyellow'
        else
            lamp.style.backgroundColor = 'red'

    }).catch(failure => {
        console.log(failure)
    })

}, false)


button.addEventListener('click', () => {

    let value;
    console.log('button switch got called..')
    console.log('id inside button')
    value = (lamp.style.backgroundColor === 'red');
    let link = `http://localhost:8085/updateroom/${cur_id}/?lamp=` + value
    fetch(link, {
        method: 'GET'
    }).then(response => {
        if (!response.ok)
            throw  new Error('Status code ' + response.status)
    }).then(() => {

    }).catch(reason => {
        console.log(reason)
    });

}, false)


//web socket part of this program

let stompClient = null;
var socket = new SockJS('/stomp-endpoint')
stompClient = Stomp.over(socket);
stompClient.connect({}, (frame) => {
    console.log('This is a connected  computer.....')
    stompClient.subscribe("/topic/updatedroom", function (message) {

        console.log("Response with message : ")
        console.log(message)
        if (JSON.parse(message.body).lamp) {
            lamp.style.backgroundColor = 'greenyellow'
        } else {
            lamp.style.backgroundColor = 'red'
        }

    })
})













