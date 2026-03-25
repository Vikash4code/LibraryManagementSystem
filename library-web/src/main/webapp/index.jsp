    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%@ include file="/common/header.jsp" %>

            <!-- HERO SECTION -->
            <section class="card" style="text-align:center;">
                <h1 class="page-title">Library Management System</h1>
                <p style="max-width:600px;margin:auto;">
                    A smart and efficient way to manage books, students, and transactions.
                    Designed for seamless campus library operations.
                </p>

                <div style="margin-top:1.5rem; display:flex; justify-content:center; gap:1rem; flex-wrap:wrap;">
                    <a href="<%= request.getContextPath() %>/login.jsp" class="primary"
                        style="padding:0.6rem 1.2rem; text-decoration:none;">
                        Login
                    </a>
                </div>
            </section>

            <!-- QUICK STATS -->
            <section class="card">
                <h2 class="page-title">Library Overview</h2>

                <div style="display:grid; grid-template-columns:repeat(auto-fit,minmax(180px,1fr)); gap:1rem;">
                    <div style="background:#f4f6fa;padding:1rem;border-radius:8px;text-align:center;">
                        <h3>1200+</h3>
                        <p>Total Books</p>
                    </div>

                    <div style="background:#f4f6fa;padding:1rem;border-radius:8px;text-align:center;">
                        <h3>350+</h3>
                        <p>Registered Students</p>
                    </div>

                    <div style="background:#f4f6fa;padding:1rem;border-radius:8px;text-align:center;">
                        <h3>220+</h3>
                        <p>Books Issued</p>
                    </div>

                    <div style="background:#f4f6fa;padding:1rem;border-radius:8px;text-align:center;">
                        <h3>15</h3>
                        <p>Categories</p>
                    </div>
                </div>
            </section>

            <!-- FEATURES -->
            <section class="card">
                <h2 class="page-title">System Features</h2>

                <div style="display:grid;grid-template-columns:1fr 1fr;gap:1rem;">
                    <div>
                        <ul>
                            <li>📚 Complete Book Management (Add, Update, Delete)</li>
                            <li>👨‍🎓 Student Registration & Tracking</li>
                            <li>🔄 Issue & Return Book System</li>
                            <li>📊 Availability & Stock Control</li>
                        </ul>
                    </div>

                    <div>
                        <ul>
                            <li>🔐 Role-Based Login (Admin / Student)</li>
                            <li>📅 Due Date & Fine Tracking</li>
                            <li>🔎 Search Books by Title / Author / ISBN</li>
                            <li>📈 Transaction History Records</li>
                        </ul>
                    </div>
                </div>
            </section>

            <!-- ANNOUNCEMENTS -->
            <section class="card">
                <h2 class="page-title">Latest Announcements</h2>

                <div style="display:grid;gap:0.8rem;">
                    <div style="border-left:4px solid #007bff;padding:0.5rem 1rem;background:#f9fbff;">
                        📢 New books added in Computer Science & AI section.
                    </div>

                    <div style="border-left:4px solid #28a745;padding:0.5rem 1rem;background:#f9fff9;">
                        📢 Library timing extended during exams (8 AM - 10 PM).
                    </div>

                    <div style="border-left:4px solid #ffc107;padding:0.5rem 1rem;background:#fffdf5;">
                        ⚠️ Students are advised to return overdue books to avoid fines.
                    </div>
                </div>
            </section>

            <!-- BLOG / HELP SECTION -->
            <section class="card">
                <h2 class="page-title">Help & Tips</h2>

                <div style="display:grid;grid-template-columns:1fr 1fr;gap:1rem;">
                    <article style="border:1px solid #e4e9f0;border-radius:8px;padding:0.8rem;">
                        <h3>How to Issue a Book?</h3>
                        <p>Login → Search Book → Click Issue → Confirm. Book will be assigned to your account.</p>
                    </article>

                    <article style="border:1px solid #e4e9f0;border-radius:8px;padding:0.8rem;">
                        <h3>How to Avoid Late Fees?</h3>
                        <p>Always check due date and return books on time. Use dashboard reminders.</p>
                    </article>
                </div>
            </section>

            <!-- FOOTER INCLUDE -->
            <%@ include file="/common/footer.jsp" %>