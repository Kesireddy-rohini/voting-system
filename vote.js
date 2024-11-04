function submitVote() {
    const candidate1 = document.getElementById("candidate1").checked;
    const candidate2 = document.getElementById("candidate2").checked;

    if (!candidate1 && !candidate2) {
        alert("Please select a candidate before submitting.");
        return;
    }

    // Example data for the graphs
    const ageData = [10, 20, 30, 40];
    const genderData = [15, 35];
    const professionData = [25, 15, 20, 30];

    // Show the graphs
    document.getElementById("graphs").style.display = "flex";

    // Render graphs using Chart.js
    renderGraph("ageGraph", "Age Distribution", ["18-24", "25-34", "35-44", "45+"], ageData);
    renderGraph("genderGraph", "Gender Distribution", ["Male", "Female"], genderData);
    renderGraph("professionGraph", "Profession Distribution", ["Student", "Engineer", "Doctor", "Other"], professionData);

    // Change submit button to green after voting
    const submitButton = document.getElementById("submitButton");
    submitButton.classList.add("voted"); // Add the "voted" class to change color
    submitButton.innerText = "Vote Submitted"; // Optional: Change button text
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
                backgroundColor: [
                    "rgba(255, 99, 132, 0.8)", // Darker red
                    "rgba(54, 162, 235, 0.8)", // Darker blue
                    "rgba(255, 206, 86, 0.8)", // Darker yellow
                    "rgba(75, 192, 192, 0.8)", // Darker teal
                    "rgba(153, 102, 255, 0.8)", // Darker purple
                    "rgba(255, 159, 64, 0.8)"  // Darker orange
                ],
                borderColor: [
                    "rgba(255, 99, 132, 1)", // Darker red border
                    "rgba(54, 162, 235, 1)", // Darker blue border
                    "rgba(255, 206, 86, 1)", // Darker yellow border
                    "rgba(75, 192, 192, 1)", // Darker teal border
                    "rgba(153, 102, 255, 1)", // Darker purple border
                    "rgba(255, 159, 64, 1)"  // Darker orange border
                ],
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}

function redirectToHome() {
    window.location.href = "homepage.html"; // Replace with your desired URL
}
