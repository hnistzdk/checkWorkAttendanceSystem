package com.zdk.controllers.controller;

import com.zdk.controllers.BaseController;
import com.zdk.interceptor.PermissionInfo;
import com.zdk.model.User;
import com.zdk.utils.ApiResponse;
import com.zdk.utils.EmailUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zdk
 * @date 2021/12/25 14:37
 * 进入首页时获取菜单的controller
 */
@Api("进入系统")
@RestController
@CrossOrigin
public class IndexController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @ApiOperation("获取菜单")
    @PermissionInfo
    @GetMapping("/menus")
    public ApiResponse getMenuList() {
        String menu;
        User user = getLoginUser();
        if ("普通用户".equals(user.getRole())){
            menu = "{\n" +
                    "  \"data\": [\n" +
                    "    {\n" +
                    "      \"id\": 101,\n" +
                    "      \"authName\": \"考勤管理\",\n" +
                    "      \"path\": \"check\",\n" +
                    "      \"children\": [\n" +
                    "        {\n" +
                    "          \"id\": 104,\n" +
                    "          \"authName\": \"考勤信息管理\",\n" +
                    "          \"path\": \"checkInfo\",\n" +
                    "          \"children\": [],\n" +
                    "          \"order\": 1\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"order\": 3\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 150,\n" +
                    "      \"authName\": \"个人中心\",\n" +
                    "      \"path\": \"center\",\n" +
                    "      \"children\": [\n" +
                    "        {\n" +
                    "          \"id\": 151,\n" +
                    "          \"authName\": \"个人面板\",\n" +
                    "          \"path\": \"center\",\n" +
                    "          \"children\": [],\n" +
                    "          \"order\": null\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"order\": 7\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"msg\": \"获取菜单列表成功\",\n" +
                    "   \"status\": 200\n" +
                    "}\n";
        }else{
            menu="{\n" +
                    "  \"data\": [\n" +
                    "    {\n" +
                    "      \"id\": 125,\n" +
                    "      \"authName\": \"用户管理\",\n" +
                    "      \"path\": \"users\",\n" +
                    "      \"children\": [\n" +
                    "        {\n" +
                    "          \"id\": 111,\n" +
                    "          \"authName\": \"员工管理\",\n" +
                    "          \"path\": \"user\",\n" +
                    "          \"children\": []\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"order\": 1\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 103,\n" +
                    "      \"authName\": \"权限管理\",\n" +
                    "      \"path\": \"rights\",\n" +
                    "      \"children\": [\n" +
                    "        {\n" +
                    "          \"id\": 111,\n" +
                    "          \"authName\": \"角色列表\",\n" +
                    "          \"path\": \"roles\",\n" +
                    "          \"children\": [],\n" +
                    "          \"order\": null\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"id\": 112,\n" +
                    "          \"authName\": \"权限列表\",\n" +
                    "          \"path\": \"rights\",\n" +
                    "          \"children\": [],\n" +
                    "          \"order\": null\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"order\": 2\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 101,\n" +
                    "      \"authName\": \"考勤管理\",\n" +
                    "      \"path\": \"check\",\n" +
                    "      \"children\": [\n" +
                    "        {\n" +
                    "          \"id\": 104,\n" +
                    "          \"authName\": \"考勤信息管理\",\n" +
                    "          \"path\": \"checkInfo\",\n" +
                    "          \"children\": [],\n" +
                    "          \"order\": 1\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"order\": 3\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 150,\n" +
                    "      \"authName\": \"个人中心\",\n" +
                    "      \"path\": \"center\",\n" +
                    "      \"children\": [\n" +
                    "        {\n" +
                    "          \"id\": 151,\n" +
                    "          \"authName\": \"个人面板\",\n" +
                    "          \"path\": \"center\",\n" +
                    "          \"children\": [],\n" +
                    "          \"order\": null\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"order\": 7\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"msg\": \"获取菜单列表成功\",\n" +
                    "   \"status\": 200\n" +
                    "}\n";
        }
        return ApiResponse.success(menu, "菜单获取成功");
    }

    @ApiOperation("发送验证码")
    @PermissionInfo
    @GetMapping("/verificationCode")
    public ApiResponse verificationCode(String email){
        if (notOk(email)){
            return ApiResponse.fail("邮箱不能为空");
        }
        Matcher matcher = Pattern.compile("^\\s*?(.+)@(.+?)\\s*$").matcher(email);
        if (!matcher.matches()){
            return ApiResponse.fail("邮箱格式错误");
        }
        String code = EmailUtil.sendEmail(email);
        return ApiResponse.success(code,"验证码获取成功");
    }
}
