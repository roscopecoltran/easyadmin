package com.easyadmin;

import com.easyadmin.security.security.JwtAuthenticationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.github.robwin.markup.builder.MarkupLanguage;
import io.github.robwin.swagger2markup.GroupBy;
import io.github.robwin.swagger2markup.Swagger2MarkupConverter;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.http.HttpResponseSnippet;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import springfox.documentation.staticdocs.SwaggerResultHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
@RunWith(SpringRunner.class)
@SpringBootTest
public class Documentation {
    private String snippetDir = "target/generated-snippets";
    private String outputDir = "target/asciidoc";
    //private String indexDoc = "docs/asciidoc/index.adoc";
    private ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @After
    public void Test() throws Exception{
        // 得到swagger.json,写入outputDir目录中
        mockMvc.perform(get("/v2/api-docs").accept(MediaType.APPLICATION_JSON))
                .andDo(SwaggerResultHandler.outputDirectory(outputDir).build())
                .andExpect(status().isOk())
                .andReturn();

        // 读取上一步生成的swagger.json转成asciiDoc,写入到outputDir
        // 这个outputDir必须和插件里面<generated></generated>标签配置一致
        Swagger2MarkupConverter.from(outputDir + "/swagger.json")
                .withPathsGroupedBy(GroupBy.TAGS)// 按tag排序
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)// 格式
                .withExamples(snippetDir)
                .build()
                .intoFolder(outputDir);// 输出
    }

    @Test
    public void TestApi() throws Exception{
        final String token = extractToken(login("18510336317", "88888888").andReturn());
        mockMvc.perform(get("/schemas/_entitys")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("getSchemas", preprocessResponse(prettyPrint())));
    }

    protected ResultActions login(String username, String password) throws Exception {
        final JwtAuthenticationRequest auth = new JwtAuthenticationRequest();
        auth.setUsername(username);
        auth.setPassword(password);
        return mockMvc.perform(
                post("/auth")
                        .content(json(auth))
                        .contentType(MediaType.APPLICATION_JSON));
    }

    protected String extractToken(MvcResult result) throws UnsupportedEncodingException {
        return JsonPath.read(result.getResponse().getContentAsString(), "$.token");
    }

    protected String json(Object o) throws IOException {
        return mapper.writeValueAsString(o);
    }
}
