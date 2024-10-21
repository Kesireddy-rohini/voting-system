document.getElementById('registrationForm').addEventListener('submit', async function(event) {
    event.preventDefault(); // Prevent default form submission
    
    const formData = {
        name: document.getElementById('name').value,
        age: document.getElementById('age').value,
        gender: document.getElementById('gender').value,
        phonenumber: document.getElementById('phonenumber').value,
        profession: document.getElementById('profession').value,
        email: document.getElementById('email').value,
        password: document.getElementById('password').value
    };
    
    

// Send data to backend (via AJAX/fetch)
fetch("/register", {
method: "POST",
body: new FormData(this)
})
.then(response => response.json())
.then(data => {
if (data.success) {
    // Redirect to login page after successful registration
    window.location.href = "loginpage.html";
} else {
    alert("Registration failed. Please try again.");
}
})
.catch(error => console.error("Error:", error));





    try {
        const response = await fetch('http://127.0.0.1:8081/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        });

        if (response.ok) {
            const message = await response.text();
            alert(message);
        } else {
            const error = await response.text();
            alert(error);
        }
    } catch (error) {
        alert('Error: ' + error.message);
    }
});