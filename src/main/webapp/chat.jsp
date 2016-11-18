<html>
<head>
    <meta charset="UTF-8"/>
    <style type="text/css">

        .chat_wrapper {
            width: 500px;
            margin-right: auto;
            margin-left: auto;
            background: #CCCCCC;
            border: 1px solid #999999;
            padding: 10px;
            font: 12px 'lucida grande',tahoma,verdana,arial,sans-serif;
        }
        .chat_wrapper .message_box {
            background: #FFFFFF;
            height: 150px;
            overflow: auto;
            padding: 10px;
            border: 1px solid #999999;
        }
        .chat_wrapper .panel input{
            padding: 2px 2px 2px 5px;
        }

    </style>
    </head>

    <title>WebSocket Chat</title>
    <body>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
    <script language="javascript" type="text/javascript">
        $(document).ready(function(){
            var ws;
            ws = new WebSocket("ws://localhost:8080/chat");
            ws.onopen = function (event) {
            };
            ws.onmessage = function (event) {
                var msg = JSON.parse(event.data);
                var type = msg.type;
                var username = msg.username;
                var message = msg.message;
                var color = msg.color;

                $('#message_box').append("<div><span style=\"color:"+color+"\">"+username+" : "+message+"</span></div>");
            };
            ws.onclose = function (event) {
            };

            $('#send-btn').click(function() {
                var messageField = document.getElementById("message");
                var message = messageField.value;
                if(message == ""){ //emtpy message?
                    alert("Enter Some message Please!");
                    return;
                }
                ws.send(message);
                messageField.value = '';
            });

        });
    </script>


    <div class="chat_wrapper">
        <div class="message_box" id="message_box"></div>
        <div class="panel">
                <input type="text" name="message" id="message" placeholder="Message" maxlength="80" style="width:80%" />
                <button id="send-btn">Send</button>
        </div>
        <form action="/LogoutServlet" method="GET">
            <button id="logout">Exit</button>
        </form>

    </div>
</body>
</html>