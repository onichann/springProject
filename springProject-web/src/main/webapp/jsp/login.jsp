<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: wutong
  Date: 2018/4/2
  Time: 15:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
    <title>login</title>
</head>
<body>
<c:if test="${!empty error}">
    <span style="color: red; ">${error}</span>
</c:if>
<form action="<c:url value="/login/loginCheck2.do"/>" method="post">
    用户名:<input type="text" name="username" >
    <br>
    密码：
    <input type="password" name="password" id="2">
    <br>
    <input type="submit" name="" id="1" value="登录">
</form>

</body>
</html>
