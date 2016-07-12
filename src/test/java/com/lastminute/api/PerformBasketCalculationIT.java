package com.lastminute.api;

import com.lastminute.model.BasketRequest;
import com.lastminute.test.ConfiguredIntegrationTest;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Tests the REST interface.
 * 
 * @author ian
 */
public class PerformBasketCalculationIT extends ConfiguredIntegrationTest {
	@Autowired
	private WebApplicationContext wac;
  
	private MockMvc mockMvc;

	private final String basketTaxationUri = "/rest/salestax/performBasketTaxation";

	@Before
	public void setup() throws IOException {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

  /**
   * Sunny day test of the calculation API
   * 
   * @throws java.io.IOException
   */
  @Test
  //@Ignore
	public void testPerformBasketTaxation() throws Exception {
    
    BasketRequest basketRequest = new BasketRequest();
    basketRequest.addBasketItem("1 music CD at 10.23");
    
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(basketTaxationUri).content(basketRequest.toJsonString()).contentType(MediaType.APPLICATION_JSON);
    ResultActions resultCurrent = mockMvc.perform(requestBuilder).andDo(print());
    resultCurrent.andExpect(status().isOk());

    MvcResult calculateResponse = resultCurrent.andReturn();
    String rawContentCurrent = calculateResponse.getResponse().getContentAsString();

    assertEquals("{\"contents\":[\"1 music CD: 11.2800\"],\"salesTax\":1.0500,\"total\":11.2800}", rawContentCurrent);
	}
}
