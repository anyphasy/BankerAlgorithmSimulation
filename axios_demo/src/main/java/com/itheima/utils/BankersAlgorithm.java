package com.itheima.utils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;


public class BankersAlgorithm {

    private final int[][] Need;                 //需求矩阵
    private int sourceKinds;              //系统资源种类数
    private int proNums;               //系统进程数目
    private int[] source;                 //初始资源数量
    private int[][] Max;                 //线程需要的最大资源数矩阵
    private int[][] Allocation;          //已分配给线程的资源矩阵
    private int[] Available;              //资源剩余矩阵
    private int[] Work;                    //系统可提供给进程继续运行所需的各类资源数目
    private boolean finish[];             //判断状态
    private ArrayList<String> result;     //输出结果

    public BankersAlgorithm(int proNums, int sourceKinds, int[] source) {   //对数组进行初始化
        this.proNums = proNums;
        this.sourceKinds = sourceKinds;
        this.source = source;
        this.Max = new int[proNums][sourceKinds];
        this.Allocation = new int[proNums][sourceKinds];
        this.Need = new int[proNums][sourceKinds];
        finish = new boolean[proNums];
        for (int i = 0; i < proNums; i++) {
            this.Max[i] = new int[sourceKinds];
            this.Allocation[i] = new int[sourceKinds];
            this.Need[i] = new int[sourceKinds];
            finish[i] = false;
        }

        Available = new int[sourceKinds];
        Work = new int[sourceKinds];
        result = new ArrayList<>();
    }

//    public void setMax(Scanner inMax) {    //设置进程需要最大资源数
//        for (int i = 0; i < proNums; i++)
//            for (int j = 0; j < sourceKinds; j++) {
//                Max[i][j] = inMax.nextInt();
//            }
//    }

    public void setMax(int[][] inMax) {    //设置进程需要最大资源数
        for (int i = 0; i < proNums; i++)
            if (sourceKinds >= 0) {
                System.arraycopy(inMax[i], 0, Max[i], 0, sourceKinds);
            }
    }

//    public void setAllocation(Scanner inAllocation) {  //设置初始已分配资源
//        for (int i = 0; i < proNums; i++)
//            for (int j = 0; j < sourceKinds; j++) {
//                Allocation[i][j] = inAllocation.nextInt();
//            }
//    }

    public void setAllocation(int[][] inAllocation) {  //设置初始已分配资源
        for (int i = 0; i < proNums; i++)
            if (sourceKinds >= 0) {
                System.arraycopy(inAllocation[i], 0, Allocation[i], 0, sourceKinds);
            }
    }
    private void setNeed(int[][] inNeed ) {  //计算need矩阵
        for (int i = 0; i < proNums; i++) {
            if (sourceKinds >= 0) {
                System.arraycopy(inNeed[i], 0, Need[i], 0, sourceKinds);
            }
        }
        System.arraycopy(Available, 0, Work, 0, Available.length);
    }

    private void setAvailableAndWork(int[] inAvailable) {
        System.arraycopy(inAvailable, 0, Available, 0, inAvailable.length);
        System.arraycopy(inAvailable, 0, Work, 0, inAvailable.length);
    }


    //旧的,计算need矩阵
    private void setNeed() {
        for (int i = 0; i < proNums; i++) {
            for (int j = 0; j < sourceKinds; j++)
                Need[i][j] = Max[i][j] - Allocation[i][j];
        }
        for (int i = 0; i < sourceKinds; i++) {  //计算剩余资源矩阵
            int sum = 0;
            for (int j = 0; j < proNums; j++)
                sum += Allocation[j][i];
            Available[i] = source[i] - sum;
        }
        System.arraycopy(Available, 0, Work, 0, Available.length);
    }

//    private void findSafeSequence(int k, StringBuilder out_result) {    //找所有的安全序列算法，运用DFS
//        if (k == proNums) {                     //当遍历到深度为proNums，表示找到安全序列，进行输出
//            for (int i = 0; i < result.size(); i++)
//                out_result.append("P").append(result.get(i)).append(" ");
//            out_result.append("\n");
//            return;
//        }
//
//        for (int i = 0; i < proNums; i++) {
//            if (!finish[i]) {
//                boolean task = true;
//                for (int j = 0; j < sourceKinds; j++) {
//                    if (Need[i][j] > Work[j])
//                        task = false;
//                }
//                if (task) {                                 //满足要求，对数据修改
//                    for (int j = 0; j < sourceKinds; j++)
//                        Work[j] += Allocation[i][j];
//                    finish[i] = true;
//                    result.add(i + "");
//                    findSafeSequence(k + 1, out_result);    //递归进入下一层
//
//                    result.remove((result.size() - 1));     //回退该层，并进行数据还原
//                    for (int j = 0; j < sourceKinds; j++)
//                        Work[j] -= Allocation[i][j];
//                    finish[i] = false;
//                }
//            }
//        }
//    }

