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

    private static DataAccessService dataAccessService = new DataAccessService();;

    //map will hold all the data from the csv file each row will be an entry linked to a data array with 7 values
    //an example usage would be csvData.get(0)[0] where get(0) is the row number in the csv file and [0] represent the first column
    private static String path = "C:\\Users\\Marshall\\Desktop\\DK challenge\\latestSwing.csv";

    private static HashMap<Integer, double[]> csvData =  dataAccessService.ReadData(path);

    @GetMapping("/Data")
    public HashMap<Integer, double[]>  getData(){
        return csvData;
    }

    //colMatch will be used to translate the names of columns to there numerical values in the data structure
    @GetMapping("/colMatch")
    @ResponseBody
    static int colMatch(@RequestParam  String val){
        if(val.equals( "timestamp")) {
            return 0;
        } else if(val.equals("ax")) {
            return 1;
        } else if(val.equals("ay")) {
            return 2;
        } else if(val.equals("az")) {
            return 3;
        } else if(val.equals("wx")) {
            return 4;
        } else if(val.equals("wy")) {
            return 5;
        } else if(val.equals("wz")) {
            return 6;
        }
        return -1;
    }
@GetMapping("/searchContinuityAboveValue")
@ResponseBody
    static int searchContinuityAboveValue(@RequestParam(name = "data") String data,@RequestParam(name = "indexBegin")int indexBegin,
                                          @RequestParam(name = "indexEnd") int  indexEnd,@RequestParam(name = "threshold") double threshold,
                                          @RequestParam(name = "winLength")  int  winLength) {
        //count is the comparison for winLength
        int count =0;
        //translate the string value of a column to its value in data structure
        int column = colMatch(data);
        //look through the data using the provided indexs
        for (int i = indexBegin; i < indexEnd; i++) {

            //When a value is greater than threshold begin incrementing count
            if(csvData.get(i)[column] > threshold){
                count++;
            }
            // When a value is less than threshold reset to 0
            else if(csvData.get(i)[column]  <threshold ){
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
    static int backSearchContinuityWithinRange(@RequestParam(name = "data")String data,@RequestParam(name = "indexBegin") int indexBegin,
                                               @RequestParam(name = "indexEnd")  int indexEnd,@RequestParam(name = "thresholdLo")  double thresholdLo,
                                               @RequestParam(name = "thresholdHi") double thresholdHi,@RequestParam(name = "winLength")  int winLength){
        //count is the comparison for winLength
        int count =0;
        //translate the string value of a column to its value in data structure
        int column = colMatch(data);
        for (int i = indexBegin; i > indexEnd; i--) {

            //When a value is greater than thresholdLo and below thresholdHi begin incrementing count
            if(csvData.get(i)[column] > thresholdLo & csvData.get(i)[column]  < thresholdHi){
                count++;
            }
            // When a value is less than thresholdLo  or above thresholdHi reset to 0
            if(csvData.get(i)[column]  <thresholdLo  || csvData.get(i)[column]  > thresholdHi){
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
    static int  searchContinuityAboveValueTwoSignals(@RequestParam(name = "data1") String data1, @RequestParam(name = "data2") String data2,
                                                     @RequestParam(name = "indexBegin")int indexBegin, @RequestParam(name = "indexEnd")int indexEnd,
                                                     @RequestParam(name = "threshold1")double threshold1,@RequestParam(name = "threshold2")double threshold2,
                                                     @RequestParam(name = "winLength") int  winLength) {
        int endOfFileCount= indexBegin;
        //v1 and v2 will hold the returns from running the functions on the two provided search areas
        int v1; int v2;

        //move through the data and check that both parameters are returning index's at the same place
        //return when they have matched indexs winLength times
        //if they do not match set count to 0
        while(endOfFileCount  < indexEnd) {
            v1 = searchContinuityAboveValue(data1, indexBegin, indexEnd, threshold1, winLength);
            v2 = searchContinuityAboveValue(data2, indexBegin, indexEnd, threshold2, winLength);
            if(v1 == v2){
                return v1;
            }
            indexBegin =endOfFileCount;
            endOfFileCount++;


        }
        return -1;
    }
    @GetMapping("/searchMultiContinuityWithinRange")
    @ResponseBody
    static Map<Integer, Integer> searchMultiContinuityWithinRange(@RequestParam(name = "data")String data,@RequestParam(name = "indexBegin") int indexBegin,
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
        int column = colMatch(data);
        for (int i = indexBegin; i < indexEnd; i++) {

            //When a value is greater than thresholdLo and below thresholdHi begin incrementing count
            if(csvData.get(i)[column] > thresholdLo & csvData.get(i)[column] < thresholdHi) {
                if(count == 0){ first = i;}
                count++;
            }
            // When a value is less than thresholdLo  or above thresholdHi reset to 0
            if(csvData.get(i)[column] < thresholdLo || csvData.get(i)[column] > thresholdHi) {
                count = 0;
            }
            //When count is greater than winLength the sample criteria has been met and the index should be returned
            if(count > winLength) {
                last = i;
                startEndList.put(first,last);
                count = 0;
            }
        }
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
