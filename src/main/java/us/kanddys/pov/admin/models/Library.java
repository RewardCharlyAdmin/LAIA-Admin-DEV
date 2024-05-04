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
@Table(name = "libraries")
@Entity
public class Library {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;
   @Column(name = "merchant")
   private Long merchant;
   @Column(name = "type_collection")
   private Integer typeCollection;
   @Column(name = "miniature")
   private String miniature;
   @Column(name = "miniature_header")
   private String miniatureHeader;
   @Column(name = "miniature_title")
   private String miniatureTitle;
   @Column(name = "miniature_subtitle")
   private String miniatureSubtitle;
   @Column(name = "title")
   private String title;
   @Column(name = "conf")
   private Integer conf;

   public Library() {
   }
}
