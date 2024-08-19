package com.itheima.service;

import com.alibaba.fastjson.JSONObject;
import com.itheima.pojo.Box;
import com.itheima.pojo.PackVO;

public interface PackAssistService {


//    String getTargetBox(PackVO packVO1);
    JSONObject getTargetBox(PackVO packVO1);
}
