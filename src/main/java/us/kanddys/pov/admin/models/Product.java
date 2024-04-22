package us.kanddys.pov.admin.models;

import java.util.Date;

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
import us.kanddys.pov.admin.models.utils.enums.ManufacturingTypeEnum;
import us.kanddys.pov.admin.models.utils.enums.StockTypeEnum;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@AllArgsConstructor
@Data
@Entity
@Table(name = "products")
public class Product {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;
   @Column(name = "user")
   private Long userId;
   @Column(name = "front_page")
   private String frontPage;
   @Column(name = "title")
   private String title;
   @Column(name = "price")
   private Double price;
   @Column(name = "stock")
   private Integer stock;
   @Column(name = "stock_type")
   @Enumerated(EnumType.STRING)
   private StockTypeEnum stockType;
   @Column(name = "manufacturing_type")
   @Enumerated(EnumType.STRING)
   private ManufacturingTypeEnum manufacturingType;
   @Column(name = "manufacturing")
   private Integer manufacturing;
   @Column(name = "created")
   private Date created;
   @Column(name = "updated")
   private Date updated;
   @Column(name = "status")
   private Integer status;
   @Column(name = "sales")
   private Integer sales;

   public Product() {
   }
}
