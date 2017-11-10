package com.easyadmin;

import com.easyadmin.schema.domain.Entity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SchemaTest extends AbstractTest {
    @Test
    public void getSchemas() throws Exception {
        final String token = extractToken(login("18510336317", "88888888").andReturn());
        mockMvc.perform(get("/schemas/_entitys")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("getSchemas", preprocessResponse(prettyPrint())));
    }

    @Test
    public void findOne() throws Exception {
        final String token = extractToken(login("18510336317", "88888888").andReturn());
        mockMvc.perform(get("/schemas/_entitys/e2")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("findOne", preprocessResponse(prettyPrint())));
    }

    @Test
    public void findAllFields() throws Exception {
        final String token = extractToken(login("18510336317", "88888888").andReturn());
        mockMvc.perform(get("/schemas/_fields?eid=e2")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("findAllFields", preprocessResponse(prettyPrint())));
    }

    @Test
    public void findOneField() throws Exception {
        final String token = extractToken(login("18510336317", "88888888").andReturn());
        mockMvc.perform(get("/schemas/_fields/f2")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("findOneField", preprocessResponse(prettyPrint())));
    }

    @Test
    public void addEntity() throws Exception {
        final String token = extractToken(login("18510336317", "88888888").andReturn());

        Entity entity = new Entity();
        entity.setLabel("test1");
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        mockMvc.perform(post("/schemas/_entitys")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(entity))
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("addEntity", preprocessResponse(prettyPrint())));
    }

    @Test
    public void syncSchemas() throws Exception {
        final String token = extractToken(login("admin", "admin").andReturn());

        mockMvc.perform(get("/schemas/sync")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
