package us.kanddys.pov.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import us.kanddys.pov.admin.models.dtos.DifferentProductDTO;
import us.kanddys.pov.admin.services.ProductService;

@Controller
public class ProductController {

   @Autowired
   private ProductService productService;

   @QueryMapping("gAdminSellProduct")
   public DifferentProductDTO gAdminSellProduct(@Argument Long productId) {
      return productService.getAdminSellProduct(productId);
   }

   @MutationMapping("dAdminSellProduct")
   public Integer dAdminSellProduct(@Argument Long productId) {
      return productService.deleteProduct(productId);
   }
}
