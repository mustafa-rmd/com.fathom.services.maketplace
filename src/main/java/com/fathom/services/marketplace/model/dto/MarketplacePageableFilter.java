package com.fathom.services.marketplace.model.dto;

import lombok.Data;

@Data
public class MarketplacePageableFilter {
  MarketplaceFilter filter;
  int pageNumber = 1;
  int pageSize = 10;
}
