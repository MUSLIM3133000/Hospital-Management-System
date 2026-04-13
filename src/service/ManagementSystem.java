package service;

import building.Building;
import building.Room;
import building.Ward;
import model.Address;
import model.Doctor;
import model.Nurse;
import model.Patient;

import java.util.*;

/**
 * Concrete implementation of {@link HospitalService}.
 *
 * Manages collections of Doctors, Nurses, Patients, and a Building.
 * Uses ArrayList for dynamic sizing (unlike the original fixed arrays).
 *
 * OOP Concepts:
 *  - ENCAPSULATION : all data collections are private
 *  - POLYMORPHISM  : implements HospitalService interface
 */
public class ManagementSystem implements HospitalService {

    // ── Data stores ────────────────────────────────────────────────────────────

    private final Map<String, Doctor>  doctors  = new LinkedHashMap<>();
    private final Map<String, Nurse>   nurses   = new LinkedHashMap<>();
    private final Map<String, Patient> patients = new LinkedHashMap<>();

    /** Appointment map: doctorId → list of patientIds */
    private final Map<String, List<String>> appointments = new LinkedHashMap<>();

    private Building building;

    // ── Doctor operations ──────────────────────────────────────────────────────

    @Override
    public boolean addDoctor(String id, String name, String phone, Address address, int capacity) {
        if (doctors.containsKey(id)) return false;   // duplicate check
        doctors.put(id, new Doctor(id, name, phone, address, capacity));
        appointments.put(id, new ArrayList<>());
        return true;
    }

    @Override
    public boolean removeDoctor(String id) {
        if (!doctors.containsKey(id)) return false;
        doctors.remove(id);
        appointments.remove(id);
        return true;
    }

    @Override
    public Doctor findDoctor(String id) { return doctors.get(id); }

    @Override
    public List<Doctor> getAllDoctors() { return new ArrayList<>(doctors.values()); }

    // ── Nurse operations ───────────────────────────────────────────────────────

    @Override
    public boolean addNurse(String id, String name, String phone, Address address, int capacity) {
        if (nurses.containsKey(id)) return false;
        nurses.put(id, new Nurse(id, name, phone, address, capacity));
        return true;
    }

    @Override
    public boolean removeNurse(String id) {
        if (!nurses.containsKey(id)) return false;
        nurses.remove(id);
        return true;
    }

    @Override
    public Nurse findNurse(String id) { return nurses.get(id); }

    @Override
    public List<Nurse> getAllNurses() { return new ArrayList<>(nurses.values()); }

    // ── Patient operations ─────────────────────────────────────────────────────

    @Override
    public boolean addPatient(String id, String name, String phone, Address address, String wardNumber) {
        if (patients.containsKey(id)) return false;
        patients.put(id, new Patient(id, name, phone, address, wardNumber));
        return true;
    }

    @Override
    public boolean removePatient(String id) {
        if (!patients.containsKey(id)) return false;
        patients.remove(id);
        // Also remove from any doctor appointments
        for (List<String> apptList : appointments.values()) {
            apptList.remove(id);
        }
        return true;
    }

    @Override
    public Patient findPatient(String id) { return patients.get(id); }

    @Override
    public List<Patient> getAllPatients() { return new ArrayList<>(patients.values()); }

    // ── Appointment operations ─────────────────────────────────────────────────

    @Override
    public boolean bookAppointment(String doctorId, String patientId) {
        Doctor  doctor  = doctors.get(doctorId);
        Patient patient = patients.get(patientId);
        if (doctor == null || patient == null) return false;
        if (doctor.isFull())                   return false;

        boolean assigned = doctor.assignPatient(patientId);
        if (assigned) {
            appointments.computeIfAbsent(doctorId, k -> new ArrayList<>()).add(patientId);
        }
        return assigned;
    }

    @Override
    public boolean cancelAppointment(String doctorId, String patientId) {
        Doctor doctor = doctors.get(doctorId);
        if (doctor == null) return false;

        boolean removed = doctor.removePatient(patientId);
        if (removed) {
            List<String> list = appointments.get(doctorId);
            if (list != null) list.remove(patientId);
        }
        return removed;
    }

    @Override
    public List<String> getAppointmentSummary() {
        List<String> summary = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : appointments.entrySet()) {
            Doctor doctor = doctors.get(entry.getKey());
            if (doctor == null) continue;
            for (String pid : entry.getValue()) {
                Patient p = patients.get(pid);
                if (p != null) {
                    summary.add(String.format("Dr. %-20s (ID: %s)  →  Patient: %-20s (ID: %s)",
                            doctor.getName(), doctor.getId(), p.getName(), p.getId()));
                }
            }
        }
        if (summary.isEmpty()) summary.add("No appointments booked yet.");
        return summary;
    }

    // ── Building / Ward operations ─────────────────────────────────────────────

    @Override
    public void initBuilding(String buildingName, int rooms, int wardsPerRoom) {
        this.building = new Building(buildingName, rooms, wardsPerRoom);
    }

    private Ward getWard(int roomNo, int wardNo) {
        if (building == null) return null;
        Room room = building.getRoom(roomNo);
        if (room == null) return null;
        return room.getWard(wardNo);
    }

    @Override
    public boolean assignDoctorToWard(int roomNo, int wardNo, String doctorId) {
        Ward ward = getWard(roomNo, wardNo);
        Doctor doctor = doctors.get(doctorId);
        if (ward == null || doctor == null) return false;
        ward.assignDoctor(doctor);
        return true;
    }

    @Override
    public boolean assignNurseToWard(int roomNo, int wardNo, String nurseId) {
        Ward ward = getWard(roomNo, wardNo);
        Nurse nurse = nurses.get(nurseId);
        if (ward == null || nurse == null) return false;
        ward.assignNurse(nurse);
        nurse.assignToWard(wardNo);
        return true;
    }

    @Override
    public boolean assignPatientToWard(int roomNo, int wardNo, String patientId) {
        Ward ward = getWard(roomNo, wardNo);
        Patient patient = patients.get(patientId);
        if (ward == null || patient == null) return false;
        ward.assignPatient(patient);
        patient.setAssignedWardNumber(String.valueOf(wardNo));
        return true;
    }

    @Override
    public String getBuildingSummary() {
        if (building == null) return "Building not initialised. Please set up rooms & wards first.";
        return building.getSummary();
    }

    /** @return true if the building has been initialised */
    public boolean isBuildingInitialised() { return building != null; }
}
