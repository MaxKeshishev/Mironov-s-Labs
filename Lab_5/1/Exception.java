//Реализовать класс исключения
public class Exception extends RuntimeException
{
    private int index; // переменная для несуществующего индекса
    public Exception(int a){
        index = a;
    }
    public String toString(){
        return "К сожалению, позиция " + index + " не содержится в строке";
    }
}
