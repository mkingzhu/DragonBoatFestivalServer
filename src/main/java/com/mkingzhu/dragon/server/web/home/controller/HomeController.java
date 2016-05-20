package com.mkingzhu.dragon.server.web.home.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mkingzhu.dragon.server.biz.business.UserBiz;
import com.mkingzhu.dragon.server.web.model.UserInfo;
import com.winchance.util.StringUtil;

@Controller
public class HomeController {
    private static String redirectUrl;

    static {
        try {
            redirectUrl = "redirect:http://open.4001113900.com/OauthAction!requestOauth.action"
                    + "?appid=" + URLEncoder.encode("264a1c0f-1cac-11e6-bb37-008cfae40fdc", "utf-8")
                    + "&redirect_url=" + URLEncoder.encode("http://theresalv.com/oauth.html", "utf-8")
                    + "&code=";
        } catch (Exception igonre) {
        }
    }

    @Autowired
    private UserBiz  userBiz;

    @RequestMapping(value = "index.html", method = { RequestMethod.GET })
    public String indexView(ModelMap modelMap,
                            HttpServletRequest request,
                            @RequestParam(name = "code", required = false) String code)
            throws UnsupportedEncodingException {
        if (StringUtil.isNotEmpty(code))
            return redirectUrl + URLEncoder.encode(code, "utf-8");

        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
        if (null != userInfo) {
            modelMap.addAttribute("played", userBiz.get(userInfo.getLcUser()));
            modelMap.addAttribute("isInApp", true);
        } else {
            modelMap.addAttribute("played", false);
            modelMap.addAttribute("isInApp", false);
        }
        return "home/index";
    }
}