package com.itheima.service;

import com.itheima.pojo.BankArr;
import com.itheima.pojo.BankerVO;

import java.util.ArrayList;
import java.util.List;

public interface BankerService {


    BankArr generateExample();

    BankArr generateRandom();

    List<BankerVO> queryLastSafeSequence(int[] sequence);
}
