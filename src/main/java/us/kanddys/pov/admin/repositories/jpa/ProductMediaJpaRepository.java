package us.kanddys.pov.admin.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import us.kanddys.pov.admin.models.ProductMedia;

@Repository
public interface ProductMediaJpaRepository extends JpaRepository<ProductMedia, Long> {

}
