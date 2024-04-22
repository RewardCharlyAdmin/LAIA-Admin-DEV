package us.kanddys.pov.admin.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import us.kanddys.pov.admin.models.Buyer;
import us.kanddys.pov.admin.models.FilterBuyer;
import us.kanddys.pov.admin.models.dtos.BuyerDTO;
import us.kanddys.pov.admin.repositories.criteria.BuyerCriteriaRepository;
import us.kanddys.pov.admin.repositories.jpa.FilterBuyerJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.ShoppingJpaRepository;
import us.kanddys.pov.admin.services.BuyerLibraryService;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@Service
public class BuyerLibraryServiceImpl implements BuyerLibraryService {
   // * ORDENAMIENTO.
   private final String DATE = "DATE";
   private final String EMAIL = "EMAIL";
   private final String NAME = "NAME";
   private final String SURNAME = "SURNAME";
   private final String COUNT = "COUNT";
   private final String PHONE = "PHONE";
   private final String TOTAL = "TOTAL";
   private final String TOTAL_MAX = "TOTAL_MAX";
   private final String COUNT_ARTICLE = "COUNT_ARTICLE";
   private final String COUNT_ARTICLE_TOTAL = "COUNT_ARTICLE_TOTAL";
   private final String ID = "ID";
   private final String MERCHANTID = "MERCHANTID";
   // * TIPOS DE CONDICIONES.
   private final String RANGE = "RANGE";
   private final String BOOLEAN = "BOOLEAN";
   private final String MULTI = "MULTI";

   @Autowired
   private FilterBuyerJpaRepository filterBuyerJpaRepository;

   @Autowired
   private BuyerCriteriaRepository buyerCriteriaRepostory;

   @Autowired
   private ShoppingJpaRepository shoppingJpaRepository;

   @Override
   public List<Map<String, Object>> gBuyerLibraryCollectionItems(Long libraryCollectionId, String[] miniatureHeader,
         String[] miniatureTitle, String[] miniatureSubtitle, String ordering, Integer ascDsc, Long userId,
         Integer page, Integer maxResults) {
      List<String> properties = new ArrayList<String>();
      properties.addAll(Arrays.asList(miniatureHeader));
      properties.addAll(Arrays.asList(miniatureTitle));
      properties.addAll(Arrays.asList(miniatureSubtitle));
      return getBuyerCollectionsItemsByMiniature(properties
            .stream()
            .flatMap(miniatureProperty -> Arrays.stream(miniatureProperty.split(" ")))
            .collect(Collectors.toList()).stream().filter(miniatureProperty -> !miniatureProperty.equals(""))
            .collect(Collectors.toList()).stream().distinct().collect(Collectors.toList()),
            libraryCollectionId, ordering, ascDsc, userId, page, maxResults);
   }

   /**
    * Este método obtiene los items de la colecciónes pertenecientes a la
    * biblioteca de compradores.
    *
    * @author Igirod0
    * @version 1.0.1
    * @param miniatureProperties
    * @param libraryCollectionId
    * @param ordering
    * @param ascDsc
    * @param userId
    * @param page
    * @return List<Map<String, Object>>
    */
   private List<Map<String, Object>> getBuyerCollectionsItemsByMiniature(List<String> miniatureProperties,
         Long libraryCollectionId, String ordering, Integer ascDsc, Long userId, Integer page, Integer maxResults) {
      Optional<FilterBuyer> filterBuyer = filterBuyerJpaRepository.findByCollectionId(libraryCollectionId);
      if (filterBuyer.isPresent()) {
         List<Buyer> buyers = buyerCriteriaRepostory.findBuyerWithFilterAndOrderingPaginated(page, maxResults, userId,
               filterBuyer.get(),
               ordering, ascDsc);
         return gBuyerLibraryCollectionItemsCreateResponse(buyers, ordering, ascDsc, miniatureProperties);
      } else {
         return gBuyerLibraryCollectionItemsCreateResponse(buyerCriteriaRepostory
               .findBuyerWithFilterAndOrderingPaginated(page, maxResults, userId, null, ordering, ascDsc),
               ordering, ascDsc, miniatureProperties);
      }
   }

   @Override
   public Integer gBuyerLibraryCollectionTotalItems(Long libraryCollectionId,
         String[] miniatureHeader, String[] miniatureTitle, String[] miniatureSubtitle, String ordering, Integer ascDsc,
         Long userId) {
      Optional<FilterBuyer> filterBuyer = filterBuyerJpaRepository.findByCollectionId(libraryCollectionId);
      if (filterBuyer.isPresent()) {
         return buyerCriteriaRepostory.findTotalBuyerWithFilter(userId, filterBuyer.get(), ordering, ascDsc).intValue();
      } else {
         return buyerCriteriaRepostory.findTotalBuyerWithFilter(userId, null, ordering, ascDsc).intValue();
      }
   }

