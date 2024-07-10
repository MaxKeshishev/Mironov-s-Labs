// 12. Класс элемента списка хранит время в часах минутах и секундах.
// Часы могут быть в диапазоне от 0 до 23, минуты и секунды от 0 до 59.
// Обобщенный метод должен удалять из списка все вхождения заданного объекта.
// Список и объект являются параметрами метода.

public class Main {

    // метод для удаления всех вхождений элемента в лист
    public static <T extends Comparable <T>> void DeleteAll(List<T> A, T element) {
        Position current = A.First(), last = A.End(); // позиция текущего элемента и после последнего
        while (!current.equal(last)) { // пока текущий не стал после последнего
            if (A.Retrieve(current).compareTo(element) == 0) { // если элемент в текущем совпал с искомым
                Position curnext = A.Next(current); // запоминаем следующий элемент после удаляемого
                A.Delete(current); // то вызываем удаление по позиции
                current = curnext; // обновляем текущий на следующий после удалённого
            }
            else
                current = A.Next(current); // сдвигаем текущий дальше
        }
    }

    public static void main(String[] args) {

        Integer[] nums = new Integer[12];
        Time[] times = new Time[12];
        String[] words = new String[12];

        List<Integer> A = new List<Integer>(nums); // создаём лист для чисел
        List<Time> B = new List<Time>(times); // создаём лист для времени
        List<String> C = new List<String>(words); // создаём лист для строк

        // работаем с листом чисел
        A.Insert(22, A.End()); // показываем, что работает добавление в пустой список
        A.Insert(44, A.End()); // показываем, что работает добавление в конец
        A.Insert(11, A.First()); // показываем, что работает добавление в начало
        A.Insert(33, A.Next(A.Locate(22))); // показываем, что работает Next и Locate
        A.Printlist(); // выводим
        A.Makenull(); // опустошаем
        A.Printlist(); // показываем, как выводится пустой

        // работаем с листом времён
        B.Insert(new Time(11,54,22), B.End());
        B.Insert(new Time(11,54,22), B.End());
        B.Insert(new Time(11,54,22), B.End()); // добавляем время
        B.Insert(new Time(7,3,5), B.First());
        B.Insert(new Time(27,65,99), B.Previous(B.Locate(new Time(11,54,22)))); // показываем, что работает Previous
        B.Printlist(); // выводим
        DeleteAll(B, new Time(11,54,22)); // удаляем все вхождения
        B.Printlist(); // выводим результат

        // работаем с листом строк
        C.Insert("Hello", C.End());
        C.Insert("Andrey", C.End());
        C.Insert("dear", C.End()); // добавляем строки
        C.Insert("my", C.Locate("dear"));
        C.Insert("Andrey", C.End());
        C.Insert("Andrey", C.End());
        C.Insert("Andrey", C.End());
        C.Insert("Andrey", C.End());
        C.Printlist();
        DeleteAll(C, "dear"); // удаляем все вхождения dear
        DeleteAll(C, "Andrey"); // удаляем все вхождения dear
        //C.Delete(C.Next(C.First())); // показываем, что работает отдельное удаление по позиции
        //C.Delete(C.First()); // показываем, что работает отдельное удаление по позиции
        C.Printlist(); // выводим результат

    }

}