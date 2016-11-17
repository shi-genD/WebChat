<html>
<head>
    <meta charset="UTF-8"/>
    <title>err page</title>

</head>
<body>
<%
    //allow access only if session exists
    String userName = null;
    Cookie[] cookies = request.getCookies();
    if(cookies !=null){
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("user")) userName = cookie.getValue();
        }
    }
%>
<h3><%=userName %>, this Session is already used.</h3>
</body>
</html>