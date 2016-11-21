<html>
<head>
    <meta charset="UTF-8"/>
    <title>err page</title>

</head>
<body>
<%@ page contentType="text/html;charset=utf-8" %>
<%
    String userName = null;
    Cookie[] cookies = request.getCookies();
    if(cookies !=null){
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("user")) userName = cookie.getValue();
        }
    }
%>
<h3>Эта сессия уже используется пользователм <%=userName %>.</h3>
</body>
</html>