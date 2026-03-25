<%@ page import="java.util.*" %>
<%@ page import="com.vikash.lms.model.User" %>
<%@ include file="/common/header.jsp" %>

<%
    List<User> students = (List<User>) request.getAttribute("students");
%>

<div class="card">

    <h2 class="page-title">Students List</h2>

    <% if (students == null || students.isEmpty()) { %>

        <!-- EMPTY STATE -->
        <div style="text-align:center; padding:1rem; color:#666;">
            No students found in the system.
        </div>

    <% } else { %>

        <!-- TABLE -->
        <div style="overflow-x:auto;">
            <table style="width:100%; border-collapse:collapse;">

                <thead style="background:#f0f4fa;">
                    <tr>
                        <th style="padding:0.6rem; border:1px solid #ddd;">#</th>
                        <th style="padding:0.6rem; border:1px solid #ddd;">ID</th>
                        <th style="padding:0.6rem; border:1px solid #ddd;">Name</th>
                        <th style="padding:0.6rem; border:1px solid #ddd;">Email</th>
                    </tr>
                </thead>

                <tbody>
                    <%
                        int count = 1;
                        for (User s : students) {
                    %>
                        <tr style="text-align:center;">
                            <td style="padding:0.5rem; border:1px solid #ddd;">
                                <%= count++ %>
                            </td>
                            <td style="padding:0.5rem; border:1px solid #ddd;">
                                <%= s.getId() %>
                            </td>
                            <td style="padding:0.5rem; border:1px solid #ddd;">
                                <%= s.getName() %>
                            </td>
                            <td style="padding:0.5rem; border:1px solid #ddd;">
                                <%= s.getEmail() %>
                            </td>
                        </tr>
                    <%
                        }
                    %>
                </tbody>

            </table>
        </div>

    <% } %>

</div>

<%@ include file="/common/footer.jsp" %>