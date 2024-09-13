<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>All Users</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .container { margin-top: 20px; }
    </style>
</head>
<body>
    <div class="container">
        <h1>All Users</h1>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.email}</td>
                        <td>
                            <button class="btn btn-warning" onclick="blockUser(${user.id})">Block</button>
                            <button class="btn btn-success" onclick="unblockUser(${user.id})">Unblock</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script>
        function blockUser(id) {
            fetch(`/admin/users/block/${id}`, { method: 'PUT' })
                .then(response => {
                    if (response.ok) {
                        location.reload();
                    }
                });
        }

        function unblockUser(id) {
            fetch(`/admin/users/unblock/${id}`, { method: 'PUT' })
                .then(response => {
                    if (response.ok) {
                        location.reload();
                    }
                });
        }
    </script>
</body>
</html>
