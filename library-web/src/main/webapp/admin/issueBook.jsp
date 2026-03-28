<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.vikash.lms.model.User" %>
<%@ page import="com.vikash.lms.model.Book" %>
<%@ include file="/common/header.jsp" %>

<style>
    body {
        background: #f4f6f9;
        font-family: 'Segoe UI', sans-serif;
    }

    .card {
        max-width: 700px;
        margin: 40px auto;
        background: #fff;
        padding: 25px;
        border-radius: 12px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
    }

    .page-title {
        font-size: 24px;
        font-weight: 600;
        margin-bottom: 20px;
        color: #333;
        text-align: center;
    }

    .form-group {
        margin-bottom: 18px;
        position: relative;
    }

    label {
        display: block;
        margin-bottom: 6px;
        font-weight: 500;
        color: #444;
    }

    .search-input {
        width: 100%;
        padding: 10px;
        border-radius: 8px;
        border: 1px solid #ccc;
        font-size: 14px;
        box-sizing: border-box;
        transition: 0.2s;
    }

    .search-input:focus {
        border-color: #4CAF50;
        outline: none;
        box-shadow: 0 0 5px rgba(76, 175, 80, 0.3);
    }

    .search-results {
        position: absolute;
        top: 100%;
        left: 0;
        right: 0;
        background: white;
        border: 1px solid #ddd;
        border-top: none;
        border-radius: 0 0 8px 8px;
        max-height: 300px;
        overflow-y: auto;
        display: none;
        z-index: 100;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    .search-results.show {
        display: block;
    }

    .result-item {
        padding: 12px 14px;
        border-bottom: 1px solid #f0f0f0;
        cursor: pointer;
        transition: 0.2s;
    }

    .result-item:hover {
        background: #f9f9f9;
        padding-left: 18px;
    }

    .result-item-title {
        font-weight: 600;
        color: #1f4e8c;
        font-size: 15px;
    }

    .result-item-subtitle {
        font-size: 12px;
        color: #666;
        margin-top: 4px;
    }

    .selected-item {
        background: #e8f5e9;
        padding: 12px;
        border-radius: 5px;
        margin-top: 10px;
        display: none;
        border-left: 4px solid #4CAF50;
    }

    .selected-item.show {
        display: block;
    }

    .selected-item-badge {
        display: inline-block;
        background: #4CAF50;
        color: white;
        padding: 6px 12px;
        border-radius: 4px;
        font-size: 14px;
        font-weight: 500;
    }

    .btn {
        width: 100%;
        padding: 12px;
        background: #4CAF50;
        color: white;
        border: none;
        border-radius: 8px;
        font-size: 16px;
        font-weight: 500;
        cursor: pointer;
        transition: 0.3s;
        margin-top: 20px;
    }

    .btn:hover {
        background: #45a049;
    }

    .btn:disabled {
        background: #ccc;
        cursor: not-allowed;
    }

    .error-msg {
        color: red;
        margin-bottom: 15px;
        padding: 10px;
        background: #ffebee;
        border-radius: 5px;
        border-left: 4px solid #f44336;
    }
</style>

<div class="card">
    <h2 class="page-title">📚 Issue Book - Search</h2>

    <% String error = request.getParameter("error");
       if (error != null) { %>
        <div class="error-msg"><%= error %></div>
    <% } %> 

    <form action="<%=request.getContextPath()%>/issueBook" method="post" id="issueForm">
        <!-- Student Search -->
        <div class="form-group">
            <label for="studentSearch">🔍 Search & Select Student (by name)</label>
            <input 
                type="text" 
                id="studentSearch"
                class="search-input" 
                placeholder="Type student name to search..."
                autocomplete="off"
            >
            <div class="search-results" id="studentResults"></div>
            <div class="selected-item" id="selectedStudent">
                ✅ Student: <span class="selected-item-badge" id="studentName"></span>
                <button type="button" onclick="clearStudent()" style="margin-left: 10px; padding: 3px 8px; background: #f44336; color: white; border: none; border-radius: 3px; cursor: pointer; font-size: 12px;">Clear</button>
            </div>
            <input type="hidden" id="userId" name="userId" required>
        </div>

        <!-- Book Search -->
        <div class="form-group">
            <label for="bookSearch">🔍 Search & Select Book (by title, ISBN, or author)</label>
            <input 
                type="text" 
                id="bookSearch"
                class="search-input" 
                placeholder="Type book title, ISBN, or author to search..."
                autocomplete="off"
            >
            <div class="search-results" id="bookResults"></div>
            <div class="selected-item" id="selectedBook">
                ✅ Book: <span class="selected-item-badge" id="bookName"></span>
                <button type="button" onclick="clearBook()" style="margin-left: 10px; padding: 3px 8px; background: #f44336; color: white; border: none; border-radius: 3px; cursor: pointer; font-size: 12px;">Clear</button>
            </div>
            <input type="hidden" id="bookId" name="bookId" required>
        </div>

        <!-- Submit -->
        <button class="btn" type="submit" id="issueBtn">📖 Issue Book</button>
    </form>
</div>

<%@ include file="/common/footer.jsp" %>

<script>
    let selectedStudentId = null;
    let selectedBookId = null;

    function fetchSearch(type, query, callback) {
        fetch('<%=request.getContextPath()%>/searchData?type=' + type + '&query=' + encodeURIComponent(query))
            .then(response => response.json())
            .then(data => callback(data))
            .catch(error => {
                console.error('Error:', error);
            });
    }

    function attachSearch(inputId, type, displayCallback, resultsId) {
        const input = document.getElementById(inputId);

        input.addEventListener('input', function() {
            const query = this.value.trim();
            fetchSearch(type, query, displayCallback);
        });

        input.addEventListener('focus', function() {
            const query = this.value.trim();
            fetchSearch(type, query, displayCallback);
        });

        input.addEventListener('keydown', function(event) {
            if (event.key === 'Escape') {
                document.getElementById(resultsId).classList.remove('show');
            }
        });
    }

    attachSearch('studentSearch', 'student', displayStudentResults, 'studentResults');
    attachSearch('bookSearch', 'book', displayBookResults, 'bookResults');

    function displayStudentResults(students) {
        const resultsDiv = document.getElementById('studentResults');
        resultsDiv.innerHTML = '';

        if (students.length === 0) {
            resultsDiv.innerHTML = '<div style="padding: 10px; color: #999;">❌ No students found</div>';
        } else {
            students.forEach(student => {
                const item = document.createElement('div');
                item.className = 'result-item';
                item.innerHTML = `<div class="result-item-title">👤 ${student.name}</div>`;
                item.onclick = () => selectStudent(student.id, student.name);
                resultsDiv.appendChild(item);
            });
        }

        resultsDiv.classList.add('show');
    }

    function displayBookResults(books) {
        const resultsDiv = document.getElementById('bookResults');
        resultsDiv.innerHTML = '';

        if (books.length === 0) {
            resultsDiv.innerHTML = '<div style="padding: 10px; color: #999;">❌ No books found</div>';
        } else {
            books.forEach(book => {
                const authorText = book.author && book.author.trim() !== '' ? book.author : 'Unknown Author';
                const isbnText = book.isbn && book.isbn.trim() !== '' ? book.isbn : 'No ISBN';
                const availableText = book.availableCopies ? `${book.availableCopies} copies` : '0 copies';
                
                const item = document.createElement('div');
                item.className = 'result-item';
                item.innerHTML = `
                    <div class="result-item-title">📖 ${book.title}</div>
                    <div class="result-item-subtitle">✍️ ${authorText} | 🔢 ${isbnText} | ✅ ${availableText}</div>
                `;
                item.onclick = () => selectBook(book.id, book.title, book.author);
                resultsDiv.appendChild(item);
            });
        }

        resultsDiv.classList.add('show');
    }

    function selectStudent(id, name) {
        selectedStudentId = id;
        document.getElementById('userId').value = id;
        document.getElementById('studentSearch').value = `👤 ${name}`;
        document.getElementById('studentName').textContent = `👤 ${name}`;
        document.getElementById('selectedStudent').classList.add('show');
        document.getElementById('studentResults').classList.remove('show');
    }

    function selectBook(id, title, author) {
        selectedBookId = id;
        document.getElementById('bookId').value = id;
        document.getElementById('bookSearch').value = `📖 ${title}`;
        document.getElementById('bookName').textContent = `📖 ${title}`;
        document.getElementById('selectedBook').classList.add('show');
        document.getElementById('bookResults').classList.remove('show');
    }

    function clearStudent() {
        selectedStudentId = null;
        document.getElementById('userId').value = '';
        document.getElementById('studentSearch').value = '';
        document.getElementById('selectedStudent').classList.remove('show');
    }

    function clearBook() {
        selectedBookId = null;
        document.getElementById('bookId').value = '';
        document.getElementById('bookSearch').value = '';
        document.getElementById('selectedBook').classList.remove('show');
    }

    // Hide results when clicking outside
    document.addEventListener('click', function(e) {
        if (!e.target.closest('.form-group')) {
            document.getElementById('studentResults').classList.remove('show');
            document.getElementById('bookResults').classList.remove('show');
        }
    });

    // Prevent form submission if nothing selected
    document.getElementById('issueForm').addEventListener('submit', function(e) {
        if (!selectedStudentId || !selectedBookId) {
            e.preventDefault();
            alert('Please select both a student and a book');
        }
    });
</script>