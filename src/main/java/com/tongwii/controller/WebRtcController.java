package com.tongwii.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ${DESCRIPTION}
 *
 * @author Zeral
 * @date 2017-07-19
 */
@Controller
public class WebRtcController {
    // 获取房间号
    @RequestMapping(value = "/app", method = RequestMethod.POST)
    public String app(@RequestParam("r") String r, Model model)  {
        model.addAttribute("room", r);
        return "hello";
    }
}
