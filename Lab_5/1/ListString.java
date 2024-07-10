//Лабораторная работа 2.1
//replace(ListString string, ListString newstring) - замена последнего вхождения
//replace(ListString string, String newstring) - замена последнего вхождения

//Реализовать класс строки
public class ListString {
    private static class StringItem {
        private final static byte SIZE = 16; //размер символьного массива в блоке
        private char[] symbols; //символы массива
        private StringItem next; //ссылка на следующий Item
        private byte size; //реальное количество символов в Item

        //Конструктор по умолчанию
        private StringItem() {
            symbols = new char[SIZE]; //Выделение памяти под символьный массив (все нули)
            next = null; //Инициализация переменных next
            size = 0; //и size (все нули)
        }

        //Копирующий конструктор
        private StringItem(StringItem A) {
            size = A.size; //создаём пустой StringItem такого же размера
            symbols = new char[SIZE];
            copAr(A.symbols, symbols, 0, A.size, 0); //целиком копируем item через метод copAr
            next = null;
        }

        //Метод разделяющий блок по индексу
        private void split(int index) {
            StringItem B = new StringItem(); // создаём пустой StringItem
            B.size = (byte) (size - index); // размер новой части
            copAr(symbols, B.symbols, index, size, 0); //копируем в него символы от index до size текущего блока
            size = (byte) (index); //изменяем размер, теперь index
            B.next = next; // ссылка нового блока меняется на ссылку исходного
            next = B; // исходный блок теперь ссылается на новый
        }

        //Метод объединения блоков
        private int join() {
            StringItem then = next; // следующий Item then
            while (then != null && (size + then.size) <= 16) { //пока сумма соседних блоков <=16 или строка не закончилась
                copAr(then.symbols, symbols, 0, then.size, size); //копируем элементы из следующего блока в текущий
                size += then.size; // увеличиваем размер текущего на размер следующего
                next = then.next; // переносим ссылку из следующего в текущий
                then = then.next; // следующий блок берем за просмотренным
            }
            return size; // возвращаем длину после сжатий
        }
    }

    //Отдельный класс для проверки валидности index. Узнаем существование, блок, позицию в блоке
    private static class Position {
        private int Index;
        private StringItem Item;

        private Position(int A, StringItem B) {
            Index = A;
            Item = B;
        }
    }

    //LISTSTRING
    private StringItem head; // назначение переменной

    //Конструктор по умолчанию
    public ListString() {
        head = null; //инициализация переменной head (пустая строка)
    }

    //Конструктор перевода String в ListString
    public ListString(String string) {
        int lenStr = string.length(); // lenStr-длина подаваемой строки string
        if (lenStr == 0)
            head = null; // если добавляемая строка пустая, то head = null
        else { // иначе будем создавать блоки по 16 и копировать с помощью getChars
            int lenItem = Math.min(lenStr, 16); // lenItem - длина копируемых символов через getChars
            head = new StringItem(); // первый блок создаем отдельно, чтобы сделать head
            string.getChars(0, lenItem, head.symbols, 0);
            head.size = (byte) lenItem;
            StringItem curItem = head;
            for (int start = 16; start < lenStr; start += 16) { // остальные блоки создаем через каждый 16 символов, пока строка не закончится
                lenItem = Math.min(lenStr - start, 16); // копируемая длина уменьшается, так как часть в предыдущем блоке
                StringItem newItem = new StringItem();
                string.getChars(start, start + lenItem, newItem.symbols, 0);
                newItem.size = (byte) lenItem;
                curItem.next = newItem; // добавляем ссылку
                curItem = newItem; //обновляем рассматриваемый блок, чтобы идти дальше
            }
        }
    }

