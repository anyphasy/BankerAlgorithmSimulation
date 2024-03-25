package com.itheima.controller;

import com.itheima.pojo.BankArr;
import com.itheima.pojo.BankerVO;
import com.itheima.pojo.Result;
import com.itheima.service.BankerService;
import com.itheima.utils.MatrixUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banker")
@CrossOrigin//支持跨域
public class BankerController {


    @Autowired
    public BankerService bankerMAService;

    //原始书本例子
    @GetMapping ("/example")
    public Result queryBankInitialExample()
    {
        BankArr bankMA=bankerMAService.generateExample();
        return Result.success(bankMA);
    }
    //随机生成
    @GetMapping("/random")
    public Result queryBankMaxAndAllocationRandom()
    {
        BankArr bankMA=bankerMAService.generateRandom();
        return Result.success(bankMA);
    }

    /**
     * 查询上一次随机资源的某一次安全序列的结果
     * @param sequence
     * @return
     */
    @GetMapping("/result")
//    public  Result queryLastSafeSequence(@RequestParam List<Integer> sequence)
    public  Result queryLastSafeSequence(@RequestParam String sequence)
    {
        String regexp = "^((\\d,|([1-9][0-9]),){4,})(\\d|([1-9][0-9]))$";
        System.out.println(sequence);
        if(sequence.matches(regexp)) {
            int[] seArray = MatrixUtils.parseTrimmedArrayString(sequence);

            List<BankerVO> bankerVOS=bankerMAService.queryLastSafeSequence(seArray);
            if(bankerVOS!=null) {
                System.out.println("查询了安全序列" + sequence);
                bankerVOS.forEach(System.out::println);
                return Result.success(bankerVOS);
            }
            else {
                System.out.println("没有此序列,请检查输入是否正确");
                return Result.error("没有此序列,请检查输入是否正确");
            }
        }
        else
        {
            return Result.error("输入有误,请检查输入是否正确");
        }


    }


}



