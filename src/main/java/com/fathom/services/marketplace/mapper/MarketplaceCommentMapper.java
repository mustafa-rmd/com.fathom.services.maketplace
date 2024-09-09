package com.fathom.services.marketplace.mapper;

import com.fathom.services.marketplace.model.MarketplaceComment;
import com.fathom.services.marketplace.model.dto.MarketplaceCommentCreateUpdateDto;
import com.fathom.services.marketplace.model.dto.MarketplaceCommentDto;
import java.util.UUID;
import org.mapstruct.*;

@Mapper(
    componentModel = "spring",
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class MarketplaceCommentMapper {

  @Mapping(target = "organizationId", source = "organizationId")
  @Mapping(target = "commentAuthor", source = "userEmail")
  public abstract MarketplaceComment toEntity(
      UUID organizationId, String userEmail, MarketplaceCommentCreateUpdateDto source);

  public abstract MarketplaceCommentDto toDto(MarketplaceComment source);

  public abstract MarketplaceComment updateDtoToEntity(
      @MappingTarget MarketplaceComment comment, MarketplaceCommentCreateUpdateDto source);
}