   /**
    * Este método obtiene los items de la colecciónes pertenecientes a la
    * biblioteca y prepara la respuesta en base a las propiedades de la miniature.
    *
    * @author Igirod0
    * @version 1.0.0
    * 
    */
   public List<Map<String, Object>> gBuyerLibraryCollectionItemsCreateResponse(List<Buyer> buyers,
         String ordering, Integer ascDsc, List<String> miniatureProperties) {
      List<BuyerDTO> buyersDTO = buyers.stream().map(buyer -> new BuyerDTO(buyer)).collect(Collectors.toList());
      List<Map<String, Object>> itemsCollectionResponse = new ArrayList<Map<String, Object>>();
      // * ATRIBUTOS EXTRAS.
      gBuyerExtraAtributtes(buyersDTO, miniatureProperties);
      // * ORDENAMIENTO
      for (BuyerDTO buyer : orderingBuyers(buyersDTO,
            Arrays.asList(ordering.split(" ")).stream().filter(order -> !order.equals(""))
                  .collect(Collectors.toList()),
            ascDsc,
            miniatureProperties)) {
         Map<String, Object> itemCollectionData = new HashMap<String, Object>();
         itemCollectionData.put("id", buyer.getId());
         if (miniatureProperties.contains("email"))
            itemCollectionData.put("email", (buyer.getEmail() != null) ? buyer.getEmail() : null);
         if (miniatureProperties.contains("name"))
            itemCollectionData.put("name", (buyer.getName() != null) ? buyer.getName() : null);
         if (miniatureProperties.contains("surname"))
            itemCollectionData.put("surname", (buyer.getSurname() != null) ? buyer.getSurname() : null);
         if (miniatureProperties.contains("phone"))
            itemCollectionData.put("phone", (buyer.getPhone() != null) ? buyer.getPhone() : null);
         if (miniatureProperties.contains("count"))
            itemCollectionData.put("count", (buyer.getCount() != null) ? buyer.getCount() : null);
         if (miniatureProperties.contains("date"))
            itemCollectionData.put("date", (buyer.getDate() != null) ? buyer.getDate() : null);
         if (miniatureProperties.contains("total_max"))
            itemCollectionData.put("total_max", (buyer.getTotalMax() != null) ? buyer.getTotalMax() : null);
         if (miniatureProperties.contains("total"))
            itemCollectionData.put("total", (buyer.getTotal() != null) ? buyer.getTotal() : null);
         if (miniatureProperties.contains("count_article_total"))
            itemCollectionData.put("count_article_total",
                  (buyer.getCountArticleTotal() != null) ? buyer.getCountArticleTotal() : null);
         if (miniatureProperties.contains("count_article"))
            itemCollectionData.put("count_article", (buyer.getCountArticle() != null) ? buyer.getCountArticle() : null);
         itemsCollectionResponse.add(itemCollectionData);
      }
      return itemsCollectionResponse;
   }

   /**
    * Este método obtiene los atributos extra de los compradores.
    *
    * @author Igirod0
    * @version 1.0.0
    * @param itemsCollectionResponse
    * @param miniatureProperties
    */
   private void gBuyerExtraAtributtes(List<BuyerDTO> itemsCollectionResponse,
         List<String> miniatureProperties) {
      List<Long> itemBuyerIds = itemsCollectionResponse.stream().map(item -> (Long) item.getId())
            .collect(Collectors.toList());
      if (miniatureProperties.contains("total")) {
         // * TOTAL DE COMPRAS.
         for (BuyerDTO buyerDTO : itemsCollectionResponse) {
            for (Map<String, Object> buyerTotalMaxData : shoppingJpaRepository.findTotalMaxByBuyerIds(itemBuyerIds)) {
               if (buyerDTO.getId().equals(Long.valueOf(buyerTotalMaxData.get("buyerId").toString()))) {
                  buyerDTO.setTotal((buyerTotalMaxData.get("total") != null)
                        ? Double.valueOf(buyerTotalMaxData.get("total").toString())
                        : null);
               }
            }
         }
      }
      if (miniatureProperties.contains("count_article_total")) {
         // * CANTIDAD TOTAL DE ARTICULOS COMPRADOS.
         for (BuyerDTO buyerDTO : itemsCollectionResponse) {
            for (Map<String, Object> buyerTotalMaxData : shoppingJpaRepository.findCountMaxByBuyerIds(itemBuyerIds)) {
               if (buyerDTO.getId().equals(Long.valueOf(buyerTotalMaxData.get("buyerId").toString()))) {
                  buyerDTO
                        .setCountArticleTotal((buyerTotalMaxData.get("count") != null)
                              ? Integer.valueOf(buyerTotalMaxData.get("count").toString())
                              : null);
               }
            }
         }
      }
      if (miniatureProperties.contains("count_article")) {
         // * MAYOR CANTIDAD DE ARTICULOS EN UNA COMPRA.
         for (BuyerDTO buyerDTO : itemsCollectionResponse) {
            for (Map<String, Object> buyerTotalMaxData : shoppingJpaRepository
                  .findMaxCountCountByBuyerIds(itemBuyerIds)) {
               if (buyerDTO.getId().equals(Long.valueOf(buyerTotalMaxData.get("buyerId").toString()))) {
                  buyerDTO.setCountArticle((buyerTotalMaxData.get("count") != null)
                        ? Integer.valueOf(buyerTotalMaxData.get("count").toString())
                        : null);
               }
            }
         }
      }
   }

