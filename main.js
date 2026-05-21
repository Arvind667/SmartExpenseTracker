let expenses = [];

let chart;

const monthlyBudget = 10000;

/* ADD EXPENSE */

function addExpense() {

    const title =
    document.getElementById("title").value;

    const amount =
    document.getElementById("amount").value;

    const category =
    document.getElementById("category").value;

    const date =
    document.getElementById("date").value;

    if (title === "" || amount === "") {

        alert("Please fill all fields");

        return;
    }

    const expense = {

        title,
        amount: parseFloat(amount),
        category,
        date
    };

    expenses.push(expense);

    updateUI();

    clearFields();
}

/* UPDATE UI */

function updateUI() {

    const expenseList =
    document.getElementById("expenseList");

    expenseList.innerHTML = "";

    let total = 0;

    expenses.forEach((expense, index) => {

        total += expense.amount;

        expenseList.innerHTML += `

        <tr>

            <td>${expense.title}</td>

            <td>${expense.category}</td>

            <td>₹${expense.amount}</td>

            <td>${expense.date}</td>

            <td>

                <button class="delete-btn"
                onclick="deleteExpense(${index})">

                Delete

                </button>

            </td>

        </tr>

        `;
    });

    /* TOTAL BALANCE */

    document.getElementById("balance")
    .innerText = `₹${total}`;

    /* BUDGET LEFT */

    const remaining =
    monthlyBudget - total;

    const budgetElement =
    document.getElementById("budgetLeft");

    if (budgetElement) {

        budgetElement.innerText =
        `₹${remaining}`;
    }

    /* AI INSIGHTS */

    generateAIInsights(total);

    /* UPDATE GRAPH */

    updateChart();

    /* SAVE DATA */

    localStorage.setItem(
        "expenses",
        JSON.stringify(expenses)
    );

    /* RECENT TRANSACTIONS */

    const recentList =
    document.getElementById("recentList");

    if (recentList) {

        recentList.innerHTML = "";

        expenses.slice(-5).forEach(expense => {

            recentList.innerHTML += `

            <li>
                ${expense.title} - ₹${expense.amount}
            </li>

            `;
        });
    }
}

/* DELETE EXPENSE */

function deleteExpense(index) {

    expenses.splice(index, 1);

    updateUI();
}

/* CLEAR INPUTS */

function clearFields() {

    document.getElementById("title").value = "";

    document.getElementById("amount").value = "";

    document.getElementById("date").value = "";
}

/* SEARCH */

function searchExpense() {

    const search =
    document.getElementById("search")
    .value
    .toLowerCase();

    const rows =
    document.querySelectorAll("#expenseList tr");

    rows.forEach(row => {

        const text =
        row.innerText.toLowerCase();

        row.style.display =
        text.includes(search)
        ? ""
        : "none";
    });
}

/* VOICE INPUT */

function startVoice() {

    const recognition =
    new webkitSpeechRecognition();

    recognition.lang = "en-US";

    recognition.onresult = function (event) {

        const text =
        event.results[0][0].transcript;

        document.getElementById("title")
        .value = text;
    };

    recognition.start();
}

/* AI INSIGHTS */

function generateAIInsights(total) {

    let message = "";

    if (total < 3000) {

        message =
        "🟢 You are a Smart Saver.";

    } else if (total < 7000) {

        message =
        "🔵 You are a Balanced Spender.";

    } else {

        message =
        "🔴 You are a Heavy Spender.";
    }

    document.getElementById("aiMessage")
    .innerText = message;
}

/* UPDATE GRAPH */

function updateChart() {

    const categories = {};

    expenses.forEach(expense => {

        if (categories[expense.category]) {

            categories[expense.category]
            += expense.amount;

        } else {

            categories[expense.category]
            = expense.amount;
        }
    });

    const labels =
    Object.keys(categories);

    const data =
    Object.values(categories);

    const ctx =
    document.getElementById("expenseChart");

    if (chart) {

        chart.destroy();
    }

    chart = new Chart(ctx, {

        type: "doughnut",

        data: {

            labels: labels,

            datasets: [{

                data: data,

                backgroundColor: [

                    '#00ffb3',
                    '#ff6384',
                    '#36a2eb',
                    '#ffce56',
                    '#8b5cf6'
                ]
            }]
        }
    });
}

/* THEME TOGGLE */

function toggleTheme() {

    document.body.classList.toggle("light");
}

/* LOAD SAVED DATA */

window.onload = function () {

    const savedExpenses =
    localStorage.getItem("expenses");

    if (savedExpenses) {

        expenses =
        JSON.parse(savedExpenses);

        updateUI();
    }
}