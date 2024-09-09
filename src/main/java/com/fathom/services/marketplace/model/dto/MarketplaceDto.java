package com.fathom.services.marketplace.model.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MarketplaceDto {

  UUID id;
  UUID organizationId;
  String name;
  String author;
  String authorEmail;
  String logo;
  List<String> images = new ArrayList<>();
  boolean isArchive;
  long commentsCount;
  double averageRating;
  LocalDateTime createdDate;
  LocalDateTime updatedDate;

  // Constructor including all fields except images and logo
  public MarketplaceDto(
      UUID id,
      UUID organizationId,
      String name,
      String author,
      String authorEmail,
      boolean isArchive,
      LocalDateTime createdDate,
      LocalDateTime updatedDate,
      long commentsCount,
      double averageRating) {

    this.id = id;
    this.organizationId = organizationId;
    this.name = name;
    this.author = author;
    this.authorEmail = authorEmail;
    this.isArchive = isArchive;
    this.commentsCount = commentsCount;
    this.averageRating = averageRating;
    this.createdDate = createdDate;
    this.updatedDate = updatedDate;
  }
}
