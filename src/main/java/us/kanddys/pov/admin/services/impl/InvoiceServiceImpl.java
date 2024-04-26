package us.kanddys.pov.admin.services.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import us.kanddys.pov.admin.exceptions.InvoiceNotFoundException;
import us.kanddys.pov.admin.exceptions.UserNotFoundException;
import us.kanddys.pov.admin.exceptions.utils.ExceptionMessage;
import us.kanddys.pov.admin.models.Invoice;
import us.kanddys.pov.admin.models.InvoiceProduct;
import us.kanddys.pov.admin.models.Product;
import us.kanddys.pov.admin.models.User;
import us.kanddys.pov.admin.models.dtos.CompleteInvoiceCalendarDTO;
import us.kanddys.pov.admin.models.dtos.CompleteInvoiceClientDTO;
import us.kanddys.pov.admin.models.dtos.CompleteInvoiceDTO;
import us.kanddys.pov.admin.models.dtos.CompleteInvoiceDirectionDTO;
import us.kanddys.pov.admin.models.dtos.CompleteInvoiceMerchantDTO;
import us.kanddys.pov.admin.models.dtos.CompleteInvoiceProductDTO;
import us.kanddys.pov.admin.models.dtos.InvoiceAddressInputDTO;
import us.kanddys.pov.admin.models.dtos.InvoiceProductInputDTO;
import us.kanddys.pov.admin.models.dtos.InvoiceReservationDTO;
import us.kanddys.pov.admin.models.dtos.utils.LongDTO;
import us.kanddys.pov.admin.models.utils.DateUtils;
import us.kanddys.pov.admin.models.utils.enums.InvoiceStatusEnum;
import us.kanddys.pov.admin.repositories.jpa.BatchJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.InvoiceJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.InvoiceProductJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.MerchantJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.ProductJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.UserJpaRepository;
import us.kanddys.pov.admin.services.InvoiceReservationService;
import us.kanddys.pov.admin.services.InvoiceService;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

   @Autowired
   private MerchantJpaRepository merchantJpaRepository;

   @Autowired
   private UserJpaRepository userJpaRepository;

   @Autowired
   private BatchJpaRepository batchJpaRepository;

   @Autowired
   private ProductJpaRepository productJpaRepository;

   @Autowired
   private InvoiceJpaRepository invoiceJpaRepository;

   @Autowired
   private InvoiceProductJpaRepository invoiceProductJpaRepository;

   @Autowired
   private InvoiceReservationService invoiceReservationService;

   @Transactional(rollbackOn = { Exception.class, RuntimeException.class })
   @Override
   public LongDTO createAdminSellInvoice(Long userId, String email, String phone, String name, String lastName,
         Long merchantId, List<InvoiceProductInputDTO> products, InvoiceAddressInputDTO address,
         Long batchId, String reservation, String reservationType, String message) {
      Invoice newInvoice = new Invoice();
      User invoiceUser = null;
      Long existUserId = null;
      newInvoice.setMerchantId(merchantId);
      Map<String, Object> merchantAtributtes = merchantJpaRepository.findMerchantEmailAndSlugByMerchantId(merchantId);
      setInvoiceMerchantAtributtes(newInvoice, merchantId,
            (merchantAtributtes.get("slug") != null ? merchantAtributtes.get("slug").toString() : null),
            (merchantAtributtes.get("phone") != null ? merchantAtributtes.get("phone").toString() : null),
            (merchantAtributtes.get("email") != null ? merchantAtributtes.get("email").toString() : null));
      if (userId == null) {
         if (email != null) {
            existUserId = userJpaRepository.existByUserEmail(email);
         }
      } else {
         existUserId = userJpaRepository.existByUserId(userId);
      }
      invoiceUser = createInvoiceUser(existUserId, email, phone, name, lastName);
      setInvoiceAddressAtributes(newInvoice, (address == null) ? null : address);
      setInvoiceUserAtributtes(newInvoice, invoiceUser);
      try {
         newInvoice.setCreatedAt(DateUtils.getCurrentDate());
         Calendar calendar = Calendar.getInstance();
         calendar.setTime(DateUtils.convertStringToDateWithoutTime(reservation));
         newInvoice.setCalendarDay(calendar.get(Calendar.DAY_OF_MONTH));
         newInvoice.setCalendarMonth(calendar.get(Calendar.MONTH) + 1);
         newInvoice.setCalendarYear(calendar.get(Calendar.YEAR));
      } catch (ParseException e) {
         throw new RuntimeException("Error al convertir la fecha de la reserva.");
      }
      newInvoice.setMessage((message == null) ? null : message);
      newInvoice.setMerchantTitle(
            (merchantAtributtes.get("mer_slug") == null) ? null : merchantAtributtes.get("mer_slug").toString());
      newInvoice.setDirectionType(reservationType);
      Map<String, String> batchAtributtes = batchJpaRepository.findFromTimeAndToTimeById(batchId);
      newInvoice.setCalendarFrom(batchAtributtes.get("CAST(from_value AS CHAR)"));
      newInvoice.setCalendarTo(batchAtributtes.get("CAST(to_value AS CHAR)"));
      newInvoice.setStatus(InvoiceStatusEnum.INITIAL);
      newInvoice.setVoucher(null);
      newInvoice.setConfirm(0);
      List<Product> productsAux = null;
      if (products == null) {
         productsAux = new ArrayList<Product>();
         newInvoice.setTotal(0.0);
         newInvoice.setCountArticles(0);
      } else {
         productsAux = productJpaRepository
               .findAllById(products.stream().map(InvoiceProductInputDTO::getProductId).collect(Collectors.toList()));
         newInvoice.setTotal(productsAux.stream().map(product -> {
            return product.getPrice() * products.stream()
                  .filter(productAux -> productAux.getProductId().equals(product.getId())).findFirst().get()
                  .getQuantity();
         }).reduce(0.0, Double::sum));
         newInvoice.setCountArticles(products.size());
      }
      Long newInvoiceId = invoiceJpaRepository.save(newInvoice).getId();
      List<InvoiceProduct> invoiceProducts = productsAux.stream().map(product -> {
         InvoiceProduct invoiceProduct = new InvoiceProduct();
         invoiceProduct.setTitle((product.getTitle() == null) ? null : product.getTitle());
         invoiceProduct.setPrice((product.getPrice() == null) ? null : product.getPrice());
         invoiceProduct.setCountType((product.getStockType() == null) ? null : product.getStockType());
         invoiceProduct.setMedia((product.getFrontPage() == null) ? null : product.getFrontPage());
         invoiceProduct.setCount(products.stream()
               .filter(article -> article.getProductId().equals(product.getId())).findFirst().get().getQuantity());
         return invoiceProduct;
      }).collect(Collectors.toList());
      invoiceProductJpaRepository.saveAll(invoiceProducts);
      // ! FALTA IMPLEMENTAR EL EMAIL.
      // alertUser(invoiceUser, newInvoiceId,
      // merchantAtributtes.get("mer_email").toString());
      return new LongDTO(newInvoiceId);
   }

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   private void setInvoiceMerchantAtributtes(Invoice newInvoice, Long merchantId, String merchantTitle,
         String merchantPhone, String merchantEmail) {
      newInvoice.setMerchantId(merchantId);
      newInvoice.setMerchantTitle((merchantTitle == null) ? null : merchantTitle);
      newInvoice.setMerchantPhone((merchantPhone == null) ? null : merchantPhone);
      newInvoice.setMerchantEmail((merchantEmail == null) ? null : merchantEmail);
   }

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   private void setInvoiceUserAtributtes(Invoice newInvoice, User invoiceUser) {
      newInvoice.setClientName((invoiceUser.getName() == null) ? null : invoiceUser.getName());
      newInvoice.setClientSurname((invoiceUser.getSurname() == null) ? null : invoiceUser.getSurname());
      newInvoice.setClientEmail((invoiceUser.getEmail() == null) ? null : invoiceUser.getEmail());
   }

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   private void setInvoiceAddressAtributes(Invoice newInvoice, InvoiceAddressInputDTO address) {
      if (address == null) {
         newInvoice.setDirectionType(null);
         newInvoice.setDirectionCity(null);
         newInvoice.setDirectionCountry(null);
         newInvoice.setDirectionLat(null);
         newInvoice.setDirectionLng(null);
         newInvoice.setDirectionNote(null);
         newInvoice.setDirectionNumber(null);
         newInvoice.setDirectionRef(null);
         newInvoice.setDirectionStreet(null);
      } else {
         newInvoice.setDirectionCity((address.getCity() == null) ? null : address.getCity());
         newInvoice.setDirectionCountry((address.getCountry() == null) ? null : address.getCountry());
         newInvoice.setDirectionLat((address.getLat() == null) ? null : address.getLat());
         newInvoice.setDirectionLng((address.getLng() == null) ? null : address.getLng());
         newInvoice.setDirectionNote((address.getNote() == null) ? null : address.getNote());
         newInvoice.setDirectionNumber((address.getNumber() == null) ? null : address.getNumber());
         newInvoice.setDirectionRef((address.getRef() == null) ? null : address.getRef());
         newInvoice.setDirectionStreet((address.getStreet() == null) ? null : address.getStreet());
      }
   }

   private User createInvoiceUser(Long existUserId, String email, String phone,
         String name, String surname) {
      // ! Usuario registrado en el sistema.
      if (existUserId != null) {
         User user = userJpaRepository.findUserById(existUserId);
         if (user == null) {
            throw new UserNotFoundException(ExceptionMessage.USER_NOT_FOUND);
         }
         return user;
      }
      // ! Usuario no registrado en el sistema.
      else {
         if (email != null) {
            User newUser = new User();
            newUser.setId(null);
            newUser.setEmail(email);
            newUser.setPhone((phone == null) ? null : phone);
            newUser.setName((name == null) ? null : name);
            newUser.setSurname((surname == null) ? null : surname);
            newUser.setReference(email);
            return userJpaRepository.save(newUser);
         }
         // ! Usuario no registrado en el sistema y sin correo electronico.
         else {
            User newUser = new User();
            newUser.setId(null);
            newUser.setEmail(null);
            newUser.setPhone((phone == null) ? null : phone);
            newUser.setName((name == null) ? null : name);
            newUser.setSurname((surname == null) ? null : surname);
            newUser.setReference(setUserReference(email, name, surname, phone));
            return newUser;
         }
      }
   }

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   private String setUserReference(String email, String name, String surname, String phone) {
      return (email != null) ? email
            : (name != null) ? name : (surname != null) ? surname : (phone != null) ? phone : null;
   }

   @Override
   public CompleteInvoiceDTO gAdminSellInvoice(Long invoiceId, Long userId) {
      CompleteInvoiceDTO completeInvoiceDTO = new CompleteInvoiceDTO();
      Optional<Invoice> invoice = invoiceJpaRepository.findById(invoiceId);
      if (invoice.isEmpty()) {
         throw new InvoiceNotFoundException(ExceptionMessage.INVOICE_NOT_FOUND);
      }
      if (invoice.get().getMerchantId() != userId) {
         completeInvoiceDTO.setOperation(0);
         return completeInvoiceDTO;
      }
      completeInvoiceDTO.setCli(new CompleteInvoiceClientDTO(invoice.get()));
      completeInvoiceDTO.setMerchant(new CompleteInvoiceMerchantDTO(invoice.get()));
      completeInvoiceDTO.setProducts(invoiceProductJpaRepository.findAllByInvoiceId(invoice.get().getId()).stream()
            .map(CompleteInvoiceProductDTO::new).collect(Collectors.toSet()));
      completeInvoiceDTO.setCalendar(new CompleteInvoiceCalendarDTO(invoice.get()));
      completeInvoiceDTO.setDirection(new CompleteInvoiceDirectionDTO(invoice.get()));
      completeInvoiceDTO.setMessage((invoice.get().getMessage() == null) ? null : invoice.get().getMessage());
      completeInvoiceDTO.setOperation(1);
      return completeInvoiceDTO;
   }

   @Override
   public InvoiceReservationDTO gAdminSellInvoiceReservation(Long merchantId, Set<Long> invoiceArticlesIds) {
      return invoiceReservationService.getInvoiceReservation(merchantId, invoiceArticlesIds);
   }
}
