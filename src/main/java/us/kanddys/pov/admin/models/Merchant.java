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
 * @auhtor Igirod0
 * @version 1.0.0
 */
@AllArgsConstructor
@Data
@Table(name = "merchants")
@Entity
public class Merchant {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;
   @Column(name = "email")
   private String email;
   @Column(name = "password")
   private String password;
   @Column(name = "title")
   private String title;
   @Column(name = "slug")
   private String slug;
   @Column(name = "phone")
   private String phone;
   @Column(name = "logo")
   private String logo;
   @Column(name = "reference")
   private String reference;

   public Merchant() {
   }
}
