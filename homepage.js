function showAgenda(agendaId) {
    // Get the element by its ID (agenda ID)
    const agenda = document.getElementById(agendaId);

    // Toggle the display property to show/hide the agenda
    if (agenda.style.display === "none") {
        agenda.style.display = "block"; // Show the agenda
    } else {
        agenda.style.display = "none"; // Hide the agenda if already visible
    }
}
