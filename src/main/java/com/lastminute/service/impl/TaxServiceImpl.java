package com.lastminute.service.impl;

import com.lastminute.repository.DataRepository;
import com.lastminute.service.TaxService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaxServiceImpl implements TaxService {

  private final static Logger logger = LoggerFactory.getLogger(TaxServiceImpl.class);

  @Autowired
  private DataRepository dataRepository;
  
  @Override
  public boolean getProductCount() {
    dataRepository.getProductRowCount();
    return true;
  }

}
