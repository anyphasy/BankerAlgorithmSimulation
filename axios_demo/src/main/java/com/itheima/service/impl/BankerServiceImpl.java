package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.mapper.BankArrMapper;
import com.itheima.pojo.BankArr;
import com.itheima.pojo.BankerVO;
import com.itheima.service.BankerService;
import com.itheima.utils.BankersAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.itheima.utils.MatrixUtils;

@Service
public class BankerServiceImpl implements BankerService {


    @Autowired
    private BankArrMapper bankArrMapper;

    // 计算资源利用率
    private static double calculateResourceUtilization(int[] Allocation, int[] Available) {
        double utilization = 0.0;
        int totalAllocation = Arrays.stream(Allocation).sum();
        int totalAvailable = Arrays.stream(Available).sum();
        if (totalAvailable != 0) {
            utilization = (double) totalAllocation / totalAvailable;
        }
        return utilization;
    }

    // 根据资源利用率排序安全序列
    private static List<String> sortSafeSequencesByResourceUtilization(List<String> safeSequences, int[][] Allocation, int[] Available) {
        Map<String, Double> utilizationMap = new HashMap<>();
        for (String sequence : safeSequences) {
            double utilization = calculateResourceUtilization(getAllocationForSequence(sequence, Allocation), Available);
            utilizationMap.put(sequence, utilization);
        }

        // 将安全序列按照资源利用率从大到小排序
        List<Map.Entry<String, Double>> sortedList = new ArrayList<>(utilizationMap.entrySet());
        sortedList.sort((entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue()));

        // 返回排序后的安全序列列表
        List<String> sortedSequences = new ArrayList<>();
        for (Map.Entry<String, Double> entry : sortedList) {
            sortedSequences.add(entry.getKey());
        }
        return sortedSequences;
    }

    // 获取特定安全序列的分配情况
    private static int[] getAllocationForSequence(String sequence, int[][] Allocation) {
        String[] split = sequence.substring(1).split("\\s?P");
        int processNum = split.length;
        int[] allocation = new int[Allocation[0].length];
        for (int i = 0; i < processNum; i++) {
            int processIndex = Integer.parseInt(String.valueOf(split[i]));
            for (int j = 0; j < Allocation[0].length; j++) {
                allocation[j] += Allocation[processIndex][j];
            }
        }
        return allocation;
    }

    @Override
    public BankArr generateExample() {
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
        buffer.append("P1 P3 P0 P2 P4 \n" +
                "P1 P3 P0 P4 P2 \n" +
                "P1 P3 P2 P0 P4 \n" +
                "P1 P3 P2 P4 P0 \n" +
                "P1 P3 P4 P0 P2 \n" +
                "P1 P3 P4 P2 P0 \n" +
                "P1 P4 P3 P0 P2 \n" +
                "P1 P4 P3 P2 P0 \n" +
                "P3 P1 P0 P2 P4 \n" +
                "P3 P1 P0 P4 P2 \n" +
                "P3 P1 P2 P0 P4 \n" +
                "P3 P1 P2 P4 P0 \n" +
                "P3 P1 P4 P0 P2 \n" +
                "P3 P1 P4 P2 P0 \n" +
                "P3 P4 P1 P0 P2 \n" +
                "P3 P4 P1 P2 P0 \n");
//        BankersAlgorithm banker = new BankersAlgorithm(processesNum, resourceKinds, resourcePerKind);
//        banker.initInput(Max, Allocation, Need, Available, buffer);

//        System.out.println(buffer);
        String finalResult = buffer.toString();
        String rPK = Arrays.toString(resourcePerKind);
        rPK = rPK.substring(1, rPK.length() - 1);
        String _Max = MatrixUtils.changeToMatrix(Max);
        String _Allocation = MatrixUtils.changeToMatrix(Allocation);
        String _Need = MatrixUtils.changeToMatrix(Need);
        String avai = Arrays.toString(Available);
        avai = avai.substring(1, avai.length() - 1);

        String sortedResult = sortSafeSequence(finalResult, Allocation, Available);//
        BankArr bankerMA = new BankArr(processesNum, resourceKinds, rPK, _Max, _Allocation, _Need, avai, sortedResult);
        bankArrMapper.insert(bankerMA);
        //打印矩阵与安全序列结果
        BankArr.printProperties(bankerMA);

        return bankerMA;
    }

