package us.kanddys.pov.admin.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import us.kanddys.pov.admin.exceptions.utils.ExceptionMessage;
import us.kanddys.pov.admin.models.ProductQuestion;
import us.kanddys.pov.admin.models.ProductQuestionMultipleQuestionOption;
import us.kanddys.pov.admin.models.utils.enums.QuestionTypeEmun;
import us.kanddys.pov.admin.repositories.jpa.ProductQuestionJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.ProductQuestionMultipleQuestionOptionJpaRepository;
import us.kanddys.pov.admin.services.ProductQuestionService;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@Service
public class ProductQuestionServiceImpl implements ProductQuestionService {

   @Autowired
   private ProductQuestionJpaRepository productQuestionJpaRepository;

   @Autowired
   private ProductQuestionMultipleQuestionOptionJpaRepository productQuestionMultipleQuestionOptionJpaRepository;

   @Override
   public Long createQuestion(String question, Optional<Integer> required, String type, Optional<Integer> limit,
         Long productId, Optional<List<String>> options) {
      var productQuestionId = productQuestionJpaRepository
            .save(new ProductQuestion(null, productId, question, determinateQuestionTypeEmun(type),
                  required.orElse(null), limit.orElse(null)))
            .getId();
      if (type.equals("MULTIPLE")) {
         if (limit.isEmpty())
            throw new IllegalArgumentException(ExceptionMessage.LIMIT_REQUIRED);
         if (options.isEmpty())
            throw new IllegalArgumentException(ExceptionMessage.OPTIONS_REQUIRED);
         productQuestionMultipleQuestionOptionJpaRepository.saveAll(options.get().stream()
               .map(option -> new ProductQuestionMultipleQuestionOption(null, option, productQuestionId))
               .toList());
      }
      return productQuestionId;
   }

   private QuestionTypeEmun determinateQuestionTypeEmun(String type) {
      switch (type) {
         case "TXT":
            return QuestionTypeEmun.TXT;
         case "CALENDAR":
            return QuestionTypeEmun.CALENDAR;
         case "MULTI":
            return QuestionTypeEmun.MULTI;
         case "TIME":
            return QuestionTypeEmun.TIME;
         default:
            return null;
      }
   }
}
