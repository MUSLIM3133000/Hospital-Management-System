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
<img width="1906" height="1027" alt="Screenshot 2026-04-13 170420" src="https://github.com/user-attachments/assets/ac73f2cd-f3d8-46af-a3ca-843bdbd9d116" />

### 👨‍⚕️ Doctor Management  
<img width="1917" height="1029" alt="Screenshot 2026-04-15 071700" src="https://github.com/user-attachments/assets/926bd937-7be2-405b-ae74-64772afb6127" />
### Nurse Management
<img width="1916" height="1028" alt="Screenshot 2026-04-15 071718" src="https://github.com/user-attachments/assets/0ccaa4cc-9005-4567-ad7a-475b129adbad" />
### Patient Management
<img width="1919" height="1016" alt="Screenshot 2026-04-15 071743" src="https://github.com/user-attachments/assets/56dc91c9-cf11-4812-8351-1c5f40817d82" />

### 🏥 Ward View
<img width="1919" height="1022" alt="Screenshot 2026-04-15 071817" src="https://github.com/user-attachments/assets/c8e899c5-d532-4ca4-b7d7-9c55af3f1a5f" />
### 📅 Appointment System  
<img width="1919" height="1020" alt="Screenshot 2026-04-15 071800" src="https://github.com/user-attachments/assets/966f22b7-3d71-4809-bb9c-592e363b9dba" />


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
