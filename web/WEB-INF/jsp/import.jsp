<%--
  Created by IntelliJ IDEA.
  User: xujingling
  Date: 2020/11/16
  Time: 21:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/import" enctype="multipart/form-data" method="post">
    <input type="file" name="excel" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel" value="选取文件" >
    <input type="submit" value="提交">
</form>
</body>
</html>
