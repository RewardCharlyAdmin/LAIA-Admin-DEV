package us.kanddys.pov.admin.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import us.kanddys.pov.admin.models.Hashtag;
import us.kanddys.pov.admin.models.dtos.HashtagDTO;
import us.kanddys.pov.admin.repositories.jpa.HashtagJpaRepository;
import us.kanddys.pov.admin.services.HashtagService;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@Service
public class HashtagServiceImpl implements HashtagService {

   @Autowired
   private HashtagJpaRepository hashtagJpaRepository;

   @Override
   public Long createHashtag(String hashtag, Long productId, Long userId) {
      return hashtagJpaRepository.save(new Hashtag(null, productId, hashtag, userId)).getId();
   }

   @Override
   public Integer deleteHashtag(Long id) {
      hashtagJpaRepository.deleteById(id);
      return 1;
   }

   @Override
   public Integer updateHashtag(Long id, String hashtag) {
      Optional<Hashtag> hashtagOptional = hashtagJpaRepository.findById(id);
      if (hashtagOptional.isPresent()) {
         Hashtag hashtagEntity = hashtagOptional.get();
         hashtagEntity.setHashtag(hashtag);
         hashtagJpaRepository.save(hashtagEntity);
         return 1;
      } else {
         return 0;
      }
   }

   @Override
   public HashtagDTO getHashtagByProductId(Long productId) {
      Optional<Hashtag> hashtag = hashtagJpaRepository.findByProductId(productId);
      if (hashtag.isEmpty())
         return new HashtagDTO();
      else
         return new HashtagDTO(hashtag.get());
   }
}
