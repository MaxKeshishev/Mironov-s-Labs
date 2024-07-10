import java.lang.Math;

public class Main {
    public static void main(String[] args) {
        System.out.println("p = 2, 2^p-1 = 3");
        int _2p = 8, i = 3, j, k, count = 1, copy_2p;
        while (count < 7){
            double copy = Math.sqrt(i);
            for (j = 3; j <= copy; j += 2){
                if (i%j == 0)
                    break;
            }
            if (j > copy) {
                copy_2p = _2p - 1;
                copy = Math.sqrt(_2p);
                for (k = 3; k <= copy; k += 2) {
                    if (copy_2p % k == 0)
                        break;
                }
                if (k > copy) {
                    System.out.println("p = " + i + ", 2^p-1 = " + copy_2p);
                    count += 1;
                }
            }
            _2p *= 4;
            i += 2;
        }
    }
}