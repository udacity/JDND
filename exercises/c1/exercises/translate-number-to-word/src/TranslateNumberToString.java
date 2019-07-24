public class TranslateNumberToString {

    private static final String[] LESS_THAN_20 =
            {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
                    "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen",
                    "seventeen", "eighteen", "nineteen"};
    private static final String[] TENS = {"", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};
    private static final String[] THOUSANDS = {"", "thousand", "million", "billion"};

    public static String translateNumberToWord(int number) {
        // Write your code here
        if (number == 0) return "zero";
        int i = 0;
        String words = "";
        while (number > 0) {
            if (number % 1000 != 0){
                words = helper(number % 1000) +THOUSANDS[i] + " " + words;
            }
            number /= 1000;
            i++;
        }
        return words.trim();
    }

    private static String helper(int num) {
        if (num == 0)
            return "";
        else if (num < 20)
            return LESS_THAN_20[num] + " ";
        else if (num < 100)
            return TENS[num / 10] + " " + helper(num % 10);
        else
            return LESS_THAN_20[num / 100] + " hundred " + helper(num % 100);
    }
}
