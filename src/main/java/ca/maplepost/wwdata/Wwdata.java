/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package ca.maplepost.wwdata;

import ca.maplepost.wwdata.entities.PHESDData;
import ca.maplepost.wwdata.entities.PHESDDataRaw;
import ca.maplepost.wwdata.entities.WwWebData;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author peterslack
 */
public class Wwdata {

    private static final Logger LOGGER = Logger.getLogger(Wwdata.class.getName());

    /**
     * This processes the data from Ottawa hospital git hub site
     * This is the first processor in the chain
     * @param baseDir  the base working directory passed by our maven build (refer to pom.xml)
     * @return true if processing succeeded, false otherwise
     */
    private static Boolean processPHESDData(String baseDir, WwWebData web) {
        
        String dataFileLocation = baseDir + File.separator + "rawdata" + File.separator
                + "PHESD" + File.separator
                + "Wastewater" + File.separator
                + "Ottawa" + File.separator
                + "Data" + File.separator
                + "wastewater_virus.csv";

        CsvMapper csvMapper = new CsvMapper();

        CsvSchema schema = CsvSchema.emptySchema().withHeader();

        ObjectReader oReader = csvMapper.reader(PHESDDataRaw.class).with(schema);
           Date lastUpdated = null;
            Double lastVal = 0.0;
            Double currentVal = 0.0;
            List<PHESDData> data = web.getPhesdData();
        try ( Reader reader = new FileReader(dataFileLocation)) {
            MappingIterator<PHESDDataRaw> mi = oReader.readValues(reader);

            
            while (mi.hasNext()) {
                PHESDDataRaw current = mi.next();
                if (lastUpdated == null) {
                    lastUpdated = current.getSampleDate();
                } else {
                    if (lastUpdated.compareTo(current.getSampleDate()) < 0) {
                        lastUpdated = current.getSampleDate();
                        currentVal = Double.parseDouble(current.getnPPMoV_Ct_mean());
                    }
                }                
                data.add(new PHESDData(current));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Wwdata.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(Wwdata.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        web.setLastUpdated(lastUpdated);
        web.setCurrentValue(currentVal);
        web.setLastValue(data.get(data.size()-2).getnPPMoV_Ct_mean());
        web.setPhesdData(data);
        
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json ="";
        try {
            json = ow.writeValueAsString(web);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Wwdata.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        String outFile = baseDir + File.separator + "docs" + File.separator + "js" + File.separator + "wwdata.json";
        
        try {
            Files.write(Path.of(outFile),json.getBytes());
        } catch (IOException ex) {
            Logger.getLogger(Wwdata.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return true;
    }

    public static void main(String[] args) throws Exception {

        LOGGER.log(Level.INFO, "****Maven build git data repo processor*****");

        if (args.length == 0) {
            throw (new Exception("Must supply base directory as an argument"));
        }

        File baseDir = new File(args[0]);

        if (!baseDir.exists()) {
            throw (new Exception("Base Directory Does Not exist"));
        }
        
        WwWebData wd = new WwWebData();
        
        if (! processPHESDData(baseDir.getAbsolutePath(), wd)) {
            throw (new Exception("Error processing PHESD data"));
        }
        
        

    }
}
