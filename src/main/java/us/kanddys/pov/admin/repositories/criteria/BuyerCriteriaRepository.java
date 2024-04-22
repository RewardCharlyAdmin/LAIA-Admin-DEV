package us.kanddys.pov.admin.repositories.criteria;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import us.kanddys.pov.admin.models.Buyer;
import us.kanddys.pov.admin.models.FilterBuyer;
import us.kanddys.pov.admin.models.ReportShopping;
import us.kanddys.pov.admin.models.Shopping;
import us.kanddys.pov.admin.models.utils.DateUtils;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@Repository
public class BuyerCriteriaRepository {

   private final String DATE = "DATE";
   private final String COUNT = "COUNT";
   private final String TOTAL = "TOTAL";

   @Autowired
   private EntityManager entityManager;

   public List<Buyer> findBuyerWithFilterAndOrderingPaginated(Integer page, Integer maxResults, Long userId,
         FilterBuyer filterBuyer, String ordering, Integer ascDsc) {
      List<String> orders = Arrays.asList(ordering.split(" "));
      CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
      CriteriaQuery<Buyer> cQueryBuyer = cBuilder.createQuery(Buyer.class);
      Root<Buyer> rBuyer = cQueryBuyer.from(Buyer.class);
      cQueryBuyer.where(cBuilder.equal(rBuyer.get("merchantId"), userId));
      if (filterBuyer != null) {
         // ! Casos especiales de ordanimientos que se relacionan directamente con el
         // ! filtrado.
         if (orders.get(0).equals(COUNT) && filterBuyer.getCountMax() == null &&
               filterBuyer.getCountMin() == null) {
            if (ascDsc == 1) {
               cQueryBuyer.orderBy(cBuilder.asc(rBuyer.get("count")));
            } else {
               cQueryBuyer.orderBy(cBuilder.desc(rBuyer.get("count")));
            }
         }
         if (orders.get(0).equals(DATE) && filterBuyer.getFrom() == null && filterBuyer.getTo() == null) {
            if (ascDsc == 1) {
               cQueryBuyer.orderBy(cBuilder.asc(rBuyer.get("date")));
            } else {
               cQueryBuyer.orderBy(cBuilder.desc(rBuyer.get("date")));
            }
         }
         if (orders.get(0).equals(TOTAL)) {
            Subquery<Double> totalSumSubquery = cQueryBuyer.subquery(Double.class);
            Root<ReportShopping> rShopping = totalSumSubquery.from(ReportShopping.class);
            totalSumSubquery.select(cBuilder.sum(rShopping.get("total")))
                  .where(cBuilder.equal(rShopping.get("buyerId"), rBuyer.get("id")));
            if (ascDsc == 1) {
               cQueryBuyer.orderBy(cBuilder.asc(totalSumSubquery));
            } else {
               cQueryBuyer.orderBy(cBuilder.desc(totalSumSubquery));
            }
         }
         if (orders.get(0).equals(COUNT) && filterBuyer.getTo() != null) {
            if (ascDsc == 1) {
               cQueryBuyer.orderBy(cBuilder.asc(rBuyer.get("count")));
            } else {
               cQueryBuyer.orderBy(cBuilder.desc(rBuyer.get("count")));
            }
         }
         if (orders.get(0).equals(COUNT) && filterBuyer.getCountMin() != null && filterBuyer.getCountMax() != null) {
            if (ascDsc == 1) {
               cQueryBuyer.orderBy(cBuilder.asc(rBuyer.get("count")));
            } else {
               cQueryBuyer.orderBy(cBuilder.desc(rBuyer.get("count")));
            }
         }
         // ! Casos normales de filtrado.
         if (filterBuyer.getPriceMin() != null && filterBuyer.getPriceMax() == null) {
            Subquery<Double> minPriceSubquery = cQueryBuyer.subquery(Double.class);
            Root<Shopping> rShoppingMin = minPriceSubquery.from(Shopping.class);
            minPriceSubquery.select(rShoppingMin.get("total"))
                  .where(
                        cBuilder.and(
                              cBuilder.equal(rShoppingMin.get("merchantId"), userId),
                              cBuilder.equal(rShoppingMin.get("buyerId"), rBuyer.get("id")),
                              cBuilder.greaterThanOrEqualTo(rShoppingMin.get("total"), filterBuyer.getPriceMin())));
         }
         if (filterBuyer.getPriceMax() != null && filterBuyer.getPriceMin() == null) {
            Subquery<Double> maxPriceSubquery = cQueryBuyer.subquery(Double.class);
            Root<Shopping> rShoppingMax = maxPriceSubquery.from(Shopping.class);
            maxPriceSubquery.select(rShoppingMax.get("total"))
                  .where(
                        cBuilder.and(
                              cBuilder.equal(rShoppingMax.get("merchantId"), userId),
                              cBuilder.equal(rShoppingMax.get("buyerId"), rBuyer.get("id")),
                              cBuilder.lessThanOrEqualTo(rShoppingMax.get("total"), filterBuyer.getPriceMax())));
         }
         if (filterBuyer.getPriceMin() != null && filterBuyer.getPriceMax() != null && !filterBuyer.getPriceMin()
               .equals(filterBuyer.getPriceMax())) {
            Root<Shopping> rShopping = cQueryBuyer.from(Shopping.class);
            cQueryBuyer.select(rShopping.get("total"))
                  .where(
                        cBuilder.and(
                              cBuilder.equal(rShopping.get("merchantId"), userId),
                              cBuilder.equal(rShopping.get("buyerId"), rBuyer.get("id")),
                              cBuilder.between(rShopping.get("total"), filterBuyer.getPriceMin(),
                                    filterBuyer.getPriceMax())));
         }
         if (filterBuyer.getPriceMin() != null && filterBuyer.getPriceMax() != null
               && filterBuyer.getPriceMin().equals(filterBuyer.getPriceMax())) {
            Root<Shopping> rShopping = cQueryBuyer.from(Shopping.class);
            cQueryBuyer.select(rShopping.get("total"))
                  .where(
                        cBuilder.and(
                              cBuilder.equal(rShopping.get("merchantId"), userId),
                              cBuilder.equal(rShopping.get("buyerId"), rBuyer.get("id")),
                              cBuilder.equal(rShopping.get("total"), filterBuyer.getPriceMin())));
         }
         if (filterBuyer.getCountMax() != null && filterBuyer.getCountMin() == null) {
            cQueryBuyer.where(cBuilder.lessThanOrEqualTo(rBuyer.get("count"), filterBuyer.getCountMax()));
         }
         if (filterBuyer.getCountMin() != null && filterBuyer.getCountMax() == null) {
            cQueryBuyer.where(cBuilder.greaterThanOrEqualTo(rBuyer.get("count"), filterBuyer.getCountMin()));
         }
         if (filterBuyer.getCountMin() != null && filterBuyer.getCountMax() != null
               && filterBuyer.getCountMin().equals(filterBuyer.getCountMax())) {
            cQueryBuyer.where(cBuilder.equal(rBuyer.get("count"), filterBuyer.getCountMin()));
         }
         if (filterBuyer.getCountMin() != null && filterBuyer.getCountMax() != null
               && !filterBuyer.getCountMin().equals(filterBuyer.getCountMax())) {
            cQueryBuyer
                  .where(cBuilder.between(rBuyer.get("count"), filterBuyer.getCountMin(), filterBuyer.getCountMax()));
         }
         if (filterBuyer.getFrom() != null && filterBuyer.getTo() == null) {
            cQueryBuyer.where(cBuilder.greaterThanOrEqualTo(rBuyer.get("date"), filterBuyer.getFrom()));
         }
         if (filterBuyer.getTo() != null && filterBuyer.getFrom() == null) {
            cQueryBuyer.where(cBuilder.lessThanOrEqualTo(rBuyer.get("date"), filterBuyer.getTo()));
         }
         if (filterBuyer.getFrom() != null && filterBuyer.getTo() != null) {
            cQueryBuyer.where(
                  cBuilder.between(rBuyer.get("date"), filterBuyer.getFrom(), filterBuyer.getTo()));
         }
         if (filterBuyer.getFrom() != null && filterBuyer.getTo() != null
               && !filterBuyer.getFrom().equals(filterBuyer.getTo())) {
            cQueryBuyer.where(cBuilder.equal(rBuyer.get("date"), filterBuyer.getFrom()));
         }
         if (filterBuyer.getCountMax() != null && filterBuyer.getCountMax().equals(0) && filterBuyer.getFrom() != null
               && filterBuyer.getTo() != null) {
            Subquery<Integer> totalSumSubquery = cQueryBuyer.subquery(Integer.class);
            Root<ReportShopping> rShopping = totalSumSubquery.from(ReportShopping.class);
            totalSumSubquery.select(cBuilder.sum(rShopping.get("count")))
                  .where(
                        cBuilder.and(
                              cBuilder.equal(rShopping.get("merchantId"), userId),
                              cBuilder.equal(rShopping.get("buyerId"), rBuyer.get("id")),
                              cBuilder.equal(rShopping.get("year"), DateUtils.getYear(filterBuyer.getFrom())),
                              cBuilder.between(
                                    rShopping.get("month"),
                                    DateUtils.getMonth(filterBuyer.getFrom()),
                                    DateUtils.getMonth(filterBuyer.getTo()))));
            cQueryBuyer.where(
                  cBuilder.equal(totalSumSubquery, 0));
         }
      }
      cQueryBuyer.select(rBuyer);
      if (page == null || maxResults == null) {
         return entityManager.createQuery(cQueryBuyer).getResultList();
      } else {
         return entityManager.createQuery(cQueryBuyer)
               .setFirstResult((page - 1) * maxResults)
               .setMaxResults(maxResults)
               .getResultList();
      }
   }

