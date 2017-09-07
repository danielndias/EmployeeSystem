package com.danieldias;


import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that a models a Employee of the Company
 *
 * @author Daniel Dias
 * @version 1.0
 */
public class Employee {

    private String fName;
    private String lName;
    private int id;
    private Position position;
    private Department dept;
    private double salary;

    /**
     * Default constructor of the Employees Class. Construct
     * an object without setting any parameter.
     */
    public Employee() {
    }

    /**
     * Construct an Employee object giving all the parameters.
     *
     * @param fName    the Employee's First Name
     * @param lName    the Employee's Last Name
     * @param id       the Employee's ID Number
     * @param position a Position object corresponding to the Employee's currently position
     * @param dept     a Department object corresponding to the Employee's Department
     * @param salary   the Employee's salary
     */
    public Employee(String fName, String lName, int id, Position position, Department dept, double salary) {
        this.fName = fName;
        this.lName = lName;
        this.id = id;
        this.position = position;
        this.dept = dept;
        this.salary = salary;
    }

    /**
     * Retrieves the Employee's first name
     *
     * @return the first name of the employee
     */
    public String getFName() {
        return fName;
    }

    /**
     * Set the first name attribute using the name given. The name cannot
     * be empty or have any special character. If it is, an exception is thrown.
     *
     * @param fName the employee's first name
     */
    public void setFName(String fName) {

        if (fName.equalsIgnoreCase("") || (fName == null)) {
            throw new IllegalArgumentException("Name cannot be empty");
        } else if (checkPattern(fName)) {
            throw new IllegalArgumentException("Name cannot contain any special characters");
        }
        this.fName = fName;
    }

    /**
     * Retrieves the Employee's last name
     *
     * @return the last name of the employee
     */
    public String getLName() {
        return lName;
    }

    /**
     * Set the last name attribute using the name given. The name cannot
     * be empty or have any special character. If it is, an exception is thrown.
     *
     * @param lName the employee's last name
     */
    public void setLName(String lName) {
        if (lName.equalsIgnoreCase("") || (lName == null)) {
            throw new IllegalArgumentException("Surname cannot be empty");
        } else if (checkPattern(lName)) {
            throw new IllegalArgumentException("Surname cannot contain any special characters");
        }
        this.lName = lName;
    }

    /**
     * Retrieves a String containing the Employee's ID Number
     *
     * @return the ID Number of the employee
     */
    public int getId() {
        return id;
    }

    /**
     * Set the employees ID
     *
     * @param id the employee's ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the Position object corresponding to the employee position
     *
     * @return the Position object
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Set the position attribute receiving a Position object.
     * If the position is not set, an exception is thrown
     *
     * @param position the Position object
     */
    public void setPosition(Position position) {
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be empty");
        }
        this.position = position;
    }

    /**
     * Retrieves the Department object corresponding to the employee department
     *
     * @return the Department object
     */
    public Department getDepartment() {
        return dept;
    }

    /**
     * Set the department attribute receiving a Department object
     * If the department is not set, an exception is thrown
     *
     * @param dept the Department object
     */
    public void setDept(Department dept) {
        if (dept == null) {
            throw new IllegalArgumentException("Department cannot be empty");
        }
        this.dept = dept;
    }

    /**
     * Retrieves the Employee's salary
     *
     * @return the salary of the employee
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Set the Employee's salary. The salary needs to be higher than 0.
     * If it isn't, an exception is thrown.
     *
     * @param salary the salary of the employee
     */
    public void setSalary(double salary) {
        if (salary <= 0) {
            throw new IllegalArgumentException("Salary needs to be higher than 0");
        }
        this.salary = salary;
    }

    /**
     * Checks if the name typed contains any special character.
     *
     * @param name the employee's name or surname
     * @return true if there's any special character
     */
    public boolean checkPattern(String name) {
        Pattern p = Pattern.compile("[^A-Za-z0-9\\-\\.]");
        Matcher m = p.matcher(name);
        boolean b = m.find();
        return b;
    }

    /**
     * Retrieves a String containing the salary value in Currency Format.
     *
     * @return the salary value in Currency Format.
     */
    public String strSalary() {
        DecimalFormat format = new DecimalFormat("#,###.00");
        return String.format("$" + format.format(getSalary()));
    }

    /**
     * Retrieves a String formatted to be inserted in the File
     *
     * @return the formatted String
     */
    public String toFileString() {
        return this.getId() + "," + this.getLName() + "," + this.getFName() + "," + this.getPosition().getId() + "," +
                this.getDepartment().getId() + "," + this.getSalary();
    }
}
