package test_rb.C;

public class C
{
  private String s;
  public String getS()
  {
    return s;
  }
  public void setS(String s)
  {
    this.s = s;
  }

  public String toString()
  {
    return "Class " +this.getClass().getName() +" from bundle C.";
  }

}
