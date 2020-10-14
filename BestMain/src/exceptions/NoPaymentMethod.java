package exceptions;
public class NoPaymentMethod extends Exception {
 private static final long serialVersionUID = 1L;
 
 public NoPaymentMethod()
  {
    super();
  }
  /**This exception is triggered if the event has already finished
  *@param s String of the exception
  */
  public NoPaymentMethod(String s)
  {
    super(s);
  }
}