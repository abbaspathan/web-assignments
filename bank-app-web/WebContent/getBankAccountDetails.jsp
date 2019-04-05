<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

</head>
<body>
	<form action="updateBankDetails.do" method="post">
		Account Id:<br> <input type="number" name="accountId"
			value="${account.accountId}" readonly="readonly"> <br>
		Account Holder Name:<br> <input type="text"
			name="accountHolderName" value="${account.accountHolderName}"
			placeholder="enter your name"> <br> Account Type:<br>
		<select name="accountType">
			<option >${account.accountType}</option>
			<option value="Saving">Saving</option>
			<option value="Current">Current</option>
		</select> <br> Account Balance:<br> <input type="number"
			name="accountBalance" value="${account.accountBalance}"
			readonly="readonly"> <br> <br> <input type="submit"
			value="Update">
	</form>
</body>
</html>