# 🏥 Hospital Management System

A Java-based Hospital Management System that manages buildings, rooms, wards, doctors, nurses, and patients. Built as an OOP project demonstrating class hierarchies, encapsulation, and object relationships.

---

## 📁 Project Structure

```
src/
├── building/
│   ├── Building.java         # Represents a hospital building with rooms
│   ├── Room.java             # Represents a room containing wards
│   └── Ward.java             # Represents a ward assigned to doctor, nurse & patient
├── service/
│   ├── Address.java          # Address model (zip, city, street)
│   ├── Patient.java          # Patient entity extending PersonDetails
│   └── ManagementSystem.java # Core system: add/delete/assign/print operations
├── personaldetailsFile/
│   └── PersonDetails.java    # Base class for person (id, name, phone, address)
└── staff/
    ├── Doctor.java           # Doctor entity with appointment capacity
    └── Nurse.java            # Nurse entity
```

---

## ✨ Features

- **Building & Ward Management** — Create buildings with configurable rooms and wards
- **Staff Management** — Add, delete, and look up Doctors and Nurses by ID
- **Patient Management** — Register patients and assign them to wards
- **Appointments** — Book and remove doctor–patient appointments
- **Ward Assignment** — Assign doctors, nurses, and patients to specific rooms and wards
- **Details Display** — Print full details of staff, patients, appointments, and building layout

---

## 🚀 Getting Started

### Prerequisites
- Java JDK 8 or higher
- Any Java IDE (IntelliJ IDEA recommended) or command-line compiler

### Running the Project

**Using an IDE (IntelliJ IDEA):**
1. Clone the repository:
   ```bash
   git clone https://github.com/MUSLIM3133000/240208.git
   ```
2. Open the project in IntelliJ IDEA.
3. Run `HospitalAppView.java` (the main entry point).

**Using the command line:**
```bash
# From the src/ directory
javac building/*.java personaldetailsFile/*.java service/*.java staff/*.java
java service.HospitalAppView
```

---

## 🧩 Class Overview

| Class | Package | Responsibility |
|---|---|---|
| `Building` | `building` | Holds an array of `Room` objects |
| `Room` | `building` | Holds an array of `Ward` objects |
| `Ward` | `building` | Links one Doctor, one Nurse, one Patient |
| `PersonDetails` | `personaldetailsFile` | Base class with id, name, phone, address |
| `Patient` | `service` | Extends `PersonDetails`, stores ward assignment |
| `Address` | `service` | Stores zip, city, street |
| `ManagementSystem` | `service` | Static management layer for all operations |
| `Doctor` | `staff` | Extends `PersonDetails`, manages appointments |
| `Nurse` | `staff` | Extends `PersonDetails` |

---

## 📌 Example Usage

```java
// Initialize system capacity
ManagementSystem.ManagementSystem1(5, 5, 10);

// Create building: 3 rooms, 4 wards each
ManagementSystem ms = new ManagementSystem();
ms.Add_Ward(3, 4);

// Add staff and patients
ManagementSystem.addDoctor("D001", "Dr. Ahmed", 3, address1, "01711000001");
ManagementSystem.addNurse("N001", "Nurse Fatima", 2, address2, "01711000002");
ManagementSystem.addpatient("P001", "John Doe", address3, "01711000003", "Ward 1");

// Book appointment
ManagementSystem.Appointment("D001", "P001");

// Assign to a ward
ManagementSystem.Addward_Doctor(1, 1, "D001");
ManagementSystem.Addward_Nurse(1, 1, "N001");
ManagementSystem.Addward_Patient(1, 1, "P001");

// Print building layout
ManagementSystem.printBuilding();
```

---

## 👤 Author

**MUSLIM3133000**  
GitHub: [@MUSLIM3133000](https://github.com/MUSLIM3133000)

---

## 📄 License

This project is for academic/educational purposes.
