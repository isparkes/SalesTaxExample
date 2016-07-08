package com.lastminute.service;

/**
 * SFTP interface encapsulation
 * 
 * @author ian
 */
public interface TaxService {
  
  /**
   * Gets the number of products we manage.
   * 
   * @param filename The filename 
   * @return 
   */
  public boolean getProductCount();
}
