package com.lastminute.service.impl;

import com.lastminute.model.ImportDuty;
import com.lastminute.model.ProductMap;
import com.lastminute.repository.DataRepository;
import com.lastminute.service.ProductService;
import java.util.List;
import java.util.Map;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  private final static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

  private final String IMPORTED_GOODS_KEY = "imported";
  private final String DOMESTIC_GOODS_KEY = "domestic";
  
  @Autowired
  private DataRepository dataRepository;

  @Override
  public List<ProductMap> getProductMapList() {
    return dataRepository.getProductMapList();
  }

  @Override
  public Map<String, ProductMap> getProductMapHashMap() {
    return dataRepository.getProductMapHashMap();
  }

  @Override
  public ProductMap getProductMapByProductName(String productName) {
    return getProductMapHashMap().get(productName.toLowerCase().trim());
  }
  
  @Override
  public List<ImportDuty> getImportDutyMapList() {
    return dataRepository.getImportDutyMapList();
  }

  @Override
  public Map<String, ImportDuty> getImportDutyMapHashMap() {
    return dataRepository.getImportDutyMapHashMap();
  }

  @Override
  public ImportDuty getImportDutyByProductName(String productName) {
    if (productName.toLowerCase().contains("import")) {
      return getImportDutyMapHashMap().get(IMPORTED_GOODS_KEY);
    } else {
      return getImportDutyMapHashMap().get(DOMESTIC_GOODS_KEY);
    }
  }
  
  

}