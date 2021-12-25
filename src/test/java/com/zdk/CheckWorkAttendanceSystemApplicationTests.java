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
        String menu="{\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"id\": 125,\n" +
                "      \"authName\": \"用户管理\",\n" +
                "      \"path\": \"users\",\n" +
                "      \"children\": [\n" +
                "        {\n" +
                "          \"id\": 111,\n" +
                "          \"authName\": \"管理员\",\n" +
                "          \"path\": \"admin\",\n" +
                "          \"children\": []\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 112,\n" +
                "          \"authName\": \"普通用户\",\n" +
                "          \"path\": \"primary\",\n" +
                "          \"children\": []\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 113,\n" +
                "          \"authName\": \"企业用户\",\n" +
                "          \"path\": \"enterprise\",\n" +
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
                "      \"authName\": \"食品管理\",\n" +
                "      \"path\": \"goods\",\n" +
                "      \"children\": [\n" +
                "        {\n" +
                "          \"id\": 104,\n" +
                "          \"authName\": \"食品列表\",\n" +
                "          \"path\": \"goods\",\n" +
                "          \"children\": [],\n" +
                "          \"order\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 121,\n" +
                "          \"authName\": \"食品分类\",\n" +
                "          \"path\": \"categories\",\n" +
                "          \"children\": [],\n" +
                "          \"order\": 3\n" +
                "        }\n" +
                "      ],\n" +
                "      \"order\": 3\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 102,\n" +
                "      \"authName\": \"货单管理\",\n" +
                "      \"path\": \"orders\",\n" +
                "      \"children\": [\n" +
                "        {\n" +
                "          \"id\": 107,\n" +
                "          \"authName\": \"货单列表\",\n" +
                "          \"path\": \"orders\",\n" +
                "          \"children\": [],\n" +
                "          \"order\": null\n" +
                "        }\n" +
                "      ],\n" +
                "      \"order\": 4\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 147,\n" +
                "      \"authName\": \"风险监测\",\n" +
                "      \"path\": \"risk\",\n" +
                "      \"children\": [\n" +
                "        {\n" +
                "          \"id\": 148,\n" +
                "          \"authName\": \"食品检测\",\n" +
                "          \"path\": \"risk\",\n" +
                "          \"children\": [],\n" +
                "          \"order\": null\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 149,\n" +
                "          \"authName\": \"站点管理\",\n" +
                "          \"path\": \"storage\",\n" +
                "          \"children\": [],\n" +
                "          \"order\": null\n" +
                "        }\n" +
                "      ],\n" +
                "      \"order\": 6\n" +
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
                "  \"meta\": {\n" +
                "    \"msg\": \"获取菜单列表成功\",\n" +
                "    \"status\": 200\n" +
                "  }\n" +
                "}";
        String result = StringEscapeUtils.unescapeJava(menu);
        System.out.println(result);
    }

    @Test
    void test(){
        String pwd = "123456";
        System.out.println(HashKit.md5(pwd));
        String time = "2021-12-25 09:07:43";
        System.out.println(DateUtil.now().substring(0, 11));
    }

}