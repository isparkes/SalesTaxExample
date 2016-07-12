package com.lastminute.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a "basket request" class, in which we can hold line items
 * for evaluation.
 * 
 * @author ian
 */
public class BasketRequest {
  @JsonProperty(required=true)
  private List<String> contents = new ArrayList<>();
  
  public void addBasketItem(String newItem) {
    getContents().add(newItem);
  }
  
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    
    // Spit out line items
    for (String item : getContents()) {
      builder.append(item).append("\n");
    }
    
    return builder.toString();
  }
  
  public String toJsonString() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();

    String jsonValue = mapper.writeValueAsString(this);
    
    return jsonValue;
  }  

  /**
   * @return the contents
   */
  public List<String> getContents() {
    return contents;
  }

  /**
   * @param contents the contents to set
   */
  public void setContents(List<String> contents) {
    this.contents = contents;
  }
}
