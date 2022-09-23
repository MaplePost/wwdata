/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.maplepost.wwdata.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The class that will be converted to json for our static web page
 * @author peterslack
 */
public class WwWebData {
    
    private Double lastValue;
    private Double currentValue;
    private Date lastUpdated;
    private List<PHESDData> phesdData;

    public WwWebData() {
        phesdData = new ArrayList<>();
    }

    public Double getLastValue() {
        return lastValue;
    }

    public void setLastValue(Double lastValue) {
        this.lastValue = lastValue;
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public List<PHESDData> getPhesdData() {
        return phesdData;
    }

    public void setPhesdData(List<PHESDData> phesdData) {
        this.phesdData = phesdData;
    }
    
    
    
}
