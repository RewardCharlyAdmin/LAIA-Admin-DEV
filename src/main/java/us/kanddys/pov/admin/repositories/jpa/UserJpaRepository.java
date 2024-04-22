package us.kanddys.pov.admin.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import us.kanddys.pov.admin.models.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

   @Query(value = "SELECT id FROM users WHERE id = ?1", nativeQuery = true)
   Long existIdByUserId(Long id);
}
