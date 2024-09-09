package com.fathom.services.marketplace.mapper;

import com.fathom.services.marketplace.model.Marketplace;
import com.fathom.services.marketplace.model.dto.MarketplaceCreateUpdateDto;
import com.fathom.services.marketplace.model.dto.MarketplaceDto;
import java.util.UUID;
import org.mapstruct.*;

@Mapper(
    componentModel = "spring",
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class MarketplaceMapper {
  @Mapping(target = "organizationId", source = "organizationId")
  @Mapping(target = "authorEmail", source = "userEmail")
  public abstract Marketplace toEntity(
      UUID organizationId, String userEmail, MarketplaceCreateUpdateDto source);

  public abstract MarketplaceDto toDto(Marketplace source);

  public abstract Marketplace updateDtoToEntity(
      @MappingTarget Marketplace marketplace, MarketplaceCreateUpdateDto source);
}
