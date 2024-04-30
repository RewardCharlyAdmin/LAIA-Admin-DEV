package us.kanddys.pov.admin.repositories.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import us.kanddys.pov.admin.models.Category;

@Repository
public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

   @Query("SELECT c FROM Category c WHERE c.category = ?1")
   Optional<Category> findByCategory(String string);
}
