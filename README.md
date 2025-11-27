<h1 style="text-align: center">🌱 AgroMinds - Plataforma de Gestión Agrícola Inteligente 🌾</h1>

<div style="text-align: center">
    <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" />
    <img src="https://img.shields.io/badge/PostgreSQL-336791?style=for-the-badge&logo=postgresql&logoColor=white" />
    <img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white" />
    <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white" />
</div>

<div style="text-align: center">
    <img src="https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white" />
    <img src="https://img.shields.io/badge/Node.js-339933?style=for-the-badge&logo=node.js&logoColor=white" />
    <img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white" />
    <img src="https://img.shields.io/badge/CSS5-1572B6?style=for-the-badge&logo=css&logoColor=white" />
    <img src="https://img.shields.io/badge/TypeScript-3178C6?style=for-the-badge&logo=typescript&logoColor=white" />
</div>

<div style="text-align: center; margin-top: 30px;">
  <a style="margin: 0 5px;">
    <img src="https://img.shields.io/github/stars/Faep28/Agromind?style=for-the-badge&logo=github&logoColor=white" alt="Stars" />
  </a>
  <a style="margin: 0 5px;">
    <img src="https://img.shields.io/github/forks/Faep28/Agromind?style=for-the-badge&logo=github&logoColor=white" alt="Forks" />
  </a>
  <a style="margin: 0 5px;">
    <img src="https://img.shields.io/github/issues/Faep28/Agromind?style=for-the-badge&logo=github&logoColor=white" alt="Issues" />
  </a>
  <a style="margin: 0 5px;">
    <img src="https://img.shields.io/github/last-commit/Faep28/Agromind?style=for-the-badge&logo=github&logoColor=white" alt="Last Commit" />
  </a>
</div>


---

## 🚀 Descripción del Proyecto

**AgroMinds** es una aplicación desarrollada con **Spring Boot + JPA + PostgreSQL**, diseñada para la **gestión integral de cultivos, sensores, fertilizantes y notificaciones agrícolas inteligentes**.  
Permite a los agricultores optimizar sus recursos, monitorear sus parcelas, recibir alertas automáticas y mantener un historial digital de sus actividades agrícolas.

El proyecto sigue una arquitectura **por capas (Entities, Repositories, Services, Controllers, DTOs)**, promoviendo la escalabilidad y el mantenimiento del código.

---

## 🧩 Módulos Principales

| Módulo                              | Descripción |
|-------------------------------------|--------------|
| 👨‍🌾 **Usuarios y Clientes**       | Gestión de usuarios, roles y clientes vinculados. |
| 🌿 **Cultivos y Parcelas**          | Registro, seguimiento y estadísticas de cultivos. |
| 🧪 **Fertilizantes y Aplicaciones** | Control de fertilizantes utilizados por cultivo. |
| ⚙️ **Sensores y Lecturas**          | Monitoreo de humedad, temperatura y pH en tiempo real. |
| 📰 **Noticias y Notificaciones**    | Comunicación de eventos, alertas y novedades al usuario. |
| 💬 **Servicios y Solicitudes**      | Solicitud de asesorías, mantenimiento y recursos agrícolas. |

---

## 🏗️ Tecnologías Utilizadas

- ☕ **Java 25**
- 🍃 **Spring Boot 3.5.5**
- 🧭 **Spring Data JPA**
- 🐘 **PostgreSQL**
- 🔁 **JPQL y SQL Nativo**
- 📦 **Arquitectura por Capas**
- 🔒 **Control de roles con Authorities**

---

## 📡 Estructura del Proyecto


```mermaid
graph LR
  A[🍃 Backend] --> B[📁 com.prueba.backend]
  A --> C[🎯 Controller]
  A --> D[🧩 Dto]
  A --> E[🧱 Entity]
  A --> F[💾 Repository]
  A --> G[⚙️ Service]
  A --> H[🔧 ServiceImpl]
  A --> I[🚀 BackendApplication.java]
  A --> J[❗ Exceptions]

  K[🅰️ Frontend] --> L[📁 src]
  K --> M[📁 app]
  K --> N[📁 assets]
  K --> O[⚙️ config]
  K --> P[🧩 components]
  K --> Q[🧩 services]
  K --> R[🧩 app.module.ts]

  S[Archivos Raíz] --> T[📄 README.md]
```

## 👨‍💻 Equipo de Desarrollo

**Universidad Peruana de Ciencias Aplicadas - UPC**
Proyecto académico del curso **Análisis y Diseño de Sistemas de Información (ASI-705)**

| Integrante         | Rol           |                                                 GitHub                                                  |
|--------------------|---------------|:-------------------------------------------------------------------------------------------------------:|
| 💻 George Baca     | Fullstack Dev | <img src="https://avatars.githubusercontent.com/u/185106239?v=4" alt="angelo" width="32" height="auto"> |
| 💻 Joshua Piero    | Fullstack Dev | <img src="https://avatars.githubusercontent.com/u/176424079?v=4" alt="angelo" width="32" height="auto"> |
| 💻 Angelo Paolo    | Fullstack Dev | <img src="https://avatars.githubusercontent.com/u/136656996?v=4" alt="angelo" width="32" height="auto"> |
| 💻 Rodrigo Otoniel | Fullstack Dev | <img src="https://avatars.githubusercontent.com/u/93228774?v=4" alt="angelo" width="32" height="auto">  |
| 💻 Frank Antoni    | Fullstack Dev | <img src="https://avatars.githubusercontent.com/u/186503826?v=4" alt="angelo" width="32" height="auto"> |
---

## ⭐ ¡Apoya el Proyecto!

Si te gusta nuestro trabajo, no olvides dejar una **estrella** ⭐ en el repo 💚
y contribuir con ideas o mejoras para futuras versiones.

---

<p align="center">Hecho con 💚 por el equipo de AgroMinds</p>

RECENT Contributions:

![Alt](https://repobeats.axiom.co/api/embed/058c955b302e1751862ee8e325bb3011174f8add.svg "Repobeats analytics image")

### Licence

Copyright © 2025 - AgroMinds