    //Копирующий конструктор
    public ListString(ListString string) {
        if (string.head == null) // если string пустой, то head=null
            head = null;
        else { // иначе копируем каждый айтем
            head = new StringItem(string.head); // создаем второй начальный айтем(head)
            StringItem nextInString = string.head.next; // nextInString-ссылка на следующий для копирования айтем
            StringItem lastInList = head; // lastInList-ссылка на последний созданный(скопированный) айтем
            while (nextInString != null) {
                StringItem copy = new StringItem(nextInString); // делаем дубликат nextInString
                lastInList.next = copy; // дубликат вставляем в следующий айтем после последнего в листе
                lastInList = copy; // обновляем ссылку lastInList
                nextInString = nextInString.next; // обновляем ссылку nextInString, чтобы дальше копировать следующий айтем
            }
        }
    }

    //Метод копирования символов
    /*from - массив, откуда копируем, to - массив куда, start_index -индекс элемента с которого начинаем копирование,
    copy_start - индекс начала вставки, k-сколько элементов копируем*/
    private static void copAr(char[] from, char[] to, int start_index, int finish_index, int copy_start) {
        for (int i = 0; i < finish_index - start_index; i++)
            to[copy_start + i] = from[start_index + i];
    }

    //Метод нахождения последнего блока
    private StringItem getLast() {
        StringItem current = null; // текущий айтем
        StringItem next = head; // следующий айтем
        while (next != null) { // пока следующий айтем не 0
            current = next; // обновляем текущий
            next = next.next; // следующий смотрим дальше
        }
        return current; // возвращаем значение текущего блока (если ListString пустой, вернется null)
    }

    //Метод проверки валидности index. Узнаем существование, блок(предыдущий), позицию в блоке
    private Position whoIndex(int index) {
        StringItem before = null; // before - ссылка на предыдущий блок
        StringItem current = head; // current - ссылка на текущий блок
        while (current != null) { // идем по блокам, пока не найдем нужный или не дойдем до конца
            if (current.size > index) // если длина айтема больше index, то элемент в этом айтеме
                return new Position(index, before); // возвращаем позицию: индекс и предыдущий айтем
            else { // иначе
                index -= current.size; // искомый индекс уменьшаем на длину просмотренного блока(чтобы искать в следующем)
                before = current; // меняем позицию предыдущего на текущий
                current = before.next; // текущий теперь смотрим дальше
            }
        }
        return new Position(-1, before); /* если не вышли из программы раньше, значит индекса нет в данной строке,
            поэтому вернем Position отрицательное(потом методы будут выбрасывать искючение по этому значению)*/
    }

    // Метод подсчета реальной длины строки (вместе с join)
    public int length() {
        if (head == null)
            return 0; // если head == null, то возвращаем 0
        else {
            int len = 0; // len - длина строки
            StringItem current = head; // current - текущий айтем
            while (current != null) { // пока в текущем блоке что-то есть, идем по блокам
                len += current.join(); // вызываем join и суммируем
                current = current.next; // меняем текущий блок на следующий
            }
            return len; // возвращаем длину
        }
    }

    //вернуть символ в строке в позиции index
    public char charAt(int index) {
        if (head == null)
            throw new Exception(index); // пытаемся сделать что-то с пустой строкой -> исключение
        if (index < 0)
            throw new Exception(index); // index<0 -> исключение
        Position A = whoIndex(index);
        if (A.Index == -1)
            throw new Exception(index); // если найденная строка пустая(whoIndex) -> исключение
        if (A.Item == null) //если не исключение
            return head.symbols[A.Index]; // возвращаем символ по индексу
        return A.Item.next.symbols[A.Index]; // или по индексу, но следующего блока
        //return (A.Item == null ? head : A.Item.next).symbols[A.Index];
    }


