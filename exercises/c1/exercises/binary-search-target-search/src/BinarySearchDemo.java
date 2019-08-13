public class BinarySearchDemo {

    public static int findTarget(int[] arr, int target) {
        if (arr.length == 0) return -1;
        int start = 0, end = arr.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] > target) {
                end = mid;
            } else {
                start = mid;
            }
        }
        if (arr[start] == target)  {
            return start;
        }
        if (arr[end] == target) {
            return end;
        }
        return -1;
    }
}
