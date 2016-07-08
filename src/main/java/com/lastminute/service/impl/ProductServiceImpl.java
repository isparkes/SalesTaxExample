package com.lastminute.service.impl;

import com.lastminute.model.ProductMap;
import com.lastminute.repository.DataRepository;
import com.lastminute.service.ProductService;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  private final static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

  @Autowired
  private DataRepository dataRepository;

  @Override
  public List<ProductMap> getproductMap() {
    return dataRepository.getProductMappings();
  }

  @Override
  public Map<String, ProductMap> getProductMap() {
    return dataRepository.getProductMap();
  }

  @Override
  public ProductMap getProductMapByProductName(String productName) {
    return getProductMap().get(productName.toLowerCase().trim());
  }
}