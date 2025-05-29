document.addEventListener("DOMContentLoaded", () => {
    const taskForm = document.getElementById("taskForm");
    const taskList = document.getElementById("taskList");

    function fetchTasks() {
        fetch("/api/tasks")
            .then(response => response.json())
            .then(data => {
                taskList.innerHTML = "";
                data.forEach(task => {
                    const li = document.createElement("li");
                    li.innerHTML = `<strong>${task.title}</strong>`;
                    
                    const details = document.createElement("div");
                    details.classList.add("description");
                    details.textContent = task.details || "Sin detalles";
                    details.style.display = "none";

                    li.appendChild(details);

                    li.addEventListener("click", () => {
                        details.style.display = details.style.display === "none" ? "block" : "none";
                    });

                    taskList.appendChild(li);
                });
            });
    }

    taskForm.addEventListener("submit", (e) => {
        e.preventDefault();

        const task = {
            title: document.getElementById("title").value,
            details: document.getElementById("details").value,
            priority: parseInt(document.getElementById("priority").value),
            dueYear: parseInt(document.getElementById("dueYear").value),
            completed: false
        };

        fetch("/api/tasks", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(task)
        })
        .then(() => {
            taskForm.reset();
            fetchTasks();
        });
    });

    fetchTasks();
});
