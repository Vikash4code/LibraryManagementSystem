<h2>Add Student</h2>

<form action="<%=request.getContextPath()%>/addStudent" method="post">

Name:<br>
<input type="text" name="name" required><br><br>

Email:<br>
<input type="email" name="email" required><br><br>

Password:<br>
<input type="password" name="password" required><br><br>

<button type="submit">Add Student</button>

</form>