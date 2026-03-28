<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/header.jsp" %>

<div style="display:flex; justify-content:center; align-items:center; min-height:70vh;">
    <div class="card" style="width:100%; max-width:420px;">
        <h2 class="page-title" style="text-align:center;">Register</h2>

        <% String error = request.getParameter("error");
           String message = request.getParameter("message"); %>

        <% if ("duplicate".equals(error)) { %>
            <div class="alert error">Email already registered. Please login or use a different email.</div>
        <% } else if ("invalid".equals(error)) { %>
            <div class="alert error">Please provide name, email, and password.</div>
        <% } else if ("registered".equals(message)) { %>
            <div class="alert" style="background:#e6ffea;color:#1c6625;">Registration successful. Please login below.</div>
        <% } %>

        <form action="<%= request.getContextPath() %>/register" method="post" class="form-grid">
            <div>
                <label for="name">Name</label>
                <input class="form-control" type="text" id="name" name="name" placeholder="Enter your name" required>
            </div>

            <div>
                <label for="email">Email</label>
                <input class="form-control" type="email" id="email" name="email" placeholder="Enter your email" required>
            </div>

            <div>
                <label for="password">Password</label>
                <input class="form-control" type="password" id="password" name="password" placeholder="Enter your password" required>
            </div>

            <div class="form-controls" style="margin-top:0.5rem;">
                <button class="primary" type="submit" style="width:100%;">Register</button>
            </div>
        </form>

        <div style="margin-top:1rem; text-align:center; font-size:0.95rem; color:#555;">
            Already have an account? <a href="<%= request.getContextPath() %>/login.jsp" style="color:#0e4c92; text-decoration:none;">Login here</a>
        </div>
    </div>
</div>

<%@ include file="/common/footer.jsp" %>
