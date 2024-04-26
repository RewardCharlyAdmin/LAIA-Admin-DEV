package us.kanddys.pov.admin.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import us.kanddys.pov.admin.models.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

   @Query(value = "SELECT id FROM users WHERE id = ?1", nativeQuery = true)
   Long existIdByUserId(Long id);

   @Query(value = "SELECT email FROM users WHERE email = ?1", nativeQuery = true)
   Long existByUserEmail(String email);

   @Query(value = "SELECT id FROM users WHERE id = ?1", nativeQuery = true)
   Long existByUserId(Long userId);

   @Query(value = "SELECT * FROM users WHERE id = ?1", nativeQuery = true)
   User findUserById(Long existUserId);
}
