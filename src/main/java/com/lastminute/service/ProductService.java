package com.lastminute.service;

import com.lastminute.model.ProductMap;
import java.util.List;
import java.util.Map;

/**
 * Sales Tax core calculation service.
 * 
 * @author ian
 */
public interface ProductService {
  
  /**
   * Get the list of products we manage as a raw list.
   * 
   * @return The list of products.
   */
  public List<ProductMap> getproductMap();
  
  /**
   * Indexed version of the above.
   * 
   * @return HashMap of products, indexed by name
   */
  public Map<String, ProductMap> getProductMap();
  
  /**
   * Get a single product by name. This could be cached, but is not right now.
   * 
   * @param productName The product to retrieve
   * @return retrieved product
   */
  public ProductMap getProductMapByProductName(String productName);
}
