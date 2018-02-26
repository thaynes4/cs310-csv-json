package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import com.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Converter {
    
    /*
    
        Consider the following CSV data:
        
        "ID","Total","Assignment 1","Assignment 2","Exam 1"
        "111278","611","146","128","337"
        "111352","867","227","228","412"
        "111373","461","96","90","275"
        "111305","835","220","217","398"
        "111399","898","226","229","443"
        "111160","454","77","125","252"
        "111276","579","130","111","338"
        "111241","973","236","237","500"
        
        The corresponding JSON data would be similar to the following (tabs and other whitespace
        have been added for clarity).  Note the curly braces, square brackets, and double-quotes!
        
        {
            "colHeaders":["ID","Total","Assignment 1","Assignment 2","Exam 1"],
            "rowHeaders":["111278","111352","111373","111305","111399","111160","111276","111241"],
            "data":[[611,146,128,337],
                    [867,227,228,412],
                    [461,96,90,275],
                    [835,220,217,398],
                    [898,226,229,443],
                    [454,77,125,252],
                    [579,130,111,338],
                    [973,236,237,500]
            ]
        }
    
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String results = "";
        
        try {
            
            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> full = reader.readAll();
            Iterator<String[]> iterator = full.iterator();
            
            JSONObject jsonObject = new JSONObject();
            
            // INSERT YOUR CODE HERE
            String [] rows;
            JSONArray jsonArray = new JSONArray();
            JSONArray data = new JSONArray();
            JSONArray jsonArrayTwo = new JSONArray();
            JSONArray jsonArrayThree = new JSONArray();
            rows = iterator.next();
            for(int i = 0; i < rows.length; ++i){
                jsonArrayTwo.add(rows[i]);
            }
            while(iterator.hasNext()){
                rows = iterator.next();
                jsonArray.add(rows[0]);
                for(int j = 1; j < rows.length; ++j){
                    jsonArrayThree.add(Integer.parseInt(rows[j]));
                }
                data.add(jsonArrayThree);
                jsonArrayThree = new JSONArray();
            }
            jsonObject.put("colHeaders",jsonArrayTwo);
            jsonObject.put("rowHeaders",jsonArray);
            jsonObject.put("data", data);
            results = jsonObject.toString();
            //System.out.println(jsonObject.toString());
            
        }
        
        catch(IOException e) { return e.toString(); }
        
        return results.trim();
        
    }
    
    public static String jsonToCsv(String jsonString) {
        
        String results = "";
        
        try {
            
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject)parser.parse(jsonString);
            
            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\n');
            
            // INSERT YOUR CODE HERE
            JSONArray row = (JSONArray) jsonObject.get("rowHeaders");
            JSONArray column = (JSONArray) jsonObject.get("colHeaders");
            JSONArray datdata = (JSONArray) jsonObject.get("data");
            String [] easyColumn = new String[column.size()];
            String [] easyRow = new String[row.size()];
            String [] easyData = new String[datdata.size()];
            for(int i = 0; i < column.size(); ++i){
                easyColumn[i] = column.get(i) + "";
            }
            for(int j = 0; j < row.size(); ++j){
                easyRow[j] = row.get(j) + "";
            }
            for(int k = 0; k < datdata.size(); ++k){
                easyData[k] = datdata.get(k) + "";
            }
            csvWriter.writeNext(easyColumn);
            for(int l = 0; l < easyRow.length; ++l){
                String [] stuff = new String[5];
                easyData[l] = easyData[l].replace("[","");
                easyData[l] = easyData[l].replace("]","");
                String [] stuffTwo = easyData[l].split(",");
                stuff[0] = easyRow[l];
                for(int m = 0; m < stuffTwo.length; ++m){
                    stuff[m+1] = stuffTwo[m];
                }
                csvWriter.writeNext(stuff);
            }
            results = writer.toString();
            //System.out.println(writer.toString());
            
        }
        
        catch(ParseException e) { return e.toString(); }
        
        return results.trim();
        
    }
	
}