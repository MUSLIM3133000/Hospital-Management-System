package model;

/**
 * Immutable value object representing a physical address.
 * Fully encapsulated — all fields are private and set via constructor only.
 */
public class Address {

    private final String zip;
    private final String city;
    private final String street;

    /**
     * @param zip    Postal / ZIP code
     * @param city   City name
     * @param street Street name and number
     */
    public Address(String zip, String city, String street) {
        this.zip    = zip;
        this.city   = city;
        this.street = street;
    }

    public String getZip()    { return zip; }
    public String getCity()   { return city; }
    public String getStreet() { return street; }

    @Override
    public String toString() {
        return street + ", " + city + " " + zip;
    }
}
