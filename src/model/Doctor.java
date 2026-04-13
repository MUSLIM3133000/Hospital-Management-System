package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a Doctor in the hospital.
 *
 * OOP Concepts:
 *  - INHERITANCE : extends PersonDetails
 *  - ENCAPSULATION : appointment list is private, exposed via read-only view
 *  - POLYMORPHISM  : overrides getRole() and toString()
 */
public class Doctor extends PersonDetails {

    private final int maxPatientCapacity;
    private final List<String> assignedPatientIds;

    /**
     * @param id                  Unique doctor ID
     * @param name                Full name
     * @param phone               Contact number
     * @param address             Physical address
     * @param maxPatientCapacity  Maximum number of patients this doctor can handle
     */
    public Doctor(String id, String name, String phone, Address address, int maxPatientCapacity) {
        super(id, name, phone, address);
        this.maxPatientCapacity = maxPatientCapacity;
        this.assignedPatientIds = new ArrayList<>();
    }

    // ── Appointment management ─────────────────────────────────────────────────

    /**
     * Assigns a patient ID to this doctor if capacity allows.
     *
     * @param patientId Patient's unique ID
     * @return true if assigned successfully, false if already at capacity
     */
    public boolean assignPatient(String patientId) {
        if (assignedPatientIds.size() >= maxPatientCapacity) {
            return false;
        }
        if (!assignedPatientIds.contains(patientId)) {
            assignedPatientIds.add(patientId);
        }
        return true;
    }

    /**
     * Removes a patient assignment from this doctor.
     *
     * @param patientId Patient's ID to remove
     * @return true if removed, false if patient was not assigned
     */
    public boolean removePatient(String patientId) {
        return assignedPatientIds.remove(patientId);
    }

    /** @return Unmodifiable view of assigned patient IDs */
    public List<String> getAssignedPatientIds() {
        return Collections.unmodifiableList(assignedPatientIds);
    }

    public int getMaxPatientCapacity()  { return maxPatientCapacity; }
    public int getCurrentPatientCount() { return assignedPatientIds.size(); }
    public boolean isFull()             { return assignedPatientIds.size() >= maxPatientCapacity; }

    @Override
    public String getRole() { return "Doctor"; }

    @Override
    public String toString() {
        return super.toString() + String.format(
            " | Capacity: %d/%d",
            assignedPatientIds.size(), maxPatientCapacity
        );
    }
}
