package com.danieldias;

import java.util.HashMap;


/**
 * A enum that stores constants representing all the companies Departments
 *
 * @author Daniel Dias
 * @version 1.0
 */
public enum Department {
    MANAGEMENT (1,"Management"),
    FINANCIAL (2,"Financial"),
    DEVELOPMENT (3,"Development"),
    HR (4,"Human Resources"),
    SUPPORT (5,"Support"),
    SALES (6,"Sales");

    private int id;
    private String deptName;
    private static HashMap <Integer, Department> lookupByNumber = null;

    /**
     * Construct a Department Enum object.
     *
     * @param id the ID corresponding to the Department
     * @param deptName the name of the department
     */
    Department(int id, String deptName) {
        this.id = id;
        this.deptName = deptName;
    }

    /**
     * Retrieves the Department's ID
     *
     * @return the ID of the department
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the Department's Name
     *
     * @return the name of the department
     */
    public String getDepName() {
        return deptName;
    }

    /**
     * Retrieves a String containing the Department's Name
     *
     * @return a String with the name of the Department
     */
    public String toString() {
        return this.deptName;
    }


    /**
     * Retrieves the Department object receiving the corresponding ID
     *
     * @param id the id of the department
     * @return the department object
     */
    public static Department getDepartment(int id) {

        if (lookupByNumber == null) {
            initNumberLookUp();
        }

        Department dept = lookupByNumber.get(Integer.valueOf(id));

        if (dept == null) {
            throw new IllegalArgumentException("Invalid Department");
        } else {
            return dept;
        }
    }

    /**
     * Creates the HashMap that will be used by the getDepartment() function
     */
    private static void initNumberLookUp() {

        lookupByNumber = new HashMap<>();

        for (Department dept : values()) {
            lookupByNumber.put(dept.id, dept);
        }
    }

}
