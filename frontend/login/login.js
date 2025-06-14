document.getElementById("loginForm").addEventListener("submit", async function (e) {
  e.preventDefault();

  const username = document.getElementById("username").value;
  const password = document.getElementById("password").value;

  try {
    const response = await fetch("http://127.0.0.1:8080/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ username, password }),
    });

    const data = await response.json();

    if (response.ok) {
      // Guardar JWT en localStorage
      localStorage.setItem("token", data.token);
      alert("Inicio de sesión exitoso");
      // Redireccionar o cargar página principal
      window.location.href = "../dashboard/dashboard.html";
    } else {
      alert(data.message || "Credenciales inválidas");
    }
  } catch (err) {
    console.error(err);
    alert("Error al conectar con el servidor");
  }
});
