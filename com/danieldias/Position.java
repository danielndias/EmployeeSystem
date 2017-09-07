package com.danieldias;

import java.util.HashMap;


/**
 * A enum that stores constants representing all the companies existing positions
 *
 * @author Daniel Dias
 * @version 1.0
 */
public enum Position {
    CEO (10, "CEO"),
    DIRECTOR (11, "Director"),
    MANAGER (12, "Manager"),
    SUPERVISOR (13, "Supervisor"),
    ACCOUNTANT (21, "Accountant"),
    BOOKKEEPER(22, "Bookkeeper"),
    J_DEVELOPER (31, "Java Developer"),
    NET_DEVELOPER (32, ".NET Developer"),
    M_DEVELOPER (33, "Mobile Developer"),
    WEB_DEVELOPER (34, "Web Developer"),
    HR_ANALYST (41, "HR Analyst"),
    SUPPORT_T (51, "Support Technician"),
    SUPPORT_A (52, "Support Analyst"),
    SALESMAN (61, "Salesman");

    private int id;
    private String posName;
    private static HashMap <Integer, Position> lookupByNumber = null;

    /**
     * Construct a Department Enum object.
     *
     * @param posId the id corresponding to the position
     * @param posName the position name
     */
    Position(int posId, String posName) {
        this.id = posId;
        this.posName = posName;
    }

    /**
     * Retrieves the Position's ID
     *
     * @return the id of the position
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the Position's name
     *
     * @return the name of the position
     */
    public String getPosName() {
        return posName;
    }

    /**
     * Retrieves a String containing the Position's name
     *
     * @return
     */
    public String toString() {
        return this.posName;
    }

    /**
     * Retrieves the Position object corresponding to the ID given
     *
     * @param id the ID of the position to retrieve
     * @return the Position object
     */
    public static Position getPosition(int id) {

        if (lookupByNumber == null) {
            initNumberLookup();
        }

        Position position = lookupByNumber.get(Integer.valueOf(id));

        if (position == null) {
            throw new IllegalArgumentException("Invalid Position");
        } else {
            return position;
        }
    }

    /**
     * Creates the HashMap that will be used by the getPosition() function
     */
    private static void initNumberLookup() {

        lookupByNumber = new HashMap<>();

        for (Position pos : values()) {
            lookupByNumber.put(pos.id, pos);
        }
    }

}
