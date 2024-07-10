// класс реализует объект время
public class Element implements Comparable<Element> {

    private double x;
    private double y;

    public Element (double x, double y) {
        this.x = x;
        this.y = y;
    }

    // переопределяем метод compareTo для сравнения двух
    public int compareTo (Element A) {
        double thisdist = (this.x * this.x + this.y * this.y);
        double Adist = (A.x * A.x + A.y * A.y);
        //меньше 0, если a<b, 0, если a==b и больше 0 a>b.
        //return (int)(thisdist - Adist);
        return Double.compare(thisdist, Adist);
    }

    // переопределяем метод toString для вывода
    public String toString () {
        return "(" + x + ", " + y + ")";
    }
}