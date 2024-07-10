public class Element implements Comparable<Element>{

    private int first, second; // два числа

    public Element (int F, int S) {
        first = Math.abs(F); // тип данных хранит только натуральные числа, поэтому на всякий случай записываем модуль
        second = Math.abs(S);
    }

    // переопределяем метод compareTo для сравнения двух времен
    public int compareTo (Element A) {
        // чей НОД больше, тот и больше
        return this.MaxDivider() - A.MaxDivider();
    }

    // считаем наибольший общий делитель first и second по алгоритму Евклида
    public int MaxDivider () {
        // всё по секретным документам Миронова А. С.
        int a = Math.max(first, second), b = Math.min(first, second), ost;
        do {
            ost = a % b;
            a = b;
            b = ost;
        } while (ost != 0);
        return a;
    }

    // переопределяем метод toString для вывода времени
    public String toString () {
        return "(" + first + "," + second + ")";
    }

}
