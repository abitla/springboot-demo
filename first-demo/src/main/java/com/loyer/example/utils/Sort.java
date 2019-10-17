package com.loyer.example.utils;

/**
 * @author kuangq
 * @projectName example
 * @title Sort
 * @description 排序
 * @date 2019-08-09 14:14
 */
public class Sort {
    public static void main(String[] args) {
        int[] data = {4, 3, 5, 1, 2}; // 声明需要排序的数组

        // SelectionSort.Select(data); //通过类名调用方法
        // BubbleSort.Bubble(data);
        InsertionSort.Insert(data);

        for (int i = 0; i < data.length; i++) // 通过for循环遍历数组
            System.out.print(data[i]);

        // for(int n:data) //通过增强for循环遍历数组
        // System.out.print(n);
    }
}

/*选择排序:主要思想是寻找未排序中最小的元素加入到已有序列，直到未排序序列为空*/
class SelectionSort {
    public static void Select(int data[]) {

        for (int i = 0; i < data.length; i++) // 循环分割有序和无序部分,取到最小值放到无序的第一个位置
        {
            // System.out.println("---------第"+(i+1)+"次循环---------");
            int smaller = i; // 设定一个值,依次跟每一个值比较，来确定最小值
            // int b=0; //记录比较次数
            for (int sum = i + 1; sum < data.length; sum++) // 最多需要sum次比较就能取得最小值
            {
                // b++;
                // System.out.println("第"+b+"次比较"+"[smaller]="+data[smaller]+"\t"+"[sum]="+data[sum]);
                if (data[smaller] > data[sum]) // 如果最小值大于比较值
                {
                    smaller = sum; // 通过下标赋值最小值继续跟后面的值比较
                    // System.out.println("赋值较小值"+"[smaller]="+data[smaller]);
                }
            }
            // System.out.println("找到最小值"+data[smaller]+"\t"+"如果找到的最小值不是当前第一个位置就互换两个值的位置");
            // System.out.println("最小值位置：smaller="+smaller+"\t"+"第一个值位置：i="+i);
            if (smaller != i) // 如果找到的最小值不是当前第一个位置
            {
                // System.out.println("换值前"+"[smaller]="+data[smaller]+"\t"+"[i]="+data[i]);
                int tempData = data[smaller]; // 就互换两个值的位置
                data[smaller] = data[i];
                data[i] = tempData;
                // System.out.println("换值后"+"[smaller]="+data[smaller]+"\t"+"[i]="+data[i]);
            }
        }
    }
}

/*冒泡排序:主要思想是进行相邻的两个元素之间比较并且交换，原则是将较大值放在最后面，有利于利用原有元素在集合中的位置优势*/
class BubbleSort {
    public static void Bubble(int data[]) {
        int temp; // 创建一个值用来交换位置
        for (int i = 0; i < data.length - 1; i++) // 至多排序i次，就能排出顺序
        {
            // System.out.println("排序"+(i+1)+"次");
            for (int j = 0; j < data.length - 1 - i; j++) // 轮循开始后数组最后一个值已经排好无序再排
            {
                // System.out.println(data[j]+"\t"+data[j+1]);
                if (data[j] > data[j + 1]) // 如果相邻两个值前面比后面大
                {
                    temp = data[j]; // 交换二者位置
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                    // for(int ii=0;ii<data.length;ii++)
                    // System.out.print(data[ii]);
                    // System.out.println("");
                }
            }
        }
    }
}

/*插入排序:主要思想是随意选取一个无序部分元素到有序部分中，寻找它所在的位置进行插入，保持有序部分仍然有序*/
class InsertionSort {
    public static void Insert(int data[]) {
        int current; // 创建一个值用来 43512
        for (int i = 1; i < data.length; i++) // 假定第一个元素就是有序部分，所以从第二个位置开始
        {
            // System.out.println("第"+i+"次循环");
            current = data[i]; // 从第二个位置开始选取无序值
            // System.out.println("选择需要插入的无序值"+current);
            for (int j = i - 1; j >= 0; j--) {
                if (current < data[j]) // 如果无序值小于前一个有序值
                {
                    // System.out.println("如果无序值"+current+"<"+"有序值"+data[j]);
                    data[j + 1] = data[j];
                    // System.out.println("就放在后面");
                    // for(int ii=0;ii<data.length;ii++)
                    // System.out.print(data[ii]);
                    // System.out.println("");
                } else {
                    // System.out.println("如果无序值"+current+">"+"有序值"+data[j]);
                    data[j + 1] = current;
                    // System.out.println("就放在后面");
                    // for(int ii=0;ii<data.length;ii++)
                    // System.out.print(data[ii]);
                    // System.out.println("");
                    break;
                }
                if (j == 0) {
                    // System.out.println("如果有序值在第一个位置");
                    data[j] = current;
                    // for(int ii=0;ii<data.length;ii++)
                    // System.out.print(data[ii]);
                    // System.out.println("");
                }
            }
        }
    }
}
