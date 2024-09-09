package com.fathom.services.marketplace.services.impl;

import com.fathom.lib.common.exception.RestException;
import com.fathom.services.marketplace.mapper.MarketplaceCommentMapper;
import com.fathom.services.marketplace.model.Marketplace;
import com.fathom.services.marketplace.model.MarketplaceComment;
import com.fathom.services.marketplace.model.dto.MarketplaceCommentCreateUpdateDto;
import com.fathom.services.marketplace.model.dto.MarketplaceCommentDto;
import com.fathom.services.marketplace.repositories.MarketplaceCommentRepository;
import com.fathom.services.marketplace.services.MarketplaceCommentService;
import com.fathom.services.marketplace.services.MarketplaceService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MarketplaceCommentServiceImpl implements MarketplaceCommentService {
  private final MarketplaceCommentRepository marketplaceCommentRepository;
  private final MarketplaceCommentMapper marketplaceCommentMapper;
  private final MarketplaceService marketplaceService;

  @Override
  public Page<MarketplaceCommentDto> getMarketplaceCommentByOrganizationId(
      UUID organizationId, UUID marketplaceId, int pageNumber, int pageSize, boolean pageable) {

    return getPagedDto(
        marketplaceCommentRepository.findByOrganizationIdAndMarketplace_Id(
            organizationId, marketplaceId, PageRequest.of(pageNumber - 1, pageSize)));
  }

  @Override
  public MarketplaceCommentDto createMarketplaceComment(
      UUID organizationId,
      UUID marketplaceId,
      String userEmail,
      MarketplaceCommentCreateUpdateDto marketplaceCreateUpdateDto) {

    Marketplace marketplace = marketplaceService.getMarketplaceById(marketplaceId);
    MarketplaceComment marketplaceComment =
        marketplaceCommentMapper.toEntity(organizationId, userEmail, marketplaceCreateUpdateDto);
    marketplaceComment.setMarketplace(marketplace);

    return marketplaceCommentMapper.toDto(marketplaceCommentRepository.save(marketplaceComment));
  }

  @Override
  public void deleteMarketplaceComment(UUID organizationId, String userEmail, UUID id) {

    MarketplaceComment marketplaceComment = getMarketplaceComment(id);
    marketplaceCommentRepository.delete(marketplaceComment);
  }

  private Page<MarketplaceCommentDto> getPagedDto(
      Page<MarketplaceComment> marketplaceCommentDtoPage) {

    return new PageImpl<>(
        marketplaceCommentDtoPage.stream().map(marketplaceCommentMapper::toDto).toList(),
        marketplaceCommentDtoPage.getPageable(),
        marketplaceCommentDtoPage.getTotalElements());
  }

  private MarketplaceComment getMarketplaceComment(UUID id) {
    return marketplaceCommentRepository
        .findById(id)
        .orElseThrow(
            () -> new RestException(HttpStatus.NOT_FOUND, "Comment with id: %s doesn't exist", id));
  }
}
