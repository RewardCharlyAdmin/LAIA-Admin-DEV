package us.kanddys.pov.admin.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import us.kanddys.pov.admin.models.utils.enums.AmountTypeEnum;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@AllArgsConstructor
@Data
@Table(name = "payments")
@Entity
public class Payment {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;
   @Column(name = "title")
   private String title;
   @Column(name = "cvu")
   private String cvu;
   @Column(name = "email")
   private String email;
   @Column(name = "amount")
   private Double amount;
   @Enumerated(value = EnumType.STRING)
   @Column(name = "amount_type")
   private AmountTypeEnum amountType;
   @Column(name = "payment")
   private String payment;
   @Column(name = "merchant")
   private Long merchant;

   public Payment() {
   }
}
