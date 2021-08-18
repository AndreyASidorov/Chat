function rename(userId){
    var formData = new FormData()
    let newName = prompt('Введите новое имя');
    if(newName == null || newName == undefined || newName == ''){
        return;
    }
    formData.append('newName',newName/*document.getElementById('newName').value*/)
    formData.append('userId',userId)
    var token = $('#_csrf').attr('content');
    var headers = new Headers({
        'Accept': '*/*',
        'X-CSRF-TOKEN': token
    });
    fetch('/userRename', {
        method: 'POST',
        headers,
        body: formData
    })
        .then(
            function(data){
                location.reload();
            }
        );
}
function makeAdmin(userId){
    var formData = new FormData()
    formData.append('userId',userId)
    var token = $('#_csrf').attr('content');
    var headers = new Headers({
        'Accept': '*/*',
        'X-CSRF-TOKEN': token
    });
    fetch('/makeAdmin', {
        method: 'POST',
        headers,
        body: formData
    })
        .then(
            function(data){
                location.reload();
            }
        );
}
function makeModer(userId){
    var formData = new FormData()
    formData.append('userId',userId)
    var token = $('#_csrf').attr('content');
    var headers = new Headers({
        'Accept': '*/*',
        'X-CSRF-TOKEN': token
    });
    fetch('/makeModer', {
        method: 'POST',
        headers,
        body: formData
    })
        .then(
            function(data){
                location.reload();
            }
        );
}
function makeUser(userId){
    var formData = new FormData()
    formData.append('userId',userId)
    var token = $('#_csrf').attr('content');
    var headers = new Headers({
        'Accept': '*/*',
        'X-CSRF-TOKEN': token
    });
    fetch('/makeUser', {
        method: 'POST',
        headers,
        body: formData
    })
        .then(
            function(data){
                location.reload();
            }
        );
}
function removeUser(userid){
    var formData = new FormData()
    var time = prompt('Через сколько минут вернуть в комнаты?(без значения - бессрочно)')
    if(time == undefined || time == null || time < 0)
        return;
    if(time && isNaN(time)){
        time = null;
    }
    formData.append('userId',userid)
    formData.append('minutes',time)
    var token = $('#_csrf').attr('content');
    var headers = new Headers({
        'Accept': '*/*',
        'X-CSRF-TOKEN': token
    });
    fetch('/removeUserInRooms', {
        method: 'POST',
        headers,
        body: formData
    })
        .then(
            function(data){
                location.reload();
            }
        );
}