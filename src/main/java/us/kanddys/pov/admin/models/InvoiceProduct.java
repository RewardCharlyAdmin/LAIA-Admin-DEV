package us.kanddys.pov.admin.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import us.kanddys.pov.admin.models.utils.enums.StockTypeEnum;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@AllArgsConstructor
@Data
@Table(name = "invoice_products")
@Entity
public class InvoiceProduct {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;
   @Column(name = "invoice")
   private Long invoice;
   @Column(name = "title")
   private String title;
   @Column(name = "price")
   private Double price;
   @Column(name = "media")
   private String media;
   @Column(name = "count")
   private Integer count;
   @Column(name = "count_type")
   private StockTypeEnum countType;

   public InvoiceProduct() {
   }
}