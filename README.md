# Swing-Data-Challenge(Java and Spring)
Coding Challenge with Spring 

Columns timestamp, ax, ay, az, wx, wy, wz. 

Class DataAccessService acts as the file reader and holds the projects database. 
In class DataAccessService:

Function ReadData takes a path of the the csv file and the file is then parsed into a 2d Array. The Array uses the format of  int for the first array representing the rows of the csv. The second part of the array contains a  array of type Double[] that holds the 7 columns of data for one row. These are the same columns as described above in the  same order as in the csv file.

Class dataFunctions are the functions to be performed on the database. This will be a  Spring service to perform querying functions on the data from the DataAccessService.
In class dataFunctions: 

Class params holds all possible parameters  a function could take this is used with the helper class 

Class HelperClass takes in data of generic type to be used in a function. Also holds a lambda expression to preform searching inside the main functions. 

Function searchContinuityAboveValue takes an int (0-6) representing  the column to be searched. It also takes in an integer for the first index and last index. A double is taken in for threshold as the values it will be used to compare to are decimal values. The final value is the number of continuous cells that must  be above threshold before an index is returned. If no such area exists -1 is returned.

Function backSearchContinuityWithinRange takes in similar values to the previous function. The changes from the previous function is that IndexBegin must be larger than IndexEnd. This function performs a backwards search starting from a higher index to the lower returning the first index that a continuous  winLength number of cells have been between a low and high threshold. If no such area exists -1 is returned.

Function searchContinuityAboveValueTwoSignals takes in 2 columns strings that  must be above an entered threshold that is unique to each one. Threshold 1 for data1 and threshold 2 for data2. This function is to find a continuous are of cells  where both columns are above there set thresholds at the same time(the areas must match indexs). If no such area exists -1 is returned. 

Function searchMultiContinuityWithinRange takes in a string for the colulmm to be searched. This column is searched in the provided indexBegin to indexEnd for values that fall between thresholdHi and thresholdLo. All continuous areas that meet this requirement are returned as an ascending  hashmap in the form <Integer, Integer> with the first value being the start of a region and the second the end of a region. 

Function Tests in dataFunctionTests performs jUnit testing on each of these funtions 
