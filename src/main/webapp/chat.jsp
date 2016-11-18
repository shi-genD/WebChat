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

    <title>Web Chat</title>
    <body>
    <%@ page contentType="text/html;charset=utf-8" %>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
    <script language="javascript" type="text/javascript">
        $(document).ready(function(){
            var ws;
            ws = new WebSocket("ws://localhost:8080/chat");
            ws.onopen = function (event) {
            };
            ws.onmessage = function (event) {
                var msg = JSON.parse(event.data);
                var uname = msg.username;
                var umsg = msg.message;
                var ucolor = msg.color;
                var utime = msg.mtime;

                $('#message_box').append("<div><span style=\"color:"+ucolor+"\">"+utime+" "+uname+": "+umsg+"</span></div>");

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
    <%
        String userName = null;
        Cookie[] cookies = request.getCookies();
        if(cookies !=null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("user")) userName = cookie.getValue();
            }
        }
    %>
    <div align="center">Welcome to chat, <%=userName%></div>
    <div class="chat_wrapper">
        <div class="message_box" id="message_box"></div>
        <div class="panel">
            Write:
            <input type="text" name="message" id="message" maxlength="80" style="width:80%" />
            <button id="send-btn">Send</button>
        </div>
        <form action="/LogoutServlet" method="GET">
            <button id="logout">Exit</button>
        </form>

    </div>
</body>
</html>