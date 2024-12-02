async function askQuestion() {
    const question = document.getElementById('userQuestion').value;
    const aiResponseElement = document.getElementById('aiResponse');

    // Clear previous response
    aiResponseElement.textContent = "Thinking...";

    try {
        // Send question to the backend API
        const response = await fetch("http://127.0.0.1:8000", {
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


function hideAllAgendas() {
    document.getElementById('agenda-trump-full').style.visibility = "hidden";
    document.getElementById('agenda-harris-full').style.visibility = "hidden";
}

function showFullAgenda(agendaId) {
    const agenda = document.getElementById(agendaId);
    agenda.style.visibility = "visible";
}

function closeFullAgenda(agendaId) {
    const agenda = document.getElementById(agendaId);
    agenda.style.visibility = "hidden";
}
function goToVotingPreference() {
    // Redirect to the voting preference page
    window.location.href = 'votingpreference.html'; // Replace with the actual URL or path to your voting preference page
}