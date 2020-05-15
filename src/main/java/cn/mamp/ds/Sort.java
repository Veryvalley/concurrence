package cn.mamp.ds;

import java.util.Random;

/**
 * 排序
 *
 * @author mamp
 * @data 2020/5/12
 */
public class Sort {
}

/**
 * 插入排序
 */
class InsertSort {
    public static void main(String[] args) {
        InsertSort sort = new InsertSort();
        int[] data = new int[]{7, 4, 5, 8, 3, 1, 7, 8, 12};
        sort.sort(data);
        for (int i : data) {
            System.out.println(i);
        }
    }

    /**
     * 插入排序
     *
     * @param data
     */
    public void sort(int data[]) {
        int i, j, temp;
        // 从1开始
        for (i = 1; i < data.length; i++) {
            temp = data[i];
            j = i - 1;
            for (; j >= 0; j--) {
                if (data[j] > temp) {
                    data[j + 1] = data[j];
                } else {
                    break;
                }
            }
            data[j + 1] = temp;
        }
    }

    /**
     * 插入排序优化
     *
     * @param data
     */
    public void sort1(int[] data) {
        int i, j, temp;
        // 从1开始
        for (i = 1; i < data.length; i++) {
            temp = data[i];
            j = i - 1;
            for (; j >= 0 && data[j] > temp; j--) {
                data[j + 1] = data[j];
            }
            data[j + 1] = temp;
        }
    }


}

/**
 * 归并排序
 */
class MergeSort {
    public static void main(String[] args) {
        MergeSort sort = new MergeSort();
        int[] data = new int[]{7, 4, 5, 8, 3};
        data = new int[100000000];
        Random random = new Random();
        for (int i = 0; i < data.length; i++) {
            data[i] = random.nextInt(10000000);
        }
        System.out.println("start...");
        long start = System.currentTimeMillis();
        int[] temp = new int[1];
        sort.sort(data, 0, data.length - 1, temp);
        long end = System.currentTimeMillis();
        System.out.println("用时:" + (end - start));
        /*for (int i : data) {
            System.out.println(i);
        }*/
    }

    /**
     * @param data  需要排序的数组
     * @param start 排序的开始
     * @param end   排序的结束
     */
    public void sort(int[] data, int start, int end, int[] temp) {
        // 如果 start == end 说明数据只有一个元素,不需要排序
        if (start >= end) {
            return;
        }

        int mid = (end + start) / 2;
        // 先拆分,拆分的时间复杂度为 O(log2n)
        sort(data, start, mid, temp);
        sort(data, mid + 1, end, temp);

        // 再合并(时间复杂度为: O(n): 循环都没有嵌套)

        // 发开辟end - start + 1 个临时数组空间
        temp = new int[end - start + 1];
        int idxLeft = start; // 左数组下标, 开始为start
        int idxRight = mid + 1;  // 右数组下标, 开始为 mid+1
        int idxTemp = 0;  // 临时数组下标,开始为0

        // 左数级和右数组都有数时,需要比较左右两个数组的数据
        while (idxLeft <= mid && idxRight <= end) {
            if (data[idxLeft] < data[idxRight]) {
                // 如果左边
                temp[idxTemp] = data[idxLeft];
                idxLeft++;
                idxTemp++;
            } else {
                temp[idxTemp] = data[idxRight];
                idxRight++;
                idxTemp++;
            }
        }
        // 右数组的数据全部已经复制到temp数组
        // 左数组的数据已经是排序好的数据,按照顺序把左数组的数据复制到temp数组即可
        while (idxLeft <= mid) {
            temp[idxTemp++] = data[idxLeft++];
        }
        // 左数组的数据全部已经复制到temp数组
        // 右数组的数据已经是排序好的数据,按照顺序把右数组的数据复制到temp数组即可
        while (idxRight <= end) {
            temp[idxTemp++] = data[idxRight++];
        }
        // 把temp数组的数据复制到 data[]
        for (int i = 0; i < idxTemp; i++) {
            data[start + i] = temp[i];
        }
    }


}


/**
 * 计数排序
 */
class CountSort {
    public static void main(String[] args) {
        double[] dataO = new double[]{235.15, 859, 666.6, 128, 588, 25, 700, 580, 666.6};
        CountSort cs = new CountSort();
        cs.countSort(dataO);
    }

    /**
     * 200W 考生的考试成绩排序, 最大数为 900,最多有两位小数
     */

    public void countSort(double[] data) {
        int[] temp = new int[90100];
        for (int i = 0; i < data.length; i++) {
            temp[(int) (data[i] * 100)]++;
        }
        for (int j = 0; j < temp.length; j++) {
            if (temp[j] > 0) {
                for (int k = 1; k <= temp[j]; k++) {
                    System.out.println((double) (j / 100));
                }
            }
        }
    }


}
