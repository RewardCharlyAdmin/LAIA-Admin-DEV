package us.kanddys.pov.admin.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@AllArgsConstructor
@Data
@Table(name = "aux_product_keywords")
@Entity
public class AuxiliarProductKeyword {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;
   @Column(name = "keyword")
   private String keyword;
   @Column(name = "auxiliar_product_id")
   private Long auxiliarProductId;
   @Column(name = "merchant")
   private Long merchant;

   public AuxiliarProductKeyword() {
   }
}
