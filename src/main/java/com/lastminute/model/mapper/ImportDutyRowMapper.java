package com.lastminute.model.mapper;

import com.lastminute.model.ImportDuty;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ImportDutyRowMapper implements RowMapper<ImportDuty> {

  @Override
  public ImportDuty mapRow(ResultSet rs, int line) throws SQLException {
    ImportDuty importDutyRow = new ImportDuty();
    importDutyRow.setCategoryName(rs.getString("category_name"));
    importDutyRow.setTaxRatePercent(rs.getBigDecimal("tax_rate_percent"));
    
    return importDutyRow;
  }
}
