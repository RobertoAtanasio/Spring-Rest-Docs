package com.spring.rest.docs.springrestdocs.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void shouldReturnProductCode() throws Exception{
        this.mockMvc.perform(get("/v2/products/2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("WD SSD")))
                .andDo(document("v2/product/get-product-by-id"));
    }
    
    @Test
    public void getProductById() throws Exception{
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/v2/products/{code}", 2))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("WD SSD")))
                .andDo(document("v2/product/get-product-by-id",
                		pathParameters(parameterWithName("code").description("Identificador Único do Produto"))));
    }
    
    @Test
    public void getProductByIdResponseFieldDocumentation() throws Exception{
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/v2/products/{code}", 2))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("WD SSD")))
                .andDo(document("v2/product/get-product-by-id",pathParameters(parameterWithName("code")
                        .description("Identificador Único do Produto")),responseFields(
                                fieldWithPath("code").description("Identificador Único do Produto"),
                                fieldWithPath("name").description("Nome do Produto"),
                                fieldWithPath("description").description("Descrição do Produto"),
                                fieldWithPath("price").description("Preço do Produto"),
                                fieldWithPath("stock").description("Estoque do Produto"))));
    }

}