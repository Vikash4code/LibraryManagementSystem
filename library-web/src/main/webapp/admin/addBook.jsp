<%@ page contentType="text/html;charset=UTF-8" %>

    <!DOCTYPE html>
    <html>

    <head>
        <title>Add Book</title>
    </head>

    <body>

        <h2>Add New Book</h2>

        <form action="<%= request.getContextPath() %>/addBook" method="post">

            Title:<br>
            <input type="text" name="title" required><br><br>

            Author:<br>
            <input type="text" name="author" required><br><br>

            ISBN:<br>
            <input type="text" name="isbn"><br><br>

            Category:<br>
            <input type="text" name="category"><br><br>

            Total Copies:<br>
            <input type="number" name="totalCopies" required><br><br>

            Available Copies:<br>
            <input type="number" name="availableCopies" required><br><br>

            <button type="submit">Add Book</button>

        </form>

    </body>

    </html>