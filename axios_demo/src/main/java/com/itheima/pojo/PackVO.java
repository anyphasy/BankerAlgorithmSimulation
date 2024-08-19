package com.itheima.pojo;

import com.itheima.enums.BoxDestination;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PackVO {

    private BoxDestination destination;
    private List<Box> boxList;
    private List<Integer> numList;
}
