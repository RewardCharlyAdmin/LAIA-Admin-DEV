package us.kanddys.pov.admin.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import lombok.AllArgsConstructor;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@AllArgsConstructor
@Data
@Entity
@Table(name = "buyers")
public class Buyer {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;
   @Column(name = "merchant_id")
   private Long merchantId;
   @Column(name = "email")
   private String email;
   @Column(name = "name")
   private String name;
   @Column(name = "surname")
   private String surname;
   @Column(name = "phone")
   private String phone;
   @Column(name = "count")
   private Integer count;
   @Column(name = "date")
   private Date date;
   @Column(name = "media")
   private String media;
   @Column(name = "pick_up")
   private Integer pickUp;
   @Column(name = "delivery")
   private Integer delivery;

   public Buyer() {
   }
}
