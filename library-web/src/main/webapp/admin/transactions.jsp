<%@ page import="java.util.*" %>
<%@ page import="com.vikash.lms.model.Transaction" %>

<h2>Transactions</h2>

<table border="1">

<tr>
<th>ID</th>
<th>User ID</th>
<th>Book ID</th>
<th>Issue Date</th>
<th>Due Date</th>
<th>Status</th>
</tr>

<%
List<Transaction> list =
(List<Transaction>)request.getAttribute("transactions");

for(Transaction t : list){
%>

<tr>

<td><%=t.getId()%></td>
<td><%=t.getUserId()%></td>
<td><%=t.getBookId()%></td>
<td><%=t.getIssueDate()%></td>
<td><%=t.getDueDate()%></td>
<td><%=t.getStatus()%></td>

</tr>

<%
}
%>

</table>