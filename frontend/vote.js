function submitVote() {
    // Get selected candidateId
    const selectedCandidate = document.querySelector('input[name="candidate"]:checked');
    const candidateId = selectedCandidate ? selectedCandidate.value : null;

    if (!candidateId) {
        alert("Please select a candidate before submitting.");
        return;
    }

    const email = sessionStorage.getItem("userEmail");
    if (!email) {
        alert("Please login to vote.");
        return;
    }

    // Send vote data to the server
    fetch(`http://localhost:8081/votes?candidateId=${candidateId}&email=${email}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.text())
    .then(message => {
        alert(message);
        // Fetch updated graph data and update the graphs dynamically
        fetchAndRenderCharts();
    })
    .catch(error => {
        alert("Error: " + error.message);
    });
}

let ageChart, genderChart, professionChart;

async function fetchAndRenderCharts() {
    try {
        // Fetch updated aggregated data
        const ageResponse = await fetch("http://localhost:8081/votes/age");
        const genderResponse = await fetch("http://localhost:8081/votes/gender");
        const professionResponse = await fetch("http://localhost:8081/votes/profession");

        const ageData = await ageResponse.json();
        const genderData = await genderResponse.json();
        const professionData = await professionResponse.json();

        // Clear existing graphs before rendering new data
        if (ageChart) ageChart.destroy();
        if (genderChart) genderChart.destroy();
        if (professionChart) professionChart.destroy();

        // Render new graphs with fresh data
        ageChart = renderGraph("ageGraph", "Age Distribution", Object.keys(ageData), Object.values(ageData));
        genderChart = renderGraph("genderGraph", "Gender Distribution", Object.keys(genderData), Object.values(genderData));
        professionChart = renderGraph("professionGraph", "Profession Distribution", Object.keys(professionData), Object.values(professionData));

        // Show the graphs section after rendering the charts
        document.getElementById("graphs").style.display = "block";

    } catch (error) {
        console.error("Error fetching chart data:", error);
    }
}

function renderGraph(canvasId, label, labels, data) {
    const ctx = document.getElementById(canvasId).getContext("2d");
    return new Chart(ctx, {
        type: "bar",
        data: {
            labels: labels,
            datasets: [{
                label: label,
                data: data,
                backgroundColor: "rgba(75, 192, 192, 0.6)",
                borderColor: "rgba(75, 192, 192, 1)",
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}
