package us.kanddys.pov.admin.services.impl;

import java.text.ParseException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import us.kanddys.pov.admin.exceptions.BuyerNotFoundException;
import us.kanddys.pov.admin.exceptions.utils.ExceptionMessage;
import us.kanddys.pov.admin.models.Buyer;
import us.kanddys.pov.admin.models.dtos.BuyerDTO;
import us.kanddys.pov.admin.models.utils.DateUtils;
import us.kanddys.pov.admin.repositories.jpa.BuyerJpaRepository;
import us.kanddys.pov.admin.services.BuyerService;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@Service
public class BuyerServiceImpl implements BuyerService {

   @Autowired
   private BuyerJpaRepository buyerJpaRepository;

   @Override
   public BuyerDTO getBuyerById(Long id) {
      Buyer buyer = buyerJpaRepository.findById(id).orElse(null);
      return new BuyerDTO(buyer, buyer != null ? 1 : 0);
   }

   @Override
   public Long cAdminBuyer(Long merchant, Optional<String> email, Optional<String> name, Optional<String> surname,
         Optional<String> phone, Optional<Integer> count,
         Optional<String> media, Optional<Integer> pickUp,
         Optional<Integer> delivery) {
      try {
         return buyerJpaRepository
               .save(new Buyer(null, merchant, email.orElse(null), name.orElse(null), surname.orElse(null),
                     phone.orElse(null), count.orElse(0), DateUtils.getCurrentDateWitheoutTime(), media.orElse(null),
                     pickUp.orElse(0), delivery.orElse(0)))
               .getId();
      } catch (ParseException e) {
         throw new RuntimeException("Error al convertir la fecha");
      }
   }

   @Override
   public Integer uAdminBuyer(Long id, Optional<String> email, Optional<String> name, Optional<String> surname,
         Optional<String> phone, Optional<Integer> count, Optional<String> media, Optional<Integer> pickUp,
         Optional<Integer> delivery) {
      Buyer buyerToUpdate = buyerJpaRepository.findById(id)
            .orElseThrow(() -> new BuyerNotFoundException(ExceptionMessage.BUYER_NOT_FOUND));
      email.ifPresent(buyerToUpdate::setEmail);
      name.ifPresent(buyerToUpdate::setName);
      surname.ifPresent(buyerToUpdate::setSurname);
      phone.ifPresent(buyerToUpdate::setPhone);
      count.ifPresent(buyerToUpdate::setCount);
      media.ifPresent(buyerToUpdate::setMedia);
      pickUp.ifPresent(buyerToUpdate::setPickUp);
      delivery.ifPresent(buyerToUpdate::setDelivery);
      buyerJpaRepository.save(buyerToUpdate);
      return 1;
   }

   @Override
   public Integer dAdminBuyer(Long id) {
      buyerJpaRepository.deleteById(id);
      return 1;
   }
}
