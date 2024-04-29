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
@Entity
@Table(name = "collections")
public class LibraryCollection {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;
   @Column(name = "library")
   private Long libraryId;
   @Column(name = "miniature_header")
   private String miniatureHeader;
   @Column(name = "miniature_title")
   private String miniatureTitle;
   @Column(name = "miniature_subtitle")
   private String miniatureSubtitle;
   @Column(name = "title")
   private String title;
   @Column(name = "subtitle")
   private String subtitle;
   @Column(name = "conf")
   private Integer conf;
   @Column(name = "ordering")
   private String ordering;
   @Column(name = "asc_dsc")
   private Integer ascDsc;

   public LibraryCollection() {
   }
}
