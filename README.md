# Today's Task

A full-stack web application for task management, combining a **pure JavaScript, HTML, and CSS frontend** with a robust **Java + Spring Boot backend**. This app enables users to create, view, filter, update, and delete tasks, and includes **JWT-based authentication**. It uses a lightweight **MySQL database** for data persistence.

🔗 [GitHub Repository – Today's Task](https://github.com/DuvanTP/Task-Project.git)

---

## 🛠 Project Overview

**Today's Task** offers a RESTful API for task management, developed with Spring Boot, JPA, and MySQL. The API supports filtering tasks by priority, due year, and completion status, making it ideal for integration with web or mobile applications.

On the frontend, the application is built with **vanilla JavaScript**, **HTML**, and **CSS**, allowing for a lightweight and responsive user interface without external JS frameworks.

---

## 🚀 Features

* **JWT authentication** for secure user sessions.
* Create tasks with validation.
* Fetch all tasks or filter by:

  * Priority
  * Due year
  * Completion status (completed / pending)
* Sort tasks dynamically (by any field and direction).
* View task details by ID.
* Update existing tasks.
* Delete tasks.
* Clean and modular architecture with layered separation.

---

## 🧱 Architecture

* **Frontend**:

  * Pure JavaScript (Vanilla JS)
  * HTML5
  * CSS3

* **Backend (Java)**:

  * `Controller`: Handles HTTP requests.
  * `Service`: Contains business logic.
  * `Repository`: Interfaces with the database via Spring Data JPA.
  * `Model`: Represents the entities (e.g., Task).

---

## 🧰 Tech Stack

### Backend

* Java 17+
* Spring Boot
* Spring Web
* Spring Data JPA
* MySQL (or H2 for in-memory testing)
* JWT (JSON Web Tokens)
* Maven

### Frontend

* HTML5
* CSS3
* Vanilla JavaScript

---


