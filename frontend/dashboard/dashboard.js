
const API_BASE_URL = 'http://127.0.0.1:8080/api/tasks';

// Obtenemos el token JWT almacenado en localStorage luego del login

const token = localStorage.getItem('token');

// Si no hay token, redirigimos al login (usuario no autenticado)
if (!token) {
  window.location.href = '../login/login.html';
}

// Esperamos que el DOM esté completamente cargado
document.addEventListener('DOMContentLoaded', () => {
  // Cargamos las tareas cuando se abre la página
  loadTasks();

  // Botón "View All": recarga la lista de tareas
  document.getElementById('view-all').addEventListener('click', e => {
    e.preventDefault();
    console.log('Click detectado');
    loadTasks();
  });

  // Botón "New Task": abre el formulario para crear una nueva tarea
  document.querySelector('.btn').addEventListener('click', e => {
    e.preventDefault();
    createNewTask();
  });
});

// Función para construir los headers con el token JWT
function authHeaders() {
  return {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}` // Importante: el backend espera "Bearer <token>"
  };
}

// Carga la lista de tareas desde el backend
function loadTasks() {
  
  fetch("http://127.0.0.1:8080/api/tasks/by-user?userId=52", {
    method: 'GET',
    headers: {
    'Authorization': `Bearer ${token}`
  },
  })
  .then(res => {
    if (!res.ok) throw new Error("Credenciales inválidas");
    return res.json();
  })

  .then(data => {

    // Contar tareas
    const counts = data.reduce((acc, task) => {
      acc.total++;
      task.completed ? acc.completed++ : acc.pending++;
      return acc;
    }, { total: 0, completed: 0, pending: 0 });

    document.getElementById("task-count").innerText = counts.total;
    document.getElementById("completed-count").innerText = counts.completed;
    document.getElementById("pending-count").innerText = counts.pending;

    const tbody = document.getElementById('task-table-body');
    tbody.innerHTML = ''; // limpia la tabla antes de insertar

    data.forEach(task => {
      const row = document.createElement('tr');
  row.innerHTML = `
        <td>${task.title}</td>
        <td>${task.details}</td>
        <td>
          <select class="status-dropdown" onchange="updateTaskStatus(${task.id}, this)">
            <option value="completed" ${task.completed ? 'selected' : ''}>✔️ Completed</option>
            <option value="pending" ${!task.completed ? 'selected' : ''}>⏳ Pending</option>
          </select>
        </td>
        <tr data-dueyear="${task.dueYear}" data-priority="${task.priority}">

        <td>
              <div class="menu-container" onclick="toggleMenu(this)">
              <button class="menu-button">⋮</button>
              <div class="menu-options">
                  <a href="#">View</a>
                  <a href="#">Edit</a>
                  <a href="#" onclick="deleteTask(${task.id})">Delete</a>
              </div>
              </div>
        </td>
      `;

      tbody.appendChild(row);
    });

    applyStatusStyles(); // aplicar clases de color

  })
  .catch(err => {
    console.error("Error al cargar tareas:", err);
  });

};

function applyStatusStyles() {
  document.querySelectorAll('.status-dropdown').forEach(select => {
    const value = select.value;
    select.classList.remove('completed', 'pending');
    select.classList.add(value);
  });
}


function toggleMenu(el) {
  // Cierra cualquier otro menú abierto
  document.querySelectorAll('.menu-container').forEach(menu => {
    if (menu !== el) menu.classList.remove('show');
  });

  // Alterna el menú actual
  el.classList.toggle('show');
}

// Cierra el menú si se hace clic fuera
document.addEventListener('click', function(e) {
  if (!e.target.closest('.menu-container')) {
    document.querySelectorAll('.menu-container').forEach(menu => {
      menu.classList.remove('show');
    });
  }
});

// Actualiza los contadores: total, completadas y pendientes
function updateCounts(tasks) {
  const total = tasks.length;
  const completed = tasks.filter(t => t.completed).length;
  const pending = total - completed;

  document.getElementById('task-count').textContent = total;
  document.getElementById('completed-count').textContent = completed;
  document.getElementById('pending-count').textContent = pending;
}


// Cambia el estado (completada/pendiente) de una tarea
function toggleTaskStatus(id, completed) {
  // Primero obtenemos la tarea original desde el backend
  fetch(`${API_BASE_URL}/${id}`, {
    headers: authHeaders()
  })
    .then(res => res.json())
    .then(task => {
      // Cambiamos el estado
      task.completed = !completed;

      // Enviamos el PUT actualizado
      return fetch(`${API_BASE_URL}/${id}`, {
        method: 'PUT',
        headers: authHeaders(),
        body: JSON.stringify(task)
      });
    })
    .then(() => loadTasks()); // Volvemos a cargar las tareas actualizadas
}


// Elimina una tarea por su ID
function deleteTask(id) {
  if (!confirm('Are you sure?')) return;

  fetch(`${API_BASE_URL}/${id}`, {
    method: 'DELETE',
        headers: {
    'Authorization': `Bearer ${token}`
  }
  })
    .then(() => loadTasks()); // Refresca la lista tras eliminación
}


// Muestra un prompt para crear una tarea simple
function createNewTask() {
   const modal = document.getElementById("modal");
  const closeBtn = document.getElementById("closeModalBtn");
  const form = document.getElementById("task-form");

  // Mostrar el modal
  modal.style.display = "block";

  // Cerrar con la X
  closeBtn.onclick = () => {
    const confirmed = confirm("Are you sure you want to cancel?");
    if (confirmed) {
      modal.style.display = "none";
      form.reset();
      return; // Salir si se cancela
    }
  };

  // Cerrar al hacer clic fuera del modal
  window.onclick = (event) => {
    if (event.target === modal) {
      const confirmed = confirm("Are you sure you want to close the form?");
      if (confirmed) {
        modal.style.display = "none";
        form.reset();
        return; // Salir si se cancela
      }
    }
  };

  // Crear la tarea
  form.onsubmit = (e) => {
    e.preventDefault();
    const confirmed = confirm("Do you want to create this task?");
    if (!confirmed) return;

    // 1. Capturar los valores del formulario
    const taskData = {
      title: form.title.value,
      details: form.details.value,
      dueYear: parseInt(form.dueYear.value),
      priority: parseInt(form.priority.value)
    };

    alert("Task created successfully!");
    // llamada a la api
      fetch(`http://localhost:8080/api/tasks/with-user?userId=52`, {
        method: 'POST',
        headers: {
          "Content-Type": "application/json",
          'Authorization': `Bearer ${token}`
      },
      body: JSON.stringify(taskData),
  })
    .then(() => loadTasks()); 

    form.reset();
    modal.style.display = "none";
  }
}

