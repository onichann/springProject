<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--
  Created by IntelliJ IDEA.
  User: wutong
  Date: 2018/4/18
  Time: 23:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>add</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/user/add.do" method="post">
    <table border="1" align="center" width="50%">
        <tr>
            <td colspan="2" align="center"><b>用户信息</b></td>
        </tr>
        <tr>
            <td>用户名</td>
            <td><input type="text" name="userName"></td>
        </tr>
        <tr>
            <td>密码</td>
            <td><input type="text" name="userPwd"></td>
        </tr>
        <tr>
            <td colspan="2" ><input type="submit" value="添加"></td>
        </tr>
    </table>



</form>
<a href="javascript:history.go(-1)">返回</a>
</body>
</html>