    private String sortSafeSequence(String finalResult, int[][] _allocation, int[] _available) {
        String re = finalResult.strip();
        String[] split = re.split("\\s\n");
        List<String> safeSequences = new ArrayList<>(Arrays.asList(split));


        // 根据资源利用率排序安全序列
        List<String> sortedSequences = sortSafeSequencesByResourceUtilization(safeSequences, _allocation, _available);

        // 输出排序后的安全序列列表
        System.out.println("Sorted Safe Sequences by Resource Utilization:");
        for (String sequence : sortedSequences) {
            System.out.println(sequence);
        }
        StringBuffer sortedFinResult = new StringBuffer();
        for (String sortedSequence : sortedSequences) {
            sortedFinResult.append(sortedSequence).append("\n");
        }
        return sortedFinResult.toString();
    }

    @Override
    public BankArr generateRandom() {
        Random r = new Random();
//        int processesNum = 5;
//        int resourceKinds = 4;
//        设置processesNum  ==>5 ~7 行
//        设置resourceKinds   ==>3 ~5 列
        int processesNum = r.nextInt(3) + 5;//行
        int resourceKinds = r.nextInt(3) + 3;//列
        //每种资源数目3~20
        int[] resourcePerKind = new int[resourceKinds];
        int[] Available = new int[resourceKinds];
        int[] remainArray = new int[resourceKinds];
        int[][] Allocation = new int[processesNum][resourceKinds];
        int[] arr = new int[processesNum];//临时一维数组
        int[][] Max = new int[processesNum][resourceKinds];
        int[][] Need;
        for (int i = 0; i < resourceKinds; i++) {
            //0~17==>3~20
            resourcePerKind[i] = r.nextInt(18) + 3; // 生成 3 到 20 之间的随机数
        }

        for (int i = 0; i < resourceKinds; i++) {
            //0~17==>3~20
            while (true) {
                int temp = r.nextInt(18) + 3; // 生成 3 到 20 之间的随机数
                if (temp < resourcePerKind[i]) {
                    Available[i] = temp;
                    break;
                }
            }
        }

        for (int i = 0; i < resourceKinds; i++) {
            remainArray[i] = resourcePerKind[i] - Available[i];
        }


        for (int col = 0; col < resourceKinds; col++) {

            int sum = 0;

            while (true) {
                int counts = remainArray[col];
                for (int row = 0; row < processesNum; row++) {
                    int i = r.nextInt(counts - sum + 1);//加1是代表可以取边界值,闭区间
                    counts -= i;
                    arr[row] = i;
                }
                //必须总和等于才能跳出循环
                if (Arrays.stream(arr).sum() == remainArray[col]) {
                    break;
                }
            }
            for (int row = 0; row < processesNum; row++) {
                Allocation[row][col] = arr[row];
            }
        }

        for (int col = 0; col < resourceKinds; col++) {
            int b = resourcePerKind[col];
            for (int row = 0; row < processesNum; row++) {
                int a = Allocation[row][col];
                Max[row][col] = r.nextInt(b - a + 1) + a;
            }
        }
        Need = MatrixUtils.subtractMatrices(Max, Allocation);

        //======================以上为随机生成

        StringBuffer buffer = new StringBuffer();
        BankersAlgorithm banker = new BankersAlgorithm(processesNum, resourceKinds, resourcePerKind);
        banker.initInput(Max, Allocation, Need, Available, buffer);

//        System.out.println(builder);
        String finalResult = buffer.toString();
        String sortedResult="";
        if (!finalResult.isEmpty()) {
            System.out.println(buffer);
        } else {
            System.out.println("该情况不存在安全序列");
            sortedResult = "该情况不存在安全序列";
        }


//        List<Integer> rPK = Arrays.stream(resourcePerKind).boxed().toList();
//        bankerMA.setResourcePerKind(rPK);
//        bankerMA.setAvailable(Arrays.stream(Available).boxed().toList());

        String rPK = Arrays.toString(resourcePerKind);
        rPK = rPK.substring(1, rPK.length() - 1);
        String _Max = MatrixUtils.changeToMatrix(Max);
        String _Allocation = MatrixUtils.changeToMatrix(Allocation);
        String _Need = MatrixUtils.changeToMatrix(Need);
        String avai = Arrays.toString(Available);
        avai = avai.substring(1, avai.length() - 1);
        //排序
        if (!sortedResult.contains("该情况不存在安全序列")) {
            sortedResult = sortSafeSequence(finalResult, Allocation, Available);//
        }
//        BankArr bankerMA = new BankArr(processesNum, resourceKinds, rPK, _Max, _Allocation, _Need, avai, finalResult);
        BankArr bankerMA = new BankArr(processesNum, resourceKinds, rPK, _Max, _Allocation, _Need, avai, sortedResult);
        bankArrMapper.insert(bankerMA);
        //打印矩阵与安全序列结果
        BankArr.printProperties(bankerMA);
        return bankerMA;


    }

