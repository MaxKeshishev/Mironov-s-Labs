//Класс, реализующий объект ввиде двух натуральных чисел
public class Element implements Comparable<Element>{
    private int first, second; // два числа
    public Element(int F, int S) {
        first = Math.abs(F); // тип данных хранит только натуральные числа, поэтому берём по модулю
        second = Math.abs(S);
    }

    // Переопределяем метод compareTo для сравнения двух чисел
    public int compareTo (Element x) {
        return gcd(this.first, this.second) - gcd(x.first, x.second); // чей НОД больше, тот и больше
    }

    // вспомогательный метод для подсчёта НОД двух чисел, храняшихся в элементе
    private static int gcd(int a, int b) {
        // всё по секретным документам Миронова А. С.
        int ost;
        do {
            ost = a % b;
            a = b;
            b = ost;
        } while (ost != 0);
        return a;
    }

    // Переопределяем метод toString для вывода чисел
    public String toString () {
        return "(" + first + "," + second + ")";
    }

}
