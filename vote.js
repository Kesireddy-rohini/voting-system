async function submitVote() {
    const candidate1 = document.getElementById("candidate1").checked;
    const candidate2 = document.getElementById("candidate2").checked;
    
    const email = sessionStorage.getItem("userEmail"); // Retrieve logged-in user's email

    if (!email) {
        alert("You must be logged in to vote.");
        return;
    }

    let candidateId;
    if (candidate1) {
        candidateId = 1; // Candidate ID for Donald Trump
    } else if (candidate2) {
        candidateId = 2; // Candidate ID for Kamala Harris
    } else {
        alert("Please select a candidate before submitting.");
        return;
    }

    try {
        const response = await fetch("http://localhost:8081/votes", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ email: email, candidateId: candidateId })
        });

        const result = await response.text();
        alert(result);

        if (response.ok) {
            document.getElementById("graphs").style.display = "flex";
            fetchAndRenderCharts();
        }
    } catch (error) {
        console.error("Error:", error);
        alert("Failed to submit vote.");
    }
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
