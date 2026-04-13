package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a Nurse in the hospital.
 *
 * OOP Concepts:
 *  - INHERITANCE  : extends PersonDetails
 *  - POLYMORPHISM : overrides getRole() and toString()
 */
public class Nurse extends PersonDetails {

    private final int maxWardCapacity;
    private final List<Integer> assignedWardNumbers;

    /**
     * @param id              Unique nurse ID
     * @param name            Full name
     * @param phone           Contact number
     * @param address         Physical address
     * @param maxWardCapacity Maximum number of wards this nurse can be assigned to
     */
    public Nurse(String id, String name, String phone, Address address, int maxWardCapacity) {
        super(id, name, phone, address);
        this.maxWardCapacity    = maxWardCapacity;
        this.assignedWardNumbers = new ArrayList<>();
    }

    /**
     * Assigns nurse to a ward number if capacity allows.
     *
     * @param wardNumber Ward number to assign
     * @return true if successful, false if already at capacity or already assigned
     */
    public boolean assignToWard(int wardNumber) {
        if (assignedWardNumbers.size() >= maxWardCapacity) return false;
        if (!assignedWardNumbers.contains(wardNumber)) {
            assignedWardNumbers.add(wardNumber);
        }
        return true;
    }

    /** @return Unmodifiable list of assigned ward numbers */
    public List<Integer> getAssignedWardNumbers() {
        return Collections.unmodifiableList(assignedWardNumbers);
    }

    public int getMaxWardCapacity()    { return maxWardCapacity; }
    public int getCurrentWardCount()   { return assignedWardNumbers.size(); }

    @Override
    public String getRole() { return "Nurse"; }

    @Override
    public String toString() {
        return super.toString() + String.format(
            " | Ward Capacity: %d/%d",
            assignedWardNumbers.size(), maxWardCapacity
        );
    }
}
