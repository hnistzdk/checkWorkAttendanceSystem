package com.zdk;

import cn.hutool.core.date.DateUtil;
import com.zdk.utils.HashKit;
import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CheckWorkAttendanceSystemApplicationTests {

    @Test
    void contextLoads() {
        String menu= "{\n" +
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
        String result = StringEscapeUtils.unescapeJava(menu);
        System.out.println(result);
    }

    @Test
    void test(){
        String pwd = "123456";
        System.out.println(DateUtil.parse("9:22", "HH:mm"));
    }

}
