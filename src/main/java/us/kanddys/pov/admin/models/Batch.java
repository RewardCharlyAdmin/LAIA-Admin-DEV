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
@Table(name = "batches")
@Entity
public class Batch {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;
   @Column(name = "calendar_id")
   private Long calendarId;
   @Column(name = "date")
   private Date date;
   @Column(name = "days")
   private Integer days;
   @Column(name = "from_time")
   private Time from;
   @Column(name = "to_time")
   private Time to;
   @Column(name = "max_limit")
   private Integer limit;
   @Column(name = "title")
   private String title;

   public Batch() {
   }
}
