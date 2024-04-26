package us.kanddys.pov.admin.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import us.kanddys.pov.admin.models.utils.enums.InvoiceStatusEnum;

@AllArgsConstructor
@Data
@Table(name = "invoices")
@Entity
public class Invoice {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;
   @Column(name = "cli_id")
   private Long clientId;
   @Column(name = "cli_name")
   private String clientName;
   @Column(name = "cli_surname")
   private String clientSurname;
   @Column(name = "cli_email")
   private String clientEmail;
   @Column(name = "cli_media")
   private String clientMedia;
   @Column(name = "cli_phone")
   private String clientPhone;
   @Column(name = "mer_id")
   private Long merchantId;
   @Column(name = "mer_title")
   private String merchantTitle;
   @Column(name = "mer_phone")
   private String merchantPhone;
   @Column(name = "mer_email")
   private String merchantEmail;
   @Column(name = "code")
   private String code;
   @Column(name = "total")
   private Double total;
   @Column(name = "message")
   private String message;
   @Enumerated(EnumType.STRING)
   @Column(name = "status")
   private InvoiceStatusEnum status;
   @Column(name = "voucher")
   private String voucher;
   @Column(name = "calendar_day")
   private Integer calendarDay;
   @Column(name = "calendar_month")
   private Integer calendarMonth;
   @Column(name = "calendar_year")
   private Integer calendarYear;
   @Column(name = "calendar_from")
   private String calendarFrom;
   @Column(name = "calendar_to")
   private String calendarTo;
   @Column(name = "direction_code")
   private String directionCode;
   @Column(name = "direction_ref")
   private String directionRef;
   @Column(name = "direction_country")
   private String directionCountry;
   @Column(name = "direction_city")
   private String directionCity;
   @Column(name = "direction_street")
   private String directionStreet;
   @Column(name = "direction_number")
   private String directionNumber;
   @Column(name = "direction_lat")
   private String directionLat;
   @Column(name = "direction_lng")
   private String directionLng;
   @Column(name = "direction_note")
   private String directionNote;
   @Column(name = "direction_type")
   private String directionType;
   @Column(name = "direction_state")
   private String directionState;
   @Column(name = "created_at")
   private Date createdAt;
   @Column(name = "updated_at")
   private Date updatedAt;
   @Column(name = "confirm")
   private Integer confirm;
   @Column(name = "count_articles")
   private Integer countArticles;

   public Invoice() {
   }
}
