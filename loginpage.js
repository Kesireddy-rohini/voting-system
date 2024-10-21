async function handleLogin(event) {
  event.preventDefault();
  const email = document.getElementById('email').value;
  const password = document.getElementById('password').value;
  if (email === 'admin' && password === '12345') {
            // Redirect to the home page after successful login
            window.location.href = "http://127.0.0.1:5500/voting-system/homepage.html"; // Specify your home page URL here
        } else {
            alert('Invalid credentials. Please try again.');
        }

  try {
      const response = await fetch(`http://127.0.0.1:8081/login?email=${encodeURIComponent(email)}&password=${encodeURIComponent(password)}`, {
          method: 'POST',
      });

      const message = await response.text();
      alert(message);
  } catch (error) {
      alert('Error: ' + error.message);
  }
}
 src="lo"
