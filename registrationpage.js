document.getElementById("registration-form").addEventListener("submit", function(event) {
    event.preventDefault(); // Prevent form submission
    alert("Registration successful!"); // Show success message (replace with actual submission logic)
    this.reset(); // Clear the form after submission
});
