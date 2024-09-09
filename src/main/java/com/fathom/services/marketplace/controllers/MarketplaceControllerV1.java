package com.fathom.services.marketplace.controllers;

import com.fathom.services.marketplace.model.dto.MarketplaceCreateUpdateDto;
import com.fathom.services.marketplace.model.dto.MarketplaceDto;
import com.fathom.services.marketplace.model.dto.MarketplacePageableFilter;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface MarketplaceControllerV1 {

  @Operation(
      summary = "Endpoint for getting all marketplaces by organization id.",
      description = "Endpoint for getting all marketplaces by organization id.")
  ResponseEntity<Page<MarketplaceDto>> getMarketplaceByOrganizationId(
      UUID organizationId, int pageNumber, int pageSize, boolean pageable);

  @Operation(
      summary = "Endpoint for getting a marketplace by id. ",
      description = "Endpoint for getting a marketplace by id. ")
  ResponseEntity<MarketplaceDto> getMarketplaceById(UUID organizationId, UUID id);

  @Operation(
      summary = "Endpoint for creating a marketplace. ",
      description = "Endpoint for creating a marketplace. ")
  ResponseEntity<MarketplaceDto> createMarketplace(
      UUID organizationId, String userEmail, MarketplaceCreateUpdateDto marketplaceCreateUpdateDto);

  @Operation(
      summary = "Endpoint for updating a marketplace. ",
      description = "Endpoint for updating a marketplace. ")
  ResponseEntity<MarketplaceDto> archiveMarketplace(
      UUID organizationId,
      String userEmail,
      UUID id,
      MarketplaceCreateUpdateDto marketplaceCreateUpdateDto);

  @Operation(
      summary = "Endpoint for a a archiving a marketplace. ",
      description = "Endpoint for a a archiving a marketplace ")
  ResponseEntity<MarketplaceDto> archiveMarketplace(UUID organizationId, String userEmail, UUID id);

  @Operation(
      summary = "Endpoint for getting marketplaces by advance filtering options. ",
      description = "Endpoint for marketplaces by advance filtering options. ")
  ResponseEntity<Page<MarketplaceDto>> getFiltered(
      UUID organizationId, MarketplacePageableFilter marketplacePageableFilter);

  @Operation(
      summary = "Endpoint for deleting a marketplace by id. ",
      description = "Endpoint for deleting a marketplace by id. ")
  ResponseEntity<MarketplaceDto> deleteMarketplace(
      UUID organizationId, String userEmail, UUID id, boolean force);

  @Operation(
      summary = "Endpoint for deleting a marketplace by ids. ",
      description = "Endpoint for deleting a marketplace by ids. ")
  ResponseEntity<MarketplaceDto> deleteMarketplace(
      UUID organizationId, String userEmail, Set<UUID> ids, boolean force);
}
