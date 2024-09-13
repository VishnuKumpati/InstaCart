<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>All Retailers</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .container { margin-top: 20px; }
    </style>
</head>
<body>
    <div class="container">
        <h1>All Retailers</h1>
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
                <c:forEach var="retailer" items="${retailers}">
                    <tr>
                        <td>${retailer.id}</td>
                        <td>${retailer.name}</td>
                        <td>${retailer.email}</td>
                        <td>
                            <button class="btn btn-warning" onclick="blockRetailer(${retailer.id})">Block</button>
                            <button class="btn btn-success" onclick="unblockRetailer(${retailer.id})">Unblock</button>
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
        function blockRetailer(id) {
            fetch(`/admin/retailers/block/${id}`, { method: 'PUT' })
                .then(response => {
                    if (response.ok) {
                        location.reload();
                    }
                });
        }

        function unblockRetailer(id) {
            fetch(`/admin/retailers/unblock/${id}`, { method: 'PUT' })
                .then(response => {
                    if (response.ok) {
                        location.reload();
                    }
                });
        }
    </script>
</body>
</html>
