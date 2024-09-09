package com.fathom.services.marketplace.model.dto;

import lombok.Data;

@Data
public class MarketplaceCommentCreateUpdateDto {
  String comment;
  float rating;
}
