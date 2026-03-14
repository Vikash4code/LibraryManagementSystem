<%@ page import="java.util.*" %>
<%@ page import="com.vikash.lms.model.User" %>

<h2>Students</h2>

<table border="1">

<tr>
<th>ID</th>
<th>Name</th>
<th>Email</th>
</tr>

<%
List<User> students=(List<User>)request.getAttribute("students");

for(User s:students){
%>

<tr>
<td><%=s.getId()%></td>
<td><%=s.getName()%></td>
<td><%=s.getEmail()%></td>
</tr>

<%
}
%>

</table>