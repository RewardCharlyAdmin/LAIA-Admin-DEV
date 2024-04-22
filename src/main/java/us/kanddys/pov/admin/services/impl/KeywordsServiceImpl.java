package us.kanddys.pov.admin.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import us.kanddys.pov.admin.models.Keyword;
import us.kanddys.pov.admin.repositories.jpa.KeywordJpaRepository;
import us.kanddys.pov.admin.services.KeywordService;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@Service
public class KeywordsServiceImpl implements KeywordService {

   @Autowired
   private KeywordJpaRepository keywordJpaRepository;

   @Override
   public Integer createKeywords(List<String> keywords, Long userId, Long productId) {
      keywordJpaRepository.saveAll(keywords.stream().map(keyword -> new Keyword(null, keyword, userId, productId))
            .collect(Collectors.toList()));
      return 1;
   }

}
