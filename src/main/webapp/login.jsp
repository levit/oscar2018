<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Form-Based Login Page</title>
    </head>
    <body>
        <h1>Form-Based Login Page</h1>

        <form method="POST" action="j_security_check">
            Username: <input type="text" name="j_username"> <p/>
            Password: <input type="password" name="j_password" autocomplete="off"> <p/>
            <input type="submit" value="Submit" name="submitButton">
            <input type="reset" value="Reset">
        </form>

    </body>
</html>