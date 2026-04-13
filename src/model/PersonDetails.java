package model;

/**
 * Abstract base class representing a person in the hospital system.
 * Demonstrates ABSTRACTION and serves as the root for all hospital staff and patients.
 *
 * OOP Concepts used:
 *  - Abstraction: getRole() forces subclasses to define their role
 *  - Encapsulation: all fields private, accessed through getters
 */
public abstract class PersonDetails {

    private String id;
    private String name;
    private String phone;
    private Address address;

    /**
     * Constructor for PersonDetails.
     *
     * @param id      Unique identifier
     * @param name    Full name
     * @param phone   Contact number
     * @param address Address object
     */
    public PersonDetails(String id, String name, String phone, Address address) {
        this.id      = id;
        this.name    = name;
        this.phone   = phone;
        this.address = address;
    }

    // ── Getters ────────────────────────────────────────────────────────────────

    public String getId()      { return id; }
    public String getName()    { return name; }
    public String getPhone()   { return phone; }
    public Address getAddress() { return address; }

    // ── Setters ────────────────────────────────────────────────────────────────

    public void setName(String name)       { this.name    = name; }
    public void setPhone(String phone)     { this.phone   = phone; }
    public void setAddress(Address address){ this.address = address; }

    /**
     * Abstract method — every subclass must declare its role.
     * This is the core ABSTRACTION hook.
     */
    public abstract String getRole();

    /**
     * Common toString foundation; subclasses call super.toString()
     * to add their specific fields.
     */
    @Override
    public String toString() {
        return String.format(
            "[%s] ID: %-8s | Name: %-20s | Phone: %-15s | Address: %s",
            getRole(), id, name, phone, address
        );
    }
}
