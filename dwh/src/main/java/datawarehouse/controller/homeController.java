package datawarehouse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hongjiayong on 2016/12/30.
 */
@Controller
public class homeController {

    /**
     * 按时间查询
     * @return
     */
    @RequestMapping("/time")
    public String time(){
        return "time";
    }

    /**
     * 按名称查找
     * @return
     */
    @RequestMapping("/name")
    public String name(){
        return "name";
    }

    /**
     * 按导演查找
     * @return
     */
    @RequestMapping("/director")
    public String director(){
        return "director";
    }

    /**
     * 按演员查找
     * @return
     */
    @RequestMapping("/actor")
    public String actor(){
        return "actor";
    }

    /**
     * 按类别查找
     * @return
     */
    @RequestMapping("/category")
    public String category(){
        return "category";
    }

    /**
     * 按组合查找
     * @return
     */
    @RequestMapping("/combine")
    public String combine(){
        return "combine";
    }

    @RequestMapping("/yun")
    public String yun(){
        return "yun";
    }
}
