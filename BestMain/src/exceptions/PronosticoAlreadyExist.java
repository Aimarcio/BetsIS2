package exceptions;
public class PronosticoAlreadyExist extends Exception {
 private static final long serialVersionUID = 1L;
 
 public PronosticoAlreadyExist()
  {
    super();
  }
  /**This exception is triggered if the question already exists 
  *@param s String of the exception
  */
  public PronosticoAlreadyExist(String s)
  {
    super(s);
  }
}