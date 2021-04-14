package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.DefaultBootstrapContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dao.DataAccessService;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class dataFunctions {

    private static DataAccessService dataAccessService = new DataAccessService();

    //map will hold all the data from the csv file each row will be an entry linked to a data array with 7 values
    //an example usage would be csvData.get(0)[0] where get(0) is the row number in the csv file and [0] represent the first column
    private static String path = "C:\\Users\\Marshall\\Desktop\\EDU-Projects\\DK challenge Java Spring\\demo\\src\\main\\resources\\latestSwing.csv";

    private static double[][] csvData =  dataAccessService.ReadData(path);

    @GetMapping("/Data")
    double[][]  getData(){
        return csvData;
    }



@GetMapping("/searchContinuityAboveValue")
@ResponseBody
      int searchContinuityAboveValue(@RequestParam(name = "data") int data,@RequestParam(name = "indexBegin")int indexBegin,
                                          @RequestParam(name = "indexEnd") int  indexEnd,@RequestParam(name = "threshold") double threshold,
                                          @RequestParam(name = "winLength")  int  winLength) {
        //count is the comparison for winLength
        int count =0;
        //translate the string value of a column to its value in data structure
        int column = data;
        //look through the data using the provided indexs
        for (int i = indexBegin; i < indexEnd; i++) {

            //When a value is greater than threshold begin incrementing count
            if(csvData[i][column] > threshold){
                count++;
            }
            // When a value is less than threshold reset to 0
            else if(csvData[i][column]  <threshold ){
                count = 0;
            }
            //When count is greater than winLength the sample criteria has been met and the index should be returned
            if(count > winLength){
                return i;
            }
        }
        //if no index meets the criteria -1 is returned
        return -1;
    }
    @GetMapping("/backSearchContinuityWithinRange")
    @ResponseBody
     int backSearchContinuityWithinRange(@RequestParam(name = "data")int data,@RequestParam(name = "indexBegin") int indexBegin,
                                               @RequestParam(name = "indexEnd")  int indexEnd,@RequestParam(name = "thresholdLo")  double thresholdLo,
                                               @RequestParam(name = "thresholdHi") double thresholdHi,@RequestParam(name = "winLength")  int winLength){
        //count is the comparison for winLength
        int count =0;
        //translate the string value of a column to its value in data structure
        int column = data;
        for (int i = indexBegin; i > indexEnd; i--) {

            //When a value is greater than thresholdLo and below thresholdHi begin incrementing count
            if(csvData[i][column]> thresholdLo & csvData[i][column]  < thresholdHi){
                count++;
            }
            // When a value is less than thresholdLo  or above thresholdHi reset to 0
            if(csvData[i][column]  <thresholdLo  || csvData[i][column] > thresholdHi){
                count = 0;
            }
            //When count is greater than winLength the sample criteria has been met and the index should be returned
            if(count > winLength){
                return i;
            }
        }


        return -1;
    }
    @GetMapping("/searchContinuityAboveValueTwoSignals")
    @ResponseBody
    static int  searchContinuityAboveValueTwoSignals(@RequestParam(name = "data1") int data1, @RequestParam(name = "data2") int data2,
                                                     @RequestParam(name = "indexBegin")int indexBegin, @RequestParam(name = "indexEnd")int indexEnd,
                                                     @RequestParam(name = "threshold1")double threshold1,@RequestParam(name = "threshold2")double threshold2,
                                                     @RequestParam(name = "winLength") int  winLength) {
        int endOfFileCount= indexBegin;



        //move through the data and check that both parameters are returning index's at the same place
        //return when they have matched indexs more than winLength times
        //if they do not match set count to 0
        int count = 0;
        while(endOfFileCount  < indexEnd) {
            for(int i = indexBegin; i < indexEnd; i++){
                if(csvData[i][data1] > threshold1 && csvData[i][data2] > threshold2 ){
                    count++;
                    if(count >winLength){
                        return i;
                    }
                }
                if(csvData[i][data1] < threshold1 || csvData[i][data2] < threshold2  ){
                    count = 0;
                }
            }
            endOfFileCount++;
        }
        return -1;
    }
    @GetMapping("/searchMultiContinuityWithinRange")
    @ResponseBody
    static Map<Integer, Integer> searchMultiContinuityWithinRange(@RequestParam(name = "data")int data,@RequestParam(name = "indexBegin") int indexBegin,
                                                                       @RequestParam(name = "indexEnd")int indexEnd,@RequestParam(name = "thresholdLo") double  thresholdLo,
                                                                       @RequestParam(name = "thresholdHi")double  thresholdHi,@RequestParam(name = "winLength") int  winLength){
        //count is the comparison for winLength
        int count =0;
        //first and last will hold first and last index's
        int first = 0;
        int last;
        //the hash map will hold the paired values that are found in the data for the continuous samples
        HashMap<Integer, Integer> startEndList = new HashMap<Integer, Integer>();
        //translate the string value of a column to its value in data structure
        int column = data;
        for (int i = indexBegin; i < indexEnd; i++) {

            //When a value is greater than thresholdLo and below thresholdHi begin incrementing count
            if(csvData[i][column] > thresholdLo & csvData[i][column] < thresholdHi) {
                if(count == 0){ first = i;}
                count++;
            }
            // When a value is less than thresholdLo  or above thresholdHi reset to 0
            if(csvData[i][column] < thresholdLo || csvData[i][column] > thresholdHi) {
                count = 0;
            }
            //When count is greater than winLength the sample criteria has been met and the index should be returned
            if(count > winLength) {
                last = i;
                startEndList.put(first,last);

            }
        }

        // organizes the data in sequential manner so return result is an ascending list
        Map<Integer, Integer> result = startEndList.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        return  result;
    }

}
