<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ include file="/common/header.jsp" %>

        <div style="display:flex; justify-content:center; align-items:center; min-height:70vh;">

            <div class="card" style="width:100%; max-width:400px;">

                <h2 class="page-title" style="text-align:center;">Login</h2>

                <!-- ROLE SWITCH -->
                <div style="display:flex; margin-bottom:1rem; border-radius:6px; overflow:hidden;">
                    <button id="adminBtn" style="flex:1; padding:0.5rem; border:none; cursor:pointer;">
                        Admin
                    </button>
                    <button id="studentBtn" style="flex:1; padding:0.5rem; border:none; cursor:pointer;">
                        Student
                    </button>
                </div>

                <!-- FORM -->
                <form action="login" method="post" class="form-grid">

                    <div>
                        <label for="email">Email</label>
                        <input class="form-control" type="email" id="email" name="email" placeholder="Enter your email"
                            required>
                    </div>

                    <div>
                        <label for="password">Password</label>
                        <input class="form-control" type="password" id="password" name="password"
                            placeholder="Enter your password" required>
                    </div>

                    <input type="hidden" id="role" name="role" value="ADMIN" />

                    <div class="form-controls" style="margin-top:0.5rem;">
                        <button class="primary" type="submit" style="width:100%;">
                            Login
                        </button>
                    </div>
                </form>

                <!-- ERROR MESSAGES -->
                <% String error=request.getParameter("error"); if ("invalid".equals(error)) { %>
                    <div class="alert error">Invalid credentials. Please check email and password.</div>
                    <% } else if ("wrongrole".equals(error)) { %>
                        <div class="alert error">
                            Role mismatch: You selected <%= request.getParameter("selected") %> and your account role
                                differs.
                        </div>
                        <% } else if ("role".equals(error)) { %>
                            <div class="alert error">Please select admin or student mode before login.</div>
                            <% } %>

            </div>
        </div>

        <!-- SCRIPT -->
        <script>
            const adminBtn = document.getElementById('adminBtn');
            const studentBtn = document.getElementById('studentBtn');
            const roleInput = document.getElementById('role');

            function setMode(mode) {
                roleInput.value = mode;

                if (mode === 'ADMIN') {
                    adminBtn.style.background = '#0e4c92';
                    adminBtn.style.color = '#fff';
                    studentBtn.style.background = '#e4e9f0';
                    studentBtn.style.color = '#333';
                } else {
                    studentBtn.style.background = '#1a8f40';
                    studentBtn.style.color = '#fff';
                    adminBtn.style.background = '#e4e9f0';
                    adminBtn.style.color = '#333';
                }
            }

            adminBtn.addEventListener('click', function (e) {
                e.preventDefault();
                setMode('ADMIN');
            });

            studentBtn.addEventListener('click', function (e) {
                e.preventDefault();
                setMode('STUDENT');
            });

            // Default mode
            setMode('ADMIN');
        </script>

        <%@ include file="/common/footer.jsp" %>