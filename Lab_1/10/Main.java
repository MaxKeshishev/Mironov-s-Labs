// №10 Парными простыми числами называются два простых числа, разность которых равна двум, например: 3 5; 5 7.
// Напишите программу нахождения первых N таких пар. N задаётся в программе.
public class Main {
    public static void main(String[] args) {
        int N = 10, lastsimple = 3, cursimple = 3;
        while (N > 0) {
            cursimple += 2;
            int d = 3;
            double copy = Math.sqrt(cursimple);
            for (; d < copy; d += 2) {
                if (cursimple % d == 0) {
                    break;
                }
            }
            if (d > copy) {
                if (cursimple-lastsimple == 2){
                    System.out.println(""+lastsimple+' '+cursimple);
                    N--;
                }
                lastsimple = cursimple;
            }
        }
    }
}
