package com.xelba.authutil;

public enum Permission {
    Employee_Create("Emplyee_Create"),
    Employee_Update("Employee_Update"),
    Employee_Delete("Employee_Delete");

    private String action;

    Permission(String action) {
        this.action = action;
    }

    /**
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }
}