    //заменить в строке символ в позиции index на символ ch
    public void setCharAt(int index, char ch) {
        if (head == null)
            throw new Exception(index); // пытаемся сделать что-то с пустой строкой -> исключение
        if (index < 0)
            throw new Exception(index); // index<0 -> исключение
        Position A = whoIndex(index);
        if (A.Index == -1)
            throw new Exception(index); // если найденная строка пустая(whoIndex) -> исключение
        if (A.Item == null) //если не исключение
            head.symbols[A.Index] = ch; // заменяем символ в head, если в первом
        else
            A.Item.next.symbols[A.Index] = ch; // заменяем символ по индексу, учитывая, что блок предыдущий
        //(A.Item == null ? head : A.Item.next).symbols[A.Index] = ch;
    }

    /* взятие подстроки от start до end, не включая end, возвращается новый объект ListString, исходный не изменяется.
    Если end не существующая позиция, то возвращается подстрока от start до конца строки */
    public ListString substring(int starti, int endi) {
        if (endi <= starti) {
            if (endi == starti) // если start = end, то возвращаем пустую строку
                return new ListString();
            else {
                int p = endi; // если start > end, то меняем местами
                endi = starti;
                starti = p;
            }
        }
        if (head == null)
            throw new Exception(starti); // пытаемся сделать что-то с пустой строкой -> исключение
        if (starti < 0)
            throw new Exception(starti); // start<0 -> исключение
        Position start = whoIndex(starti);
        if (start.Index == -1)
            throw new Exception(starti); // если найденная строка пустая(whoIndex) -> исключение
        start.Item = (start.Item == null ? head : start.Item.next); //узнаем блок старта
        Position end = whoIndex(endi); //проверяем конец
        if (end.Index == -1)
            end.Index = end.Item.size; //если конечного значения не существует, концом становится длина блока
        else
            end.Item = (end.Item == null ? head : end.Item.next); // если конец есть, то определяем его айтем
        ListString newList = new ListString(); //newList - новый пустой ListString
        StringItem one = new StringItem(); //one - первый заполняемый блок
        newList.head = one;
        if (start.Item == end.Item) { //если start и end в одном блоке
            copAr(start.Item.symbols, one.symbols, start.Index, end.Index, 0); //копируем в one все элементы  от start до end
            one.size = (byte) (end.Index - start.Index);
        } else { // если start и end в разных блоках
            copAr(start.Item.symbols, one.symbols, start.Index, start.Item.size, 0); //копируем в one все символы начального блока, начиная с start
            one.size = (byte) (start.Item.size - start.Index);
            start.Item = start.Item.next; // рассматриваем следующий блок
            StringItem cur = one; //cur - текущий рассматриваемый блок
            while (start.Item != end.Item) { //смотрим, пока не дойдем до блока с end
                StringItem newItem = new StringItem(start.Item); //newItem-айтем для копирования блока
                cur.next = newItem; //обновляем ссылку
                cur = newItem;
                start.Item = start.Item.next; //переходим к следующему блоку
            }
            StringItem lastItem = new StringItem(); //lastItem - последний создаваемый айтем
            copAr(start.Item.symbols, lastItem.symbols, 0, end.Item.size, 0); //копируем до end
            lastItem.size = (byte) (end.Index);
            cur.next = lastItem; //добавляем ссылку
        }
        return newList; //возвращаем заполненный ListString
    }

    /* добавить в конец строки символ (в конец символьного массива последнего блока,
    если там есть свободное место, иначе в начало символьного массива нового блока) */
    public void append(char ch) {
        if (head == null) {
            head = new StringItem(); // если последний блок пустой, то создаем новый StringItem и добавляем в него
            head.symbols[0] = ch;
            head.size++;
        } else {
            StringItem last = getLast();
            if (last.size < 16) {
                last.symbols[last.size] = ch; // если не пустой и есть место, добавляем туда
                last.size++;
            } else {
                StringItem newItem = new StringItem();
                newItem.symbols[0] = ch; // если последний полный, создаем новый StringItem и добавляем туда
                newItem.size++;
                last.next = newItem; // добавляем ссылку на созданный айтем к последнему
            }
        }
    }

