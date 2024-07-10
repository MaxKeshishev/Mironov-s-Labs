// 13. Класс элемента хранит пару натуральных чисел. Тот из объектов класса больше,
// у кого больше наибольший общий делитель. Обобщенный метод должен объединять два
// списка в третий. Сначала записываются элементы из первого списка до максимального
// значения первого списка (первое вхождение), не включая его, затем все элементы из
// второго списка, а потом оставшиеся элементы из первого списка. Все три списка
// являются параметрами метода.

public class Main {

    public static <T extends Comparable <T>> Position MaxPos(List<T> A) {
        Position maxpos = A.First(); // позиция максимального элемента первого списка
        T maximum = A.Retrieve(maxpos); // значение максимального элемента первого списка
        Position current = A.First(), lastA = A.End(); // позиция текущего элемента и после последнего в A

        while (!current.equal(lastA)) { // пока текущий не стал после последнего
            if (A.Retrieve(current).compareTo(maximum) > 0) { // сравниваем текущий с максимумов
                // если текущий больше, то запоминаем его содержание и позицию
                maximum = A.Retrieve(current);
                maxpos = current;
            }
            // обновляем позицию текущего
            current = A.Next(current);
        }

        return maxpos;
    }

    // статический метод, объединяющий два массива в один
    public static <T extends Comparable <T>> void MixTwo(List<T> A, List<T> B, List<T> C) {
        Position maxpos = MaxPos(A); // позиция максимального элемента первого списка

        Position current = A.First(); // ставим текущий в начало А
        while (!current.equal(maxpos)) { // идём до максимума
            C.Insert(A.Retrieve(current), C.End()); // переносим элементы из А в С
            current = A.Next(current); // обновляем позицию текущего
        }

        current = B.First(); // ставим текущий в начало В
        Position lastB = B.End(); // конец листа В
        while (!current.equal(lastB)) { // пока в В не дошли до конца
            C.Insert(B.Retrieve(current), C.End()); // переносим элементы из В в С
            current = B.Next(current); // обновляем позицию текущего
        }

        current = maxpos; // ставим текущий в позицию максимального
        Position lastA = A.End(); // позиция после последнего в A
        while (!current.equal(lastA)) { // пока не дошли до конца А
            C.Insert(A.Retrieve(current), C.End()); // переносим элементы из А в С
            current = A.Next(current); // обновляем позицию текущего
        }

        // зануляем А и В, чтобы в них не лежали копии элементов из С
        A.Makenull();
        B.Makenull();
    }

    public static void main(String[] args) {

        Integer[] nums = new Integer[12];
        Element[] elements = new Element[12];
        String[] words = new String[12];

        List<Integer> A = new List<Integer>(nums); // создаём лист для чисел
        List<Element> B = new List<Element>(elements); // создаём лист для элементов
        List<String> C = new List<String>(words); // создаём лист для строк

        // работаем с листом чисел
        A.Insert(22, A.End()); // показываем, что работает добавление в пустой список
        A.Insert(44, A.End()); // показываем, что работает добавление в конец
        A.Insert(11, A.First()); // показываем, что работает добавление в начало
        A.Insert(33, A.Next(A.Locate(22))); // показываем, что работает Next и Locate
        A.Printlist(); // выводим
        A.Makenull(); // опустошаем
        A.Printlist(); // показываем, как выводится пустой

        System.out.println();

        // работаем с листом времён
        B.Insert(new Element(45, 90), B.End());
        B.Insert(new Element(111, 3), B.End());
        B.Insert(new Element(3, 9), B.End()); // добавляем элементы
        B.Insert(new Element(1, 100), B.First());
        B.Insert(new Element(8, 13), B.Previous(B.Locate(new Element(90,45)))); // показываем, что работает Previous
        B.Printlist(); // выводим результат
        B.Delete(B.Next(B.First())); // показываем, что работает отдельное удаление по позиции
        B.Printlist(); // выводим результат

        System.out.println();

        // работаем с листом строк
        C.Insert("Hello", C.End());
        C.Insert("Andrey", C.End());
        C.Insert("dear", C.End()); // добавляем строки
        C.Insert("my", C.Locate("dear"));
        C.Insert("Andrey", C.End());
        C.Printlist();
        C.Delete(C.First()); // показываем, что работает отдельное удаление по позиции
        C.Printlist(); // выводим результат

        System.out.println();

        Element[] elements1 = new Element[4];
        Element[] elements2 = new Element[4]; // заводим три листа для демонстрации работы статического метода
        Element[] elements3 = new Element[8];

        List<Element> el1 = new List<Element>(elements1);
        List<Element> el2 = new List<Element>(elements2);
        List<Element> el3 = new List<Element>(elements3);

        el1.Insert(new Element(80,10), el1.End()); // заполняем первый лист
        el1.Insert(new Element(-5,9), el1.End());
        el1.Insert(new Element(12,24), el1.End());
        el1.Insert(new Element(1,1000), el1.End());

        el2.Insert(new Element(4,8), el2.End()); // заполняем второй лист
        el2.Insert(new Element(99,100), el2.End());
        el2.Insert(new Element(54,-1), el2.End());
        el2.Insert(new Element(10,80), el2.End());

        el1.Printlist(); // выводим первый лист
        el2.Printlist(); // выводим второй лист
        MixTwo(el1,el2,el3); // запускаем метод
        el3.Printlist(); // выводим результат
    }

}