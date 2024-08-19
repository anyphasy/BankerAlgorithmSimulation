package com.itheima.controller;

import com.alibaba.fastjson.JSONObject;
import com.itheima.enums.BoxDestination;
import com.itheima.pojo.Box;
import com.itheima.pojo.PackVO;
import com.itheima.service.PackAssistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/pack")
@CrossOrigin//支持跨域
public class PackAssistantController {


    private static PackVO packVO1 = new PackVO();

    static {
        // 初始化8个 Box 对象
        Box[] boxes = new Box[8];

        // 模拟数据
        double[][] dimensions = {
                {30, 20, 8}, {29, 19, 7.5}, {30.5, 20.5, 8.2}, {31, 21, 8.3},
                {29.8, 19.5, 7.8}, {30.2, 20.3, 8.1}, {29.5, 19.8, 7.7}, {30.3, 20.2, 8},
                {30.1, 20.1, 8.5}
        };

        double[] weights = {0.68, 0.65, 0.72, 0.69, 0.67, 0.71, 0.66, 0.73};

        // 初始化 Box 对象
        for (int i = 0; i < 8; i++) {
            boxes[i] = new Box();
            boxes[i].setBoxName(i + "号箱子");
            boxes[i].setLx(dimensions[i][0]);
            boxes[i].setLy(dimensions[i][1]);
            boxes[i].setLz(dimensions[i][2]);
            boxes[i].setType(i);
            boxes[i].setWeight(weights[i]);
        }
        List<Box> boxList = new ArrayList<>(List.of(boxes));

        int[] arr = new int[boxes.length];
        Arrays.fill(arr, 1);
        List<Integer> numList = Arrays.stream(arr).boxed().toList();


        packVO1 = new PackVO(BoxDestination.AUN, boxList, numList);
    }

    @Autowired
    private PackAssistService packAssistService;

    @PostMapping("/box")
    public Result queryBoxesPackaging(@RequestBody PackVO packVO) {

        BoxDestination destination = packVO.getDestination();
        System.out.println("目的地" + destination + "==>" + destination.getCountry());
        System.out.println("箱子种类:");
        packVO.getBoxList().forEach(System.out::println);
        System.out.println("每种箱子个数:" + packVO.getNumList());
        System.out.println(packVO);
        JSONObject a = packAssistService.getTargetBox(packVO);
        if(a !=null) {
            System.out.println("已经捕获到计算结果如下:");
            System.out.println(a);
            System.out.println("====================完成====================");
            return Result.success(a);

        }
        else {
            return Result.
                    error("错误,无法一次性放下,请考虑减少箱子个数,或分批计算");
        }

    }

    @GetMapping("/test")
    public Result queryBoxesPackagingTest() {
        BoxDestination destination = packVO1.getDestination();
        System.out.println("目的地" + destination + "==>" + destination.getCountry());
        System.out.println("箱子种类:");
        packVO1.getBoxList().forEach(System.out::println);
        System.out.println("每种箱子个数:" + packVO1.getNumList());

//        String a = packAssistService.getTargetBox(packVO1);
        JSONObject a = packAssistService.getTargetBox(packVO1);


        return Result.success(a);
    }
}
