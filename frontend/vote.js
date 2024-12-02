const baseUrl = 'http://127.0.0.1:8081/votes'; // Change port as needed

// Function to cast a vote for a candidate
async function castVote(candidateId) {
    const email = prompt("Enter your email to cast your vote:");

    if (email) {
        try {
            const response = await fetch(`${baseUrl}?email=${email}&candidateId=${candidateId}`, {
                method: 'POST'
            });
            const message = await response.text();
            alert(message);
        } catch (error) {
            console.error('Error casting vote:', error);
            alert('Error casting vote. Please try again.');
        }
    } else {
        alert('Please enter a valid email.');
    }
}

// Fetch data and generate charts
async function fetchAndRenderGraphs() {
    const endpoints = {
        profession: `${baseUrl}/profession`,
        age: `${baseUrl}/age`,
        gender: `${baseUrl}/gender`
    };

    try {
        const [professionData, ageData, genderData] = await Promise.all([
            fetch(endpoints.profession).then(response => response.json()),
            fetch(endpoints.age).then(response => response.json()),
            fetch(endpoints.gender).then(response => response.json())
        ]);

        generateProfessionChart(professionData);
        generateAgeChart(ageData);
        generateGenderChart(genderData);

    } catch (error) {
        console.error('Error fetching data:', error);
        alert('Error fetching data. Please try again.');
    }
}

// Chart generation functions (similar to previous example)
function generateProfessionChart(data) {
    const labels = Object.keys(data);
    const candidateIds = new Set();

    labels.forEach(label => {
        Object.keys(data[label]).forEach(candidateId => candidateIds.add(candidateId));
    });

    const datasets = Array.from(candidateIds).map(candidateId => ({
        label: `Candidate ${candidateId}`,
        data: labels.map(label => data[label][candidateId] || 0),
        borderColor: getRandomColor(),
        backgroundColor: getRandomColor(0.5)
    }));

    new Chart(document.getElementById('professionChart').getContext('2d'), {
        type: 'bar',
        data: { labels, datasets },
        options: {
            responsive: true,
            plugins: {
                legend: { position: 'top' },
                title: { display: true, text: 'Votes by Profession' }
            }
        }
    });
}

function generateAgeChart(data) {
    const labels = Object.keys(data).map(age => `Age ${age}`);
    const candidateIds = new Set();

    labels.forEach((_, index) => {
        const ageKey = Object.keys(data)[index];
        Object.keys(data[ageKey]).forEach(candidateId => candidateIds.add(candidateId));
    });

    const datasets = Array.from(candidateIds).map(candidateId => ({
        label: `Candidate ${candidateId}`,
        data: labels.map((_, index) => {
            const ageKey = Object.keys(data)[index];
            return data[ageKey][candidateId] || 0;
        }),
        borderColor: getRandomColor(),
        backgroundColor: getRandomColor(0.5)
    }));

    new Chart(document.getElementById('ageChart').getContext('2d'), {
        type: 'bar',
        data: { labels, datasets },
        options: {
            responsive: true,
            plugins: {
                legend: { position: 'top' },
                title: { display: true, text: 'Votes by Age' }
            }
        }
    });
}

function generateGenderChart(data) {
    const labels = Object.keys(data);
    const candidateIds = new Set();

    labels.forEach(label => {
        Object.keys(data[label]).forEach(candidateId => candidateIds.add(candidateId));
    });

    const datasets = Array.from(candidateIds).map(candidateId => ({
        label: `Candidate ${candidateId}`,
        data: labels.map(label => data[label][candidateId] || 0),
        borderColor: getRandomColor(),
        backgroundColor: getRandomColor(0.5)
    }));

    new Chart(document.getElementById('genderChart').getContext('2d'), {
        type: 'bar',
        data: { labels, datasets },
        options: {
            responsive: true,
            plugins: {
                legend: { position: 'top' },
                title: { display: true, text: 'Votes by Gender' }
            }
        }
    });
}

// Utility function for generating random colors
function getRandomColor(alpha = 1) {
    const r = Math.floor(Math.random() * 255);
    const g = Math.floor(Math.random() * 255);
    const b = Math.floor(Math.random() * 255);
    return `rgba(${r}, ${g}, ${b}, ${alpha})`;
}
