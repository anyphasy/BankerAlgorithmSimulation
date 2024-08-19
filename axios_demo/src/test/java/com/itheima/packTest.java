package com.itheima;

import com.itheima.enums.BoxDestination;
import com.itheima.pojo.Box;
import com.itheima.pojo.PackVO;
import com.itheima.service.PackAssistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class packTest {
    @Autowired
    private PackAssistService packAssistService;


    @Test
    public void testJsonForPython()
    {
        // 初始化8个 Box 对象
//        Box[] boxes = new Box[8];
        List<Box> boxList = new ArrayList<>();
        // 模拟数据
        double[][] dimensions = {
                {30, 20, 8}, {29, 19, 7.5}, {30.5, 20.5, 8.2}, {31, 21, 8.3},
                {29.8, 19.5, 7.8}, {30.2, 20.3, 8.1}, {29.5, 19.8, 7.7}, {30.3, 20.2, 8},
                {30.1, 20.1, 8.5}
        };

        double[] weights = {0.68, 0.65, 0.72, 0.69, 0.67, 0.71, 0.66, 0.73};

        // 初始化 Box 对象

        for (int i = 0; i < 8; i++) {
            Box boxes = new Box();
            boxes.setBoxName(i+"号箱子");
            boxes.setLx(dimensions[i][0]);
            boxes.setLy(dimensions[i][1]);
            boxes.setLz(dimensions[i][2]);
            boxes.setType(i);
            boxes.setWeight(weights[i]);
            boxList.add(boxes);
        }


        int[] arr = new int[boxList.size()];
        Arrays.fill(arr, 1);
        List<Integer> numList = Arrays.stream(arr).boxed().toList();
        PackVO packVO1 = new PackVO(BoxDestination.AUN, boxList, numList);


        BoxDestination destination = packVO1.getDestination();
        System.out.println("目的地" + destination + "==>" + destination.getCountry());
        System.out.println("箱子种类:");
        packVO1.getBoxList().forEach(System.out::println);
        System.out.println("每种箱子个数:" + packVO1.getNumList());
        System.out.println(packVO1);

        packAssistService.getTargetBox(packVO1);

    }


}
