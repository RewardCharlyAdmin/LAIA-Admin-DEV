package us.kanddys.pov.admin.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import us.kanddys.pov.admin.models.Invoice;

@Repository
public interface InvoiceJpaRepository extends JpaRepository<Invoice, Long> {

   @Modifying
   @Query("UPDATE Invoice i SET i.status = ?2 WHERE i.id = ?1")
   void updateInvoiceStatus(Long invoice, String status);
}
