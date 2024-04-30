package us.kanddys.pov.admin.models;

import java.sql.Time;
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
@Table(name = "exceptions")
@Entity
public class Exception {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;
   @Column(name = "date")
   private Date date;
   @Column(name = "from_value")
   private Time fromValue;
   @Column(name = "to_value")
   private Time toValue;
   @Column(name = "calendar")
   private Long calendar;
   @Column(name = "title")
   private String title;
   @Column(name = "max_limit")
   private Integer maxLimit;

   public Exception() {
   }
}
