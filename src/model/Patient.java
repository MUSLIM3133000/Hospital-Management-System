package model;

/**
 * Represents a Patient in the hospital.
 *
 * OOP Concepts:
 *  - INHERITANCE  : extends PersonDetails
 *  - POLYMORPHISM : overrides getRole() and toString()
 */
public class Patient extends PersonDetails {

    private String assignedWardNumber;

    /**
     * @param id                Unique patient ID
     * @param name              Full name
     * @param phone             Contact number
     * @param address           Physical address
     * @param assignedWardNumber Ward number the patient is assigned to
     */
    public Patient(String id, String name, String phone, Address address, String assignedWardNumber) {
        super(id, name, phone, address);
        this.assignedWardNumber = assignedWardNumber;
    }

    public String getAssignedWardNumber()              { return assignedWardNumber; }
    public void setAssignedWardNumber(String wardNumber) { this.assignedWardNumber = wardNumber; }

    @Override
    public String getRole() { return "Patient"; }

    @Override
    public String toString() {
        return super.toString() + " | Ward: " + assignedWardNumber;
    }
}
