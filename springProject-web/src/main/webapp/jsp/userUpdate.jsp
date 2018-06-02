

<%--
  Created by IntelliJ IDEA.
  User: wutong
  Date: 2018/4/18
  Time: 23:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/base/page/include/include.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>update</title>
</head>
<body>
<form action="${ctx}/user/update" method="post">
    <table border="1" align="center" width="50%">
        <tr>
            <td colspan="2" align="center"><b>用户信息</b></td>
        </tr>
        <tr>
            <td>用户编号</td>
            <td><input type="text" name="userId" value=${user.userId } readonly ></td>
        </tr>
        <tr>
            <td>用户名</td>
            <td><input type="text" name="userName" value=${user.userName }></td>
        </tr>
        <tr>
            <td>密码</td>
            <td><input type="text" name="password" value=${user.password }></td>
        </tr>
        <tr>
            <td colspan="2" ><input type="submit" value="修改"></td>
        </tr>
    </table>



</form>
</body>
</html>
