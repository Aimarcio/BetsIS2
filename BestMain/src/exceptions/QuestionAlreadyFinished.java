package exceptions;
public class QuestionAlreadyFinished extends Exception {
 private static final long serialVersionUID = 1L;
 
 public QuestionAlreadyFinished()
  {
    super();
  }
  /**This exception is triggered if the question already exists 
  *@param s String of the exception
  */
  public QuestionAlreadyFinished(String s)
  {
    super(s);
  }
}