package com.itheima;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.mapper.BankArrMapper;
import com.itheima.pojo.BankArr;
import com.itheima.pojo.BankerVO;
import com.itheima.pojo.Result;
import com.itheima.service.BankerService;
import com.itheima.utils.BankersAlgorithm;
import com.itheima.utils.MatrixUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SpringBootTest
class AxiosDemoApplicationTests {

    @Autowired
    private BankArrMapper bankArrMapper;
    @Autowired
    private BankerService bankerService;

    public static void printMatrix(int[][] matrix) {
        // 输出二维整型数组
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (j == matrix[i].length - 1) {
                    System.out.print(matrix[i][j]);
                    break;
                }
                System.out.print(matrix[i][j] + ",");

            }
            System.out.println();
        }
    }

    @Test
    public void testParseFinalResult() {
        String s = "P1 P3 P4 P0 P2 \n" +
                "P1 P3 P4 P2 P0 \n" +
                "P1 P4 P3 P0 P2 \n" +
                "P1 P4 P3 P2 P0 \n" +
                "P3 P1 P0 P2 P4 \n" +
                "P3 P1 P0 P4 P2 \n" +
                "P3 P1 P2 P0 P4 \n" +
                "P3 P1 P2 P4 P0 ";
        int[][] ints = MatrixUtils.parseFinalResultString(s);
        printMatrix(ints);
    }

    @Test
    public void test() {
        int processesNum = 5;
        int resourceKinds = 3;
        int[] resourcePerKind = {10, 5, 7};
        int[][] Max = {
                {7, 5, 3},
                {3, 2, 2},
                {9, 0, 2},
                {2, 2, 2},
                {4, 3, 3}
        };
        int[][] Allocation = {
                {0, 1, 0},
                {2, 0, 0},
                {3, 0, 2},
                {2, 1, 1},
                {0, 0, 2}
        };
        int[][] Need = {
                {7, 4, 3},
                {1, 2, 2},
                {6, 0, 0},
                {0, 1, 1},
                {4, 3, 1}
        };
        int[] Available = {3, 3, 2};

        StringBuffer buffer = new StringBuffer();
        BankersAlgorithm banker = new BankersAlgorithm(processesNum, resourceKinds, resourcePerKind);
        banker.initInput(Max, Allocation, Need, Available, buffer);

//        System.out.println(builder);
        String finalResult = buffer.toString();//最终结果转字符串


        BankArr bankerMA = new BankArr();

        bankerMA.setProcessesNum(processesNum);
        bankerMA.setResourceKinds(resourceKinds);

//        List<Integer> rPK = Arrays.stream(resourcePerKind).boxed().toList();

        String rPk = Arrays.toString(resourcePerKind);
        bankerMA.setResourcePerKind(rPk);

        String _Max = MatrixUtils.changeToMatrix(Max);
        bankerMA.setMax(_Max);

        String _Allocation = MatrixUtils.changeToMatrix(Allocation);
        bankerMA.setAllocation(_Allocation);

        String _Need = MatrixUtils.changeToMatrix(Need);
        bankerMA.setNeed(_Need);
        String _Available = Arrays.toString(Available);
        bankerMA.setAvailable(_Available);

        bankerMA.setFinalResult(finalResult);
        bankArrMapper.insert(bankerMA);

//        System.out.println(bankerMA);


    }

    @Test
    public void queryLastUpdateOne() {
//        List<Integer> list = Arrays.fill(1, 4, 3, 2, 0);//正确序列
//        List<Integer> list = List.of(1, 4,2,0,3);错误序列
        int[] list = {1, 4, 3, 2, 0};

        List<BankerVO> bankerVOS = bankerService.queryLastSafeSequence(list);
        if (bankerVOS != null) {
            System.out.println("查询安全序列" + Arrays.toString(list) + "的结果如下");

            bankerVOS.forEach(System.out::println);
        } else {
            System.out.println("没有此序列,请检查输入是否正确");
        }
    }

    @Test
    public void testRegex() {
        String[] a = {
                "1,3,4,5,6",
                "1,2,4,,5",
                "1,2,4,,5 "
        };
        String regexp = "^(\\d{1,2},?){5,}$";
//        Pattern pattern = Pattern.compile(regexp);
//        Matcher matcher = pattern.matcher(a);
//        while (matcher.find()) {
//            String a = matcher.group(); // 获取匹配到的 IP 地址
////                        System.out.println("匹配到的 IP 地址：" + targetIpAddress);
//        }

        for (String s : a) {
            if (s.matches(regexp)) {
                System.out.println(s + "   正确~~~");
            } else {
                System.out.println(s + "   错误~~~");
            }

        }
    }

}
//        // 使用Stream API将二维整型数组转换为嵌套List类型
//        List<List<Integer>> _Max = Arrays.stream(Max)
//                .map(row -> Arrays.stream(row)
//                        .boxed()
//                        .collect(Collectors.toList()))
//                .toList();

//        bankerMA.setMax(_Max);

//        List<List<Integer>> _Allocation = Arrays.stream(Allocation)
//                .map(row -> Arrays.stream(row)
//                        .boxed()
//                        .collect(Collectors.toList()))
//                .toList();
//        bankerMA.setAllocation(_Allocation);

//        List<List<Integer>> _Need = Arrays.stream(Need)
//                .map(row -> Arrays.stream(row)
//                        .boxed()
//                        .collect(Collectors.toList()))
//                .toList();
//        bankerMA.setNeed(_Need);


//        bankerMA.setAvailable(Arrays.stream(Available).boxed().toList());
