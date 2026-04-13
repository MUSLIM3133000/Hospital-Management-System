package service;

import model.Address;
import model.Doctor;
import model.Nurse;
import model.Patient;

import java.util.List;

/**
 * Service contract for the Hospital Management System.
 *
 * OOP Concept — ABSTRACTION:
 *   Callers (especially the GUI) depend only on this interface, not the
 *   concrete ManagementSystem class. This decouples the UI from the backend
 *   and makes the system easily testable/replaceable.
 */
public interface HospitalService {

    // ── Staff management ───────────────────────────────────────────────────────

    boolean addDoctor(String id, String name, String phone, Address address, int capacity);
    boolean addNurse(String id, String name, String phone, Address address, int capacity);
    boolean addPatient(String id, String name, String phone, Address address, String wardNumber);

    boolean removeDoctor(String id);
    boolean removeNurse(String id);
    boolean removePatient(String id);

    Doctor  findDoctor(String id);
    Nurse   findNurse(String id);
    Patient findPatient(String id);

    List<Doctor>  getAllDoctors();
    List<Nurse>   getAllNurses();
    List<Patient> getAllPatients();

    // ── Appointments ───────────────────────────────────────────────────────────

    boolean bookAppointment(String doctorId, String patientId);
    boolean cancelAppointment(String doctorId, String patientId);
    List<String> getAppointmentSummary();

    // ── Building / Ward management ─────────────────────────────────────────────

    void initBuilding(String buildingName, int rooms, int wardsPerRoom);

    boolean assignDoctorToWard(int room, int ward, String doctorId);
    boolean assignNurseToWard(int room, int ward, String nurseId);
    boolean assignPatientToWard(int room, int ward, String patientId);

    String getBuildingSummary();
}
