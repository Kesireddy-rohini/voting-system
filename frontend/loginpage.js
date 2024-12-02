async function handleLogin(event) {
    event.preventDefault();
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    try {
        const response = await fetch(`http://localhost:8081/login?email=${encodeURIComponent(email)}&password=${encodeURIComponent(password)}`, {
            method: 'POST'
        });

        if (response.ok) {
            const message = await response.text();
            alert(message);

            // Store the email in sessionStorage to keep the user logged in across pages
            sessionStorage.setItem("userEmail", email);

            // Redirect to homepage
            window.location.href = "homepage.html";
        } else {
            const error = await response.text();
            alert(error);
        }
    } catch (error) {
        alert('Error: ' + error.message);
    }
}
