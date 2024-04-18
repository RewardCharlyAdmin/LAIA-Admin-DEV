package us.kanddys.pov.admin.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class TestController {

   @GetMapping("/test")
   public String getMethodName(@RequestParam String param) {
      return new String("Prueba exitosa de conexion");
   }
}
