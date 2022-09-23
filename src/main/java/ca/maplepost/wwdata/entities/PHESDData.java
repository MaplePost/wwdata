/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.maplepost.wwdata.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

/**
 *
 * @author peterslack
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PHESDData {
    
  private Date  sampleDate;
  private String sampleID;
  private String siteID;
  private String siteName;
  private String  reportDate;
  private String covN1_nPMMoV_meanNr;
  private String covN1_nPMMoV_sdNr;
  private String covN2_nPMMoV_meanNr;
  private String covN2_nPMMoV_sdNr;
  private String nPPMoV_Ct_mean;
/*    "detectB117",
    "fractionB117",
    "fractionB117_stdev",
    "test_delta",
    "detect_delta",
    "fraction_delta",
    "fraction_delta_stdev",
    "testC2811T",
    "detectC2811T",
    "fractionC2811T",
    "fractionC2811T_stdev"
*/

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

    public String getCovN1_nPMMoV_meanNr() {
        return covN1_nPMMoV_meanNr;
    }

    public void setCovN1_nPMMoV_meanNr(String covN1_nPMMoV_meanNr) {
        this.covN1_nPMMoV_meanNr = covN1_nPMMoV_meanNr;
    }

    public String getCovN1_nPMMoV_sdNr() {
        return covN1_nPMMoV_sdNr;
    }

    public void setCovN1_nPMMoV_sdNr(String covN1_nPMMoV_sdNr) {
        this.covN1_nPMMoV_sdNr = covN1_nPMMoV_sdNr;
    }

    public String getCovN2_nPMMoV_meanNr() {
        return covN2_nPMMoV_meanNr;
    }

    public void setCovN2_nPMMoV_meanNr(String covN2_nPMMoV_meanNr) {
        this.covN2_nPMMoV_meanNr = covN2_nPMMoV_meanNr;
    }

    public String getCovN2_nPMMoV_sdNr() {
        return covN2_nPMMoV_sdNr;
    }

    public void setCovN2_nPMMoV_sdNr(String covN2_nPMMoV_sdNr) {
        this.covN2_nPMMoV_sdNr = covN2_nPMMoV_sdNr;
    }

    public String getnPPMoV_Ct_mean() {
        return nPPMoV_Ct_mean;
    }

    public void setnPPMoV_Ct_mean(String nPPMoV_Ct_mean) {
        this.nPPMoV_Ct_mean = nPPMoV_Ct_mean;
    }


  
  
    
}
