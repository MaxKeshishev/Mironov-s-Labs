// Лабораторная №2.3
// 13. Класс элемента хранит пару натуральных чисел. Тот из объектов класса больше,
// у кого больше наибольший общий делитель. Обобщенный метод должен объединять два
// списка в третий. Сначала записываются элементы из первого списка до максимального
// значения первого списка (первое вхождение), не включая его, затем все элементы из
// второго списка, а потом оставшиеся элементы из первого списка. Все три списка
// являются параметрами метода.

public class Main {
    // Статический метод для поиска максмального элемента
    public static <T extends Comparable <T>> Position MaxPos(List<T> el) {
        Position maxPos = el.First(); // позиция максимального элемента первого списка
        T maxEl = el.Retrieve(maxPos); // значение максимального элемента первого списка
        Position current = el.First(), lastA = el.End(); // позиция текущего элемента и после последнего в A

        while (!current.equal(lastA)) { // пока текущий не стал после последнего
            if (el.Retrieve(current).compareTo(maxEl) > 0) { // сравниваем текущий с максимумом
                // если текущий больше, то запоминаем его содержание и позицию
            	maxEl = el.Retrieve(current);
                maxPos = current;
            }
            // обновляем позицию текущего
            current = el.Next(current);
        }

        return maxPos;
    }

    // Статический метод для объединения двух массивов в один
    public static <T extends Comparable <T>> void MixTwo(List<T> el1, List<T> el2, List<T> el3) {
        Position maxPos = MaxPos(el1); // позиция максимального элемента первого списка

        Position current = el1.First(); // ставим текущий в начало el1
        while (!current.equal(maxPos)) { // идём до максимума
        	el3.Insert(el1.Retrieve(current), el3.End()); // переносим элементы из el1 в el3
            current = el1.Next(current); // обновляем позицию текущего
        }

        current = el2.First(); // ставим текущий в начало el2
        Position lastEl2 = el2.End(); // конец листа el2
        while (!current.equal(lastEl2)) { // пока в el2 не дошли до конца
        	el3.Insert(el2.Retrieve(current), el3.End()); // переносим элементы из el2 в el3
            current = el2.Next(current); // обновляем позицию текущего
        }

        current = maxPos; // ставим текущий в позицию максимального
        Position lastEl1 = el1.End(); // позиция после последнего в el1
        while (!current.equal(lastEl1)) { // пока не дошли до конца el1
        	el3.Insert(el1.Retrieve(current), el3.End()); // переносим элементы из el1 в el3
            current = el1.Next(current); // обновляем позицию текущего
        }
        // обнуляем el1 и el2, чтобы в них не лежали копии элементов из el3
        el1.Makenull();
        el2.Makenull();
    }

    public static void main(String[] args) {

        Integer[] numb = new Integer[12]; // заводим лист для чисел
        String[] symb = new String[12]; // заводим лист для строк

        List<Integer> list1 = new List<Integer>(numb); // создаём лист для чисел
        List<String> list2 = new List<String>(symb); // создаём лист для строк

        // Лист числел
        list1.Insert(50, list1.End()); // добавление в пустой список
        list1.Insert(40, list1.First()); // добавление в начало
        list1.Insert(10, list1.Previous(list1.Locate(50))); // работа Previous и Locate
        list1.Insert(20, list1.Next(list1.First())); // работа Next и First
        list1.Insert(30, list1.Next(list1.Locate(20))); // работа Next и Locate
        list1.Printlist(); // вывод
        list1.Delete(list1.Next(list1.First())); // удаление по позиции Next от First
        list1.Printlist(); // вывод
        list1.Delete(list1.Previous(list1.Locate(50))); // удаление по позиции Previous от Locate
        list1.Printlist(); // вывод
        list1.Makenull(); // обнуление списка
        list1.Printlist(); // вывод пустого списка

        System.out.println();

        // Лист строк
        list2.Insert("Just", list2.First()); // добавление в пустой список
        list2.Insert("ordinary", list2.End()); // добавление в конец
        list2.Insert("an", list2.Next(list2.Locate("Just"))); // работа Next и Locate
        list2.Insert("!", list2.End()); // добавление в конец
        list2.Insert("test", list2.Locate("!")); // // работа Locate
        list2.Printlist(); // вывод
        list2.Delete(list2.Next(list2.First())); // удаление по позиции Next от First
        list2.Delete(list2.Locate("!")); // удаление по позиции Locate !
        list2.Printlist(); // вывод

        System.out.println();

        Element[] elem1 = new Element[4];
        Element[] elem2 = new Element[4]; // заводим три листа для демонстрации работы статического метода
        Element[] elem3 = new Element[8];

        List<Element> el1 = new List<Element>(elem1);
        List<Element> el2 = new List<Element>(elem2); // создаём три листа
        List<Element> el3 = new List<Element>(elem3);
        
        // Три листа элементов
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
        MixTwo(el1, el2, el3); // запускаем метод
        el3.Printlist(); // выводим результат
    }

}