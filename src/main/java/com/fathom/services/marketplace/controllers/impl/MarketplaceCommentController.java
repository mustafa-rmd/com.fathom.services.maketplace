package com.fathom.services.marketplace.controllers.impl;

import static com.fathom.services.marketplace.util.StaticProperties.EMAIL_HEADER;
import static com.fathom.services.marketplace.util.StaticProperties.ORGANIZATION_HEADER;

import com.fathom.services.marketplace.controllers.MarketplaceCommentControllerV1;
import com.fathom.services.marketplace.model.dto.MarketplaceCommentCreateUpdateDto;
import com.fathom.services.marketplace.model.dto.MarketplaceCommentDto;
import com.fathom.services.marketplace.services.MarketplaceCommentService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MarketplaceCommentController implements MarketplaceCommentControllerV1 {
  private final MarketplaceCommentService marketplaceCommentService;

  @Override
  @GetMapping("marketplace/comment/{marketplaceId}")
  public ResponseEntity<Page<MarketplaceCommentDto>> getMarketplaceCommentByOrganizationId(
      @RequestHeader(ORGANIZATION_HEADER) UUID organizationId,
      @PathVariable UUID marketplaceId,
      @RequestParam(defaultValue = "1", required = false) int pageNumber,
      @RequestParam(defaultValue = "10", required = false) int pageSize,
      @RequestParam(defaultValue = "false", required = false) boolean pageable) {
    return ResponseEntity.ok(
        marketplaceCommentService.getMarketplaceCommentByOrganizationId(
            organizationId, marketplaceId, pageNumber, pageSize, pageable));
  }

  @Override
  @PostMapping("marketplace/comment/{marketplaceId}")
  public ResponseEntity<MarketplaceCommentDto> createMarketplaceComment(
      @RequestHeader(ORGANIZATION_HEADER) UUID organizationId,
      @PathVariable UUID marketplaceId,
      @RequestHeader(EMAIL_HEADER) String userEmail,
      @RequestBody MarketplaceCommentCreateUpdateDto marketplaceCreateUpdateDto) {

    return ResponseEntity.ok(
        marketplaceCommentService.createMarketplaceComment(
            organizationId, marketplaceId, userEmail, marketplaceCreateUpdateDto));
  }

  @Override
  @DeleteMapping("marketplace/comment/{id}")
  public ResponseEntity<MarketplaceCommentDto> deleteMarketplaceComment(
      @RequestHeader(ORGANIZATION_HEADER) UUID organizationId,
      @RequestHeader(EMAIL_HEADER) String userEmail,
      @PathVariable UUID id) {
    marketplaceCommentService.deleteMarketplaceComment(organizationId, userEmail, id);
    return ResponseEntity.ok().build();
  }
}
