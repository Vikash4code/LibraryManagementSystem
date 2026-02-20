<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Add Book</title>
</head>
<body>

<h2>Add New Book</h2>

<form action="addBook" method="post">
    <label>Book ID:</label><br>
    <input type="text" name="id" required><br><br>

    <label>Title:</label><br>
    <input type="text" name="title" required><br><br>

    <label>Author:</label><br>
    <input type="text" name="author" required><br><br>

    <button type="submit">Add Book</button>
</form>

</body>
</html>
