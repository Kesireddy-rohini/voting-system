function validateForm() {
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var errorMessage = document.getElementById("errorMessage");

    if (!email.includes('@')) {
        errorMessage.textContent = "Please enter a valid email.";
        return false;
    }

    if (password.length < 6) {
        errorMessage.textContent = "Password must be at least 6 characters long.";
        return false;
    }

    errorMessage.textContent = "";
    alert("Login successful!");
    return true;
}