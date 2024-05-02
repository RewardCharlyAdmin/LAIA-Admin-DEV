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
import us.kanddys.pov.admin.models.utils.enums.QuestionTypeEmun;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@AllArgsConstructor
@Data
@Table(name = "aux_product_questions")
@Entity
public class AuxiliarProductQuestion {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;
   @Column(name = "product")
   private Long product;
   @Column(name = "question")
   private String question;
   @Enumerated(EnumType.STRING)
   @Column(name = "type")
   private QuestionTypeEmun type;
   @Column(name = "required")
   private Integer required;
   @Column(name = "max_limit")
   private Integer maxLimit;

   public AuxiliarProductQuestion() {
   }

}
