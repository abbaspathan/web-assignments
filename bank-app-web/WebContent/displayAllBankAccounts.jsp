<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Account Details</title>
<style>
table {
	font-family: arial, sans-serif;
	border-collapse: collapse;
	width: 100%;
}

td, th {
	border: 1px solid #dddddd;
	text-align: left;
	padding: 8px;
}

tr:nth-child(even) {
	background-color: #dddddd;
}
</style>
</head>
<body>
	<h2>Account Details</h2>

	<table>
		<thead>
			<tr>
				<th>account_id</th>
				<th>customer_name</th>
				<th>account_type</th>
				<th>account_balance</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="account" items="${accounts}">
				<tr>
					<td>${account.accountId}</td>
					<td>${account.accountHolderName}</td>
					<td>${account.accountType}</td>
					<td>${account.accountBalance}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>