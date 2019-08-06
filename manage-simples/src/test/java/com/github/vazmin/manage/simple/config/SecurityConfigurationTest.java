package com.github.vazmin.manage.simple.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Chwing on 2019/8/9.
 */
class SecurityConfigurationTest {

    @org.junit.jupiter.api.Test
    void passwordEncoder() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("chwingwong"));

    }

    @Test
    public void foo() {
        int[] a = new int[]{1, 4, 6, 5, 3};
        mp(a);
        System.out.println(Arrays.toString(a));

        System.out.println(findKthLargest(a, 1));

    }

    public void mp(int[] arr) {
        if (arr.length == 0) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j + 1] < arr[j]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    @Test
    public void sss() {
        System.out.println(ss("agsAA"));
        System.out.println((int) 'A' + " " + (int) 'a');
    }

    public String ss(String s) {
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j < chars.length - i - 1; j++) {
                if (chars[j + 1] < chars[j]) {
                    char temp = chars[j];
                    chars[j] = chars[j + 1];
                    chars[j + 1] = temp;
                }
            }
        }
        return Arrays.toString(chars);
    }

    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> minQueue = new PriorityQueue<>(k);
        for (int num : nums) {
            if (minQueue.size() < k || num > minQueue.peek())
                minQueue.offer(num);
            if (minQueue.size() > k)
                minQueue.poll();
        }
        return minQueue.peek();

    }

    @Test
    public void yhmain() {
        yhm(4);
    }

    public void yhm(int n) {
        int[][] odds = new int[n][];
        for (int i = 0; i < n; i++) {
            odds[i] = new int[i + 1];
            for (int j = 0; j < odds[i].length; j++) {
                if (i == 0 || j == 0 || i == j) {
                    odds[i][j] = 1;
                } else {
                    odds[i][j] = odds[i - 1][j] + odds[i - 1][j - 1];
                }
            }

        }
        pyh(odds);
    }

    public void pyh(int[][] odds) {
        int l = odds.length - 1;
        for (int[] row : odds) {

            if (l > 0)
                System.out.printf("%" + l + "s", " ");
            l--;
            for (int odd : row)
                System.out.printf("%4d", odd);
            System.out.println();
        }
    }

    public void yh() {
        final int NMAX = 3;

        // allocate triangular array
        int[][] odds = new int[NMAX + 1][];
        for (int n = 0; n <= NMAX; n++)
            odds[n] = new int[n + 1];

        // fill triangular array
        for (int n = 0; n < odds.length; n++)
            for (int k = 0; k < odds[n].length; k++) {
                /*
                 * compute binomial coefficient n*(n-1)*(n-2)*...*(n-k+1)/(1*2*3*...*k)
                 */
                int lotteryOdds = 1;
                for (int i = 1; i <= k; i++)
                    lotteryOdds = lotteryOdds * (n - i + 1) / i;

                odds[n][k] = lotteryOdds;
            }

        int l = odds.length - 1;
        // print triangular array
        for (int[] row : odds) {

            if (l > 0)
                System.out.printf("%" + l + "s", " ");
            l--;
            for (int odd : row)
                System.out.printf("%4d", odd);
            System.out.println();
        }
    }


    public static int[] sortQuick(int[] in, int left, int right) {
        int key = in[left]; //选定数组第一个数字作为key
        int start = left;
        int end = right;
        while (start < end) {
            //从右向左遍历,找到小于key的,放入下标strat中。
            while (start < end && key <= in[end]) {
                end--;
            }
            in[start] = in[end];

            //从左向右遍历,找到大于key的,放入下标end中。
            while (start < end && key > in[start]) {
                start++;
            }
            in[end] = in[start];
        }
        //此时start==end,这就是所谓的轴，把key放入轴中，轴左边的都<key,轴右边的都>key
        in[start] = key;
        //此时大家想象一下，轴在数组中间，说明把数组分成两部分，此时要对这两部分分别进行快排。
        if (start > left) sortQuick(in, left, start - 1);
        if (end < right) sortQuick(in, end + 1, right);
        return in;
    }

    @Test
    public void qs() {
        int arr[] = new int[]{13, 26, 22, 22, 35, 18};
        sort(arr, 0, 5);
        System.out.println(Arrays.toString(arr));
    }

    public void sort(int[] arr, int left, int right) {

        if (left > right) return;
        int base = arr[left];
        int lo = left;
        int hi = right;
        while (left < right) {
            while (right > left && arr[right] > base) {
                right--; //2
            }
            while (left < right && arr[left] <= base) {
                left++; // 1
            }
            if (left < right) {
                int temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
            }
        }

        arr[lo] = arr[left];
        arr[left] = base;
        sort(arr, lo, left - 1);
        sort(arr, left + 1, hi);
    }

    public int[] qsort(int arr[], int start, int end) {
        int pivot = arr[start];
        int i = start;
        int j = end;
        while (i < j) {
            while ((i < j) && (arr[j] > pivot)) {
                j--;
            }
            while ((i < j) && (arr[i] < pivot)) {
                i++;
            }
            if ((arr[i] == arr[j]) && (i < j)) {
                i++;
            } else {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        if (i - 1 > start) arr = qsort(arr, start, i - 1);
        if (j + 1 < end) arr = qsort(arr, j + 1, end);
        return (arr);
    }

    @Test
    public void qss() {
        int arr[] = new int[]{3,3,3,7,9,122344,4656,34,34,4656,5,6,7,8,9,343,57765,23,12321};
        int len = arr.length-1;
        arr=qsort(arr,0,len);
        for (int i:arr) {
            System.out.print(i+"\t");
        }
    }
}

