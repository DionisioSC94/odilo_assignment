function validate() {
    var pattern = /^(?=.*[A-Z])(?=.*\d).{8,}$/;

    if (document.f.username.value == "" && document.f.password.value == "") {
        alert("Username and password are required");
        document.f.username.focus();
        return false;
    }
    if (document.f.username.value == "") {
        alert("Username is required");
        document.f.username.focus();
        return false;
    }
    if (document.f.password.value == "") {
        alert("Password is required");
        document.f.password.focus();
        return false;
    }

//not working
    if (!pattern.test(document.f.password.value)) {
        alert("Password must contain at least 8 characters, from which at least one capital letter and one number")
    	document.f.password.focus();
    	return false
    }
}