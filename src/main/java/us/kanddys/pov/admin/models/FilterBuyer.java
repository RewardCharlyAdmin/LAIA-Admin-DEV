package us.kanddys.pov.admin.models;

import java.util.Date;

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
@Table(name = "filter_buyers")
@Entity
public class FilterBuyer {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;
   @Column(name = "collection_id")
   private Long collectionId;
   @Column(name = "from_date")
   private Date from;
   @Column(name = "to_date")
   private Date to;
   @Column(name = "count_min")
   private Integer countMin;
   @Column(name = "count_max")
   private Integer countMax;
   @Column(name = "price_min")
   private Double priceMin;
   @Column(name = "price_max")
   private Double priceMax;
   @Column(name = "alias")
   private String alias;

   public FilterBuyer() {
   }
}
