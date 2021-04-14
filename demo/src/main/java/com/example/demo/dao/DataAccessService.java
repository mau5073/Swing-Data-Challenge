package com.example.demo.dao;

import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

@Repository
public class DataAccessService {
    //Takes in a path to the csv file returns the size(number of rows that are in the file
    static int  csvSize(String path) {
        int size = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line= "";
            while ((line = br.readLine()) != null) {

                size++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }

    static public double[][]  ReadData(String path){
         int size = csvSize(path);
         double[][] csvData= new double[size][];
        //x will be used to keep track of the row that is being read from the file that data needs to be taking in
        //starts at 1 so that consistency between csv file and data structure is made (1 in structure is 1 in csv)
        int x = 0;
        int rowLength = 0;
        String line= "";
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while((line = br.readLine()) != null){
                rowLength = (line.split(",")).length;
                String [] rowData   = line.split(",");
                // row will hold the data values in each row of the csv file
                double[] row = new double[rowLength];

                // loop through the row obtained by splitting line and enter it into the row array
                for(int i = 0; i < rowLength; i++){
                    row[i] = Double.parseDouble(rowData[i]);
                }
                //add the new row array to data entering the corresponding row number x that it came from in the file
                csvData[x]= row;
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
