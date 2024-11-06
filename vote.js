function submitVote(candidateId) {
    console.log("Candidate ID:", candidateId); // Debugging line
    const email = sessionStorage.getItem("userEmail");

    if (!email) {
        alert("Please login to vote.");
        return;
    }

    fetch(`http://localhost:8081/votes?candidateId=${candidateId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'userEmail': email
        }
    })
    .then(response => response.text())
    .then(message => {
        alert(message);
    })
    .catch(error => {
        alert("Error: " + error.message);
    });
}



async function fetchAndRenderCharts() {
    try {
        const ageResponse = await fetch("http://localhost:8081/votes/age");
        const genderResponse = await fetch("http://localhost:8081/votes/gender");
        const professionResponse = await fetch("http://localhost:8081/votes/profession");

        const ageData = await ageResponse.json();
        const genderData = await genderResponse.json();
        const professionData = await professionResponse.json();

        renderGraph("ageGraph", "Age Distribution", Object.keys(ageData), Object.values(ageData));
        renderGraph("genderGraph", "Gender Distribution", Object.keys(genderData), Object.values(genderData));
        renderGraph("professionGraph", "Profession Distribution", Object.keys(professionData), Object.values(professionData));

    } catch (error) {
        console.error("Error fetching chart data:", error);
    }
}

function renderGraph(canvasId, label, labels, data) {
    const ctx = document.getElementById(canvasId).getContext("2d");
    new Chart(ctx, {
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

function redirectToHome() {
    window.location.href = "homepage.html"; // Redirect to homepage
}
