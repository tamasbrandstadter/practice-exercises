public class SortingAlgorithm {

  public int[] bubbleSort(int[] nums) {
    for (int lastUnsortedIndex = nums.length - 1; lastUnsortedIndex >= 0; lastUnsortedIndex--) {
      for (int i = 0; i < lastUnsortedIndex; i++) {
        if (nums[i] > nums[i + 1]) {
          swap(nums, i, i + 1);
        }
      }
    }

    return nums;
  }

  public int[] selectionSort(int[] nums) {
    for (int lastUnsortedIndex = nums.length - 1; lastUnsortedIndex > 0; lastUnsortedIndex--) {
      int largest = 0;

      for (int i = 1; i <= lastUnsortedIndex; i++) {
        if (nums[i] > nums[largest]) {
          largest = i;
        }
      }
      swap(nums, largest, lastUnsortedIndex);
    }
    return nums;
  }

  public int[] selectionSort2(int[] nums) {
    for (int i = 0; i < nums.length - 1; i++) {
      int minimum = i;

      for (int j = i + 1; j < nums.length; j++) {
        if (nums[j] < nums[minimum]) {
          minimum = j;
        }
      }

      int temp = nums[i];
      nums[i] = nums[minimum];
      nums[minimum] = temp;
    }
    return nums;
  }

  public int[] insertionSort(int[] nums) {
    for (int firstUnsortedIndex = 1; firstUnsortedIndex < nums.length; firstUnsortedIndex++) {
      int newElement = nums[firstUnsortedIndex];
      int i;

      for (i = firstUnsortedIndex; i > 0 && nums[i - 1] > newElement; i--) {
        nums[i] = nums[i - 1];
      }
      nums[i] = newElement;
    }
    return nums;
  }

  public int[] insertionSort2(int[] nums) {
    for (int i = 1; i < nums.length; i++) {
      int number = nums[i];
      int j = i - 1;

      while (j >= 0 && nums[j] > number) {
        nums[j + 1] = nums[j];
        j--;
      }

      nums[j + 1] = number;
    }
    return nums;
  }

  private void swap(int[] nums, int i, int j) {
    if (i == j) {
      return;
    }

    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
  }

  public void mergeSort(int[] input, int start, int end) {
    if (end - start < 2) {
      return;
    }

    int mid = (start + end) / 2;

    mergeSort(input, start, mid);
    mergeSort(input, mid, end);
    merge(input, start, mid, end);
  }

  private void merge(int[] input, int start, int mid, int end) {
    if (input[mid - 1] <= input[mid]) {
      return;
    }

    int i = start;
    int j = mid;
    int tempIndex = 0;

    int[] temp = new int[end - start];

    while (i < mid && j < end) {
      temp[tempIndex++] = input[i] <= input[j] ? input[i++] : input[j++];
    }

    System.arraycopy(input, i, input, start + tempIndex, mid - i);
    System.arraycopy(temp, 0, input, start, tempIndex);
  }

  public void quickSort(int[] nums, int start, int end) {
    if (start < end) {
      int partition = getPartition(nums, start, end);

      quickSort(nums, start, partition - 1);
      quickSort(nums, partition + 1, end);
    }
  }

  private int getPartition(int[] nums, int start, int end) {
    int x = nums[end];
    int i = start - 1;

    for (int j = start; j <= end - 1; j++) {
      if (nums[j] <= x) {
        i++;
        swap(nums, i, j);
      }
    }

    nums[end] = nums[i + 1];
    nums[i + 1] = x;

    return i + 1;
  }

}

