<html>
<head>
<title>Mirum Entitlement Services</title>
</head>
<body>
<form action="SignInWithCredentials" method="POST">
<h1>Mirum Entitlement Services Testing Page</h1>
<h3>Sign In With Credentials</h3>
<textarea rows="4" cols="50" name="credentials">
<credentials>
<emailAddress>email</emailAddress>
<password>pass</password>
</credentials>
</textarea><br>
<input type="submit" value="Sign In"/><br>
<h3>Generate a New Token</h3>
authToken: <input type="text" name="authToken"  /><br>
<input type="submit" value="Generate it" onclick="form.action='RenewAuthToken'"/><br>
<h3>Get Products</h3>
authToken: <input type="text" name="authToken"  /><br>
<input type="submit" value="Submit" onclick="form.action='Entitlements'"/><br>
<h3>Verify Entitlement</h3>
authToken: <input type="text" name="authToken"  /><br>
productId: <input type="text" name="productId"  /><br>
<input type="submit" value="Verify" onclick="form.action='VerifyEntitlement'"/><br>
</form>
</body>
</html>