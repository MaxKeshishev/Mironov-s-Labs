// Класс Operation реализует потоки, выполняющие операции над элементами стека
import java.util.Random;
public class Operation implements Runnable {
    private Stack Array; // ссылка на стек с цифрами
    private final static char[] op = {'+', '-', '*', '/'}; // массив доступных операций

    public Operation(char num, Stack A) {
        Array = A;
        new Thread(this, "Operation" + num).start(); // запуск нового потока
    }

    public void run() {
        while(true) {
            Random r = new Random();
            try {
                int count = r.nextInt(2) + 1; // определение количества операций (1 или 2)
                for (int i = 0; i < count; i++)
                    Array.operation(op[r.nextInt(4)]); // выполнение случайной операции
                Thread.sleep(500); // пауза между операциями
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}