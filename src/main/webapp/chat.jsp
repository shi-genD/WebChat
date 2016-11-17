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
            ws = new WebSocket("ws://localhost:8080/WebChat/chat");
            ws.onopen = function (event) {
            };
            ws.onmessage = function (event) {
                $('#message_box').append("<div>" + event.data + "</div>");
            };
            ws.onclose = function (event) {
            };

            $('#send-btn').click(function() {
                var messageField = document.getElementById("message");
                var message = ":" + messageField.value;
                ws.send(message);
                messageField.value = '';
            });

        });
    </script>
    <%
        //allow access only if session exists
        String userName = null;
        String sessionID = null;
        Cookie[] cookies = request.getCookies();
        if(cookies !=null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("user")) userName = cookie.getValue();
                if(cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
            }
        }
    %>
    <h3>Hi <%=userName %>, Login successful. Your Session ID=<%=sessionID %></h3>

    <div class="chat_wrapper">
        <div class="message_box" id="message_box"></div>
        <div class="panel">
                <input type="text" name="message" id="message" placeholder="Message" maxlength="80" style="width:80%" />
                <button id="send-btn">Send</button>
        </div>
        <form action="/WebChat/LogoutServlet" method="GET">
            <button id="logout">Exit</button>
        </form>

    </div>
</body>
</html>