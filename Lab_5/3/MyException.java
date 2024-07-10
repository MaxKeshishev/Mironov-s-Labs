//Реализовать класс исключения
public class MyException extends RuntimeException
{
    private int detail; // переменная для несуществующего индекса
    public MyException(int a){
        detail = a;
    }
    public String toString(){
        return "Position " + detail + " is not contained in the string";
    }
}