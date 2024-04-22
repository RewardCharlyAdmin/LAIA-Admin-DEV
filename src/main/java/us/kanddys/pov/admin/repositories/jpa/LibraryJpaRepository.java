package us.kanddys.pov.admin.repositories.jpa;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import us.kanddys.pov.admin.models.Library;

@Repository
public interface LibraryJpaRepository extends JpaRepository<Library, Long> {
   @Query("SELECT l FROM Library l WHERE l.userId = ?1 AND l.typeCollection = ?2")
   public Library findByUserIdAndTypeCollection(Long userId, Integer typeCollection);

   @Query(value = "SELECT id FROM libraries WHERE user_id = ?1 AND title = ?2 AND id != ?3", nativeQuery = true)
   public Optional<Long> existLibraryWithUserIdAndTitleAndNotLibraryId(Long userId, String title, Long libraryId);

   @Modifying
   @Query(value = "UPDATE libraries SET conf = ?1 WHERE id = ?2", nativeQuery = true)
   public Integer updateConfByLibraryId(Integer conf, Long libraryId);

   @Query(value = "SELECT conf FROM libraries WHERE id = ?1", nativeQuery = true)
   public Integer findConfByLibraryId(Long libraryId);

   @Modifying
   @Query(value = "UPDATE libraries SET miniature_header = ?2, miniature_title = ?3, miniature_subtitle = ?4 WHERE id = ?1", nativeQuery = true)
   public Integer updateMiniatureByLibraryId(Long libraryId, String miniatureHeader,
         String miniatureTitle, String miniatureSubtitle);

   @Query(value = "SELECT type_collection, user_id FROM libraries WHERE id = ?1", nativeQuery = true)
   public Map<String, Object> findTypeCollectionAndUserIdByLibraryId(Long libraryId);

   @Query(value = "SELECT conf, user_id, title, type_collection FROM libraries WHERE id = ?1", nativeQuery = true)
   public Map<String, Object> findConfAndUserIdAndTypeCollectionAndTitleByLibraryId(Long libraryId);

   @Query(value = "SELECT title, user_id, miniature, miniature_header, miniature_title, miniature_subtitle FROM libraries WHERE id = ?1", nativeQuery = true)
   public Map<String, Object> findTitleAndMiniatureAndUserIdAndMiniatureHeaderAndMiniatureTitleAndMiniatureSubtitleByLibraryId(
         Long libraryId);
}
