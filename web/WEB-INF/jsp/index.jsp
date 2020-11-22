<%--
  Created by IntelliJ IDEA.
  User: xujingling
  Date: 2020/11/15
  Time: 11:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="pojo.Student" %>
<%@ page import="java.util.List" %>
<%@ page import="pojo.Page" %>
<%
    Page<Student> studentPage = (Page<Student>) request.getAttribute("page");
    List <Student> list = studentPage.getList();
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<input type="button" value="导入文件" onclick="window.location.href = '${pageContext.request.contextPath}/add'"/>
<br>
<input type="button" value="导出文件" onclick="window.location.href = '${pageContext.request.contextPath}/export'"/>
<p>共有<%=studentPage.getTotal()%>条数据</p>
<table cellpadding="2" cellspacing="2" border="2">
    <thead>
    <tr>
        <td>学号</td>
        <td>姓名</td>
        <td>出生日期</td>
        <td>班级</td>
        <td>电话</td>
    </tr>
    </thead>
    <tbody>
    <%
        for (Student student:list){
    %>
    <tr>
        <td><%=student.getStuId()%></td>
        <td><%=student.getStuName()%></td>
        <td><%=student.getStuBirthday()%></td>
        <td><%=student.getStuClass()%></td>
        <td><%=student.getStuPhone()%></td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
<p class="page">
    <%
        int pageNum = studentPage.getPageNum();
        for(int i = 0;i < studentPage.getPages();i++){
            if(pageNum == i + 1){
    %>
    <span><%=pageNum%></span>
    <%
    }
    else{
    %>
    <a href="${pageContext.request.contextPath}/show?pageNum=<%=i + 1%>"><%=i + 1%></a>
    <%
            }
        }
    %>
</p>
<p>共有<%=studentPage.getPages()%>页</p>
</body>
</html>
