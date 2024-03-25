package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankerVO {

    private String processName;
    private String Work;
    private String Need;
    private String Allocation;
    private String WorkPlusAllocation;
    private Boolean Finish;
//    private Integer processesNum;
//    private Integer resourceKinds;
//    private List<Integer> resourcePerKind;
////    private List<List<Integer>> Max;
//    private String Max;
////    private List<List<Integer>> Allocation;
//    private String Allocation;
////    private List<List<Integer>> Need;
//    private String Need;
//    private List<Integer> Available;
//    private String finalResult;


}
