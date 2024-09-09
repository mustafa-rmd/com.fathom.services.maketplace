package com.fathom.services.marketplace.model.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class MarketplaceCommentDto {

  UUID id;
  UUID organizationId;
  String comment;
  String commentAuthor;
  float rating;
  LocalDateTime createdDate;
  LocalDateTime updatedDate;
}
