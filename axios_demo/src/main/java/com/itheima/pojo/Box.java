package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Box {
    private double lx;//长
    private double ly;//宽
    private double lz;//高
    private String boxName;//货物名称
    private int type; //类型,代表几号箱子
    private double weight;//重量

    @Override
    public String toString() {
        return "lx: " + lx + ", ly: " + ly + ", lz: " + lz + ", type: " + type + ", weight: " + weight;
    }
}
