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

@AllArgsConstructor
@Data
@Table(name = "categories")
@Entity
public class Category {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;
   @Column(name = "merchant")
   private Long merchant;
   @Column(name = "category")
   private String category;
   @Column(name = "created")
   private Date created;
   @Column(name = "updated")
   private Date updated;

   public Category() {
   }
}
