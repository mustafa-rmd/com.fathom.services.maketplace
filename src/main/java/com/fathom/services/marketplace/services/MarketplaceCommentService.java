package com.fathom.services.marketplace.services;

import com.fathom.services.marketplace.model.dto.MarketplaceCommentCreateUpdateDto;
import com.fathom.services.marketplace.model.dto.MarketplaceCommentDto;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface MarketplaceCommentService {

  Page<MarketplaceCommentDto> getMarketplaceCommentByOrganizationId(
      UUID organizationId, UUID marketplaceId, int pageNumber, int pageSize, boolean pageable);

  MarketplaceCommentDto createMarketplaceComment(
      UUID organizationId,
      UUID marketplaceId,
      String userEmail,
      MarketplaceCommentCreateUpdateDto marketplaceCreateUpdateDto);

  void deleteMarketplaceComment(UUID organizationId, String userEmail, UUID id);
}
