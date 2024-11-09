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


