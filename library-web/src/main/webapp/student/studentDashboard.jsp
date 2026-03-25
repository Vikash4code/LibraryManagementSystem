<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="/common/header.jsp" %>

        <style>
            body {
                background: #f4f6f9;
                font-family: 'Segoe UI', sans-serif;
            }

            .container {
                max-width: 1000px;
                margin: 30px auto;
                padding: 20px;
            }

            .title {
                font-size: 28px;
                font-weight: 600;
                margin-bottom: 20px;
                color: #333;
            }

            .card {
                background: #fff;
                border-radius: 12px;
                padding: 20px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
                margin-bottom: 20px;
            }

            .btn {
                display: inline-block;
                padding: 10px 18px;
                border-radius: 8px;
                text-decoration: none;
                font-weight: 500;
                margin-right: 10px;
                transition: 0.3s;
            }

            .btn-primary {
                background: #4CAF50;
                color: white;
            }

            .btn-danger {
                background: #e74c3c;
                color: white;
            }

            .btn:hover {
                opacity: 0.9;
            }

            .blog-title {
                font-size: 20px;
                margin-bottom: 10px;
                color: #222;
            }

            .blog-content {
                color: #555;
                line-height: 1.6;
                font-size: 14px;
            }

            .highlight {
                color: #4CAF50;
                font-weight: 500;
            }
        </style>

        <div class="container">

            <!-- Title -->
            <div class="title"> Student Dashboard</div>

            <!-- Welcome Card -->
            <div class="card">
                <h3> Welcome, Student!</h3>
                <p class="blog-content">
                    Welcome to your <span class="highlight">Library Management System</span>.
                    Here you can explore books, track your issued books, and manage your reading journey efficiently.
                </p>

                <a href="<%=request.getContextPath()%>/student/books" class="btn btn-primary">📖 Browse Books</a>
                <a href="<%=request.getContextPath()%>/logout" class="btn btn-danger">🚪 Logout</a>
            </div>

            <!-- Blog Section -->
            <div class="card">
                <div class="blog-title">📘 Why Reading Books is Important?</div>
                <p class="blog-content">
                    Reading books enhances your knowledge, improves concentration, and develops critical thinking
                    skills.
                    In today's fast-paced digital world, reading helps you stay focused and mentally active.
                </p>
            </div>

            <div class="card">
                <div class="blog-title">🚀 Tips to Become a Better Reader</div>
                <p class="blog-content">
                    • Set a daily reading goal (at least 20 minutes)<br>
                    • Choose books based on your interests<br>
                    • Take notes while reading<br>
                    • Avoid distractions and create a reading habit
                </p>
            </div>

            <div class="card">
                <div class="blog-title">💡 Did You Know?</div>
                <p class="blog-content">
                    Students who read regularly tend to perform better academically and develop stronger communication
                    skills.
                    Reading is not just a habit, it's a <span class="highlight">superpower</span>!
                </p>
            </div>

        </div>

        <%@ include file="/common/footer.jsp" %>