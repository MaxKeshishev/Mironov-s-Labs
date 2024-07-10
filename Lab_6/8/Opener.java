import java.util.Random;

// реализация потока, генерирующего открывающие скобки
public class Opener implements Runnable {

    private final static char[] Brackets = {'{','[','('}; // массив, чтобы удобно добавлять скобки
    private Stack Array; // ссылка на стэк, в который добавляются скобки

    public Opener (char Num, Stack A) {
        Array = A; // записываем ссылку
        new Thread(this,"Opener" + Num).start(); // запускаем поток
    }

    // запуск потока
    public void run () {
        while (true) {
            Random r = new Random();
            try {
                int Times = Math.abs(r.nextInt()) % 3 + 1; // количество скобочек, которое хотим добавить
                for (int i = 0; i < Times; i++) {
                    Array.for_Opener(Brackets[Math.abs(r.nextInt()) % 3]); // добавляем случайную скобочку
                }
                Thread.sleep(500);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}