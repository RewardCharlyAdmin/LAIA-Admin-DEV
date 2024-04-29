package us.kanddys.pov.admin.models.dtos;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@AllArgsConstructor
@Data
public class DifferentProductDTO {
   @JsonProperty
   private Long productId;
   @JsonProperty
   private Set<String> medias;
   @JsonProperty
   private String title;
   @JsonProperty
   private Double price;
   @JsonProperty
   private Integer stock;
   @JsonProperty
   private Integer invenstmentsCount;
   @JsonProperty
   private HashtagDTO hashtag;
   @JsonProperty
   private Integer segments;
   @JsonProperty
   private String manufacturingType;
   @JsonProperty
   private Integer manufacturing;
   @JsonProperty
   private Integer keywords;
   @JsonProperty
   private Integer questions;

   public DifferentProductDTO() {
   }
}
