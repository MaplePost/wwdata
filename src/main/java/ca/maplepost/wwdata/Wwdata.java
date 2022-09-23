/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package ca.maplepost.wwdata;

import ca.maplepost.wwdata.entities.PHESDData;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author peterslack
 */
public class Wwdata {

       private static final Logger LOGGER = Logger.getLogger(NonBlockingDeserializer.class.getName());

    public static class DHelper extends DeserializationProblemHandler{

        @Override
        public Object handleWeirdNumberValue(DeserializationContext ctxt, Class<?> targetType, Number valueToConvert, String failureMsg) throws IOException {
            return super.handleWeirdNumberValue(ctxt, targetType, valueToConvert, failureMsg); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }
        
        
    }
       
       
    public static class NonBlockingDeserializer<T> extends JsonDeserializer<T> {

 
    private StdDeserializer<T> delegate;

    public NonBlockingDeserializer(StdDeserializer<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public T deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        try {

            // Delegate the deserialization
            return delegate.deserialize(jp, ctxt);

        } catch (JsonProcessingException e) {

            // Log the exception
            logException(e);

            // Return default null value
            return delegate.getNullValue(ctxt);
        }
    }

    private void logException(JsonProcessingException e) {

        StringBuilder builder = new StringBuilder(e.getOriginalMessage() + System.lineSeparator());

        builder.append(String.format("Source: %s \n", e.getLocation().getSourceRef()));
        builder.append(String.format("Line: %s \n", e.getLocation().getLineNr()));
        builder.append(String.format("Column: %s \n", e.getLocation().getColumnNr()));

//        if (e instanceof InvalidFormatException) {
//
//            InvalidFormatException e1 = (InvalidFormatException) e;
//            builder.append(String.format("Value: %s \n", e1.getValue()));
//            builder.append(String.format("Value type: %s \n", e1.getValue().getClass().getTypeName()));
//            builder.append(String.format("Target type: %s \n", e1.getTargetType().getTypeName()));
//
//        } else if (e instanceof UnrecognizedPropertyException) {
//
//            UnrecognizedPropertyException e1 = (UnrecognizedPropertyException) e;
//            builder.append(String.format("Property name: %s \n", e1.getPropertyName()));
//            builder.append(String.format("Known properties: %s \n", e1.getKnownPropertyIds()));
//
//        }

        LOGGER.warning(builder.toString());
    }
};
    
    
    
    public static void main(String[] args) {
        System.out.println("Hello World!");
        
        System.out.println(args[0]);

String dataFileLocation = args[0] + File.separator + "rawdata"+ File.separator + 
        "PHESD"+ File.separator + 
        "Wastewater"+ File.separator + 
        "Ottawa"+ File.separator + 
        "Data"+ File.separator + 
        "wastewater_virus.csv" ;        
        
// Create module for custom deserializers
SimpleModule module = new SimpleModule("customDeserializers", Version.unknownVersion());

// Add deserializers for primitive types
module.addDeserializer(Double.TYPE,   new NonBlockingDeserializer<Double>(
                                      new NumberDeserializers.DoubleDeserializer(Double.TYPE, 0.d)));
module.addDeserializer(Integer.TYPE,  new NonBlockingDeserializer<Integer>(
                                      new NumberDeserializers.IntegerDeserializer(Integer.TYPE, 0)));



        CsvMapper csvMapper = new CsvMapper();
        csvMapper.addHandler(new DHelper());
                
CsvSchema schema = CsvSchema.emptySchema().withHeader(); 
 
ObjectReader oReader = csvMapper.reader(PHESDData.class).with(schema);

try (Reader reader = new FileReader(dataFileLocation)) {
    MappingIterator<PHESDData> mi = oReader.readValues(reader);
    
    while(mi.hasNext()) {
        PHESDData current = mi.next();
        System.out.println(current.getSampleDate() + " " + current.getnPPMoV_Ct_mean());
    }
}       catch (FileNotFoundException ex) {
            Logger.getLogger(Wwdata.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Wwdata.class.getName()).log(Level.SEVERE, null, ex);
        } 


//Read more: https://www.java67.com/2019/05/how-to-read-csv-file-in-java-using-jackson-library.html#ixzz7fjIJPvr4
        
        
        
    }
}
