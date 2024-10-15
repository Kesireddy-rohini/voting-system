function showAgenda(candidate) {
    const trumpAgenda = document.getElementById('trump-agenda');
    const harrisAgenda = document.getElementById('harris-agenda');

    // Hide both agendas first
    trumpAgenda.classList.remove('fade-in');
    harrisAgenda.classList.remove('fade-in');

    // Display relevant agenda
    if (candidate === 'trump') {
        trumpAgenda.style.display = 'block';
        trumpAgenda.classList.add('fade-in');
        harrisAgenda.style.display = 'none'; // Hide Harris agenda when Trump is shown
    } else if (candidate === 'harris') {
        harrisAgenda.style.display = 'block';
        harrisAgenda.classList.add('fade-in');
        trumpAgenda.style.display = 'none'; // Hide Trump agenda when Harris is shown
    }
}

