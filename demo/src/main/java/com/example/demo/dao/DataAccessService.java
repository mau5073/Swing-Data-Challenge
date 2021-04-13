package com.example.demo.dao;

import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

@Repository
public class DataAccessService {
    static HashMap<Integer, double[]> csvData = new HashMap<Integer, double[]>();

    static public HashMap<Integer, double[]>  ReadData(String path){
        //x will be used to keep track of the row that is being read from the file that data needs to be taking in
        //starts at 1 so that consistency between csv file and data structure is made (1 in structure is 1 in csv)
        int x = 1;
        int rowLength = 0;
        String line= "";
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while((line = br.readLine()) != null){
                rowLength = (line.split(",")).length;
                // row will hold the data values in each row of the csv file
                double[] row = new double[rowLength];

                // loop through the row obtained by splitting line and enter it into the row array
                for(int i = 0; i < rowLength; i++){
                    row[i] = Double.parseDouble(line.split(",")[i]);
                }
                //add the new row array to data entering the corresponding row number x that it came from in the file
                csvData.put(x,row);
                x++;
            }
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvData;
    }
}
