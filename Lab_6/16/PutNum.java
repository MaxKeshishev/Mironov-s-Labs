// Класс PutNum реализует потоки, добавляющие значения в стек
import java.util.Random;
public class PutNum implements Runnable {
    private Stack Array; // ссылка на стек с цифрами

    public PutNum(char num, Stack A) {
        Array = A;
        new Thread(this, "Put" + num).start(); // запуск нового потока
    }

    public void run() {
        while (true) {
            Random r = new Random();
            try {
                int count = r.nextInt(4) + 2; // определение количества значений (от 2 до 5)
                for (int i = 0; i < count; i++) {
                    Array.put(r.nextInt(9) + 1); // добавление случайного значения от 1 до 9
                }
                Thread.sleep(500); // пауза между добавлениями
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


