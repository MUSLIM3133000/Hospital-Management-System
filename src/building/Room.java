package building;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a Room in the hospital building.
 * A Room contains a fixed number of Wards, created at construction time.
 */
public class Room {

    private final int roomNumber;
    private final List<Ward> wards;

    /**
     * @param roomNumber   Unique room identifier
     * @param wardCount    Number of wards to create inside this room
     */
    public Room(int roomNumber, int wardCount) {
        this.roomNumber = roomNumber;
        this.wards = new ArrayList<>();
        for (int i = 1; i <= wardCount; i++) {
            wards.add(new Ward(i));
        }
    }

    /**
     * Returns the Ward at the given 1-based ward number.
     *
     * @param wardNumber 1-based ward number
     * @return the Ward, or null if invalid
     */
    public Ward getWard(int wardNumber) {
        if (wardNumber < 1 || wardNumber > wards.size()) return null;
        return wards.get(wardNumber - 1);
    }

    public int getRoomNumber()       { return roomNumber; }
    public int getWardCount()        { return wards.size(); }
    public List<Ward> getAllWards()  { return Collections.unmodifiableList(wards); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Room %d (%d wards)\n", roomNumber, wards.size()));
        for (Ward w : wards) {
            sb.append(w).append("\n");
        }
        return sb.toString();
    }
}
