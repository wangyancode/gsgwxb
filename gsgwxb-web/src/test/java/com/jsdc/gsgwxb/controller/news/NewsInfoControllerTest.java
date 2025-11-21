package com.jsdc.gsgwxb.controller.news;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsdc.gsgwxb.news.NewsInfo;
import com.jsdc.gsgwxb.service.news.NewsInfoService;
import com.jsdc.gsgwxb.utils.ResultInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NewsInfoController.class)
class NewsInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsInfoService mainService;

    @Autowired
    private ObjectMapper objectMapper;

    private NewsInfo sampleNews;
    private String token;

    @BeforeEach
    void setUp() {
        sampleNews = new NewsInfo();
        sampleNews.setId("1");
        sampleNews.setTitle("测试新闻");
        sampleNews.setContent("这是一个测试新闻内容");
    }

    /**
     * 模拟登录，获取 Token
     */
    @Test
    void testLogin() throws Exception {
        MvcResult result = mockMvc.perform(post("/login")
                        .param("username", "admin")
                        .param("password", "123456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andReturn();

        // 从返回结果中提取 token
        String json = result.getResponse().getContentAsString();
        JsonNode node = objectMapper.readTree(json);
        token = node.get("data").asText();
        System.out.println("登录成功，Token = " + token);
    }

    /**
     * 模拟登录 + 验证 Token
     */
    @Test
    void testLoginAndCheckToken() throws Exception {
        // 第一步：登录
        MvcResult result = mockMvc.perform(post("/login")
                        .param("username", "admin")
                        .param("password", "123456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        JsonNode node = objectMapper.readTree(json);
        token = node.get("data").asText();

        // 第二步：验证 token
        mockMvc.perform(get("/news/checkToken")
                        .param("username", "admin")
                        .param("token", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Token有效"));
    }

    /**
     * 其他已有接口测试
     */
    @Test
    void testSelectPage() throws Exception {
        Mockito.when(mainService.selectPage(any(NewsInfo.class)));

        mockMvc.perform(get("/news/selectPage"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));
    }

    @Test
    void testSelectList() throws Exception {
        Mockito.when(mainService.selectList(any(NewsInfo.class)));

        mockMvc.perform(get("/news/selectList"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));
    }

    @Test
    void testSelectById() throws Exception {
        Mockito.when(mainService.selectBean(any(NewsInfo.class)))
                .thenReturn(sampleNews);

        mockMvc.perform(get("/news/selectById")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value("1"));
    }

    @Test
    void testAddUpdateInfo() throws Exception {
        Mockito.when(mainService.addUpdateInfo(any(NewsInfo.class)))
                .thenReturn(ResultInfo.success("保存成功"));

        mockMvc.perform(post("/news/addUpdateInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleNews)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));
    }

    @Test
    void testDelData() throws Exception {
        Mockito.when(mainService.delData(eq("1")))
                .thenReturn("删除成功".isEmpty());

        mockMvc.perform(post("/news/delData")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));
    }

    @Test
    void testDelData_IdEmpty() throws Exception {
        mockMvc.perform(post("/news/delData"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("id不能未空，请选择后删除！"));
    }

    @Test
    void testBatchDeletion() throws Exception {
        Mockito.when(mainService.batchDeletion(eq("1,2,3")))
                .thenReturn(ResultInfo.success("批量删除成功"));

        mockMvc.perform(post("/news/batchDeletion")
                        .param("ids", "1,2,3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));
    }
}
