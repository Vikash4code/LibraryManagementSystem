<%@ page contentType="text/html;charset=UTF-8" %>

    <!DOCTYPE html>
    <html>

    <head>
        <title>Add Book - Library Management System</title>

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
                max-width: 600px;
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

            .form-row {
                display: grid;
                grid-template-columns: 1fr 1fr;
                gap: 1rem;
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
        </style>
    </head>

    <body>

        <%@ include file="/common/header.jsp" %>
 
        <div class="card">
            <h2 class="page-title">➕ Add New Book</h2>

            <form action="<%= request.getContextPath() %>/addBook" method="post" onsubmit="return validateForm();">

                <div class="form-group">
                    <label class="form-label">Book Title <span class="required">*</span></label>
                    <input type="text" name="title" class="form-control" placeholder="Enter book title" required>
                    <div class="form-helper">Required field - Enter the complete title of the book</div>
                </div>

                <div class="form-group">
                    <label class="form-label">Author <span class="required">*</span></label>
                    <input type="text" name="author" class="form-control" placeholder="Enter author name" required>
                    <div class="form-helper">Required field - Enter the author's name</div>
                </div>

                <div class="form-group">
                    <label class="form-label">ISBN</label>
                    <input type="text" name="isbn" class="form-control" placeholder="e.g., 978-0-123456-78-9">
                    <div class="form-helper">Optional - ISBN or other unique identifier</div>
                </div>

                <div class="form-group">
                    <label class="form-label">Category</label>
                    <input type="text" name="category" class="form-control" placeholder="e.g., Fiction, Science, History">
                    <div class="form-helper">Optional - Book category or genre</div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label class="form-label">Total Copies <span class="required">*</span></label>
                        <input type="number" name="totalCopies" class="form-control" placeholder="0" min="1" required>
                        <div class="form-helper">Total books available in library</div>
                    </div>

                    <div class="form-group">
                        <label class="form-label">Available Copies <span class="required">*</span></label>
                        <input type="number" name="availableCopies" class="form-control" placeholder="0" min="0" required>
                        <div class="form-helper">Initially available for issue</div>
                    </div>
                </div>

                <div class="button-group">
                    <button type="submit" class="btn btn-primary">✅ Add Book</button>
                    <button type="button" class="btn btn-secondary" onclick="clearAddBookForm();">❌ Cancel</button>
                </div>

            </form>
        </div>

        <footer>
            <p>&copy; 2024 Library Management System. All rights reserved.</p>
        </footer>

        <script>
            function clearAddBookForm() {
                const form = document.querySelector('form');
                form.reset();

                // In case we want to keep auto defaults
                form.querySelector('input[name="totalCopies"]').value = '';
                form.querySelector('input[name="availableCopies"]').value = '';
            }

            function validateForm() {
                const totalCopies = parseInt(document.querySelector('input[name="totalCopies"]').value);
                const availableCopies = parseInt(document.querySelector('input[name="availableCopies"]').value);

                if (availableCopies > totalCopies) {
                    alert('❌ Available Copies cannot exceed Total Copies!');
                    return false;
                }

                if (totalCopies <= 0) {
                    alert('❌ Total Copies must be at least 1');
                    return false;
                }

                if (availableCopies < 0) {
                    alert('❌ Available Copies cannot be negative');
                    return false;
                }

                return true;
            }
        </script>

    </body>

    </html>