package com.udacity.examples.Testing;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

public class Helper {
	
	/**
	    * Method to get the count of empty strings
	    * @param strings
	    * @return
	    */
		public static long getCount(List<String> strings){
			return strings.stream().filter(string->string.isEmpty()).count();
		}
		
		/**
		 * Method to get the stats of a list of numbers 
		 * @param numbers
		 * @return
		 */
		public static IntSummaryStatistics getStats(List<Integer> expYears) {
		   return expYears.stream().mapToInt((x) ->x).summaryStatistics();
	   }
		
	   
		/**
		 * 
		 * @param empName
		 * @return
		 */
		public static long getStringsOfLength3(List<String> empName) {
		   return empName.stream().filter(string -> string.length() == 3).count();
	   }
	   
		/**
		 * 
		 * @param expYears
		 * @return
		 */
		public static List<Integer> getSquareList(List<Integer> expYears) {
		   return expYears.stream().map( i ->i*i).distinct().collect(Collectors.toList());
	   }
	   
		/**
		 * 
		 * @param empName
		 * @return
		 */
		public static String getMergedList(List<String> empName) {
		   return empName.stream().filter(string ->!string.isEmpty()).collect(Collectors.joining(", "));
	   }

		/**
		 * 
		 * @param empName
		 * @return
		 */
		public static List<String> getFilteredList(List<String> empName) {
		   return empName.stream().filter(string ->!string.isEmpty()).collect(Collectors.toList());
	   }

}
