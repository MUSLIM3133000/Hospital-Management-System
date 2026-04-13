package building;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the entire hospital building.
 * Contains multiple Rooms, each with multiple Wards.
 */
public class Building {

    private final String buildingName;
    private final List<Room> rooms;

    /**
     * @param buildingName Name of the building
     * @param roomCount    Number of rooms to initialise
     * @param wardsPerRoom Number of wards in each room
     */
    public Building(String buildingName, int roomCount, int wardsPerRoom) {
        this.buildingName = buildingName;
        this.rooms = new ArrayList<>();
        for (int i = 1; i <= roomCount; i++) {
            rooms.add(new Room(i, wardsPerRoom));
        }
    }

    /**
     * Returns the Room with the given 1-based room number.
     *
     * @param roomNumber 1-based room number
     * @return the Room, or null if not found
     */
    public Room getRoom(int roomNumber) {
        if (roomNumber < 1 || roomNumber > rooms.size()) return null;
        return rooms.get(roomNumber - 1);
    }

    public String getBuildingName()    { return buildingName; }
    public int getRoomCount()          { return rooms.size(); }
    public List<Room> getAllRooms()    { return Collections.unmodifiableList(rooms); }

    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Building: ").append(buildingName).append(" ===\n");
        for (Room r : rooms) {
            sb.append(r);
        }
        return sb.toString();
    }
}
