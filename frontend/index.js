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

    try {
        const response = await fetch('http://localhost:8081/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        });

        if (response.ok) {
            alert('User registered successfully');
            window.location.href = "loginpage.html"; // Redirect to login page
        } else {
            const error = await response.text();
            alert(error);
        }
    } catch (error) {
        alert('Error: ' + error.message);
    }
});