    // добавить в конец строку ListString перекинуть указатель на следующий последнего блока исходной строки на голову добавляемой строки
    public void append(ListString string) {
        if (string.head != null) { // проверка на пустоту вставляемого (тогда ничего не изменится)
            ListString newList = new ListString(string); // делаем копию вставляемого ListString
            if (head == null)
                head = newList.head; // если head = null, переопределяем head
            else
                getLast().next = newList.head; // иначе голову нового в getLast
        }
    }

    //добавить в конец строку String перекинуть указатель на следующий последнего блока исходной строки на голову добавляемой строки
    public void append(String string) {
        if (string.length() != 0) { //проверка на пустоту вставляемого (тогда ничего не изменится)
            ListString newList = new ListString(string); // если не пустой, то делаем ListString
            if (head == null)
                head = newList.head; // если head = null, переопределяем head
            else
                getLast().next = newList.head; // иначе голову нового в getLast
        }
    }

    //вставить в строку в позицию index строку ListString (разбить блок на два по позиции index и строку вставить между этими блоками)
    public void insert(int index, ListString string) {
        if (head == null)
            throw new Exception(index); // пытаемся сделать что-то с пустой строкой -> исключение
        if (string.head != null) { // проверить, что вставляемый ListString не пустой
            if (index < 0)
                throw new Exception(index); // index<0 -> исключение
            Position A = whoIndex(index);
            if (A.Index == -1)
                throw new Exception(index); // если найденная строка пустая(whoIndex) -> исключение
            ListString copy = new ListString(string); // делаем копию вставляемого, если не пустой
            INSERT(A, copy); //INSERT (whoIndex, NewString)
        }
    }

    //вставить в строку в позицию index строку String (разбить блок на два по позиции index и строку вставить между этими блоками)
    public void insert(int index, String string) {
        if (head == null)
            throw new Exception(index); // пытаемся сделать что-то с пустой строкой -> исключение
        if (string.length() != 0) { // проверить, что вставляемый ListString не пустой
            if (index < 0)
                throw new Exception(index); // index<0 -> исключение
            Position A = whoIndex(index);
            if (A.Index == -1)
                throw new Exception(index); // если найденная строка пустая(whoIndex) -> исключение
            ListString copy = new ListString(string); // делаем копию вставляемого, если не пустой
            INSERT(A, copy); //INSERT (whoIndex, NewString)
        }
    }

    // общий метод для insert: разбивает блоки или вставляем между готовыми
    private void INSERT(Position A, ListString string) {
        if (A.Index == 0 && A.Item == null) { // если вставляем в начало
            string.getLast().next = head; // у последнего вставляемого блока меняем ссылку на первый изначальный
            head = string.head; // меняем head
        } else {
            if (A.Index == 0) { // если вставляем между блоками или в конец
                string.getLast().next = A.Item.next; //у последнего вставляемого меняем ссылку на перед которым вставляем
                A.Item.next = string.head; // после которого вставляем меняем ссылку на первый вставляемый
            } else {
                A.Item = (A.Item == null ? head : A.Item.next); //если в блок
                A.Item.split(A.Index); //разделяем блок по индексу через метод split
                string.getLast().next = A.Item.next; // в первую часть изначального блока добавляем ссылку на начало вставляемого
                A.Item.next = string.head; // в конец вставляемого ссылку на начало второй части блока
            }
        }
    }

    //Строковое представление объекта ListString (переопределить метод)
    public String toString() {
        if (head == null) //если пустой ListString
            return ""; //возвращаем пустую строку
        char[] txt = new char[length()]; //создаем массив по размеру ListString
        int status = 0; //status - счетчик вставленных символов
        StringItem cur = head; //cur - текущий (рассматриваемый) айтем
        while (cur != null) { //в цикле копируем элементы в массив
            copAr(cur.symbols, txt, 0, cur.size, status);
            status += cur.size; //увеличиваем индекс, в который вставляем
            cur = cur.next; //переходим к следующему блоку
        }
        return String.valueOf(txt); //возвращаем строку, сделанную из массива встроенным методом
    }