   public Long findTotalBuyerWithFilter(Long userId, FilterBuyer filterBuyer, String ordering, Integer ascDsc) {
      List<String> orders = Arrays.asList(ordering.split(" "));
      CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
      CriteriaQuery<Long> cQueryBuyer = cBuilder.createQuery(Long.class);
      Root<Buyer> rBuyer = cQueryBuyer.from(Buyer.class);
      cQueryBuyer.where(cBuilder.equal(rBuyer.get("merchantId"), userId));
      cQueryBuyer.select(cBuilder.count(rBuyer));
      if (filterBuyer != null) {
         // ! Casos especiales de ordanimientos que se relacionan directamente con el
         // ! filtrado.
         if (orders.get(0).equals(COUNT) && filterBuyer.getCountMax() == null &&
               filterBuyer.getCountMin() == null) {
            if (ascDsc == 1) {
               cQueryBuyer.orderBy(cBuilder.asc(rBuyer.get("count")));
            } else {
               cQueryBuyer.orderBy(cBuilder.desc(rBuyer.get("count")));
            }
         }
         if (orders.get(0).equals(DATE) && filterBuyer.getFrom() == null && filterBuyer.getTo() == null) {
            if (ascDsc == 1) {
               cQueryBuyer.orderBy(cBuilder.asc(rBuyer.get("date")));
            } else {
               cQueryBuyer.orderBy(cBuilder.desc(rBuyer.get("date")));
            }
         }
         if (orders.get(0).equals(TOTAL)) {
            Subquery<Double> totalSumSubquery = cQueryBuyer.subquery(Double.class);
            Root<ReportShopping> rShopping = totalSumSubquery.from(ReportShopping.class);
            totalSumSubquery.select(cBuilder.sum(rShopping.get("total")))
                  .where(cBuilder.equal(rShopping.get("buyerId"), rBuyer.get("id")));
            if (ascDsc == 1) {
               cQueryBuyer.orderBy(cBuilder.asc(totalSumSubquery));
            } else {
               cQueryBuyer.orderBy(cBuilder.desc(totalSumSubquery));
            }
         }
         if (orders.get(0).equals(COUNT) && filterBuyer.getTo() != null) {
            if (ascDsc == 1) {
               cQueryBuyer.orderBy(cBuilder.asc(rBuyer.get("count")));
            } else {
               cQueryBuyer.orderBy(cBuilder.desc(rBuyer.get("count")));
            }
         }
         if (orders.get(0).equals(COUNT) && filterBuyer.getCountMin() != null && filterBuyer.getCountMax() != null) {
            if (ascDsc == 1) {
               cQueryBuyer.orderBy(cBuilder.asc(rBuyer.get("count")));
            } else {
               cQueryBuyer.orderBy(cBuilder.desc(rBuyer.get("count")));
            }
         }
         // ! Casos normales de filtrado.
         if (filterBuyer.getPriceMin() != null && filterBuyer.getPriceMax() == null) {
            Subquery<Double> minPriceSubquery = cQueryBuyer.subquery(Double.class);
            Root<Shopping> rShoppingMin = minPriceSubquery.from(Shopping.class);
            minPriceSubquery.select(rShoppingMin.get("total"))
                  .where(
                        cBuilder.and(
                              cBuilder.equal(rShoppingMin.get("merchantId"), userId),
                              cBuilder.equal(rShoppingMin.get("buyerId"), rBuyer.get("id")),
                              cBuilder.greaterThanOrEqualTo(rShoppingMin.get("total"), filterBuyer.getPriceMin())));
         }
         if (filterBuyer.getPriceMax() != null && filterBuyer.getPriceMin() == null) {
            Subquery<Double> maxPriceSubquery = cQueryBuyer.subquery(Double.class);
            Root<Shopping> rShoppingMax = maxPriceSubquery.from(Shopping.class);
            maxPriceSubquery.select(rShoppingMax.get("total"))
                  .where(
                        cBuilder.and(
                              cBuilder.equal(rShoppingMax.get("merchantId"), userId),
                              cBuilder.equal(rShoppingMax.get("buyerId"), rBuyer.get("id")),
                              cBuilder.lessThanOrEqualTo(rShoppingMax.get("total"), filterBuyer.getPriceMax())));
         }
         if (filterBuyer.getPriceMin() != null && filterBuyer.getPriceMax() != null && !filterBuyer.getPriceMin()
               .equals(filterBuyer.getPriceMax())) {
            Root<Shopping> rShopping = cQueryBuyer.from(Shopping.class);
            cQueryBuyer.select(rShopping.get("total"))
                  .where(
                        cBuilder.and(
                              cBuilder.equal(rShopping.get("merchantId"), userId),
                              cBuilder.equal(rShopping.get("buyerId"), rBuyer.get("id")),
                              cBuilder.between(rShopping.get("total"), filterBuyer.getPriceMin(),
                                    filterBuyer.getPriceMax())));
         }
         if (filterBuyer.getPriceMin() != null && filterBuyer.getPriceMax() != null
               && filterBuyer.getPriceMin().equals(filterBuyer.getPriceMax())) {
            Root<Shopping> rShopping = cQueryBuyer.from(Shopping.class);
            cQueryBuyer.select(rShopping.get("total"))
                  .where(
                        cBuilder.and(
                              cBuilder.equal(rShopping.get("merchantId"), userId),
                              cBuilder.equal(rShopping.get("buyerId"), rBuyer.get("id")),
                              cBuilder.equal(rShopping.get("total"), filterBuyer.getPriceMin())));
         }
         if (filterBuyer.getCountMax() != null && filterBuyer.getCountMin() == null) {
            cQueryBuyer.where(cBuilder.lessThanOrEqualTo(rBuyer.get("count"), filterBuyer.getCountMax()));
         }
         if (filterBuyer.getCountMin() != null && filterBuyer.getCountMax() == null) {
            cQueryBuyer.where(cBuilder.greaterThanOrEqualTo(rBuyer.get("count"), filterBuyer.getCountMin()));
         }
         if (filterBuyer.getCountMin() != null && filterBuyer.getCountMax() != null
               && filterBuyer.getCountMin().equals(filterBuyer.getCountMax())) {
            cQueryBuyer.where(cBuilder.equal(rBuyer.get("count"), filterBuyer.getCountMin()));
         }
         if (filterBuyer.getCountMin() != null && filterBuyer.getCountMax() != null
               && !filterBuyer.getCountMin().equals(filterBuyer.getCountMax())) {
            cQueryBuyer
                  .where(cBuilder.between(rBuyer.get("count"), filterBuyer.getCountMin(), filterBuyer.getCountMax()));
         }

         if (filterBuyer.getFrom() != null) {
            cQueryBuyer.where(cBuilder.greaterThanOrEqualTo(rBuyer.get("date"), filterBuyer.getFrom()));
         }
         if (filterBuyer.getTo() != null) {
            cQueryBuyer.where(cBuilder.lessThanOrEqualTo(rBuyer.get("date"), filterBuyer.getTo()));
         }
         if (filterBuyer.getCountMax() != null && filterBuyer.getCountMax().equals(0) && filterBuyer.getFrom() != null
               && filterBuyer.getTo() != null) {
            Subquery<Integer> totalSumSubquery = cQueryBuyer.subquery(Integer.class);
            Root<ReportShopping> rShopping = totalSumSubquery.from(ReportShopping.class);
            totalSumSubquery.select(cBuilder.sum(rShopping.get("count")))
                  .where(
                        cBuilder.and(
                              cBuilder.equal(rShopping.get("merchantId"), userId),
                              cBuilder.equal(rShopping.get("buyerId"), rBuyer.get("id")),
                              cBuilder.equal(rShopping.get("year"), DateUtils.getYear(filterBuyer.getFrom())),
                              cBuilder.between(
                                    rShopping.get("month"),
                                    DateUtils.getMonth(filterBuyer.getFrom()),
                                    DateUtils.getMonth(filterBuyer.getTo()))));
            cQueryBuyer.where(
                  cBuilder.equal(totalSumSubquery, 0));
         }
      }
      return entityManager.createQuery(cQueryBuyer).getSingleResult();
   }
}
