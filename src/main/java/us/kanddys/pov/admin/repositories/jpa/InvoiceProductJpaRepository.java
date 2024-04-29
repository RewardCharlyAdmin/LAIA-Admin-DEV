package us.kanddys.pov.admin.repositories.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import us.kanddys.pov.admin.models.InvoiceProduct;

@Repository
public interface InvoiceProductJpaRepository extends JpaRepository<InvoiceProduct, Long> {

   @Query(value = "SELECT * FROM invoice_products WHERE invoice = ?1", nativeQuery = true)
   List<InvoiceProduct> findAllByInvoiceId(Long invoiceId);
}
