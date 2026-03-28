
    <!DOCTYPE html>
    <html>

    <head>
        <title>Library Management System</title>

        <style>
            body {
                margin: 0;
                font-family: Arial, sans-serif;
                background: #f4f6fa;
            }

            /* NAVBAR */
            nav {
                background: #1f4e8c;
                padding: 0.7rem 1rem;
                position: sticky;
                top: 0;
                z-index: 999;
            }

            .nav-container {
                display: flex;
                gap: 0.5rem;
                flex-wrap: wrap;
                align-items: center;
            }

            .nav-btn {
                color: white;
                padding: 0.5rem 1rem;
                border: 1px solid #5fa3ff;
                border-radius: 6px;
                text-decoration: none;
                font-size: 14px;
                font-weight: 600;
                transition: .2s;
            }

            .nav-btn:hover {
                background: #2d6cdf;
                box-shadow: 0 2px 8px rgba(0,0,0,0.15);
            }

            .nav-btn:hover {
                background: #2d6cdf;
            }

            /* COMMON UI */
            .card {
                background: white;
                margin: 1rem;
                padding: 1rem;
                border-radius: 10px;
                box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
            }

            .page-title {
                margin-top: 0;
            }

            .primary {
                background: #0e4c92;
                color: white;
                border: none;
                padding: 0.5rem 1rem;
                border-radius: 6px;
                cursor: pointer;
            }

            .primary:hover {
                background: #0b3a70;
            }

            .form-control {
                width: 100%;
                padding: 0.4rem;
                margin-top: 0.2rem;
                border: 1px solid #ccc;
                border-radius: 5px;
            }

            .form-grid {
                display: grid;
                gap: 0.8rem;
            }

            .form-controls {
                margin-top: 0.5rem;
            }

            .alert {
                margin-top: 1rem;
                padding: 0.5rem;
                border-radius: 5px;
            }

            .alert.error {
                background: #ffe5e5;
                color: #a33;
            }
        </style>
    </head>

    <body>

        <% String role=(String) session.getAttribute("role"); %>

            <!-- NAVBAR -->
            <nav>
                <div class="nav-container">

                    <% if (role == null) { %>

                        <!-- PUBLIC NAVBAR: Guest LMS -->
                        <a href="<%= request.getContextPath() %>/books" class="nav-btn">Browse Books</a>
                        <a href="<%= request.getContextPath() %>/help.jsp" class="nav-btn">Help</a>
                        <a href="<%= request.getContextPath() %>/contactus.jsp" class="nav-btn">Contact</a>
                        <a href="<%= request.getContextPath() %>/login.jsp" class="nav-btn">Login</a>
                        <a href="<%= request.getContextPath() %>/register.jsp" class="nav-btn">Register</a>

                    <% } else if ("ADMIN".equals(role)) { %>

                        <!-- ADMIN NAVBAR -->
                        <a href="<%= request.getContextPath() %>/books" class="nav-btn">Browse Books</a>
                        <a href="<%= request.getContextPath() %>/admin/adminDashboard.jsp" class="nav-btn">Dashboard</a>
                        <a href="<%= request.getContextPath() %>/logout" class="nav-btn">Logout</a>

                    <% } else if ("STUDENT".equals(role)) { %>

                        <!-- STUDENT NAVBAR -->
                        <a href="<%= request.getContextPath() %>/books" class="nav-btn">Browse Books</a>
                        <a href="<%= request.getContextPath() %>/help.jsp" class="nav-btn">Help</a>
                        <a href="<%= request.getContextPath() %>/contactus.jsp" class="nav-btn">Contact</a>
                        <a href="<%= request.getContextPath() %>/student/studentDashboard.jsp" class="nav-btn">Dashboard</a>
                        <a href="<%= request.getContextPath() %>/logout" class="nav-btn">Logout</a>

                    <% } %>

                </div>
            </nav> 