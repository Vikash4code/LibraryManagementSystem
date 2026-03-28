<%@ page contentType="text/html;charset=UTF-8" %>

    <!DOCTYPE html>
    <html>

    <head>
        <title>Add Student - Library Management System</title>

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
            }

            .nav-container {
                display: flex;
                gap: 0.5rem;
                flex-wrap: wrap;
                align-items: center;
            } 

            .nav-btn {
                color: white;
                padding: 0.4rem 0.8rem;
                border: 1px solid #5fa3ff;
                border-radius: 6px;
                text-decoration: none;
                font-size: 14px;
                cursor: pointer;
            }

            .nav-btn:hover {
                background: #2d6cdf;
            }

            /* CARD */
            .card {
                background: white;
                margin: 2rem auto;
                padding: 2rem;
                border-radius: 10px;
                box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
                max-width: 500px;
            }

            .page-title {
                margin: 0 0 1rem 0;
                color: #1f4e8c;
                font-size: 28px;
            }

            .form-group {
                margin-bottom: 1.2rem;
            }

            .form-label {
                display: block;
                font-weight: 600;
                color: #333;
                margin-bottom: 0.4rem;
                font-size: 14px;
            }

            .form-control {
                width: 100%;
                padding: 0.7rem;
                border: 1px solid #ddd;
                border-radius: 5px;
                font-size: 14px;
                box-sizing: border-box;
                transition: border-color 0.3s;
            }

            .form-control:focus {
                outline: none;
                border-color: #1f4e8c;
                box-shadow: 0 0 5px rgba(31, 78, 140, 0.2);
            }

            .button-group {
                display: flex;
                gap: 0.8rem;
                margin-top: 2rem;
                justify-content: space-between;
            }

            .btn {
                padding: 0.7rem 1.5rem;
                border: none;
                border-radius: 6px;
                font-size: 14px;
                font-weight: 600;
                cursor: pointer;
                transition: all 0.3s;
            }

            .btn-primary {
                background: #0e4c92;
                color: white;
                flex: 1;
            }

            .btn-primary:hover {
                background: #0b3a70;
                box-shadow: 0 4px 8px rgba(11, 58, 112, 0.2);
            }

            .btn-secondary {
                background: #e9ecef;
                color: #333;
                padding: 0.7rem 1.5rem;
            }

            .btn-secondary:hover {
                background: #dee2e6;
            }

            .form-helper {
                font-size: 12px;
                color: #666;
                margin-top: 0.2rem;
            }

            .required {
                color: #d32f2f;
            }

            footer {
                text-align: center;
                padding: 1.5rem;
                background: #f0f0f0;
                margin-top: 2rem;
                font-size: 12px;
                color: #666;
            }

            .info-box {
                background: #e3f2fd;
                border-left: 4px solid #1f4e8c;
                padding: 1rem;
                border-radius: 4px;
                margin-bottom: 1rem;
                font-size: 13px;
                color: #0d47a1;
            }
        </style>
    </head>

    <body>

        <nav>
            <div class="nav-container">
                <a href="<%= request.getContextPath() %>/adminDashboard" class="nav-btn">📊 Dashboard</a>
                <a href="<%= request.getContextPath() %>/books" class="nav-btn">📚 Books</a>
                <a href="<%= request.getContextPath() %>/students" class="nav-btn">👥 Students</a>
                <a href="<%= request.getContextPath() %>/transactions" class="nav-btn">📋 Transactions</a>
                <a href="<%= request.getContextPath() %>/logout" class="nav-btn">🚪 Logout</a>
            </div>
        </nav>

        <div class="card">
            <h2 class="page-title">👤 Add New Student</h2>

            <div class="info-box">
                ℹ️ Create a new student account. Students can log in to view and issue books.
            </div>

            <form action="<%=request.getContextPath()%>/addStudent" method="post" onsubmit="return validateForm();">

                <div class="form-group">
                    <label class="form-label">Student Name <span class="required">*</span></label>
                    <input type="text" name="name" class="form-control" placeholder="Enter full name" required>
                    <div class="form-helper">Full name of the student</div>
                </div>

                <div class="form-group">
                    <label class="form-label">Email Address <span class="required">*</span></label>
                    <input type="email" name="email" class="form-control" placeholder="Enter email address" required>
                    <div class="form-helper">Valid email address (e.g., student@example.com)</div>
                </div>

                <div class="form-group">
                    <label class="form-label">Password <span class="required">*</span></label>
                    <input type="password" name="password" class="form-control" placeholder="Enter password" id="password" required>
                    <div class="form-helper">Minimum 6 characters recommended</div>
                </div>

                <div class="form-group">
                    <label class="form-label">Confirm Password <span class="required">*</span></label>
                    <input type="password" name="confirmPassword" class="form-control" placeholder="Re-enter password" id="confirmPassword" required>
                    <div class="form-helper">Must match the password above</div>
                </div>

                <div class="button-group">
                    <button type="submit" class="btn btn-primary">✅ Add Student</button>
                    <a href="<%= request.getContextPath() %>/students" class="btn btn-secondary">❌ Cancel</a>
                </div>

            </form>
        </div>

        <footer>
            <p>&copy; 2024 Library Management System. All rights reserved.</p>
        </footer>

        <script>
            function validateForm() {
                const name = document.querySelector('input[name="name"]').value.trim();
                const email = document.querySelector('input[name="email"]').value.trim();
                const password = document.querySelector('input[name="password"]').value;
                const confirmPassword = document.querySelector('input[name="confirmPassword"]').value;

                if (name.length < 2) {
                    alert('❌ Please enter a valid student name');
                    return false;
                }

                const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                if (!emailRegex.test(email)) {
                    alert('❌ Please enter a valid email address');
                    return false;
                }

                if (password.length < 3) {
                    alert('❌ Password must be at least 3 characters');
                    return false;
                }

                if (password !== confirmPassword) {
                    alert('❌ Passwords do not match');
                    return false;
                }

                return true;
            }

            // Real-time password match check
            document.getElementById('confirmPassword').addEventListener('blur', function() {
                if (this.value && document.getElementById('password').value !== this.value) {
                    this.style.borderColor = '#d32f2f';
                    this.style.backgroundColor = '#ffebee';
                } else {
                    this.style.borderColor = '#ddd';
                    this.style.backgroundColor = 'white';
                }
            });
        </script>

    </body>

    </html>