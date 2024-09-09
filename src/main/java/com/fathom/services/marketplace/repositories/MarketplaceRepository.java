package com.fathom.services.marketplace.repositories;
import com.fathom.services.marketplace.model.Marketplace;
import com.fathom.services.marketplace.model.dto.MarketplaceDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public interface MarketplaceRepository extends JpaRepository<Marketplace, UUID> {

  // Common query fragment for fetching MarketplaceDto
  String MARKETPLACE_DTO_QUERY = """
        SELECT new com.fathom.services.marketplace.model.dto.MarketplaceDto(
            m.id,\s
            m.organizationId,\s
            m.name,\s
            m.author,\s
            m.authorEmail,\s
            m.isArchive,\s
            m.createdDate,\s
            COALESCE(MAX(c.updatedDate), m.createdDate),\s
            COUNT(c.id),\s
            COALESCE(AVG(c.rating), 0))
        FROM Marketplace m
        LEFT JOIN m.marketplaceComments c
       \s""";

  @Query("SELECT c FROM Marketplace c WHERE c.organizationId = ?1 AND c.id IN ?2")
  Set<Marketplace> findByOrganizationIdAndIdIn(UUID organizationId, Collection<UUID> ids);

  @Query(value = """
            SELECT * FROM marketplaces c
            WHERE (COALESCE(:name, '') = '' OR c.name LIKE %:name%)
              AND (COALESCE(:author, '') = '' OR c.user_email = :author)
              AND (COALESCE(:createdDateFrom, TIMESTAMP '1970-01-01 00:00:00') <= c.created_date)
              AND (COALESCE(:createdDateTo, TIMESTAMP '9999-12-31 23:59:59') >= c.created_date)
              AND (COALESCE(:updatedDateFrom, TIMESTAMP '1970-01-01 00:00:00') <= c.updated_date)
              AND (COALESCE(:updatedDateTo, TIMESTAMP '9999-12-31 23:59:59') >= c.updated_date)
            """, nativeQuery = true)
  Page<Marketplace> findByFilter(
          @Param("name") String content,
          @Param("author") String userEmail,
          @Param("createdDateFrom") LocalDateTime createdDateFrom,
          @Param("createdDateTo") LocalDateTime createdDateTo,
          @Param("updatedDateFrom") LocalDateTime updatedDateFrom,
          @Param("updatedDateTo") LocalDateTime updatedDateTo,
          Pageable pageable
  );

  @Query(MARKETPLACE_DTO_QUERY +
          "WHERE m.organizationId = :organizationId " +
          "GROUP BY m.id")
  Page<MarketplaceDto> findWithCommentCountAndAverageRating(UUID organizationId, Pageable pageable);

  @Query(MARKETPLACE_DTO_QUERY +
          "WHERE m.id = :id " +
          "GROUP BY m.id")
  Optional<MarketplaceDto> findWithCommentCountAndAverageRating(UUID id);
}
