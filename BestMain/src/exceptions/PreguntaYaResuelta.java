package exceptions;
public class PreguntaYaResuelta extends Exception {
 private static final long serialVersionUID = 1L;
 
 public PreguntaYaResuelta()
  {
    super();
  }
  /**This exception is triggered if the event has already finished
  *@param s String of the exception
  */
  public PreguntaYaResuelta(String s)
  {
    super(s);
  }
}