# Swing-Data-Challenge
Coding Challenge with Spring 

Columns timestamp, ax, ay, az, wx, wy, wz. 

Class DataAccessService acts as the file reader and holds the projects database. 
In class DataAccessService:

Function ReadData a path the the csv file is taken in and the file is then parsed into a Hashmap. The Hashmap uses the format of <Integer, double[]>. Integer represents the rows of the csv file and matches theses rows directly. Double[] is an array that holds the 7 columns of data for one row. These are the same columns as described above in the  same order as in the csv file.

Class dataFunctions are the functions to be performed on the database. This will service Spring implements to perform querying functions on the data from the DataAccessService.
In class dataFunctions: 

Function colMatch is used to take in the column labels from other functions. This takes in a string column label such as timestamp and converts it to a number that timestamp is indexed at in the csv data. Timestamp is column 0 in the csv as such it returns 0 when passed. If a value is passed that does not match any of the columns in csv -1 is returned. 

Function searchContinuityAboveValue takes a string representing such as “ax” for the column to be searched. It also takes in an integer for the first index and last index. A double is taken in for threshold as the values it will be used to compare to are decimal values. The final value is the number of continuous cells that must meet be above threshold before an index is returned. If no such area exists -1 is returned.

Function backSearchContinuityWithinRange takes in similar values to the previous function. The changes from the previous function is that IndexBegin must be larger than IndexEnd. This function performs a backwards search starting from a higher index to the lower returning the first index that a continuous  winLength number of cells have been between a low and high threshold. If no such area exists -1 is returned.

Function searchContinuityAboveValueTwoSignals takes in 2 columns strings that will must be above an entered threshold that is unique to each one. Threshold 1 for data1 and threshold 2 for data2. This function is to find a continuous are of cells that where both columns are above there set thresholds at the same time(the areas must match). To do this the searchContinuityAboveValue function twice each time passing the two criteria being compared. This function is then compared until the two functions being called match. If no such area exists -1 is returned. 

Function searchMultiContinuityWithinRange takes in a string for the colulmm to be searched. This column is searched in the provided indexBegin to IndexEnd for values that fall between thresholdHi and thresholdLo. All continuous areas that meet this requirement are returned as an ascending  hashmap in the form <Integer, Integer> with the first value being the start of a region and the second the end of a region. 

