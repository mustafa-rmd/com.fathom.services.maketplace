package com.fathom.services.marketplace.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@Table(name = "marketplaces")
public class Marketplace {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", updatable = false, nullable = false)
  UUID id;

  UUID organizationId;

  @Column(nullable = false)
  String name;

  String author;

  @OneToMany(mappedBy = "marketplace", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<MarketplaceComment> marketplaceComments = new ArrayList<>();

  @Column(nullable = false)
  String authorEmail;

  LocalDateTime createdDate;
  LocalDateTime updatedDate;

  boolean isArchive;

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
