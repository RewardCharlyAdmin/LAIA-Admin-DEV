package us.kanddys.pov.admin.services;

import java.util.List;

/**
 * @author Igirod0
 * @version 1.0.0
 */
public interface KeywordService {

   /**
    * Este método se encarga de crear varios hashtags.
    * 
    * @param keywords
    * @param userId
    * @param productId
    * @return Long
    */
   public Integer createKeywords(List<String> keywords, Long userId, Long productId);
}
