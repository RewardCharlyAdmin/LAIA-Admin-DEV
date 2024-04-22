package us.kanddys.pov.admin.models;

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
@Table(name = "users")
@Entity
public class User {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;
   @Column(name = "email")
   private String email;
   @Column(name = "name")
   private String name;
   @Column(name = "password")
   private String password;
   @Column(name = "surname")
   private String surname;
   @Column(name = "phone")
   private String phone;
   @Column(name = "media")
   private String media;
   @Column(name = "first")
   private Integer first;
   @Column(name = "reference")
   private String reference;

   public User() {
   }
}
