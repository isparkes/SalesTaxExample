package com.lastminute.service;

import com.lastminute.model.ProductMap;
import java.util.List;

/**
 * Sales Tax core calculation service.
 * 
 * @author ian
 */
public interface ProductService {
  
  /**
   * Get the list of products we manage.
   * 
   * @return The list of products.
   */
  public List<ProductMap> getproductMap();
}
