<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.vikash.lms.model.Book" %>
<%
Book book = (Book)request.getAttribute("book");
%>
<%@ include file="/common/header.jsp" %>

<style>
    .edit-card {
        max-width: 600px;
        margin: 2rem auto;
    }

    .form-group-edit {
        margin-bottom: 1.2rem;
    }
 
    .form-label-edit {
        display: block;
        font-weight: 600;
        color: #333;
        margin-bottom: 0.4rem;
        font-size: 14px;
    }

    .form-control-edit {
        width: 100%;
        padding: 0.7rem;
        border: 1px solid #ddd;
        border-radius: 5px;
        font-size: 14px;
        box-sizing: border-box;
        transition: border-color 0.3s;
    }

    .form-control-edit:focus {
        outline: none;
        border-color: #1f4e8c;
        box-shadow: 0 0 5px rgba(31, 78, 140, 0.2);
    }

    .form-row-edit {
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 1rem;
    }

    .button-group-edit {
        display: flex;
        gap: 0.8rem;
        margin-top: 2rem;
        justify-content: space-between;
    }

    .btn-edit {
        padding: 0.7rem 1.5rem;
        border: none;
        border-radius: 6px;
        font-size: 14px;
        font-weight: 600;
        cursor: pointer;
        transition: all 0.3s;
    }

    .btn-primary-edit {
        background: #0e4c92;
        color: white;
        flex: 1;
    }

    .btn-primary-edit:hover {
        background: #0b3a70;
        box-shadow: 0 4px 8px rgba(11, 58, 112, 0.2);
    }

    .btn-secondary-edit {
        background: #e9ecef;
        color: #333;
        padding: 0.7rem 1.5rem;
    }

    .btn-secondary-edit:hover {
        background: #dee2e6;
    }

    .form-helper-edit {
        font-size: 12px;
        color: #666;
        margin-top: 0.2rem;
    }

    .required {
        color: #d32f2f;
    }

    .book-id-badge {
        display: inline-block;
        background: #e3f2fd;
        color: #1f4e8c;
        padding: 0.3rem 0.8rem;
        border-radius: 4px;
        font-size: 12px;
        margin-bottom: 1rem;
        font-weight: 600;
    }
</style>

<div class="card edit-card">
    <div class="book-id-badge">📖 Book ID: <%=book.getId()%></div>
    <h2 class="page-title">✏️ Edit Book Details</h2>

    <form action="<%=request.getContextPath()%>/updateBook" method="post" onsubmit="return validateForm();">
        <input type="hidden" name="id" value="<%=book.getId()%>">

        <div class="form-group-edit">
            <label class="form-label-edit">Book Title <span class="required">*</span></label>
            <input class="form-control-edit" type="text" name="title" value="<%=book.getTitle()%>" required>
            <div class="form-helper-edit">The title of the book</div>
        </div>

        <div class="form-group-edit">
            <label class="form-label-edit">Author <span class="required">*</span></label>
            <input class="form-control-edit" type="text" name="author" value="<%=book.getAuthor()%>" required>
            <div class="form-helper-edit">The author's name</div>
        </div>

        <div class="form-group-edit">
            <label class="form-label-edit">ISBN</label>
            <input class="form-control-edit" type="text" name="isbn" value="<%=book.getIsbn()%>">
            <div class="form-helper-edit">ISBN or unique identifier</div>
        </div>

        <div class="form-group-edit">
            <label class="form-label-edit">Category</label>
            <input class="form-control-edit" type="text" name="category" value="<%=book.getCategory()%>">
            <div class="form-helper-edit">Book category or genre</div>
        </div>

        <div class="form-row-edit">
            <div class="form-group-edit">
                <label class="form-label-edit">Total Copies <span class="required">*</span></label>
                <input class="form-control-edit" type="number" name="totalCopies" value="<%=book.getTotalCopies()%>" min="1" required>
                <div class="form-helper-edit">Total books in library</div>
            </div>

            <div class="form-group-edit">
                <label class="form-label-edit">Available Copies <span class="required">*</span></label>
                <input class="form-control-edit" type="number" name="availableCopies" value="<%=book.getAvailableCopies()%>" min="0" required>
                <div class="form-helper-edit">Copies available for issue</div>
            </div>
        </div>

        <div class="button-group-edit">
            <button class="btn-edit btn-primary-edit" type="submit">✅ Update Book</button>
            <a href="<%=request.getContextPath()%>/books" class="btn-edit btn-secondary-edit">❌ Cancel</a>
        </div>
    </form>
</div>

<script>
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

<%@ include file="/common/footer.jsp" %>