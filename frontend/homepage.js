
function showFullAgenda(agendaId) {
    // Show the full-page agenda
    const agenda = document.getElementById(agendaId);
    agenda.style.display = "flex";
}

function closeFullAgenda(agendaId) {
    // Hide the full-page agenda
    const agenda = document.getElementById(agendaId);
    agenda.style.display = "none";
}
async function askQuestion() {
    const question = document.getElementById('userQuestion').value;
    const aiResponseElement = document.getElementById('aiResponse');

    // Clear previous response
    aiResponseElement.textContent = "Thinking...";

    try {
        // Send question to the backend API
        const response = await fetch("http://localhost:5000/answer", {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ question: question })
        });

        // Get response data
        const data = await response.json();
        aiResponseElement.textContent = data.answer;
    } catch (error) {
        aiResponseElement.textContent = "Sorry, something went wrong. Please try again later.";
        console.error("Error fetching AI response:", error);
    }
}