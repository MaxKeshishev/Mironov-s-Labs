//Класс исключения
public class myException extends RuntimeException {
    private Position index; // переменная для несуществующего индекса
    public myException (Position indexError) {
        index = indexError;
    }

    public String toString() {
        return "Position " + index.position + " do not exist in stroke";
    }
}