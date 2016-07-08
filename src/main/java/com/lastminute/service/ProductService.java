package com.lastminute.service;

import com.lastminute.model.ImportDuty;
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
  public List<ProductMap> getProductMapList();
  
  /**
   * Indexed version of the above.
   * 
   * @return HashMap of products, indexed by name
   */
  public Map<String, ProductMap> getProductMapHashMap();
  
  /**
   * Get a single product by name. This could be cached, but is not right now.
   * 
   * @param productName The product to retrieve
   * @return retrieved product
   */
  public ProductMap getProductMapByProductName(String productName);
  
  /**
   * Get the list of import duty rates we manage as a raw list.
   * @return 
   */
  public List<ImportDuty> getImportDutyMapList();
  
  /**
   * Indexed version of the above.
   * 
   * @return HashMap of products, indexed by name
   */
  public Map<String, ImportDuty> getImportDutyMapHashMap();
  
  /**
   * Get the import duty rate based on a product name
   * 
   * @param productName The product to retrieve
   * @return retrieved import duty
   */
  public ImportDuty getImportDutyByProductName(String productName);
}
