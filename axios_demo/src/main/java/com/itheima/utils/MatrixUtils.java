package com.itheima.utils;

import com.itheima.pojo.BankArr;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MatrixUtils {

    /**
     * 把一维数组格式化字符串输出
     * @param numbers
     * @return
     */
    public static String changToArrayString(int[] numbers) {
        return changToArrayString(numbers, "[", "]", ", ");
    }

    /**
     * 把一维数组格式化字符串输出,支持写开始符号,结束符号和分隔符
     * @param numbers
     * @param startSymbol
     * @param endSymbol
     * @param separator
     * @return
     */
    public static String changToArrayString(int[] numbers, String startSymbol, String endSymbol, String separator) {
        // 在这里实现函数的具体逻辑

        return startSymbol +
                Arrays.stream(numbers)
                        .mapToObj(num -> num < 10 ? " " + num : String.valueOf(num))
                        .collect(Collectors.joining(separator)) +
                endSymbol;
    }


    /**
     * 解析并恢复以逗号分隔的整型数字数组字符串为一维整型数组
     *
     * @param trimmedArrayString
     * @return
     */
    public static int[] parseTrimmedArrayString(String trimmedArrayString) {
        // 将字符串按逗号分隔成字符串数组
        String[] stringArray = trimmedArrayString.split(",\\s?");
        // 创建一个整型数组并将字符串数组转换为整型数组
        int[] intArray = new int[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            intArray[i] = Integer.parseInt(stringArray[i]);
        }

        return intArray;
    }

    /**
     * 解析并恢复二维矩阵字符串为二维整型数组
     *
     * @param matrixString
     * @return
     */
    public static int[][] parseMatrixString(String matrixString) {

        String[] rows = matrixString.split("\n");
        int numRows = rows.length;
        int numCols = rows[0].trim().split("\\s+").length;

        int[][] matrix = new int[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            String[] elements = rows[i].trim().split("\\s+");
            for (int j = 0; j < numCols; j++) {
                matrix[i][j] = Integer.parseInt(elements[j]);
            }
        }

        return matrix;
    }

    /**
     * 解析数据库存储的最终结果,每行结果解析成一个一维数组,最后返回一个二维数组
     * @param FinalResultString
     * @return
     */
    public static int[][] parseFinalResultString(String FinalResultString) {

        String[] rows = FinalResultString.split("\n");
        int numRows = rows.length;
        int numCols = rows[0].trim().split("\\s").length;

        int[][] matrix = new int[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            String[] elements = rows[i].trim().split("\\s");
            for (int j = 0; j < numCols; j++) {
                String substring = elements[j].substring(1);
                matrix[i][j] = Integer.parseInt(substring);
            }
        }

        return matrix;
    }

    /**
     * 把二维数组转化为矩阵
     *
     * @param matrix
     * @return 字符串格式化矩阵
     */
    public static String changeToMatrix(int[][] matrix) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] < 10) {
                    sb.append(" "); // 如果数字是1位数，补一个空格
                }
                sb.append(matrix[i][j]);
                if (j < matrix[i].length - 1) {
                    sb.append(" ");
                }
            }
            if (i < matrix.length - 1) {
                sb.append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    /**
     * 矩阵相减,matrix1-matrix2
     *
     * @param matrix1
     * @param matrix2
     * @return 二维数组
     */
    public static int[][] subtractMatrices(int[][] matrix1, int[][] matrix2) {
        if (matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length) {
            throw new IllegalArgumentException("Matrix dimensions must be the same");
        }

        int numRows = matrix1.length;
        int numCols = matrix1[0].length;
        int[][] result = new int[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                result[i][j] = matrix1[i][j] - matrix2[i][j];
            }
        }

        return result;
    }


}