    @Override
    public List<BankerVO> queryLastSafeSequence(int[] sequence) {

        LambdaQueryWrapper<BankArr> wrapper = new LambdaQueryWrapper<BankArr>()
                .select(BankArr::getProcessesNum,
                        BankArr::getResourceKinds,
                        BankArr::getResourcePerKind,
                        BankArr::getMax,
                        BankArr::getAllocation,
                        BankArr::getNeed,
                        BankArr::getAvailable,
                        BankArr::getFinalResult)
                .orderByDesc(BankArr::getUpdateTime).last("Limit 1");

        BankArr bankArr = bankArrMapper.selectOne(wrapper);
        int[][] finalResult = MatrixUtils.parseFinalResultString(bankArr.getFinalResult());


//        int[] seArray = sequence.stream().mapToInt(Integer::intValue).toArray();
        boolean flag = false;
        for (int[] row : finalResult) {
            if (Arrays.equals(row, sequence)) {
                flag = true;
                break;
            }
        }


        if (flag) {
            BankArr.printProperties(bankArr);
//        int[] resourcePerKind = MatrixUtils.parseTrimmedArrayString(bankArr.getResourcePerKind());
            int[][] Allocation = MatrixUtils.parseMatrixString(bankArr.getAllocation());
            int[][] Need = MatrixUtils.parseMatrixString(bankArr.getNeed());
            int[] Available = MatrixUtils.parseTrimmedArrayString(bankArr.getAvailable());
            int[] Work = new int[bankArr.getResourceKinds()];
            List<BankerVO> bankerVOList = new ArrayList<>();
            System.arraycopy(Available, 0, Work, 0, Available.length);


            //List.of(1,3,4,2,0)
            // 根据安全序列创建 BankerVO 对象列表
            for (Integer row : sequence) {
                String pName = "P" + row;
                String workOld = MatrixUtils.changToArrayString(Work, "", "", " ");
                String need = MatrixUtils.changToArrayString(Need[row], "", "", " ");
                String allocation = MatrixUtils.changToArrayString(Allocation[row], "", "", " ");
                BankerVO bankerVO = new BankerVO(pName, workOld, need, allocation, "", true);
                for (int col = 0; col < bankArr.getResourceKinds(); col++) {
                    Work[col] += Allocation[row][col];
                }
                bankerVO.setWorkPlusAllocation(MatrixUtils.changToArrayString(Work, "", "", " "));
                // 设置相应的数据，根据数据进行填充
                bankerVOList.add(bankerVO);
            }
            return bankerVOList;
        } else {
            return null;
        }
    }


}
