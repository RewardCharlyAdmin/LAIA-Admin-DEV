package us.kanddys.pov.admin.repositories.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import us.kanddys.pov.admin.models.LibraryCollection;

/**
 * @author Igirod0
 * @version 1.0.0
 */
public interface LibraryCollectionJpaRepository extends JpaRepository<LibraryCollection, Long> {

   @Query("SELECT lc FROM LibraryCollection lc WHERE lc.libraryId = ?1")
   public List<LibraryCollection> findByLibraryId(Long libraryId);

   @Query("SELECT lc.id FROM LibraryCollection lc WHERE lc.libraryId = ?1")
   public List<Long> findLibraryCollectionsByLibraryId(Long libraryId);

   @Modifying
   @Query(value = "UPDATE collections SET miniature_header = ?2, miniature_title = ?3, miniature_subtitle = ?4 WHERE id IN ?1", nativeQuery = true)
   public Integer updateMiniaturesByLibraryCollectionsIds(List<Long> libraryCollections, String miniatureHeader,
         String miniatureTitle, String miniatureSubtitle);

   @Query(value = "SELECT id FROM collections WHERE title = ?2 AND id != ?1 AND library = ?3", nativeQuery = true)
   public Optional<Long> existLibraryCollectionWithLibraryIdAndTitleAndNotLibraryCollectionId(Long collectionId,
         String title, Long libraryId);

   @Query(value = "SELECT count(*) FROM collections WHERE library = ?1", nativeQuery = true)
   public Integer countByLibraryId(Long libraryId);

   @Query(value = "SELECT count(*) FROM collections WHERE library = ?1 AND title LIKE %?2%", nativeQuery = true)
   public Integer countByLibraryIdAndTitle(Long libraryId, String title);
}
