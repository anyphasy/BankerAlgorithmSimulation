package com.itheima.controller;

import com.itheima.pojo.Article;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/article")
//@RequestMapping("/user")
@CrossOrigin//支持跨域
public class ArticleController {

    private final List<Article> articleList = new ArrayList<>();

    {
        articleList.add(new Article("匡光力竟然是段飞的爸爸", "安大时事", "2024-03-03", "已发布"));
        articleList.add(new Article("医疗反腐绝非砍医护收入", "时事", "2023-09-5", "已发布"));
        articleList.add(new Article("中国男篮缘何一败涂地", "篮球", "2023-09-5", "草稿"));
        articleList.add(new Article("华山景区已受大风影响阵风达7-8级", "旅游", "2023-09-5", "已发布"));
        articleList.add(new Article("城市垃圾分类新规将于下月实施", "环保", "2023-09-06", "已发布"));
        articleList.add(new Article("影视剧《长安十二时辰》收视率破纪录", "娱乐", "2023-09-06", "草稿"));
        articleList.add(new Article("全国铁路局加强安全管理提升服务质量", "交通", "2023-09-06", "已发布"));
        articleList.add(new Article("科技公司推出全新智能手表产品", "科技", "2023-09-06", "已发布"));
        articleList.add(new Article("2023年度国际农业展览即将开幕", "农业", "2023-09-06", "草稿"));
        articleList.add(new Article("中国女排备战奥运会成绩喜人", "体育", "2023-09-06", "已发布"));
        articleList.add(new Article("新型疫苗研发取得重大突破", "健康", "2023-09-06", "草稿"));
        articleList.add(new Article("国家博物馆举办《明清文物展览》", "文化", "2023-09-06", "已发布"));
        articleList.add(new Article("数字化人民币试点扩大至更多城市", "经济", "2023-09-06", "已发布"));
        articleList.add(new Article("青岛海鲜节吸引众多游客参与", "旅游", "2023-09-06", "草稿"));
        articleList.add(new Article("我国成功发射新一代通信卫星", "航天", "2023-09-06", "已发布"));
        articleList.add(new Article("教育部发布新政策支持高校科研", "教育", "2023-09-06", "已发布"));
        articleList.add(new Article("云南丽江举办国际花卉展览", "旅游", "2023-09-06", "草稿"));
        articleList.add(new Article("2023年度消费电子产品展示会即将开幕", "科技", "2023-09-06", "已发布"));
        articleList.add(new Article("北京首次启用自动驾驶公交车", "交通", "2023-09-06", "已发布"));
        articleList.add(new Article("国内首个5G智能工厂正式投入运营", "经济", "2023-09-06", "草稿"));
        articleList.add(new Article("中国足球青训计划成效显著", "体育", "2023-09-06", "已发布"));
        articleList.add(new Article("医疗机器人在手术中发挥重要作用", "健康", "2023-09-06", "草稿"));
        articleList.add(new Article("国家图书馆举办《古代名人文化展览》", "文化", "2023-09-06", "已发布"));

    }

    //新增文章
    @PostMapping("/add")
    public String add(@RequestBody Article article) {
        articleList.add(article);
        articleList.forEach(System.out::println);
        System.out.println("//新增文章");
        System.out.println("return \"新增成功\";");
        return "新增成功";
    }



    //获取所有文章信息
    @GetMapping("/getAll")
    public List<Article> getAll(/*HttpServletResponse response*/) {
        System.out.println(" //获取所有文章信息");
        articleList.forEach(System.out::println);
        return articleList;
    }

    //根据文章分类和发布状态搜索
    @GetMapping("/search")
    public List<Article> search(String category, String state) {
        System.out.println("根据文章分类和发布状态搜索");
        if(category.isEmpty() && state.isEmpty())
        {
           return getAll();
        }
        else if (category.isEmpty() || state.isEmpty()) {
            return articleList.stream().filter(
                            a -> a.getCategory().equals(category) || a.getState().equals(state)).
                    collect(Collectors.toList());
        }
        else{
            return articleList.stream().filter(
                            a -> a.getCategory().equals(category) && a.getState().equals(state)).
                    collect(Collectors.toList());
        }
    }
}
