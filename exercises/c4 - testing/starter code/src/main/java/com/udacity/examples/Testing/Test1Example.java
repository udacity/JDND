package com.udacity.examples.Testing;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Random;

public class Test1Example {

	   public static void main(String args[]) {
	      // Count empty strings
	      List<String> empNames = Arrays.asList("sareeta", "", "john","");
	      List<Integer> empLevel = Arrays.asList(3,3,3,5,7,2,2,5,7,5);
	      List<Integer> yrsOfExperience = Arrays.asList(13,4,15,6,17,8,19,1,2,3);
	      //print ten random numbers
	      Random random = new Random();
			
	      for(int i = 0; i < 10; i++) {
	         System.out.println(random.nextInt());
	      }
			
	      System.out.println("List: " + empNames);
			
	      long count = Helper.getCount(empNames) ;
	      
	      System.out.println("Empty Strings: " + count);
			
	      count = Helper.getCount(empNames);
	      System.out.println("Strings of length 3: " + count);
			
	      List<String> filtered = Helper.getFilteredList(empNames);
	      System.out.println("Filtered List: " + filtered);
			
	      String mergedString = Helper.getMergedList(empNames);
	      System.out.println("Merged String: " + mergedString);
			
	      List<Integer> squaresList = Helper.getSquareList(empLevel);
	      System.out.println("Squares List: " + squaresList);
			
	      IntSummaryStatistics stats = yrsOfExperience.stream().mapToInt((x) ->x).summaryStatistics();
			
	      System.out.println("Highest number in List : " + stats.getMax());
	      System.out.println("Lowest number in List : " + stats.getMin());
	      System.out.println("Sum of all numbers : " + stats.getSum());
	      System.out.println("Average of all numbers : " + stats.getAverage());
	      System.out.println("Random Numbers: ");
			
	      random.ints().limit(10).sorted().forEach(System.out::println);
			
	      //parallel processing
	      count = empNames.parallelStream().filter(string -> string.isEmpty()).count();
	      System.out.println("Empty Strings: " + count);
	   }
		
		
	   
}