    //找所有的安全序列算法，运用DFS
    private void findSafeSequence2(int k, StringBuffer out_result) {
        //当遍历到深度为proNums，表示找到安全序列，进行输出
        if (k == proNums) {
            for (int i = 0; i < result.size(); i++) {
                out_result.append("P").append(result.get(i)).append(" ");
            }
            out_result.append("\n");
            return;
        }

        for (int i = 0; i < proNums; i++) {
            if (!finish[i]) {
                boolean task = true;
                for (int j = 0; j < sourceKinds; j++) {
                    if (Need[i][j] > Work[j]) {
                        task = false;
                        break;
                    }
                }
                if (task) {                                 //满足要求，对数据修改
                    for (int j = 0; j < sourceKinds; j++)
                        Work[j] += Allocation[i][j];
                    finish[i] = true;
                    result.add(i + "");
                    findSafeSequence2(k + 1, out_result);    //递归进入下一层

                    result.remove((result.size() - 1));     //回退该层，并进行数据还原
                    for (int j = 0; j < sourceKinds; j++)
                        Work[j] -= Allocation[i][j];
                    finish[i] = false;
                }
            }
        }
    }
//    private void findSafeSequence(int k, JTextArea out_result) {    //找所有的安全序列算法，运用DFS
//        if (k == proNums) {                     //当遍历到深度为proNums，表示找到安全序列，进行输出
//            for (int i = 0; i < result.size(); i++)
//                out_result.append("P" + result.get(i));
//            out_result.append("\n");
//            return;
//        }
//
//        for (int i = 0; i < proNums; i++) {
//            if (!finish[i]) {
//                boolean task = true;
//                for (int j = 0; j < sourceKinds; j++) {
//                    if (Need[i][j] > Work[j])
//                        task = false;
//                }
//                if (task) {                                 //满足要求，对数据修改
//                    for (int j = 0; j < sourceKinds; j++)
//                        Work[j] += Allocation[i][j];
//                    finish[i] = true;
//                    result.add(i + "");
//                    findSafeSequence(k + 1, out_result);    //递归进入下一层
//
//                    result.remove((result.size() - 1));     //回退该层，并进行数据还原
//                    for (int j = 0; j < sourceKinds; j++)
//                        Work[j] -= Allocation[i][j];
//                    finish[i] = false;
//                }
//            }
//        }
//    }

    //    public void initInput(Scanner inMax, Scanner inAllocation, StringBuilder out_result) {   //将初始化以及寻找安全序列函数整合到一起
//        setMax(inMax);
//        setAllocation(inAllocation);
//        setNeed();
//        findSafeSequence(0, out_result);
//    }
//    public void initInput(int[][] inMax, int[][] inAllocation, StringBuilder out_result) {   //将初始化以及寻找安全序列函数整合到一起
//        setMax(inMax);
//        setAllocation(inAllocation);
//        setNeed();
//        findSafeSequence(0, out_result);
//    }
    public void initInput(int[][] inMax, int[][] inAllocation, int[][] inNeed,int[] inAvailable,StringBuffer out_result) {   //将初始化以及寻找安全序列函数整合到一起
        setMax(inMax);
        setAllocation(inAllocation);
        setNeed(inNeed);
        setAvailableAndWork(inAvailable);
        findSafeSequence2(0, out_result);
    }
    public void initInput(int[][] inMax, int[][] inAllocation, StringBuffer out_result) {   //将初始化以及寻找安全序列函数整合到一起
        setMax(inMax);
        setAllocation(inAllocation);
       setNeed();
        findSafeSequence2(0, out_result);
    }


}


