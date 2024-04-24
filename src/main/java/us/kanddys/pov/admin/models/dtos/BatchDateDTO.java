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
public class BatchDateDTO {
   @JsonProperty
   private String date;
   @JsonProperty
   private Long batchId;
   @JsonProperty
   private Integer count;

   public BatchDateDTO(Object[] data) {
      this.batchId = Long.parseLong(data[0].toString());
      this.date = data[2].toString();
      this.count = Integer.parseInt(data[1].toString());
   }
}
