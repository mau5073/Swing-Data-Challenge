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

    /**
     * This class deals with holding all the parameters a function could have and will be passed to the helper function
     *
     * data - an integer for a column number in the csv file
     * data2 - a possible second column if needed
     * indexBegin - integer for starting index ( or index to work back from for backcontSearch)
     * indxEnd - integer for ending index
     * threshold - double to hold value the columns values need to be greater than
     * thresholdHi - double for a upper value for comparison
     * thresholdLo - double for lower value comparison
     * winlength - int for number of times a continuous area must meet criteria
     * index - int for returned  index from search
     * ranges - map for returned index ranges meeting criteria
     */
    static class params{
        int data;
        int data2;
        int indexBegin;
        int indexEnd;
        double threshold;
        double thresholdHi;
        double thresholdLo;
        int winlength;
        int index;
        Map<Integer, Integer> ranges;
    }
    static class HelperClass {
        /**
         * This function deals with helping the main functions
         *
         * @param xx - data to be used in function
         * @param hg - this is a lambda expression. It will be defined withing the function and return what is needed for that function
         * @return - returns a class object of params
         */
        public static <T> params helpermethod(T xx, HelperGen<T> hg) {
            return hg.search(xx);
        }

        interface HelperGen<G> {
            /**
             * This function helps the main functions perform searching logic and is implemented in the function being called
             *
             * @param x - data to be used in function as defined when called
             * @return - returns a class object of params
             */
            params search(G x);
        }
    }

