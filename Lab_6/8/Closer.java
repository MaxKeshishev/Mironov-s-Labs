// реализация потока, генерирующего закрывающие скобки
public class Closer implements Runnable {

    private char Bracket; // переменная хранит скобку, которую будет генерировать поток
    private Stack Array; // ссылка на стек, в который добавляются скобки

    // конструктор
    public Closer (char A, Stack B) {
        Bracket = A; // записываем скобочку
        Array = B; // записываем ссылку
        new Thread(this,"Closer" + A).start(); // запускаем поток
    }

    // запуск потока
    public void run () {
        while (true) {
            try {
                Array.for_Closer(Bracket);
                Thread.sleep(250);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
