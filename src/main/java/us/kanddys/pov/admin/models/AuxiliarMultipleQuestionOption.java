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
@Table(name = "aux_question_options")
public class AuxiliarMultipleQuestionOption {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;
   @Column(name = "question")
   private Long question;
   @Column(name = "option_value")
   private String optionValue;

   public AuxiliarMultipleQuestionOption() {
   }
}
