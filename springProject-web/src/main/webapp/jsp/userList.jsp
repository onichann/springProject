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
<h3>欢迎${sessionScope.user.username }登陆 <a href="<%=request.getContextPath()%>/login/loginOut" style="align-content: flex-end">登出</a></h3>

<a href="<%=request.getContextPath()%>/user/toAdd">add</a>
    <table border="1" align="center">
        <tr>
            <td>编号</td>
            <td>登录名</td>
            <td>用户名</td>
            <td>用户密码</td>
            <%--<td>积分</td>--%>
            <%--<td>ip</td>--%>
            <%--<td>最后预览</td>--%>
            <td>操作</td>
        </tr>
        <c:forEach var="u" items="${userList }" step="1">
            <tr>
                <td><c:out value="${u.featid }"></c:out></td>
                <td><c:out value="${u.userid }"></c:out></td>
                <td><c:out value="${u.username }"></c:out></td>
                <td><c:out value="${u.password }"></c:out></td>
                <%--<td><c:out value="${u.credits }"></c:out></td>--%>
                <%--<td><c:out value="${u.lastIp }"></c:out></td>--%>
                <%--<td><fmt:formatDate value="${u.lastVisit }" pattern="yyyy-MM-dd"/></td>--%>
                <td><a href="<%=request.getContextPath()%>/user/toUpdate?id=${u.featid}">修改</a>|<a href="<%=request.getContextPath()%>/user/delete?id=${u.featid}">删除</a></td>
            </tr>
        </c:forEach>

    </table>
<form id="formid" target="frame1" action="<%=request.getContextPath()%>/upload/upload" method="post" enctype="multipart/form-data" >
    <input name="fileId" value="1111" type="hidden"/>
    <input type="file" name="file"  multiple/>
    <input type="submit" value="submit" multiple="multiple">
</form>
<iframe name="frame1" frameborder="0" height="40"></iframe>
<a href="<%=request.getContextPath()%>/upload/download?fileName=53dedd94-160d-4319-aacc-03f852640666.txt">下载</a>
</body>
<script>
    // alert(1);
</script>
</html>
