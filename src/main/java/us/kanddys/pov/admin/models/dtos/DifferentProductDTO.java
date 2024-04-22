package us.kanddys.pov.admin.models.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import us.kanddys.pov.admin.models.utils.enums.ManufacturingTypeEnum;

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
   private List<String> medias;
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
   private ManufacturingTypeEnum manufacturingType;
   @JsonProperty
   private Integer manufacturing;
   @JsonProperty
   private Integer keywords;
   @JsonProperty
   private Integer questions;

   public DifferentProductDTO() {
   }
}
