<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<style type="text/css">
body
{	
	font-family: Verdana, Geneva, sans-serif; 
	font-size: 12px;
}
</style>
</head>
<body>
	<h1>OAuth Approval</h1>
	<p>Do you authorize '${authorizationRequest.clientId}' to access
		your protected resources?</p>
	<form id='confirmationForm' name='confirmationForm'
		action='${path}/oauth/authorize' method='post'>
		<input name='user_oauth_approval' value='true' type='hidden' />
		<input type='hidden' name='${_csrf.parameterName}' value='${_csrf.token}' /> 
		
		${scopes} 
	</form>
</body>
</html>
