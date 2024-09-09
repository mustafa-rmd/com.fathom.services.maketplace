package com.fathom.services.marketplace.repositories;

import com.fathom.services.marketplace.model.MarketplaceComment;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MarketplaceCommentRepository extends JpaRepository<MarketplaceComment, UUID> {
  @Query("select m from MarketplaceComment m where m.organizationId = ?1 and m.marketplace.id = ?2")
  Page<MarketplaceComment> findByOrganizationIdAndMarketplace_Id(
      UUID organizationId, UUID id, Pageable pageable);
}
