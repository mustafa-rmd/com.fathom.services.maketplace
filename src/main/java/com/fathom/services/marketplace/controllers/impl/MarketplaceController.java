package com.fathom.services.marketplace.controllers.impl;

import static com.fathom.services.marketplace.util.StaticProperties.EMAIL_HEADER;
import static com.fathom.services.marketplace.util.StaticProperties.ORGANIZATION_HEADER;

import com.fathom.services.marketplace.controllers.MarketplaceControllerV1;
import com.fathom.services.marketplace.model.dto.MarketplaceCreateUpdateDto;
import com.fathom.services.marketplace.model.dto.MarketplaceDto;
import com.fathom.services.marketplace.model.dto.MarketplacePageableFilter;
import com.fathom.services.marketplace.services.MarketplaceService;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MarketplaceController implements MarketplaceControllerV1 {
  private final MarketplaceService marketplaceService;

  @Override
  @GetMapping("marketplace")
  public ResponseEntity<Page<MarketplaceDto>> getMarketplaceByOrganizationId(
      @RequestHeader(ORGANIZATION_HEADER) UUID organizationId,
      @RequestParam(defaultValue = "1", required = false) int pageNumber,
      @RequestParam(defaultValue = "10", required = false) int pageSize,
      @RequestParam(defaultValue = "false", required = false) boolean pageable) {

    return ResponseEntity.ok(
        marketplaceService.getMarketplaceByOrganizationId(
            organizationId, pageNumber, pageSize, pageable));
  }

  @Override
  @GetMapping("marketplace/{id}")
  public ResponseEntity<MarketplaceDto> getMarketplaceById(
      @RequestHeader(ORGANIZATION_HEADER) UUID organizationId, @PathVariable UUID id) {

    return ResponseEntity.ok(marketplaceService.getMarketplaceById(organizationId, id));
  }

  @Override
  @PostMapping("marketplace")
  public ResponseEntity<MarketplaceDto> createMarketplace(
      @RequestHeader(ORGANIZATION_HEADER) UUID organizationId,
      @RequestHeader(EMAIL_HEADER) String userEmail,
      @RequestBody MarketplaceCreateUpdateDto marketplaceCreateUpdateDto) {
    return ResponseEntity.ok(
        marketplaceService.createMarketplace(
            organizationId, userEmail, marketplaceCreateUpdateDto));
  }

  @Override
  @PutMapping("marketplace/{id}")
  public ResponseEntity<MarketplaceDto> archiveMarketplace(
      @RequestHeader(ORGANIZATION_HEADER) UUID organizationId,
      @RequestHeader(EMAIL_HEADER) String userEmail,
      @PathVariable UUID id,
      @RequestBody MarketplaceCreateUpdateDto marketplaceCreateUpdateDto) {

    return ResponseEntity.ok(
        marketplaceService.updateMarketplace(
            organizationId, userEmail, id, marketplaceCreateUpdateDto));
  }

  @Override
  @PutMapping("marketplace/archive/{id}")
  public ResponseEntity<MarketplaceDto> archiveMarketplace(
      @RequestHeader(ORGANIZATION_HEADER) UUID organizationId,
      @RequestHeader(EMAIL_HEADER) String userEmail,
      @PathVariable UUID id) {

    return ResponseEntity.ok(marketplaceService.archiveMarketplace(organizationId, userEmail, id));
  }

  @Override
  @PostMapping("marketplace/filtered")
  public ResponseEntity<Page<MarketplaceDto>> getFiltered(
      @RequestHeader(ORGANIZATION_HEADER) UUID organizationId,
      @RequestBody MarketplacePageableFilter marketplacePageableFilter) {

    return ResponseEntity.ok(
        marketplaceService.getFiltered(organizationId, marketplacePageableFilter));
  }

  @Override
  @DeleteMapping("marketplace/{id}")
  public ResponseEntity<MarketplaceDto> deleteMarketplace(
      @RequestHeader(ORGANIZATION_HEADER) UUID organizationId,
      @RequestHeader(EMAIL_HEADER) String userEmail,
      @PathVariable UUID id,
      @RequestParam(defaultValue = "false", required = false) boolean force) {

    marketplaceService.deleteMarketplace(organizationId, userEmail, id, force);
    return ResponseEntity.ok().build();
  }

  @Override
  @PostMapping("marketplace/delete")
  public ResponseEntity<MarketplaceDto> deleteMarketplace(
      @RequestHeader(ORGANIZATION_HEADER) UUID organizationId,
      @RequestHeader(EMAIL_HEADER) String userEmail,
      @RequestBody Set<UUID> ids,
      @RequestParam(defaultValue = "false", required = false) boolean force) {
    marketplaceService.deleteMarketplaces(organizationId, userEmail, ids, force);
    return ResponseEntity.ok().build();
  }
}
