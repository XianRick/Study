package com.exe.study.java;

/**
 * Created by Administrator on 2017/4/7.
 */

public class Sort {

    public static void main(String args[]) {
        int[] arr = {12, 02, 13, 15, 23, 12, 35, 42, 94, 58, 73};
        arr = insertSort(arr, arr.length);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }

    /**
     * 冒泡排序
     * 从最后一位开始每一位都和他的前一位比较，如果前一位大于后一位，就后移
     * 第一轮过后最前面就是最小的，然后依次递减次数
     *
     * @param arr
     * @return
     */
    public static int[] bubbleSort(int[] arr, int length) {
        int temp;
        boolean flag;
        for (int i = 0; i < length - 1; i++) {
            flag = false;
            for (int j = length - 1; j > i; j--) {
                if (arr[j] < arr[j - 1]) {
                    temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                    flag = true;
                }
            }
            if (!flag) break;
        }
        return arr;
    }

    /**
     * 选择排序
     *
     * @param arr
     * @param length
     * @return
     */
    public static int[] selectSort(int[] arr, int length) {
        for (int i = 0; i < length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < length; j++) {//遍历出集合中最小的数
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {//将最小数和第i个数进行交换
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
        return arr;
    }

    /**
     * 插入排序
     *
     * @param arr
     * @param length
     * @return
     */
    public static int[] insertSort(int[] arr, int length) {
        int temp;
        for (int i = 0; i < length - 1; i++) {
            for (int j = i + 1; j > 0; j--) {//依次判断当前数与他的前一位的大小，然后进行交换
                if (arr[j] < arr[j - 1]) {
                    temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                } else {
                    break;
                }
            }
        }
        return arr;
    }

    /**
     * 希尔排序
     *
     * @param arr
     * @param length
     * @return
     */
    public static int[] shellSort(int[] arr, int length) {
        int temp;
        int incre = length;
        while (true) {
            incre = incre / 2;
            for (int k = 0; k < incre; k++) {
                for (int i = k + incre; i < length; i += incre) {
                    for (int j = i; j > k; j -= incre) {
                        if (arr[j] < arr[j - incre]) {
                            temp = arr[j - incre];
                            arr[j - incre] = arr[j];
                            arr[j] = temp;
                        } else {
                            break;
                        }
                    }
                }
            }
            if (incre == 1) {
                break;
            }
        }
        return arr;
    }
}