    //метод для поиска суммы хэша по начальной позиции и заданной длине поиска
    private int hash(Position a, int length) { //a-начальная позиция, length-количество символов для сравнения
        int sumHash = 0; // sumHash - искомая сумма хэшей
        Position cur = new Position(a.Index, head); // cur - положение старта
        //if (a.Item != null) // если null, то мы в настоящем head, поэтому там остаемся
        //    cur.Item = a.Item.next; //иначе переходим к следующему блоку, так как a.Item указывает на предыдущий, а не в котором находимся
        cur.Item = (a.Item == null ? head : a.Item.next); //
        for (int i = 0; i < length; i++) { //перебираем символы, пока не найдем для нужной длины
            if (cur.Item == null) //если дошли до последнего айтема и не набрали нужной длины
                return -1; // возвращаем -1
            sumHash += cur.Item.symbols[cur.Index]; // прибавляем значение хэша символа
            if (cur.Index == cur.Item.size - 1) { //проверяем, последний ли символ был в айтеме
                cur.Index = 0; //если последний, меняем индекс на 0
                cur.Item = cur.Item.next; // и переходим к следующему айтему
            } else
                cur.Index++; //если символ не последний, переходим к следующему в этом же блоке
        }
        return sumHash; // возвращаем сумму
    }

    //метод для посимвольного сравнения подстроки, начина с элемента a, со строкой string
    private boolean symbolic(Position a, ListString newstring) {
        Position cur = new Position(0, null); //cur - копия начала для сравнения
        cur.Index = a.Index; //начало индекса сравнения
        cur.Item = (a.Item == null ? head : a.Item.next); //начальный айтем
        Position curStr = new Position(0, newstring.head); //curStr - копия сравниваемой строки
        while (curStr.Item != null) { // сравниваем элементы, пока не дойдем до конца ListString
            if (curStr.Item.symbols[curStr.Index] != cur.Item.symbols[cur.Index])
                return false; //если символы оказались разными, возвращаем false
            if (cur.Index == cur.Item.size - 1) { //проверяем, последний ли символ был в айтеме
                cur.Index = 0; //если последний, меняем индекс на 0
                cur.Item = cur.Item.next; // и переходим к следующему айтему
            }
            else
                cur.Index++; //если символ не последний, переходим к следующему в этом же блоке
            if (curStr.Index == curStr.Item.size - 1) { //проверяем, последний ли символ был в айтеме
                curStr.Index = 0; //если последний, меняем индекс на 0
                curStr.Item = curStr.Item.next; // и переходим к следующему айтему
            } else
                curStr.Index++; //если символ не последний, переходим к следующему в этом же блоке
        }
        return true; //если еще не вышли из метода, значит все элементы совпали и возвращает true
    }

    //метод для удаления символов от start до end (могли запоминать концы и потом перебрасывать ссылки при вставлении, но тогда появлялись бы случаи с запоминанием концов, которые находятся в разных блоках, head, в начале или конце)
    private void remove(Position start, Position end) {
        StringItem startItem = (start.Item == null ? head : start.Item.next); //копируем настоящий айтем старта
        StringItem endItem = (end.Item == null ? head : end.Item.next); //копируем настоящий айтем конца
        if (start.Item == end.Item) { //если подстрока в одном блоке
            int sizeItem = endItem.size; //sizeItem - размер блока
            if (start.Index == 0 && end.Index == sizeItem - 1) { //если вырезаем целый блок
                if (startItem == head)  //если это голова
                    head = head.next; //голова теперь в следующем блоке
                else  //если не голова
                    start.Item.next = startItem.next; //в ссылку предыдущего блока записывается следующий блок
            }
            else { //если вырезаем не целый блок
                copAr(startItem.symbols, startItem.symbols, end.Index + 1, startItem.size, start.Index);//копируем элементы блока от end в начало блока
                startItem.size -= (byte) (end.Index - start.Index + 1); //уменьшаем размер блока на удаленную часть
            }
        }
        else { //если start и end в разных блоках
            copAr(endItem.symbols, endItem.symbols, end.Index + 1, endItem.size, 0); //копируем все после end последнего блока в начало блока
            endItem.size -= (byte) (end.Index + 1); //обновляем размер последнего блока
            if (endItem.size == 0) //если после изменения кусок без хвоста, начинающегося end, стал пустым
                endItem = endItem.next; //переносим конец в следующий блок
            startItem.size = (byte) start.Index; //обрезаем длину начального блока до значения start
            if (startItem.size == 0) { //если блок от начала до start стал пустым
                if (start.Item == null)  //если блок был головой
                    head = endItem; //переносим голову
                else  //если не голова
                    start.Item.next = endItem; //в ссылку прошлого от старта бока записываем блок конца
            }
            else //если в блоке остались символы
                startItem.next = endItem; //в ссылку прошлого от старта бока записываем блок конца
        }
    }

