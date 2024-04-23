package us.kanddys.pov.admin.repositories.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import us.kanddys.pov.admin.models.AuxiliarMultipleQuestionOption;

@Repository
public interface AuxiliarMultipleQuestionOptionJpaRepository
      extends JpaRepository<AuxiliarMultipleQuestionOption, Long> {

   @Query(value = "SELECT option_value FROM aux_question_options WHERE question_id = ?1", nativeQuery = true)
   List<String> findOptionsByQuestionId(Long questionId);

   @Modifying
   @Query(value = "DELETE FROM aux_questions_options WHERE aux_product_id = ?1", nativeQuery = true)
   public void deleteOptionsByProductId(Long productId);
}
