package com.lastminute.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lastminute.model.BasketRequest;
import com.lastminute.model.BasketResponse;
import com.lastminute.service.TaxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("rest/salestax")
public class ApiController extends AbstractRestController {
  
  private final static Logger logger = LoggerFactory.getLogger(ApiController.class);

  @Autowired
  TaxService taxService;
  
  /**
   * Get a JSON basket and evaluate it.
   *
   * @param basketRequestBody The input strings to evaluate
   * @return The evaluated body
   * @throws com.fasterxml.jackson.core.JsonProcessingException
   */
  @RequestMapping(value = "performBasketTaxation", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
  @ResponseBody
  public BasketResponse performBasketTaxation(@RequestBody BasketRequest basketRequestBody) throws JsonProcessingException {
    logger.info("REST POST request:" + basketRequestBody.toJsonString());
    BasketResponse response = taxService.performTaxationOnBasket(basketRequestBody);
    logger.info("REST response:" + response.toJsonString());
    return response;
  }
}
