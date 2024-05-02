package us.kanddys.pov.admin.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@AllArgsConstructor
@Data
public class RLoginMerchantDTO {
   @JsonProperty
   private String phone;
   @JsonProperty
   private String logo;
   @JsonProperty
   private Integer status;

   public RLoginMerchantDTO() {
   }
}