@GetMapping("/searchContinuityAboveValue")
@ResponseBody
    /**
     * This function searches for a value greater than a passed threshold for a continuous number of times
     *
     * @param data - an integer for a column number in the csv file
     * @param indexBegin - integer for starting index
     * @param indexEnd - integer for ending index
     * @param threshold - double to hold value the columns values need to be greater than
     * @param winLength - int for number of times a continuous area must meet criteria
     * @return - returns an integer for the index that meets the criteria
     */
    int searchContinuityAboveValue(@RequestParam(name = "data") int data,@RequestParam(name = "indexBegin")int indexBegin,
                                   @RequestParam(name = "indexEnd") int  indexEnd,@RequestParam(name = "threshold") double threshold,
                                   @RequestParam(name = "winLength")  int  winLength) {
        params pL = new params();
        pL.data = data;
        pL.indexBegin = indexBegin;
        pL.indexEnd = indexEnd;
        pL.threshold = threshold;
        pL.winlength = winLength;



        //look through the data using the provided indexs
        HelperClass.HelperGen<params> hgsearch1 = (params parameters) -> {
            params pLT = new params();
            int count = 0;
            for (int i =  parameters.indexBegin; i <  parameters.indexEnd; i++) {
                //When a value is greater than threshold begin incrementing count
                if(csvData[i][ parameters.data] >  parameters.threshold) {
                    count++;
                }
                // When a value is less than threshold reset to 0
                else if(csvData[i][ parameters.data] <  parameters.threshold) {
                    count = 0;
                }
                //When count is greater than winLength the sample criteria has been met and the index should be returned
                if(count > parameters.winlength) {
                    pLT.index = i;
                    return pLT;
                }
            }
            pLT.index = -1;
            return pLT;
        };

        int index =  HelperClass.helpermethod(pL, hgsearch1).index;
        return index;
    }
    /**
     * This function searches backwards from a higher index to lower index for values between two thresholds
     *
     * @param data - an integer for a column number in the csv file
     * @param indexBegin - integer for starting index
     * @param indexEnd - integer for ending index
     * @param thresholdLo - double to hold value the columns values need to be greater than
     * @param thresholdHi - double to hold value the columns values need to be lesser than
     * @param winLength - int for number of times a continuous area must meet criteria
     * @return - returns an integer for the index that meets the criteria
     */
    @GetMapping("/backSearchContinuityWithinRange")
    @ResponseBody
     int backSearchContinuityWithinRange(@RequestParam(name = "data")int data,@RequestParam(name = "indexBegin") int indexBegin,
                                               @RequestParam(name = "indexEnd")  int indexEnd,@RequestParam(name = "thresholdLo")  double thresholdLo,
                                               @RequestParam(name = "thresholdHi") double thresholdHi,@RequestParam(name = "winLength")  int winLength){
        params pL = new params();
        pL.data = data;
        pL.indexBegin = indexBegin;
        pL.indexEnd = indexEnd;
        pL.thresholdLo = thresholdLo;
        pL.thresholdHi = thresholdHi;
        pL.winlength = winLength;

        HelperClass.HelperGen<params> hgsearch1 = (params parameters) -> {
            params pLT = new params();
            //count is the comparison for winLength
            int count = 0;
            //translate the string value of a column to its value in data structure
            int column = parameters.data;
            for (int i = parameters.indexBegin; i > parameters.indexEnd; i--) {

                //When a value is greater than thresholdLo and below thresholdHi begin incrementing count
                if(csvData[i][column] > parameters.thresholdLo & csvData[i][column] < parameters.thresholdHi) {
                    count++;
                }
                // When a value is less than thresholdLo  or above thresholdHi reset to 0
                if(csvData[i][column] < parameters.thresholdLo || csvData[i][column] > parameters.thresholdHi) {
                    count = 0;
                }
                //When count is greater than winLength the sample criteria has been met and the index should be returned
                if(count > parameters.winlength) {
                    pLT.index = i;
                    return pLT;
                }
            }
            pLT.index = -1;
            return pLT;
        };
        int index =  HelperClass.helpermethod(pL, hgsearch1).index;
        return index;
    }
    /**
     * This function searches two columns at once where the columns data values are greater than set threshold values at same indexes
     *
     * @param data1 - an integer for a column number in the csv file
     * @param data2 - an integer for a column number in the csv file
     * @param indexBegin - integer for starting index
     * @param indexEnd - integer for ending index
     * @param threshold1 - double to hold value the first columns (data1) values need to be greater than
     * @param threshold2 - double to hold value the columns (data2) values need to be greater than
     * @param winLength - int for number of times a continuous area must meet criteria
     * @return - returns an integer for the index that meets the criteria
     */
    @GetMapping("/searchContinuityAboveValueTwoSignals")
    @ResponseBody
    static int  searchContinuityAboveValueTwoSignals(@RequestParam(name = "data1") int data1, @RequestParam(name = "data2") int data2,
                                                     @RequestParam(name = "indexBegin")int indexBegin, @RequestParam(name = "indexEnd")int indexEnd,
                                                     @RequestParam(name = "threshold1")double threshold1,@RequestParam(name = "threshold2")double threshold2,
                                                     @RequestParam(name = "winLength") int  winLength) {
        params pL = new params();
        pL.data = data1;
        pL.data2 = data2;
        pL.indexBegin = indexBegin;
        pL.indexEnd = indexEnd;
        pL.thresholdLo = threshold1;
        pL.thresholdHi = threshold2;
        pL.winlength = winLength;

        HelperClass.HelperGen<params> hgsearch1 = (params parameters) -> {
            params pLT = new params();
            int endOfFileCount = parameters.indexBegin;
            //move through the data and check that both parameters are returning index's at the same place
            //return when they have matched indexs winLength times
            //if they do not match set count to 0
            int count = 0;
            //reuse of low and hi but renamed to avoid confusion
            double t1 = parameters.thresholdLo;
            double t2 = parameters.thresholdHi;
            while (endOfFileCount < parameters.indexEnd) {
                for (int i = parameters.indexBegin; i < parameters.indexEnd; i++) {
                    if(csvData[i][parameters.data] > t1 && csvData[i][parameters.data2] > t2) {
                        count++;
                        if(count > parameters.winlength) {
                            pLT.index = i;
                            return pLT;
                        }
                    }
                    if(csvData[i][parameters.data] < t1 || csvData[i][parameters.data2] < t2) {
                        count = 0;
                    }
                }
                endOfFileCount++;
            }
            pLT.index = -1;
            return pLT;
        };
        int index =  HelperClass.helpermethod(pL, hgsearch1).index;
        return index;
    }
    /**
     * This function searches for indexs between two values for a certain amount of continuous indexs
     *
     * @param data - an integer for a column number in the csv file
     * @param indexBegin - integer for starting index
     * @param indexEnd - integer for ending index
     * @param thresholdLo - double to hold value the columns values need to be greater than
     * @param thresholdHi - double to hold value the columns values need to be lesser than
     * @param winLength - int for number of times a continuous area must meet criteria
     * @return - returns a map containing index areas from start to end that were greater than the set threshold for at least winLength times
     */
    @GetMapping("/searchMultiContinuityWithinRange")
    @ResponseBody
    static Map<Integer, Integer> searchMultiContinuityWithinRange(@RequestParam(name = "data")int data,@RequestParam(name = "indexBegin") int indexBegin,
                                                                       @RequestParam(name = "indexEnd")int indexEnd,@RequestParam(name = "thresholdLo") double  thresholdLo,
                                                                       @RequestParam(name = "thresholdHi")double  thresholdHi,@RequestParam(name = "winLength") int  winLength){
        params pL = new params();
        pL.data = data;
        pL.indexBegin = indexBegin;
        pL.indexEnd = indexEnd;
        pL.thresholdLo = thresholdLo;
        pL.thresholdHi = thresholdHi;
        pL.winlength = winLength;

        HelperClass.HelperGen<params> hgsearch1 = (params parameters) -> {
            params pLT = new params();
            //count is the comparison for winLength
            int count = 0;
            //first and last will hold first and last index's
            int first = 0;
            int last;
            //the hash map will hold the paired values that are found in the data for the continuous samples
            HashMap<Integer, Integer> startEndList = new HashMap<Integer, Integer>();
            //translate the string value of a column to its value in data structure
            int column = parameters.data;
            for (int i = parameters.indexBegin; i < parameters.indexEnd; i++) {

                //When a value is greater than thresholdL     o and below thresholdHi begin incrementing count
                if(csvData[i][column] > parameters.thresholdLo & csvData[i][column] < parameters.thresholdHi) {
                    if(count == 0) {
                        first = i;
                    }
                    count++;
                }
                // When a value is less than thresholdLo  or above thresholdHi reset to 0
                if(csvData[i][column] < parameters.thresholdLo || csvData[i][column] > parameters.thresholdHi) {
                    count = 0;
                }
                //When count is greater than winLength the sample criteria has been met and the index should be returned
                if(count > parameters.winlength) {
                    last = i;
                    startEndList.put(first, last);
                }
            }

            pLT.ranges = startEndList.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            return pLT;
        };
        Map<Integer, Integer> ranges =  HelperClass.helpermethod(pL, hgsearch1).ranges;
        return  ranges;
    }

}
