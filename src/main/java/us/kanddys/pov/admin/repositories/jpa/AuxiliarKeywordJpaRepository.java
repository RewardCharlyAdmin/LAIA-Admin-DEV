package us.kanddys.pov.admin.repositories.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import us.kanddys.pov.admin.models.AuxiliarKeyword;

@Repository
public interface AuxiliarKeywordJpaRepository extends JpaRepository<AuxiliarKeyword, Long> {

   @Query(value = "SELECT keyword FROM aux_keywords WHERE id IN ?1", nativeQuery = true)
   List<String> findKeywordsByIds(List<Long> ids);
}
