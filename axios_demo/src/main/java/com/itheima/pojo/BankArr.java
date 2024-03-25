package com.itheima.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("bank_str_store")
public class BankArr {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer processesNum;
    private Integer resourceKinds;
    private String resourcePerKind;
    private String Max;
    private String Allocation;
    private String Need;
    private String Available;
    private String finalResult;
    private LocalDateTime createTime;//创建时间



    private LocalDateTime updateTime;//更新时间
    public BankArr(Integer processesNum, Integer resourceKinds, String resourcePerKind, String max, String allocation, String need, String available, String finalResult) {
        this.processesNum = processesNum;
        this.resourceKinds = resourceKinds;
        this.resourcePerKind = resourcePerKind;
        Max = max;
        Allocation = allocation;
        Need = need;
        Available = available;
        this.finalResult = finalResult;
    }

    /**
     * 打印所有信息
     *
     */
    public  void printProperties() {
        System.out.println("进程数目:" + processesNum);
        System.out.println("资源种类数目:" + resourceKinds);
        System.out.println("每种资源的数目:[" + resourcePerKind + "]");
        System.out.println("Max矩阵:");

        System.out.println(Max);
        System.out.println("Allocation矩阵:");

        System.out.println(Allocation);
        System.out.println("Need矩阵:");
        System.out.println(Need);

        System.out.println("Available数组:[" + Available + "]");
        System.out.println("===========================分隔线==========================");
    }

    /**
     * 打印所有信息
     * @param bankArr
     */
    public static void printProperties(BankArr bankArr) {
        System.out.println("进程数目:" + bankArr.getProcessesNum());
        System.out.println("资源种类数目:" + bankArr.getResourceKinds());
        System.out.println("每种资源的数目:[" + bankArr.getResourcePerKind() + "]");
        System.out.println("Max矩阵:");

        System.out.println(bankArr.getMax());
        System.out.println("Allocation矩阵:");

        System.out.println(bankArr.getAllocation());
        System.out.println("Need矩阵:");
        System.out.println(bankArr.getNeed());

        System.out.println("Available数组:[" + bankArr.getAvailable() + "]");
        System.out.println("===========================分隔线==========================");
    }
}
