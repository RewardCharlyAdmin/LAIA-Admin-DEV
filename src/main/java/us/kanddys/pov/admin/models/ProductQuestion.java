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
@Table(name = "product_questions")
@Entity
public class ProductQuestion {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;
   @Column(name = "product")
   private Long productId;
   @Column(name = "question")
   private String question;
   @Column(name = "type")
   @Enumerated(EnumType.STRING)
   private QuestionTypeEmun type;
   @Column(name = "required")
   private Integer required;
   @Column(name = "max_limit")
   private Integer maxLimit;

   public ProductQuestion() {
   }
}
