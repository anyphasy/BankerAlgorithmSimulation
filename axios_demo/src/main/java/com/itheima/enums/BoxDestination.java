package com.itheima.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum BoxDestination {

    AUN(1,"澳洲", 63.0,63.0,63.0,22.0),
    USY(2,"美国", 63.0,63.0,63.0,22.0),
    UKB(3,"英国", 63.0,63.0,63.0,15.0),
    DEW(4,"德国", 63.0,63.0,63.0,22.5),
    JPY(5,"日本", 60.0,50.0,50.0,40.0);

    @EnumValue
    private final Integer id;
    private final String country;
    private final Double LengthCons;
    private final Double WidthCons;
    private final Double HeightCons;
    private final Double WeightCons;


    BoxDestination(Integer id, String country, Double lengthCons, Double widthCons, Double heightCons, Double weightCons) {
        this.id = id;
        this.country = country;
        LengthCons = lengthCons;
        WidthCons = widthCons;
        HeightCons = heightCons;
        WeightCons = weightCons;
    }
}
