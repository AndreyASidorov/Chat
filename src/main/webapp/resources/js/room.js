var el = document.querySelector('.tabs');
var instance = M.Tabs.init(el, {});
    var dropdowns = document.querySelectorAll('.dropdown-button')
    for (var i = 0; i < dropdowns.length; i++){
    M.Dropdown.init(dropdowns[i]);}
    function addUser(userid, roomId){
      var formData = new FormData()
      formData.append('userId',userid)
      formData.append('roomId',roomId)
      var token = $('#_csrf').attr('content');
      var headers = new Headers({
        'Accept': '*/*',
        'X-CSRF-TOKEN': token
      });
        fetch('/addUserToRoom', {
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
    function removeUser(userid, roomId){
      var formData = new FormData()
      var time = prompt('Через сколько минут вернуть в комнату?(без значения - бессрочно)')
      if(time == undefined || time == null || time < 0)
          return;
      if(time && isNaN(time)){
          time = null;
      }
      formData.append('userId',userid)
      formData.append('roomId',roomId)
      formData.append('minutes',time)
      var token = $('#_csrf').attr('content');
      var headers = new Headers({
        'Accept': '*/*',
        'X-CSRF-TOKEN': token
      });
        fetch('/removeUserInRoom', {
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
    function roomRemove(roomId){
      var formData = new FormData()
      formData.append('roomId',roomId)
      var token = $('#_csrf').attr('content');
      var headers = new Headers({
        'Accept': '*/*',
        'X-CSRF-TOKEN': token
      });
        fetch('/roomRemove', {
            method: 'POST',
            headers,
            body: formData
        })
        .then(
          (data)=> {
            window.location.href = "/room";
          }
         );
    }
    function roomRename(roomId){
      var formData = new FormData()
      let newName = prompt('Введите новое имя');
      if(newName == null || newName == undefined || newName == ''){
          return;
      }
      formData.append('newName',newName/*document.getElementById('newName').value*/)
      formData.append('roomId',roomId)
      var token = $('#_csrf').attr('content');
      var headers = new Headers({
        'Accept': '*/*',
        'X-CSRF-TOKEN': token
      });
        fetch('/roomRename', {
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
    function removeMessage(messageId, roomId){
          var formData = new FormData()
          formData.append('messageId',messageId)
          formData.append('roomId',roomId)
          //var obj = {}
          //obj.a1 = 123
          //JSON.stringify(obj)
          var token = $('#_csrf').attr('content');
          var headers = new Headers({
            'Accept': '*/*',
            'X-CSRF-TOKEN': token
          });
            fetch('/removeMessage', {
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

function messageEdit(newText, id){
    var formData = new FormData()
    if(newText == undefined || newText == null)
        return;
    formData.append('roomId',roomId)
    formData.append('newText',newText)
    formData.append('messageId',id)
    var token = $('#_csrf').attr('content');
    var headers = new Headers({
        'Accept': '*/*',
        'X-CSRF-TOKEN': token
    });
    fetch('/messageEdit', {
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

function connect(key) {
    console.log('connect')
    var socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/'+key+'/sendMessage', function (messageSend) {
            if (JSON.parse(messageSend.body).roomId == roomId) {
                let messageIdAndRoomId = JSON.parse(messageSend.body).messageId + ',' + JSON.parse(messageSend.body).roomId
                $("#messageTable").append("<tr id='message" + JSON.parse(messageSend.body).messageId + "'><td>"
                    + JSON.parse(messageSend.body).author
                    + "</td><td>" + JSON.parse(messageSend.body).text
                    + "</td><td>" + JSON.parse(messageSend.body).date
                    + (principalRole == 'admin'
                        ? '<td><button onclick="removeMessage(' + messageIdAndRoomId + ')"' +
                        ' class="right col s1 btn-flat disable" type="submit" name="action">' +
                        ' <i class="material-icons right">backspace</i>' +
                        '</button></td>'
                        : '')
                    + "</td></tr>");
                document.getElementById("message"+ JSON.parse(messageSend.body).messageId).scrollIntoView(true);
            }
        });
        stompClient.subscribe('/topic/'+key+'/removeMessage', function (messageRemove) {
            $("#message"+JSON.parse(messageRemove.body).messageId).remove();
        })


        /*stompClient.subscribe('/topic/myroom', function (greeting) {
            showGreeting('MessageFromMyRoom:'+JSON.parse(greeting.body).content);
        });
        stompClient.subscribe('/topic/123/mymessage', function (greeting) {
            console.log(greeting)
            //showGreeting('MessageFromMyRoom:'+JSON.parse(greeting.body).content);
        });*/
    });
}
function getUserKey() {
    var headers = new Headers({
                'Accept': '*/*'
              });
    fetch('/getUserKey', {
                    method: 'GET',
                    headers
                })
                .then(response => response.json())
                  .then(key => connect(key.key));
}
getUserKey();