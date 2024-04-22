package us.kanddys.pov.admin.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import us.kanddys.pov.admin.models.Product;

@Service
public interface ProductJpaRepository extends JpaRepository<Product, Long> {
   
}
