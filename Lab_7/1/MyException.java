//Реализовать класс исключения
public class MyException extends RuntimeException {

    private Position detail; // переменная для несуществующего индекса

    public MyException (Position a) {
        detail = a;
    }

    public String toString () {
        return "Position " + detail.position + " is not contained in the List";
    }

}