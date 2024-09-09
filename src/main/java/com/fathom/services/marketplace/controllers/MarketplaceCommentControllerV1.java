package com.fathom.services.marketplace.controllers;

import com.fathom.services.marketplace.model.dto.MarketplaceCommentCreateUpdateDto;
import com.fathom.services.marketplace.model.dto.MarketplaceCommentDto;
import io.swagger.v3.oas.annotations.Operation;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface MarketplaceCommentControllerV1 {

  @Operation(
      summary =
          "Endpoint for getting all marketplace comments by organization id and marketplace id. ",
      description =
          "Endpoint for getting all marketplace comments by organization id and marketplace id .")
  ResponseEntity<Page<MarketplaceCommentDto>> getMarketplaceCommentByOrganizationId(
      UUID organizationId, UUID marketplaceId, int pageNumber, int pageSize, boolean pageable);

  @Operation(
      summary = "Endpoint for creating a marketplace comment. ",
      description = "Endpoint for creating a marketplace comment. ")
  ResponseEntity<MarketplaceCommentDto> createMarketplaceComment(
      UUID organizationId,
      UUID marketplaceId,
      String userEmail,
      MarketplaceCommentCreateUpdateDto marketplaceCreateUpdateDto);

  @Operation(
      summary = "Endpoint for deleting a marketplace comment by id. ",
      description = "Endpoint for deleting a marketplace comment by id. ")
  ResponseEntity<MarketplaceCommentDto> deleteMarketplaceComment(
      UUID organizationId, String userEmail, UUID id);
}
