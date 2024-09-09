package com.fathom.services.marketplace.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@Table(name = "marketplace_comment")
public class MarketplaceComment {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", updatable = false, nullable = false)
  UUID id;

  UUID organizationId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "marketplace_id", referencedColumnName = "id")
  Marketplace marketplace;

  String comment;
  String commentAuthor;
  float rating;

  LocalDateTime createdDate;
  LocalDateTime updatedDate;

  @PrePersist
  private void prePersist() {
    LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
    this.createdDate = now;
    this.updatedDate = now;
  }

  @PreUpdate
  private void preUpdate() {
    LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
    if (this.createdDate == null) {
      this.createdDate = now;
    }

    this.updatedDate = now;
  }
}
