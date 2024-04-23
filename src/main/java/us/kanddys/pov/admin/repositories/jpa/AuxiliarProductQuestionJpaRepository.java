package us.kanddys.pov.admin.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import us.kanddys.pov.admin.models.AuxiliarProductQuestion;

@Repository
public interface AuxiliarProductQuestionJpaRepository extends JpaRepository<AuxiliarProductQuestion, Long> {

   @Query(value = "SELECT * FROM aux_product_questions WHERE product = ?1", nativeQuery = true)
   AuxiliarProductQuestion findByProduct(Long product);

}
