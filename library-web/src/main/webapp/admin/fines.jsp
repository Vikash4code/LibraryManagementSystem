<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ page import="java.util.List" %>
        <%@ page import="com.vikash.lms.model.Fine" %>
            <%@ include file="/common/header.jsp" %>

                <style>
                    .fine-status-paid {
                        color: green;
                        font-weight: bold;
                    }

                    .fine-status-pending {
                        color: red;
                        font-weight: bold;
                    }
                </style>

                <div class="card">
                    <h2 class="page-title">Library Fines Management</h2>

                    <% String message=request.getParameter("message"); String error=request.getParameter("error"); if
                        (message !=null) { %>
                        <div style="color: green; margin-bottom: 10px;">
                            <%= message %>
                        </div>
                        <% } else if (error !=null) { %>
                            <div style="color: red; margin-bottom: 10px;">
                                <%= error %>
                            </div>
                            <% } %>

                                <table border="1" style="width: 100%; border-collapse: collapse;">
                                    <thead>
                                        <tr style="background-color: #f2f2f2;">
                                            <th style="padding: 8px;">Fine ID</th>
                                            <th style="padding: 8px;">Transaction ID</th>
                                            <th style="padding: 8px;">User ID</th>
                                            <th style="padding: 8px;">Book ID</th>
                                            <th style="padding: 8px;">Days Overdue</th>
                                            <th style="padding: 8px;">Amount (₹)</th>
                                            <th style="padding: 8px;">Fine Date</th>
                                            <th style="padding: 8px;">Status</th>
                                            <th style="padding: 8px;">Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <% List<Fine> fines = (List<Fine>) request.getAttribute("fines");
                                                if (fines != null && !fines.isEmpty()) {
                                                for (Fine fine : fines) {
                                                String statusClass = "PAID".equals(fine.getStatus()) ?
                                                "fine-status-paid" : "fine-status-pending";
                                                %>
                                                <tr>
                                                    <td style="padding: 8px; text-align: center;">
                                                        <%= fine.getId() %>
                                                    </td>
                                                    <td style="padding: 8px; text-align: center;">
                                                        <%= fine.getTransactionId() %>
                                                    </td>
                                                    <td style="padding: 8px; text-align: center;">
                                                        <%= fine.getUserId() %>
                                                    </td>
                                                    <td style="padding: 8px; text-align: center;">
                                                        <%= fine.getBookId() %>
                                                    </td>
                                                    <td style="padding: 8px; text-align: center;">
                                                         <%= fine.getDaysOverdue() %>
                                                    </td>
                                                    <td style="padding: 8px; text-align: center;">₹<%= fine.getAmount()
                                                            %>
                                                    </td>
                                                    <td style="padding: 8px; text-align: center;">
                                                        <%= fine.getFineDate() %>
                                                    </td>
                                                    <td style="padding: 8px; text-align: center;">
                                                        <span class="<%= statusClass %>">
                                                            <%= fine.getStatus() %>
                                                        </span>
                                                    </td>
                                                    <td style="padding: 8px; text-align: center;">
                                                        <% if ("PENDING".equals(fine.getStatus())) { %>
                                                            <form action="<%= request.getContextPath() %>/payFine"
                                                                method="post" style="display: inline;">
                                                                <input type="hidden" name="fineId"
                                                                    value="<%= fine.getId() %>">
                                                                <button type="submit"
                                                                    style="background: #4CAF50; color: white; border: none; padding: 5px 10px; border-radius: 3px; cursor: pointer;">Mark
                                                                    as Paid</button>
                                                            </form>
                                                            <% } else { %>
                                                                <span style="color: green;">✓ Paid</span>
                                                                <% } %>
                                                    </td>
                                                </tr>
                                                <% } %>
                                                    <% } else { %>
                                                        <tr>
                                                            <td colspan="9"
                                                                style="padding: 20px; text-align: center; color: #666;">
                                                                No fines found. All books are returned on time! 🎉
                                                            </td>
                                                        </tr>
                                                        <% } %>
                                    </tbody>
                                </table>

                                <div style="margin-top: 20px;">
                                    <a href="<%= request.getContextPath() %>/admin/adminDashboard.jsp" class="primary"
                                        style="text-decoration: none;">← Back to Dashboard</a>
                                </div>
                </div>

                <%@ include file="/common/footer.jsp" %>