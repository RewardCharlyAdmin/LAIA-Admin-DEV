package us.kanddys.pov.admin.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import us.kanddys.pov.admin.models.Payment;

@Repository
public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {
}
