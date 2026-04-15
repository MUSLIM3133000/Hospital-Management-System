# 🏥 Hospital Management System v1.0  
### Paradise Hospital — Java Swing + OOP Architecture  


![Java](https://img.shields.io/badge/Java-11%2B-orange)  
![GUI](https://img.shields.io/badge/GUI-Swing-blue)  
![OOP](https://img.shields.io/badge/Concepts-OOP-success)  
![Status](https://img.shields.io/badge/Project-Completed-brightgreen)  
![License](https://img.shields.io/badge/License-MIT-lightgrey)  

---

## 📌 Overview  

A **desktop-based Hospital Management System** built with **Java (JDK 11+)** and **Swing GUI**, designed to demonstrate **clean architecture and Object-Oriented Programming (OOP)** principles.  

This system helps manage:  
- 👨‍⚕️ Doctors  
- 👩‍⚕️ Nurses  
- 🧑‍🤝‍🧑 Patients  
- 🏥 Rooms & Wards  
- 📅 Appointments  
Real-time dashboard updates
---

## ✨ Features  

- 🖥️ Interactive **Swing GUI**
- 🧱 Clean **modular architecture**
- 🔄 Real-time dashboard updates  
- ✅ Input validation system  
- 📊 Live hospital statistics  
- 🧩 Strong OOP implementation  

---

## 📸 Screenshots  

> 📌 Add your screenshots inside a `/screenshots` folder in your repo  

### 🏠 Dashboard  
![Dashboard](screenshots/dashboard.png)

### 👨‍⚕️ Doctor Management  
![Doctors](screenshots/doctors.png)

### 🏥 Ward View  
![Wards](screenshots/wards.png)

### 📅 Appointment System  
![Appointments](screenshots/appointments.png)

---

## 🗂️ Project Structure  

```bash
src/
├── Main.java                         # Application entry point
│
├── model/                            # Core domain models
│   ├── PersonDetails.java            # Abstract base class
│   ├── Address.java                  # Immutable value object
│   ├── Doctor.java                   # Doctor entity
│   ├── Nurse.java                    # Nurse entity
│   └── Patient.java                  # Patient entity
│
├── building/                         # Hospital infrastructure
│   ├── Building.java                 # Main container
│   ├── Room.java                     # Room representation
│   └── Ward.java                     # Ward management
│
├── service/                          # Business logic layer
│   ├── HospitalService.java          # Interface (abstraction)
│   └── ManagementSystem.java         # Implementation
│
├── ui/                               # Graphical User Interface
│   └── HospitalGUI.java              # Swing-based UI (MVC View)
│
└── util/                             # Utility classes
    └── Validator.java                # Input validation
```

## 🧠 OOP Concepts Used  

- 🔹 **Abstraction** → Interfaces & abstract classes  
- 🔹 **Encapsulation** → Private fields + validation  
- 🔹 **Inheritance** → Shared base class (`PersonDetails`)  
- 🔹 **Polymorphism** → Method overriding  

---


## 🧪 Example Workflow  

1. Setup building (rooms & wards)  
2. Add doctors and nurses  
3. Register patients  
4. Assign wards  
5. Book appointments  
6. Monitor dashboard  
7. Assign staff

---
🎯 Purpose of the Project

This project was developed as a learning-focused software system to:

Practice OOP concepts in Java
Build a GUI-based real-world application
Understand software architecture and design patterns
Improve problem-solving and coding skills

---
## 🚀 Future Improvements  

- 🔐 User authentication system  
- 🗄️ Database integration (MySQL)  
- 🌐 Web version (Spring Boot + React)  
- 📈 Advanced analytics dashboard  

---

## 👨‍💻 Author-> MD Sakib Hasan CSE KU  

**Paradise Hospital Project**  
Built for learning **Java, GUI, and OOP concepts**  
