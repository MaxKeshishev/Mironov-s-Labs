import java.util.concurrent.locks.*;

// Класс Stack реализует ограниченный стек с блокировками для синхронизации
public class Stack {
    private int[] S; // массив значений стека
    private int up; // индекс верхнего элемента в стеке
    private Condition fullStack, emptyStack, zeroStack; // условия для синхронизации: полный стек, пустой стек и проверка на ноль
    private Lock StackLock; // блокировка для управления доступом к стеку

    public Stack() {
        S = new int[15]; // инициализация стека размером 15
        up = -1; // начальное значение индекса верхнего элемента
        StackLock = new ReentrantLock(); // создание объекта блокировки
        fullStack = StackLock.newCondition(); // условие для проверки полного стека
        emptyStack = StackLock.newCondition(); // условие для проверки пустого стека
        zeroStack = StackLock.newCondition(); // условие для проверки наличия нуля на вершине стека
    }

    // Метод возвращает верхний элемент стека без удаления
    public int top() {
        return S[up];
    }

    // Метод удаляет и возвращает верхний элемент стека
    public int pop() {
        return S[up--];
    }

    // Метод добавляет элемент на вершину стека
    public void push(int i) {
        S[++up] = i;
    }

    // Метод проверяет, пуст ли стек
    public boolean empty() {
        return up == -1;
    }

    // Метод проверяет, полон ли стек
    public boolean full() {
        return up == S.length - 1;
    }

    // Метод добавляет элемент в стек с синхронизацией
    public void put(int n) throws InterruptedException {
        StackLock.lock(); // захват блокировки
        try {
            while (full()) // ожидание, если стек полон
                fullStack.await();
            push(n); // добавление элемента в стек
            print(); // вывод состояния стека
            System.out.println(Thread.currentThread().getName() + " " + n); // вывод имени потока и добавленного значения
            emptyStack.signalAll(); // сигнализация для всех потоков, ожидающих непустого стека
        } finally {
            StackLock.unlock(); // освобождение блокировки
        }
    }

    // Метод выполняет операцию над двумя верхними элементами стека с синхронизацией
    public void operation(char d) throws InterruptedException {
        StackLock.lock(); // захват блокировки
        try {
            while (chempty() || (d == '/' && top() == 0)) { // ожидание, если стек пуст или деление на ноль
                if (chempty())
                    emptyStack.await(); // ожидание непустого стека
                else
                    zeroStack.await(); // ожидание ненулевого значения на вершине стека
            }
            int b = pop(); // извлечение правого операнда
            int a = pop(); // извлечение левого операнда
            int res = oper(a, b, d); // выполнение операции
            push(res); // добавление результата обратно в стек
            print(); // вывод состояния стека
            System.out.println(Thread.currentThread().getName() + " " + d); // вывод имени потока и операции
            fullStack.signalAll(); // сигнализация для всех потоков, ожидающих неполного стека
            zeroStack.signalAll(); // сигнализация для всех потоков, ожидающих ненулевого значения
        } finally {
            StackLock.unlock(); // освобождение блокировки
        }
    }

    // Метод проверяет, есть ли в стеке хотя бы два элемента
    private boolean chempty() {
        if (empty())
            return true;
        int b = pop(); // временное извлечение элемента
        if (empty()) { // проверка, остался ли стек пустым
            push(b); // возврат элемента обратно в стек
            return true;
        }
        push(b); // возврат элемента обратно в стек
        return false;
    }

    // Метод выполняет арифметическую операцию над двумя числами
    private int oper(int a, int b, char d) {
        int res = 0;
        switch(d) {
            case '+':
                res = a + b;
                break;
            case '-':
                res = a - b;
                break;
            case '*':
                res = a * b;
                break;
            case '/':
                res = a / b;
                break;
        }
        return res;
    }

    // Метод выводит текущее состояние стека
    public void print() {
        StackLock.lock(); // захват блокировки
        try {
            int i;
            System.out.print("[ ");
            for (i = 0; i <= up; i++) {
                System.out.print(S[i] + " ");
            }
            System.out.print("] <- ");
        }finally {
            StackLock.unlock(); // освобождение блокировки
        }
    }
}