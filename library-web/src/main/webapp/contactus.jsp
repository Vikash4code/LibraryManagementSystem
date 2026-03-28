<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/header.jsp" %>

<div style="display:flex; justify-content:center; align-items:center; min-height:70vh;">
    
    <div class="card" style="width:100%; max-width:500px;">
        
        <h2 class="page-title" style="text-align:center;">Contact Us</h2>

        <p style="text-align:center;">
            Have questions or issues? Reach out to us.
        </p>

        <form method="post" action="<%= request.getContextPath() %>/contactus.jsp">
            
            <div class="form-grid">

                <div>
                    <label>Name</label>
                    <input type="text" name="name" class="form-control" required>
                </div>

                <div>
                    <label>Email</label>
                    <input type="email" name="email" class="form-control" required>
                </div>

                <div>
                    <label>Subject</label>
                    <input type="text" name="subject" class="form-control" required>
                </div>

                <div>
                    <label>Message</label>
                    <textarea name="message" class="form-control" rows="4" required></textarea>
                </div>

                <div class="form-controls">
                    <button type="submit" class="primary" style="width:100%;">
                        Send Message
                    </button>
                </div>

            </div>

        </form>

        <!-- SUCCESS / ERROR -->
        <% String msg = request.getParameter("msg"); 
           if ("sent".equals(msg)) { %>
            <div class="alert" style="background:#e6f4ea;color:#1f5d2d;">
                Message sent successfully!
            </div>
        <% } else if ("error".equals(msg)) { %>
            <div class="alert error">
                Failed to send message. Try again.
            </div>
        <% } else if ("".equals(msg) || msg == null) { %>
            <div style="margin-top:1rem;color:#444;">
                Use this form to send a message; as a guest, you'll see this confirmation locally (no email backend required).
            </div>
        <% } %>

    </div>
</div>

<%@ include file="/common/footer.jsp" %> 