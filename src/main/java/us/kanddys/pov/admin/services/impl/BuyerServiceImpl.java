package us.kanddys.pov.admin.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import us.kanddys.pov.admin.exceptions.BuyerNotFoundException;
import us.kanddys.pov.admin.exceptions.utils.ExceptionMessage;
import us.kanddys.pov.admin.models.Buyer;
import us.kanddys.pov.admin.models.dtos.BuyerDTO;
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
   public Long cBuyer(Long merchantId, String email, String name, String surname, String phone, Integer count,
         String media) {
      Buyer buyer = new Buyer();
      buyer.setMerchantId(merchantId);
      buyer.setEmail(email);
      buyer.setName(name);
      buyer.setSurname(surname);
      buyer.setPhone(phone);
      buyer.setCount(count);
      buyer.setMedia(media);
      buyerJpaRepository.save(buyer);
      return buyer.getId();
   }

   @Override
   public Integer uBuyer(Long buyerId, Optional<String> email, Optional<String> name, Optional<String> surname,
         Optional<String> phone, Optional<Integer> count, Optional<String> media) {
      Buyer buyerToUpdate = buyerJpaRepository.findById(buyerId)
            .orElseThrow(() -> new BuyerNotFoundException(ExceptionMessage.BUYER_NOT_FOUND));
      email.ifPresent(buyerToUpdate::setEmail);
      name.ifPresent(buyerToUpdate::setName);
      surname.ifPresent(buyerToUpdate::setSurname);
      phone.ifPresent(buyerToUpdate::setPhone);
      count.ifPresent(buyerToUpdate::setCount);
      media.ifPresent(buyerToUpdate::setMedia);
      buyerJpaRepository.save(buyerToUpdate);
      return 1;
   }

   @Override
   public Integer dBuyer(Long buyerId) {
      Long buyerIdToDelete = buyerJpaRepository.findById(buyerId)
            .orElseThrow(() -> new BuyerNotFoundException(ExceptionMessage.BUYER_NOT_FOUND))
            .getId();
      buyerJpaRepository.deleteById(buyerIdToDelete);
      return 1;
   }
}
