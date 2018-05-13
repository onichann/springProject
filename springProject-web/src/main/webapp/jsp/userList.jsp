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
    <title>userList</title>
</head>
<body>
<h3>欢迎${userList[0].userName }登陆</h3> <a href="<%=request.getContextPath()%>/user/toAdd.do">add</a>
    <table border="1" align="center">
        <tr>
            <td>编号</td>
            <td>用户名</td>
            <td>用户密码</td>
            <td>积分</td>
            <td>ip</td>
            <td>最后预览</td>
            <td>操作</td>
        </tr>
        <c:forEach var="u" items="${userList }" step="1">
            <tr>
                <td><c:out value="${u.userId }"></c:out></td>
                <td><c:out value="${u.userName }"></c:out></td>
                <td><c:out value="${u.password }"></c:out></td>
                <td><c:out value="${u.credits }"></c:out></td>
                <td><c:out value="${u.lastIp }"></c:out></td>
                <td><fmt:formatDate value="${u.lastVisit }" pattern="yyyy-MM-dd"/></td>
                <td><a href="<%=request.getContextPath()%>/user/toUpdate.do?id=${u.userId}">修改</a>|<a href="<%=request.getContextPath()%>/user/delete.do?id=${u.userId}">删除</a></td>
            </tr>
        </c:forEach>

    </table>
<form id="formid" action="<%=request.getContextPath()%>/user/upload.do" method="post" enctype="multipart/form-data">
    <input name="fileId" value="1111" type="hidden"/>
    <input type="file" name="file" />
    <input type="submit" value="submit" multiple="multiple">
</form>
</body>
</html>
