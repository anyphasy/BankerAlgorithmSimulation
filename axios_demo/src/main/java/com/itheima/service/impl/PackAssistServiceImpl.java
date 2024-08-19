package com.itheima.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itheima.enums.BoxDestination;
import com.itheima.pojo.PackVO;
import com.itheima.service.PackAssistService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Service
public class PackAssistServiceImpl implements PackAssistService {
    /**
     * 把国家对应属性添加限制条件
     *
     * @param packVO1
     * @return
     */
    private static JSONObject getCountryInfoJsonObject(PackVO packVO1) {
        BoxDestination destinationInfo = packVO1.getDestination();
        // 构建新的 JSONObject 对象
        JSONObject destinationObject = new JSONObject();
        destinationObject.put("id", destinationInfo.getId());
        destinationObject.put("country", destinationInfo.getCountry());
        destinationObject.put("LengthCons", destinationInfo.getLengthCons());
        destinationObject.put("WidthCons", destinationInfo.getWidthCons());
        destinationObject.put("HeightCons", destinationInfo.getHeightCons());
        destinationObject.put("WeightCons", destinationInfo.getWeightCons());
        return destinationObject;

    }

    private static JSONObject toPython(String jsonString) {


        String targetJsonStr = null;
        // 设置Python脚本路径
        String pythonScriptPath = "D:\\pythoncode\\demo\\tCopy.py";
        String pythonInterpreter = "D:\\anacondaa\\python.exe"; // 将路径替换为你的 Python 解释器路径
        try {

//            // 传递给Python脚本的参数
//            String param1 = "2";
//            String param2 = "3";
            // 创建进程构建器
            ProcessBuilder processBuilder = new ProcessBuilder(
                    pythonInterpreter, pythonScriptPath, jsonString);
            processBuilder.redirectErrorStream(true);

            // 启动进程并执行Python脚本
            Process process = processBuilder.start();
            // 读取Python脚本的输出
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(),
                            StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("{\"OuterBoxRange\":"))
                    targetJsonStr = line.strip();
                System.out.println(line);
            }

            // 等待Python脚本执行完毕
            int exitCode = process.waitFor();
            System.out.println("Python script exited with code: " + exitCode);

            //打印错误信息
            BufferedReader errorReader = new BufferedReader(
                    new InputStreamReader(
                            process.getErrorStream()));
            String errorLine;
            //打印错误信息
            while ((errorLine = errorReader.readLine()) != null) {
                System.out.println("Error: " + errorLine); // 输出来自Python脚本的错误信息
            }

            reader.close();
            errorReader.close();


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();

        }

        JSONObject jsonObject = JSONObject.parseObject(targetJsonStr);
        return jsonObject;
    }

    @Override
    public JSONObject getTargetBox(PackVO packVO1) {

//        try {
//            String pathToSaveImage="D:\\vscode2023\\vue-project1\\";
//            File file= new File(pathToSaveImage+"src\\assets\\3d_multilayer_search.png");
//        if(file.exists()&&file.isFile())
//        {
//            boolean delete = file.delete();
//
//        }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        String jsonString = JSON.toJSONString(packVO1);
        System.out.println(jsonString);

        JSONObject jsonObject = JSONObject.parseObject(jsonString);

        System.out.println("添加国家前转换前:\n" + jsonObject);
        jsonObject.remove("destination");
        System.out.println("已经删除旧的destination:\n" + jsonObject);


        JSONObject destinationObject = getCountryInfoJsonObject(packVO1);
        jsonObject.put("destinationInfo", destinationObject);
        System.out.println("添加国家后转换后:\n" + jsonObject);
//        System.out.println("\n");
//        System.out.println(jsonObject.toJSONString());

        String escapedJsonString = jsonObject.toJSONString().replace("\"", "\\\"");
        System.out.println("添加转义字符后:\n" + escapedJsonString);
        System.out.println("python接手后输出:");
        JSONObject targetJson = null;
        targetJson = toPython(escapedJsonString);
        System.out.println("python调用结束");


//        jsonObject.put("OuterBox")
//        return null;
        return targetJson;
    }
}