function updateTaskStatus(taskId, selectElement) {
  const newStatus = selectElement.value;
  const completed = newStatus === "completed";

  // Actualizar estilos visuales
  selectElement.classList.remove("completed", "pending");
  selectElement.classList.add(newStatus);

  // Llamada a la API para actualizar
  fetch(`http://localhost:8080/api/tasks/${taskId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    },
    body: JSON.stringify({ completed })
  })
  .then(res => {
    if (!res.ok) throw new Error("Failed to update task status");
    console.log(`Task ${taskId} updated successfully`);
  })
  .catch(err => {
    alert("Error updating status");
    console.error(err);
  });
}


function log_out(){
  const confirmed = confirm("Are you sure you want to log out? This will end your session.");

  if (confirmed) {
    localStorage.removeItem('token');
    alert("You have been logged out.");
    window.location.href = '../login/login.html'; // redirige al login si quieres
  } 
}


function updateTaskStatus(taskId, selectElement) {
  // 1. Obtener el valor nuevo del estado
  const isCompleted = selectElement.value === "completed";

  // 2. Buscar la fila actual y obtener los otros datos visibles
  const row = selectElement.closest('tr');
  const title = row.children[0].innerText;
  const details = row.children[1].innerText;

  const dueYear = parseInt(row.dataset.dueyear);
  const priority = parseInt(row.dataset.priority);


  // 3. Enviar el objeto completo de la tarea con el estado actualizado
  const updatedTask = {
    
    id: taskId,
    title: title,
    details: details,
    completed: isCompleted,
    dueYear: dueYear,        // ⚠️ Puedes reemplazar con valor real si lo tienes en la tabla
    priority: priority           // ⚠️ Lo mismo aquí: deberías obtenerlo si lo tienes visualmente
  };

  fetch(`http://localhost:8080/api/tasks/${taskId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    },
    body: JSON.stringify(updatedTask)
  })
    .then(res => {
      if (!res.ok) throw new Error("Failed to update task");
      return res.json();
    })
    .then(() => {
      applyStatusStyles(); // Vuelve a aplicar estilo según el nuevo estado
    })
    .catch(err => {
      console.error("Error updating task:", err);
      alert("There was a problem updating the task.");
    });
}


