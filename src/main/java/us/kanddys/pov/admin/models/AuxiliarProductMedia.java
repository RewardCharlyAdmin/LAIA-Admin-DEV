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
@Table(name = "aux_products_medias")
@Entity
public class AuxiliarProductMedia {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;
   @Column(name = "product")
   private Long product;
   @Column(name = "media")
   private String media;
   @Column(name = "media_type")
   private String type;

   public AuxiliarProductMedia() {
   }
}
