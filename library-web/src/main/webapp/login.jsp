<%@ page contentType="text/html;charset=UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <title>Login</title>
    </head>

    <body>

        <h2>Login</h2>

        <form action="login" method="post">
            Email:<br>
            <input type="email" name="email" required><br><br>

            Password:<br>
            <input type="password" name="password" required><br><br>

            <button type="submit">Login</button>
        </form>

        <% String error=request.getParameter("error"); if ("invalid".equals(error)) { %>
            <p style="color:red;">Invalid Credentials!</p>
            <% } %>

    </body>

    </html>