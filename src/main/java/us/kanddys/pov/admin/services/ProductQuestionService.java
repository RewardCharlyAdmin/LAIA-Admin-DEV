package us.kanddys.pov.admin.services;

import java.util.List;
import java.util.Optional;

/**
 * @author Igirod0
 * @version 1.0.0
 */
public interface ProductQuestionService {

   /**
    * Este método se encarga de crear una nueva pregunta para un vendedor.
    *
    * @author Igirod0
    * @version 1.0.0
    * @param question
    * @param required
    * @param type
    * @param limit
    * @param productId
    * @param options
    * @return Integer
    */
   public Long createQuestion(String question, Optional<Integer> required, String type,
         Optional<Integer> limit, Long productId, Optional<List<String>> options);
}
