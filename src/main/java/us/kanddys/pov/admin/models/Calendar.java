package us.kanddys.pov.admin.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import us.kanddys.pov.admin.models.utils.enums.TypeCalendarEnum;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
@Entity
@Table(name = "calendars")
public class Calendar {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;
   @Column(name = "merchant")
   private Long merchant;
   @Column(name = "delay")
   private Integer delay;
   @Enumerated(EnumType.STRING)
   @Column(name = "delay_type")
   private TypeCalendarEnum delayType;

   public Calendar() {
   }
}
