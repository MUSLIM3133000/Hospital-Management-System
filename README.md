# Hospital Management System v1.0
### Paradise Hospital — Built with Java 11+ & Swing

---

## Project Structure

```
src/
├── Main.java                         ← Entry point
├── model/
│   ├── PersonDetails.java            ← Abstract base class
│   ├── Address.java                  ← Immutable value object
│   ├── Doctor.java                   ← Extends PersonDetails
│   ├── Nurse.java                    ← Extends PersonDetails
│   └── Patient.java                  ← Extends PersonDetails
├── building/
│   ├── Building.java                 ← Top-level container
│   ├── Room.java                     ← Contains wards
│   └── Ward.java                     ← Holds Doctor/Nurse/Patient
├── service/
│   ├── HospitalService.java          ← Interface (abstraction layer)
│   └── ManagementSystem.java         ← Concrete implementation
├── ui/
│   └── HospitalGUI.java              ← Full Swing GUI (MVC View)
└── util/
    └── Validator.java                ← Input validation utilities
```

---

## How to Compile and Run

### Option 1 — Command Line (from the project root folder)

```bash
# 1. Compile all sources into the /out directory
find src -name "*.java" > sources.txt
javac -d out @sources.txt

# 2. Run
java -cp out Main
```

### Option 2 — IntelliJ IDEA (recommended)
1. File → Open → select this folder
2. Right-click `src/` → Mark Directory as → Sources Root
3. Run `Main.java`

### Option 3 — Eclipse
1. File → New → Java Project
2. Delete the default `src/` folder
3. Import this `src/` folder as the source
4. Run `Main.java`

---

## OOP Concepts Demonstrated

| Concept       | Where applied |
|---------------|---------------|
| Abstraction   | `PersonDetails` (abstract class) + `HospitalService` (interface) |
| Encapsulation | All fields private; collections returned as unmodifiable views |
| Inheritance   | `Doctor`, `Nurse`, `Patient` all extend `PersonDetails` |
| Polymorphism  | `getRole()` overridden in each subclass; `HospitalService` backed by `ManagementSystem` |

---

## Sample Walkthrough

1. Launch the app → click **Building & Wards → Setup Building** → 3 rooms, 4 wards
2. **Doctors → Add Doctor** → ID: D001, Name: Dr. Ahmed, capacity: 5
3. **Nurses → Add Nurse** → ID: N001, Name: Nurse Farzana, capacity: 3
4. **Patients → Add Patient** → ID: P001, Name: Karim Hossain, Ward: 1
5. **Appointments → Book Appointment** → D001 + P001
6. **Building → Assign Doctor** → Room 1, Ward 1, ID: D001
7. **Building → Refresh View** → see full ward layout
8. **Dashboard** → all counts update automatically every 1.5s

---

*Built as a professional OOP demonstration project.*
