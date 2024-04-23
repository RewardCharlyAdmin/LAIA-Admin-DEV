package us.kanddys.pov.admin.services;

import us.kanddys.pov.admin.models.dtos.HashtagDTO;

/**
 * @author Igirod0
 * @version 1.0.0
 */
public interface HashtagService {

   /**
    * Este método se encarga de crear un hashtag.
    *
    * @author Igirod0
    * @version 1.0.0
    * @param hashtag
    * @param productId
    * @param user
    * @return Long
    */
   public Long createHashtag(String hashtag, Long productId, Long userId);

   /**
    * Este método se encarga de eliminar un hashtag.
    * 
    * @param id El id del hashtag.
    * @return Integer
    */
   public Integer deleteHashtag(Long id);

   /**
    * Este método se encarga de modificar un hashtag.
    *
    * @param id      El id del hashtag.
    * @param hashtag El nuevo hashtag.
    * @return String
    */
   public Integer updateHashtag(Long id, String hashtag);

   /**
    * Este método se encarga de obtener todos los hashtags asociados a un producto.
    *
    * @param productId El id del producto.
    * @return List
    */
   public HashtagDTO getHashtagByProductId(Long productId);
}
