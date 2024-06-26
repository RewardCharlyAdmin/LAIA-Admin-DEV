package us.kanddys.pov.admin.models;

import java.sql.Time;

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
@Table(name = "batches")
@Entity
public class Batch {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;
   @Column(name = "calendar")
   private Long calendarId;
   @Column(name = "in_days")
   private Integer days;
   @Column(name = "from_value")
   private Time from;
   @Column(name = "to_value")
   private Time to;
   @Column(name = "max_limit")
   private Integer limit;
   @Column(name = "title")
   private String title;

   public Batch() {
   }
}
