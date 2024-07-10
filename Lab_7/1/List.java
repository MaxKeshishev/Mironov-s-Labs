// Абстрактный тип данных (АТД) «Список»
public class List <T extends Comparable <T>> {

    private T[] objects; // массив объектов
    private int[] next; // массив ссылок
    private int start; // начало списка объектов
    private int space; // начало списка свободных

    public List (T[] A) {
        objects = A;
        int len = A.length;
        next = new int[len];
        start = -1; // потому что список пока пустой
        int i;
        for (i = 0; i < len-1; i++) // расставляем ссылки для пустого списка
            next[i] = i+1;
        next[i] = -1; // конец пустого списка
        space = 0; // начало пустого списка
    }

    // возвращает позицию после последнего
    public Position End () {
        return new Position(-1);
    }

    // возвращает 1-ую позицию в списке L. Если список пустой, то возвращается End(L).
    public Position First () {
        return new Position(start); // если список пустой, то старт = -1
    }

    // возвращает прошлую позицию для текущей, если позиции есть, и -1, если её нет
    // старт не приходит и список не пустой
    private int itexist(Position P) {
        int prev = start;
        int cur = next[prev]; // текущая и следующая позиции
        while (cur != -1) {
            if (cur == P.position)
                return prev; // возвращаем прошлое для текущей позиции
            prev = cur;
            cur = next[prev];
        }
        return -1; // дошли до конца и не нашли нужную позицию в полных
    }

    // вставка в позицию
    public void Insert (T x, Position P) {
        if (space != -1) {
            if (P.position == -1) { // вставим x в позицию End(L)
                if (start == -1) { // список пустой
                    objects[space] = x;
                    start = space; // первым элементом станет первый из пустых
                    space = next[space]; // переносим первый пустой в следующий для него
                    next[start] = -1; // меняем ссылку для первого элемента на -1, потому что пока он последний

                } else { // список не пустой
                    objects[space] = x; // записываем элемент
                    next[last()] = space; // новый элемент запишем в первый из пустых
                    int s = space;
                    space = next[space]; // первый элемент из пустых переносим в следующий
                    next[s] = -1; // новый элемент последний, поэтому ссылка у него -1
                }

            }
            else {
                int real = itexist(P); // вставляем не в конец, поэтому проверяем, существует ли позиция
                if (P.position == start) // если позиция равна старту, то real приравниваем к старту
                    real = start;
                else { // иначе
                    if (real == -1) // если позиции не существует, то не делаем ничего
                        return;
                    else
                        real = next[real]; // обновляем на настоящую
                }
                // если позиция существует
                objects[space] = objects[real]; // в первый пустой объект записываем старое содержание позиции
                objects[real] = x; // в нужную позицию записываем новое значение
                int s = next[space];
                next[space] = next[real]; // меняем ссылку для старого элемента
                next[real] = space; // меняем ссылку для нового элемента
                space = s; // обновляем позицию первого пустого элемента
                // если позиции p в списке L нет, то результат неопределен (ничего не делать)
            }
        }
    }


    // Delete(p, L) – удалить элемент списка L в позиции p.
    // Результат неопределен, если в списке L нет позиции p или p=End(L) (ничего не делать).
    public void Delete (Position p) {
        if (p.position != -1) { // если позиция не -1
            if (p.position == start) { // если удаляем первый элемент
                int s = next[start];
                next[start] = space;
                space = start;
                start = s;
            }
            else{
                int before = itexist(p);
                if (before != -1) { // если позиция существует, то начинаем удаление
                    // удаляем не первый элемент
                    int current = next[before]; // удаляемый элемент
                    next[before] = next[current];
                    int s = space;
                    space = current;
                    next[current] = s;
                }
            }
        }
    }

    // возвращает позицию в списке L объекта x. Если объекта в списке нет,то возвращается позиция End(L).
    // Если несколько значений, совпадает со значением x, то возвращается первая позиция от начала.
    public Position Locate (T X) {
        int current = start; // текущая позиция
        while (current != -1) { // идём до конца
            if (objects[current].compareTo(X) == 0) // если объект совпал с искомым
                return new Position(current); // возвращаем позицию найденного элемента
            current = next[current]; // обновляем ссылку
        }
        return new Position(-1); // если не нашли, то End(L)
    }

    // возвращается объект списка L в позиции p
    public T Retrieve (Position p) {
        if (p.position == -1) // Результат неопределен, если p=End(L)
            throw new MyException(p);
        if (p.position == start) // позиция в старте, то отдаём объект из старта
            return objects[start];
        int before = itexist(p); // проверяем, что позиция существует
        if (before != -1) // если позиция существует, то отдаём объект из неё
            return objects[p.position];
        throw new MyException(p); // в L нет позиции p
    }

    // возвращает следующую за p позицию в списке L. Если p – последняя позиция в списке L,
    // то Next(p, L) = End(L). Результат неопределен, если p нет в списке или p=End(L) (выбросить исключение)
    public Position Next (Position P) {
        if (P.position == -1) // после после последнего исключение
            throw new MyException(P);
        if (P.position == start)
            return new Position(next[start]); // если позиция равна старту, то возвращаем следующий от старта
        int before = itexist(P); // IsReal возвращает нам позицию до p, если p существует
        if (before != -1)
            return new Position(next[P.position]); // если позиция существует, то возвращаем следующую от неё
        throw new MyException(P);
    }

    // возвращает предыдущую перед p позицию в списке L.
    // Результат неопределен, если p = 1, p = End(L) или позиции p нет в списке L (выбросить исключение).
    public Position Previous (Position p) {
        if (p.position == start || p.position == -1) // у первого и после последнего нет прошлых
            throw new MyException(p);
        int before = itexist(p); // IsReal возвращает нам позицию до p, если p существует
        if (before == -1)
            throw new MyException(p); // если не существует, то исключение
        return new Position(before);
    }

    // делает список пустым.
    public void Makenull () {
        if (start != -1) {
        next[last()] = space; // соединяем список полных и список пустых
        space = start; // переносим начало пустого в общее начало
        start = -1; // список пустой, поэтому старт станет -1
        }
    }

    // вспомогательный метод,  позицию ищущий последний существующий объект
    private int last () {
        int current = start;
        int after = next[current]; // текущая и следующая позиции
        while (after != -1) { // идём до последнего элемента
            current = after;
            after = next[after];
        }
        return current;
    }

    // вывод списка на печать в порядке расположения элементов в списке.
    public void Printlist () {
        int current = start; // текущая и следующая позиции
        while (current != -1) {
            System.out.print(objects[current] + " "); // печатаем объекты
            current = next[current]; // идём по ссылкам
        }
        System.out.println();

    }

}
