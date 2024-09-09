package com.fathom.services.marketplace.model.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class MarketplaceFilter {
  String content;
  String userEmail;
  LocalDateTime createdDateFrom;
  LocalDateTime createdDateTo;
  LocalDateTime updatedDateFrom;
  LocalDateTime updatedDateTo;
}
