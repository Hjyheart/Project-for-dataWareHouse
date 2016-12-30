package datawarehouse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hongjiayong on 2016/12/30.
 */
@Controller
public class homeController {

    @RequestMapping("/home")
    public String home(){
        return "home";
    }

}
