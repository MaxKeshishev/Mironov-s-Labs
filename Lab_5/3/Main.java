public class Main {
    public static void main(String[] args) {
        // у меня первый индекс в листе - это 0!
        ListString A = new ListString("Maxim! Can you say <<Hallo>> to my best friend Andrey!"); // конструктор из строки
        System.out.println(A); // выводим оригинал строки
        ListString Acopy = new ListString(A); // копирующий конструктор
        System.out.println(A.length()); // длина строки
        A.setCharAt(22, 'e'); // замена символа по индексу
        System.out.println(A);
        System.out.println(A.charAt(7)); // узнавание символа по индексу
        ListString B = A.substring(19,28);
        System.out.println(B); // вырезаем из строки подстроку
        A.append('?'); // добавляем в конец один символ
        A.append(" Only one "); // добавляем в конец строку
        A.append(B); // добавляем в конец лист
        System.out.println(A);
        ListString C = new ListString(" Dear ");
        A.insert(0, C); // вставляем лист
        A.insert(0, B); // вставляем другой лист
        A.insert(25, "'t"); // вставляем строку
        System.out.println(A);
        A.delete(B); // удаляем все вхождения листа B
        System.out.println(A);
        System.out.println(Acopy); // при всех изменениях оригинала копия не изменилась
        A.setCharAt(100, '#'); // показываем, что исключение работает корректно
    }
}