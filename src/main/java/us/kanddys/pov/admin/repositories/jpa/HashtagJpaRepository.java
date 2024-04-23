package us.kanddys.pov.admin.repositories.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import us.kanddys.pov.admin.models.Hashtag;

@Repository
public interface HashtagJpaRepository extends JpaRepository<Hashtag, Long> {

   @Query("select h from Hashtag h where h.productId = ?1")
   Optional<Hashtag> findByProductId(Long productId);

   @Query(value = "SELECT id FROM hashtags WHERE value = ?1 AND user = ?2", nativeQuery = true)
   Optional<Long> findIdByValueAndUser(String value, Long user);
}
