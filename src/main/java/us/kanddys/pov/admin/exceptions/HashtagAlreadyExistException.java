package us.kanddys.pov.admin.exceptions;

/**
 * @author Igirod0
 * @version 1.0.0
 */
public class HashtagAlreadyExistException extends RuntimeException {

   private static final long serialVersionUID = 1L;

   public HashtagAlreadyExistException(String message) {
      super(message);
   }
}
