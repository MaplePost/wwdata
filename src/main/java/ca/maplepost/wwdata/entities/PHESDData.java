/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.maplepost.wwdata.entities;

import java.util.Date;

/**
 *
 * @author peterslack
 */
public class PHESDData {
  private Date  sampleDate;
  private String sampleID;
  private String siteID;
  private String siteName;
  private String  reportDate;
  private Double covN1_nPMMoV_meanNr;
  private Double covN1_nPMMoV_sdNr;
  private Double covN2_nPMMoV_meanNr;
  private Double covN2_nPMMoV_sdNr;
  private Double nPPMoV_Ct_mean;

    public PHESDData(PHESDDataRaw rawData) {
        
        sampleDate = rawData.getSampleDate();
        sampleID = rawData.getSampleID();
        siteID = rawData.getSiteID();
        siteName = rawData.getSiteName();
        reportDate = rawData.getReportDate();
        
        //we have to do this because they decided to put NA in the numeric data
        if (rawData.getCovN1_nPMMoV_meanNr().equals("NA")) {
            rawData.setCovN1_nPMMoV_meanNr("0.0");
        }
        if (rawData.getCovN1_nPMMoV_sdNr().equals("NA")) {
            rawData.setCovN1_nPMMoV_sdNr("0.0");
        }
        if (rawData.getCovN2_nPMMoV_meanNr().equals("NA")) {
            rawData.setCovN2_nPMMoV_meanNr("0.0");
        }
        if (rawData.getCovN2_nPMMoV_sdNr().equals("NA")) {
            rawData.setCovN2_nPMMoV_sdNr("0.0");
        }
        if (rawData.getnPPMoV_Ct_mean().equals("NA")) {
            rawData.setnPPMoV_Ct_mean("0.0");
        }
        
        covN1_nPMMoV_meanNr = Double.parseDouble(rawData.getCovN1_nPMMoV_meanNr());
        covN1_nPMMoV_sdNr = Double.parseDouble(rawData.getCovN1_nPMMoV_sdNr());
        covN2_nPMMoV_meanNr = Double.parseDouble(rawData.getCovN2_nPMMoV_meanNr());
        covN2_nPMMoV_sdNr = Double.parseDouble(rawData.getCovN2_nPMMoV_sdNr());
        nPPMoV_Ct_mean = Double.parseDouble(rawData.getnPPMoV_Ct_mean());
        
    }

  
  
    public Date getSampleDate() {
        return sampleDate;
    }

    public void setSampleDate(Date sampleDate) {
        this.sampleDate = sampleDate;
    }

    public String getSampleID() {
        return sampleID;
    }

    public void setSampleID(String sampleID) {
        this.sampleID = sampleID;
    }

    public String getSiteID() {
        return siteID;
    }

    public void setSiteID(String siteID) {
        this.siteID = siteID;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public Double getCovN1_nPMMoV_meanNr() {
        return covN1_nPMMoV_meanNr;
    }

    public void setCovN1_nPMMoV_meanNr(Double covN1_nPMMoV_meanNr) {
        this.covN1_nPMMoV_meanNr = covN1_nPMMoV_meanNr;
    }

    public Double getCovN1_nPMMoV_sdNr() {
        return covN1_nPMMoV_sdNr;
    }

    public void setCovN1_nPMMoV_sdNr(Double covN1_nPMMoV_sdNr) {
        this.covN1_nPMMoV_sdNr = covN1_nPMMoV_sdNr;
    }

    public Double getCovN2_nPMMoV_meanNr() {
        return covN2_nPMMoV_meanNr;
    }

    public void setCovN2_nPMMoV_meanNr(Double covN2_nPMMoV_meanNr) {
        this.covN2_nPMMoV_meanNr = covN2_nPMMoV_meanNr;
    }

    public Double getCovN2_nPMMoV_sdNr() {
        return covN2_nPMMoV_sdNr;
    }

    public void setCovN2_nPMMoV_sdNr(Double covN2_nPMMoV_sdNr) {
        this.covN2_nPMMoV_sdNr = covN2_nPMMoV_sdNr;
    }

    public Double getnPPMoV_Ct_mean() {
        return nPPMoV_Ct_mean;
    }

    public void setnPPMoV_Ct_mean(Double nPPMoV_Ct_mean) {
        this.nPPMoV_Ct_mean = nPPMoV_Ct_mean;
    }

  
  
}
