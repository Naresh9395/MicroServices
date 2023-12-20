package com.programming.techie.productservice;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.awt.PageAttributes.MediaType;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.assertions.Assertions;
import com.programming.techie.productservice.dto.ProductRequest;
import com.programming.techie.productservice.dto.ProductResponse;
import com.programming.techie.productservice.repository.ProductRepository;

//@Configuration
@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc

class ProductServiceApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.4.2"));
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ProductRepository productRepository;
//	@Autowired
//	private ProductResponse productResponse;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);

	}

	@Test
	void shouldCreateProduct() throws Exception {

		ProductRequest productRequest = getProductRequest();
		String productRequeString = objectMapper.writeValueAsString(productRequest);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON).content(productRequeString))
				.andExpect(status().isCreated());
		Assertions.assertTrue(productRepository.findAll().size() == 1);

	}
//	@Test
//	void shouldGetProduct() throws Exception {
//
//		ProductResponse productResponse = getProductResponse();
//		String productRequeString = objectMapper.writeValueAsString(productResponse);
//
//		mockMvc.perform(MockMvcRequestBuilders.get("/api/product")
//				.contentType(org.springframework.http.MediaType.APPLICATION_JSON).content(productRequeString))
//				.andExpect(status().isOk());
//		Assertions.assertTrue(productRepository.findAll().size() == 1);
//
//	}
//	
//
//	private ProductResponse getProductResponse() {
//		 return ProductResponse.builder()
//				 .build();
//
//	}

	private ProductRequest getProductRequest() {
		return ProductRequest.builder().name("Samsung").description("Samsung").price(BigDecimal.valueOf(1200)).build();
	}

}
