package com.mkingzhu.dragon.server.web.home.controller;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mkingzhu.dragon.server.web.model.User;
import com.winchance.util.convertor.GsonConvertor;
import com.winchance.util.httpclient.HttpUtil;

@Controller
public class OauthController {
    private static String postUrl;

    static {
        try {
            postUrl = "http://open.4001113900.com/OauthAction!getPubInfo.action"
                    + "?appid=" + URLEncoder.encode("264a1c0f-1cac-11e6-bb37-008cfae40fdc", "utf-8")
                    + "&appsecret=" + URLEncoder.encode("C38qN8iRXh8VMZF1najB8uUmVNkOvIMx", "utf-8")
                    + "&access_token=";
        } catch (Exception igonre) {
        }
    }

    @RequestMapping(value = "oauth.html", method = { RequestMethod.GET })
    public String indexView(ModelMap modelMap,
                            HttpServletRequest request,
                            @RequestParam(name = "access_token", required = true) String accessToken) {
        try {
            User user = GsonConvertor.fromJson(HttpUtil.post(postUrl + URLEncoder.encode(accessToken, "utf-8")), User.class);
            request.getSession().setAttribute("userInfo", user.getUserInfo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:index.html";
    }
}