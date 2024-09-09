package com.fathom.services.marketplace.services;

import com.fathom.services.marketplace.model.Marketplace;
import com.fathom.services.marketplace.model.dto.MarketplaceCreateUpdateDto;
import com.fathom.services.marketplace.model.dto.MarketplaceDto;
import com.fathom.services.marketplace.model.dto.MarketplacePageableFilter;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface MarketplaceService {

  MarketplaceDto createMarketplace(
      UUID organizationId, String userEmail, MarketplaceCreateUpdateDto marketplaceCreateUpdateDto);

  MarketplaceDto updateMarketplace(
      UUID organizationId,
      String userEmail,
      UUID id,
      MarketplaceCreateUpdateDto marketplaceCreateUpdateDto);

  Page<MarketplaceDto> getMarketplaceByOrganizationId(
      UUID organizationId, int pageNumber, int pageSize, boolean pageable);

  MarketplaceDto getMarketplaceById(UUID organizationId, UUID id);

  Marketplace getMarketplaceById(UUID id);

  Page<MarketplaceDto> getFiltered(
      UUID organizationId, MarketplacePageableFilter marketplacePageableFilter);

  void deleteMarketplace(UUID organizationId, String userEmail, UUID id, boolean force);

  void deleteMarketplaces(UUID organizationId, String userEmail, Set<UUID> ids, boolean force);

  MarketplaceDto archiveMarketplace(UUID organizationId, String userEmail, UUID id);
}
