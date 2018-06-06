<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: wutong
  Date: 2018/4/2
  Time: 15:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/base/page/include/include.jsp" %>
<html>
<head>
    <title>login</title>
</head>
<body>
<c:if test="${!empty error}">
    <span style="color: red; ">${error}</span>
</c:if>
<form action="<c:url value="/login/loginCheck2"/>" method="post">
    用户名:<input type="text" name="username">
    <br>
    密码：
    <input type="password" name="password" id="2">
    <br>
    <input type="submit" name="" id="1" value="登录">
</form>

<input type="button" value="点击" onclick="aaa()"/>
<script>
   function aaa(){
       var obj={"name":"姓名","ids":['1','2']}
       $.ajax({
           url: "${ctx}/test/checkParamters.do",
           type: 'POST',
           contentType:"application/json",
           data:JSON.stringify(obj),
           cache: false,
           async: true,
           dataType: 'json',
           success: function (data) {
              alert(JSON.stringify(data))
           },
           error: function (msg) {
               alert("调用失败");
           }
       });

   }

</script>
</body>
</html>
