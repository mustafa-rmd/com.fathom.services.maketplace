package com.fathom.services.marketplace.services.impl;

import com.fathom.lib.common.exception.RestException;
import com.fathom.services.marketplace.mapper.MarketplaceMapper;
import com.fathom.services.marketplace.model.Marketplace;
import com.fathom.services.marketplace.model.dto.MarketplaceCreateUpdateDto;
import com.fathom.services.marketplace.model.dto.MarketplaceDto;
import com.fathom.services.marketplace.model.dto.MarketplacePageableFilter;
import com.fathom.services.marketplace.repositories.MarketplaceRepository;
import com.fathom.services.marketplace.services.MarketplaceService;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarketplaceServiceImpl implements MarketplaceService {
  private final MarketplaceRepository marketplaceRepository;
  private final MarketplaceMapper marketplaceMapper;

  @Override
  public MarketplaceDto createMarketplace(
      UUID organizationId,
      String userEmail,
      MarketplaceCreateUpdateDto marketplaceCreateUpdateDto) {

    return marketplaceMapper.toDto(
        marketplaceRepository.save(
            marketplaceMapper.toEntity(organizationId, userEmail, marketplaceCreateUpdateDto)));
  }

  @Override
  public MarketplaceDto updateMarketplace(
      UUID organizationId,
      String userEmail,
      UUID id,
      MarketplaceCreateUpdateDto marketplaceCreateUpdateDto) {

    return marketplaceMapper.toDto(
        marketplaceRepository.save(
            marketplaceMapper.updateDtoToEntity(getMarketplace(id), marketplaceCreateUpdateDto)));
  }

  @Override
  public Page<MarketplaceDto> getMarketplaceByOrganizationId(
      UUID organizationId, int pageNumber, int pageSize, boolean pageable) {

    return marketplaceRepository.findWithCommentCountAndAverageRating(
        organizationId, PageRequest.of(pageNumber - 1, pageSize));
  }

  @Override
  public MarketplaceDto getMarketplaceById(UUID organizationId, UUID id) {

    return marketplaceRepository
        .findWithCommentCountAndAverageRating(id)
        .orElseThrow(
            () ->
                new RestException(
                    HttpStatus.NOT_FOUND,
                    "Marketplace with id: %s doesn't exist",
                    id,
                    organizationId));
  }

  @Override
  public Marketplace getMarketplaceById(UUID id) {
    return getMarketplace(id);
  }

  @Override
  public Page<MarketplaceDto> getFiltered(
      UUID organizationId, MarketplacePageableFilter marketplacePageableFilter) {

    return getPagedDto(
        marketplaceRepository.findByFilter(
            marketplacePageableFilter.getFilter().getContent(),
            marketplacePageableFilter.getFilter().getUserEmail(),
            marketplacePageableFilter.getFilter().getCreatedDateFrom(),
            marketplacePageableFilter.getFilter().getCreatedDateTo(),
            marketplacePageableFilter.getFilter().getUpdatedDateFrom(),
            marketplacePageableFilter.getFilter().getUpdatedDateTo(),
            PageRequest.of(
                marketplacePageableFilter.getPageNumber() - 1,
                marketplacePageableFilter.getPageSize())));
  }

  @Override
  public void deleteMarketplace(UUID organizationId, String userEmail, UUID id, boolean force) {

    Marketplace marketplace =
        marketplaceRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new RestException(
                        HttpStatus.NOT_FOUND,
                        "Marketplace with id: %s doesn't exist for organization id: %s",
                        id,
                        organizationId));

    if (!marketplace.isArchive() && !force) {
      throw new RestException(
          HttpStatus.BAD_REQUEST,
          "Marketplace with id: %s is not archived either archive it or use option force",
          id);
    }

    marketplaceRepository.delete(marketplace);
  }

  @Override
  public void deleteMarketplaces(
      UUID organizationId, String userEmail, Set<UUID> ids, boolean force) {
    Set<Marketplace> marketplaces =
        marketplaceRepository.findByOrganizationIdAndIdIn(organizationId, ids);

    Set<UUID> uuids = marketplaces.stream().map(Marketplace::getId).collect(Collectors.toSet());

    if (uuids.size() != ids.size()) {
      ids.removeAll(uuids);
      throw new RestException(
          HttpStatus.NOT_FOUND,
          "Marketplace with ids: %s doesn't exist",
          ids.stream().map(UUID::toString).collect(Collectors.joining(", ", "[", "]")));
    }

    var notArchiveMarketplace = marketplaces.stream().filter(x -> !x.isArchive()).toList();

    if (!notArchiveMarketplace.isEmpty() && !force) {
      throw new RestException(
          HttpStatus.BAD_REQUEST,
          "Marketplace with ids: %s is not archived either archive it or use option force",
          notArchiveMarketplace.stream()
              .map(x -> x.getId().toString())
              .collect(Collectors.joining(", ", "[", "]")));
    }

    marketplaceRepository.deleteAll(marketplaces);
  }

  @Override
  public MarketplaceDto archiveMarketplace(UUID organizationId, String userEmail, UUID id) {

    Marketplace marketplace = getMarketplace(id);
    marketplace.setArchive(true);

    return marketplaceMapper.toDto(marketplaceRepository.save(marketplace));
  }

  private Page<MarketplaceDto> getPagedDto(Page<Marketplace> assetPage) {

    return new PageImpl<>(
        assetPage.stream().map(marketplaceMapper::toDto).toList(),
        assetPage.getPageable(),
        assetPage.getTotalElements());
  }

  private Marketplace getMarketplace(UUID id) {
    return marketplaceRepository
        .findById(id)
        .orElseThrow(
            () ->
                new RestException(
                    HttpStatus.NOT_FOUND, "Marketplace with id: %s is not found", id));
  }
}
