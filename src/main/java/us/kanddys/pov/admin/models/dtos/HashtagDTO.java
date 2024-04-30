package us.kanddys.pov.admin.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import us.kanddys.pov.admin.models.Hashtag;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@AllArgsConstructor
@Data
public class HashtagDTO {
   @JsonProperty
   private Long id;
   @JsonProperty
   private Long product;
   @JsonProperty
   private String hashtag;
   @JsonProperty
   private Long user;

   public HashtagDTO() {
   }

   public HashtagDTO(Hashtag hashtag) {
      super();
      this.id = (hashtag.getId() == null) ? null : hashtag.getId();
      this.product = (hashtag.getProductId() == null) ? null : hashtag.getProductId();
      this.hashtag = (hashtag.getHashtag() == null) ? null : hashtag.getHashtag();
      this.user = (hashtag.getMerchant() == null) ? null : hashtag.getMerchant();
   }
}