   private List<BuyerDTO> orderingBuyers(List<BuyerDTO> buyers, List<String> orderings,
         Integer ascDsc, List<String> miniatureProperties) {
      // ! El primer ordenamiento es el principal y se relacionada con el atributo
      // ! ascDsc.
      switch (orderings.get(0)) {
         case MERCHANTID:
            Comparator<BuyerDTO> merchantIdComparator = Comparator.comparing(BuyerDTO::getMerchantId,
                  Comparator.nullsLast(Comparator.naturalOrder()));
            if (ascDsc == 0) {
               buyers.sort(merchantIdComparator.reversed());
            } else {
               buyers.sort(merchantIdComparator);
            }
            break;
         case ID:
            Comparator<BuyerDTO> idComparator = Comparator.comparing(BuyerDTO::getId,
                  Comparator.nullsLast(Comparator.naturalOrder()));
            if (ascDsc == 0) {
               buyers.sort(idComparator.reversed());
            } else {
               buyers.sort(idComparator);
            }
            break;
         case EMAIL:
            Comparator<BuyerDTO> emailComparator = Comparator.comparing(BuyerDTO::getEmail,
                  Comparator.nullsLast(Comparator.naturalOrder()));
            if (ascDsc == 0) {
               buyers.sort(emailComparator.reversed());
            } else {
               buyers.sort(emailComparator);
            }
            break;
         case NAME:
            Comparator<BuyerDTO> nameComparator = Comparator.comparing(BuyerDTO::getName,
                  Comparator.nullsLast(Comparator.naturalOrder()));
            if (ascDsc == 0) {
               buyers.sort(nameComparator.reversed());
            } else {
               buyers.sort(nameComparator);
            }
            break;
         case SURNAME:
            Comparator<BuyerDTO> surnameComparator = Comparator.comparing(BuyerDTO::getSurname,
                  Comparator.nullsLast(Comparator.naturalOrder()));
            if (ascDsc == 0) {
               buyers.sort(surnameComparator.reversed());
            } else {
               buyers.sort(surnameComparator);
            }
            break;
         case PHONE:
            Comparator<BuyerDTO> phoneComparator = Comparator.comparing(BuyerDTO::getPhone,
                  Comparator.nullsLast(Comparator.naturalOrder()));
            if (ascDsc == 0) {
               buyers.sort(phoneComparator.reversed());
            } else {
               buyers.sort(phoneComparator);
            }
            break;
         case DATE:
            Comparator<BuyerDTO> dateComparator = Comparator.comparing(BuyerDTO::getDate,
                  Comparator.nullsLast(Comparator.naturalOrder()));
            if (ascDsc == 0) {
               buyers.sort(dateComparator.reversed());
            } else {
               buyers.sort(dateComparator);
            }
            break;
         case COUNT:
            Comparator<BuyerDTO> countComparator = Comparator.comparing(BuyerDTO::getCount,
                  Comparator.nullsLast(Comparator.naturalOrder()));
            if (ascDsc == 0) {
               buyers.sort(countComparator.reversed());
            } else {
               buyers.sort(countComparator);
            }
            break;
         case COUNT_ARTICLE:
            Comparator<BuyerDTO> countArticleComparator = Comparator.comparing(BuyerDTO::getCountArticle,
                  Comparator.nullsLast(Comparator.naturalOrder()));
            if (ascDsc == 0) {
               buyers.sort(countArticleComparator.reversed());
            } else {
               buyers.sort(countArticleComparator);
            }
            break;
         case COUNT_ARTICLE_TOTAL:
            Comparator<BuyerDTO> countArticleTotalComparator = Comparator.comparing(BuyerDTO::getCountArticleTotal,
                  Comparator.nullsLast(Comparator.naturalOrder()));
            if (ascDsc == 0) {
               buyers.sort(countArticleTotalComparator.reversed());
            } else {
               buyers.sort(countArticleTotalComparator);
            }
            break;
         case TOTAL_MAX:
            Comparator<BuyerDTO> totalMaxComparator = Comparator.comparing(BuyerDTO::getTotalMax,
                  Comparator.nullsLast(Comparator.naturalOrder()));
            if (ascDsc == 0) {
               buyers.sort(totalMaxComparator.reversed());
            } else {
               buyers.sort(totalMaxComparator);
            }
            break;
         case TOTAL:
            Comparator<BuyerDTO> totalComparator = Comparator.comparing(BuyerDTO::getTotal,
                  Comparator.nullsLast(Comparator.naturalOrder()));
            if (ascDsc == 0) {
               buyers.sort(totalComparator.reversed());
            } else {
               buyers.sort(totalComparator);
            }
            break;
      }
      // ! Recorremos los ordenamientos secundarios.
      orderings.stream().skip(1).forEach(order -> {
         switch (order) {
            case ID:
               buyers.sort(Comparator.comparing(b -> {
                  if (b.getId() != null) {
                     return b.getId();
                  } else {
                     return Long.MAX_VALUE;
                  }
               }));
               break;
            case MERCHANTID:
               buyers.sort(Comparator.comparing(b -> {
                  if (b.getMerchantId() != null) {
                     return b.getMerchantId();
                  } else {
                     return Long.MAX_VALUE;
                  }
               }));
               break;
            case NAME:
               buyers.sort(Comparator.comparing(b -> {
                  if (b.getName() != null) {
                     return b.getName();
                  } else {
                     return "\uFFFF";
                  }
               }));
               break;
            case SURNAME:
               buyers.sort(Comparator.comparing(b -> {
                  if (b.getSurname() != null) {
                     return b.getSurname();
                  } else {
                     return "\uFFFF";
                  }
               }));
               break;
            case EMAIL:
               buyers.sort(Comparator.comparing(b -> {
                  if (b.getSurname() != null) {
                     return b.getSurname();
                  } else {
                     return "\uFFFF";
                  }
               }));
               break;
            case COUNT:
               buyers.sort(Comparator.comparing(b -> {
                  if (b.getCount() != null) {
                     return b.getCount();
                  } else {
                     return Integer.MAX_VALUE;
                  }
               }));
               break;
            case PHONE:
               buyers.sort(Comparator.comparing(b -> {
                  if (b.getPhone() != null) {
                     return b.getPhone();
                  } else {
                     return "\uFFFF";
                  }
               }));
               break;
            case DATE:
               buyers.sort(Comparator.comparing(b -> {
                  if (b.getDate() != null) {
                     return b.getDate();
                  } else {
                     return null;
                  }
               }, Comparator.nullsLast(Comparator.naturalOrder())));
               break;
            case COUNT_ARTICLE:
               buyers.sort(Comparator.comparing(b -> {
                  if (b.getCountArticle() != null) {
                     return b.getCountArticle();
                  } else {
                     return Integer.MAX_VALUE;
                  }
               }));
               break;
            case COUNT_ARTICLE_TOTAL:
               buyers.sort(Comparator.comparing(b -> {
                  if (b.getCountArticleTotal() != null) {
                     return b.getCountArticleTotal();
                  } else {
                     return Integer.MAX_VALUE;
                  }
               }));
               break;
            case TOTAL_MAX:
               buyers.sort(Comparator.comparing(b -> {
                  if (b.getTotalMax() != null) {
                     return b.getTotalMax();
                  } else {
                     return Double.MAX_VALUE;
                  }
               }));
               break;
            case TOTAL:
               buyers.sort(Comparator.comparing(b -> {
                  if (b.getTotal() != null) {
                     return b.getTotal();
                  } else {
                     return Double.MAX_VALUE;
                  }
               }));
               break;
         }
      });
      return buyers;
   }

   @Override
   public Map<String, Object> gTableConditionBuyerProperties(Long tableConditionId) {
      Map<String, Object> propsConditionData = new HashMap<>();
      switch (tableConditionId.intValue()) {
         case 1:
            propsConditionData = shoppingJpaRepository.findMaxMinTotal();
            break;
         case 2:
            propsConditionData = shoppingJpaRepository.findMaxMinDate();
      }
      return propsConditionData;
   }
}