    //публичные методы, ссылающиеся на REPLACE
    public void replace(ListString string, ListString newstring0) {
        ListString newstring = new ListString(newstring0); //newstring-копия newstring0
        REPLACE(string, newstring);
    }

    //публичные методы, ссылающиеся на REPLACE
    public void replace(ListString string, String newstring0) {
        ListString newstring = new ListString(newstring0); //newstring-копия newstring0
        REPLACE(string, newstring);
    }

    //собственный приватный метод для замены строки на новую
    private void REPLACE(ListString string, ListString newstring) {
        int len = string.length(); // len - длина строки
        if (len != 0 && length() >= len) { //проверка, что заменяем не пустую строку и что она не больше всего ListString
            Position start = new Position(0, null);//start и end - границы подстроки, start изначально берем как начало листа
            Position end = whoIndex(len - 1); //end по длине искомой строки
            Position lastStart = null;
            Position lastEnd = null; //lastStart и lastEnd - последние сохраненные границы подстроки
            int sumHash = string.hash(new Position(0, null), len); //sumHash - сумма хэша string
            int newHash = hash(start, len); //newHash - сумма хэша первых len элементов в листе
            StringItem startItem = (start.Item == null ? head : start.Item.next); //копируем настоящий айтем старта
            StringItem endItem = (end.Item == null ? head : end.Item.next); //копируем настоящий айтем конца
            while (endItem != null) { //идем до конца
                if (sumHash == newHash && symbolic(start, string)) { // если нашли такую же подстроку
                    lastStart = new Position(start.Index, start.Item); // обновили последний старт
                    lastEnd = new Position(end.Index, end.Item); // обновили последний конец
                }
                newHash -= startItem.symbols[start.Index]; //вычитаем из суммы хэшей первое значение хэша
                if (start.Index == startItem.size - 1) { //если символ был последним в блоке старта
                    start.Index = 0; //переходим в другой блок
                    start.Item = startItem;
                    startItem = startItem.next;
                } else
                    start.Index++; //если символ не последний, переходим к следующему в этом же блоке
                if (end.Index == endItem.size - 1) { //если символ был последним в блоке конца
                    end.Index = 0; //переходим в другой блок
                    end.Item = endItem;
                    endItem = endItem.next;
                } else
                    end.Index++; //если символ не последний, переходим к следующему в этом же блоке
                if (endItem != null) //если не вышли за границы, прибавляем значение хэша последнего символа
                    newHash += endItem.symbols[end.Index];
            }
            if (lastStart != null) { //когда дошли до конца и последний старт не null
                Position lastPos = new Position(lastStart.Index, lastStart.Item);
                remove(lastStart, lastEnd); //удаляем по позициям, которые надо удалить
                if (newstring.length() != 0) //если вставляемая строка не пустая
                    INSERT(lastPos, newstring); //вставляем по позиции
            }
        }
    }
}