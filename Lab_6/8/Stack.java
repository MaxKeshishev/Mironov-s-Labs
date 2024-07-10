import java.util.concurrent.locks.*;

// реализация стека для скобочек
public class Stack{

    private char[] Symbols; // массив для скобочек
    private int up; // индекс последнего элемента в стеке
    private Lock MethodLock;
    private Condition FullCondition, EmptyCondition, BracketDiffCond;
    // стек полный, стек пустой, закрывающая скобка не совпадает с верхней

    // конструктор стека
    public Stack () {
        Symbols = new char[10]; // задаём массив
        up = -1; // стек пустой, поэтому -1
        MethodLock = new ReentrantLock();
        FullCondition = MethodLock.newCondition();
        EmptyCondition = MethodLock.newCondition();
        BracketDiffCond = MethodLock.newCondition();
    }

    // добавить в стек элемент
    public void push (char a) {
        Symbols[++up] = a;
    }

    // удаляем верхний элемент
    public void pop () {
        up--;
    }

    // смотрим, что вверху
    public char top () {
        return Symbols[up];
    }

    // полный или нет
    public boolean full () {
        return up == Symbols.length - 1;
    }

    // пустой или нет
    public boolean empty () {
        return up == -1;
    }

    // вспомогательный метод для класса с открывающими скобками
    public void for_Opener (char Symbol) throws InterruptedException {
        MethodLock.lock();
        try {
            // пока стек полный, потоки на добавление спят
            while (full()) {
                FullCondition.await();
            }
            // добавляет символ, который передали
            push(Symbol);
            // выводим поток и состояние стека
            System.out.println(Thread.currentThread().getName() + " " + Symbol + "\n" + this);
            // скобка добавлена, значит уже не пустой и можно будить всех, кто не работал, потому что пустой
            EmptyCondition.signalAll();
            // скобка добавлена, значит может совпадать с кем-то, кто спит, потому что не совпадала
            BracketDiffCond.signalAll();
        }
        finally{
            MethodLock.unlock();
        }
    }

    // вспомогательный метод для класса с закрывающими скобками
    public void for_Closer (char Symbol) throws InterruptedException {
        MethodLock.lock();
        try {
            // определяем открывающую скобку для поступившей закрывающей
            char SymbolCopy = transform(Symbol);

            while (empty())
                EmptyCondition.await();
            while (top() != SymbolCopy) {
                BracketDiffCond.await();
                while (empty())
                    EmptyCondition.await();
            }

            // если подходит, то удаляем верхнюю скобку
            pop();
            // выводим поток и состояние стека
            System.out.println(Thread.currentThread().getName() + " " + Symbol + "\n" + this);
            // скобка удалена, значит уже не полный и можно будить всех, кто не работал, потому что полный
            FullCondition.signalAll();
            // скобка удалена, значит может совпадать с кем-то, кто спит, потому что не совпадала
            BracketDiffCond.signalAll();
        }
        finally{
            MethodLock.unlock();
        }
    }

    // вспомогательный метод, возвращающий открытую скобку для закрытой
    private char transform (char Close) {
        switch (Close) {
            case '}' : return '{';
            case ')' : return '(';
            default: return '[';
        }
    }

    // переопределение метода для вывода
    public String toString () {
        MethodLock.lock();
        try {
            char[] newSym = new char[up + 1]; // собираем массив чаров и делаем из него строку
            for (int i = up; i >= 0; i--) // идём в обратную сторону, чтобы вывод был от вершины до основания
                newSym[up - i] = Symbols[i];
            return String.valueOf(newSym);
        } finally {
            MethodLock.unlock();
        }

    }

}
