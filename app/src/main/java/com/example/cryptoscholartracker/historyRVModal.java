package com.example.cryptoscholartracker;

import java.io.Serializable;

public class historyRVModal implements Serializable {

    private String roninname;
    private String roninAddress;
    private float manager;
    private float scholar;

    public historyRVModal(String roninname, String roninAddress, float manager, float scholar ) {
        this.roninname = roninname;
        this.roninAddress = roninAddress;
        this.manager = manager;
        this.scholar = scholar;
    }

    public String getRoninname() {
        return roninname;
    }

    public float getManager() {
        return manager;
    }

    public void setManager(float manager) {
        this.manager = manager;
    }

    public float getScholar() {
        return scholar;
    }

    public void setScholar(float scholar) {
        this.scholar = scholar;
    }

    public void setRoninname(String roninname) {
        this.roninname = roninname;
    }

    public String getRoninAddress() {
        return roninAddress;
    }

    public void setRoninAddress(String roninAddress) {
        this.roninAddress = roninAddress;
    }
}
