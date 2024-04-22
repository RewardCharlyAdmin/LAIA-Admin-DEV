package us.kanddys.pov.admin.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * @author Igirod0
 * @version 1.0.0
 */
public class Shopping {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;
   @Column(name = "buyer_id")
   private Long buyerId;
   @Column(name = "merchant_id")
   private Long merchantId;
   @Column(name = "day")
   private Integer day;
   @Column(name = "month")
   private Integer month;
   @Column(name = "year")
   private Integer year;
   @Column(name = "count")
   private Integer count;
   @Column(name = "total")
   private Double total;

   public Shopping() {
   }
}
