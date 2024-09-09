package com.fathom.services.marketplace.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MarketplaceCreateUpdateDto {

  @NotBlank(message = "Marketplace name cannot be empty")
  String name;

  @NotBlank(message = "Marketplace author name cannot be empty")
  String author;
}
