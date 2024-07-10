// Пятая лабораторная работа
// Специальная операция DoublingWords
public class ListString { // Класс строки
    private static class StringItem {
        //отладочный метод для вывода блока
        private final static byte SIZE = 16; // Размер массива в блоке
        private final char [] symbols; // Массив символов
        private StringItem next; // Ссылка на следующий блок (объект, айтем)
        private byte size; // Количество символов в одном блоке
        private StringItem() { // Конструктор по умолчанию, в котором изначально все значения равны нулю
            symbols = new char[SIZE];
            next = null;
            size = 0;
        }
        private StringItem(StringItem A) { // Конструктор, копирующий целый блок
            size = A.size; // Размер скопированного блока
            symbols = new char[SIZE];
            arrayCopy(A.symbols, symbols, 0, A.size, 0); // Копирование через метод ArrayCopy
            next = null;
        }
        private void split(int index) { // Вспомогательный метод для разрезания одного блока на два по индексу
            StringItem B = new StringItem(); // Создание пустого блока
            B.size = (byte)(this.size - index); // Размером копии будет разница size и индекса
            arrayCopy(this.symbols, B.symbols, index, this.size, 0); // Копирование символов от индекса до size из первоначального блока
            this.size = (byte)(index); // Новый уменьшенный size для первоначального блока
            B.next = this.next; // Ссылка из первоначального блока становится ссылкой нового блока
            this.next = B; // Первоначальный блок теперь ссылается на новый
        }
        private int join() { // Вспомогательный метод для объединения блоков в один
            StringItem Next = this.next;
            while(Next != null && this.size + Next.size <= 16) { // Выполнять пока сумма длин текущего блока и следующего блока не станет больше 16 или пока строка не кончилась
                arrayCopy(Next.symbols, this.symbols, 0, Next.size, this.size); // Копирование элементов из следующего блока в текущий
                this.size += Next.size; // Увеличение размера текущего блока
                this.next = Next.next; // Перенос ссылки из следующего в текущий
                Next = Next.next;
            }
            return this.size;
        }
    }
    private static class TruePosition { // Вспомогательный класс, необходимый для передачи точного местоположения символа
        private int Index;
        private StringItem Item;
        private TruePosition(int A, StringItem B) {
            Index = A;
            Item = B;
        }
    }
    private StringItem head; // Начало ListString
    public ListString() { // Конструктор по умолчанию
        head = null;
    }
    public ListString(String string) { // Конструктор строки
        int length = string.length();
        if (length == 0) { // Если строка пустая, то и начало пустое
            head = null;
        }
        else { // Иначе из строки с помощью getChars() разбиваем на блоки символьных массивов
            int length2 = Math.min(length, 16);
            head = new StringItem(); // Создание начального блока заново
            string.getChars(0, length2, head.symbols, 0); // Перенос первых 16 символов в блок
            head.size = (byte)length2;
            StringItem current = head; // Текущим блоком становится начальный блок
            for (int i = 16; i < length; i+= 16){ // Дальше блоки будут создаваться в цикле, где i - индекс символа, от которого нужно начинать копировать символы
                StringItem newItem = new StringItem(); // Создание нового блока
                length2 = Math.min(length - i, 16); // Если до конца строки осталось меньше 16 символов, то копируем до конца строки, иначе копируем 16
                string.getChars(i, i + length2, newItem.symbols, 0);
                newItem.size = (byte)length2; // Размер
                current.next = newItem; // Добавление нового блока в лист
                current = newItem; // Обновление переменной последнего блока в листе
            }
        }
    }
    public ListString(ListString string) { // Копирующий конструктор
        if (string.head == null) // Если строка пустая, то начало пустое
            head = null;
        else { // Иначе идём по всем блокам и копируем через копирующий конструктор
            head = new StringItem(string.head); // Копирование начального блок
            StringItem CurrentInString = string.head.next; // Переменная для ссылки на блок, который будет скопирован из строки
            StringItem CurrentInList = head; // Переменная для ссылки на последний блок в новом листе
            while (CurrentInString != null) {
                StringItem CurrentCopy = new StringItem(CurrentInString); // Копия CurrentInString
                CurrentInList.next = CurrentCopy; // Добавление копии в новый лист
                CurrentInList = CurrentCopy; // Обновление позиции последнего блока в листе
                CurrentInString = CurrentInString.next; // Обновление позиции последнего скопированного из строки блока
            }
        }
    }
    private TruePosition existOnIndex(int index) { // Вспомогательный метод, проверяет существование элемента по индексу
        StringItem Previous = null; // Ссылка на предыдущий
        StringItem Current = head; // Ссылка на текущий
        while (Current != null) {
            if (index < Current.size) // Символ в этом блоке и можно вернуть TruePosition, если длина блока больше, чем индекс нужного символа
                return new TruePosition(index, Previous);
            else { // Иначе уменьшаем index на длину данного блока
                index -= Current.size;
                Previous = Current; // Обновление позиции текущего блока
                Current = Current.next;
            }
        }
        return new TruePosition(-1, null); // Если весь лист проверен, а index не кончился, то символ за длиной листа и возвращаем TruePostion с отрицательным индексом
        // В методах, от которых, вызывался данный метод будут выбрасывать исключения в зависимости от индекса
    }
    private static void arrayCopy(char[] from, char[] to, int start_index, int finish_index, int copy_start) { // Метод, копирующий символы из одного массива в другой, где from - массив, откуда копируем, to - массив куда, start_index и finish_index - границы копирования, copy_start - индекс начала вставки
        for (int i = 0; i < finish_index - start_index; i++)
            to[copy_start + i] = from[start_index + i];
    }
    public int length() { // Длина введенной строки
        if (head == null) // Если head = null, то возвращаем 0
            return 0;
        else { // Иначе цикл по блокам строки, вызываем join для каждого, суммируем возвращаемые от этого метода длины
            int len = 0;
            StringItem cur = head; // Переменная для ссылки на текущий блок
            while (cur != null){
                len += cur.join();
                cur = cur.next; // Обновление позиции текущего блока
            }
            return len;
        }
    }
    private StringItem last() { // Вспомогательный приватный метод, возвращающий последний блок в листе
        StringItem Previous = null;
        StringItem Next = head;
        while (Next != null) {
            Previous = Next;
            Next = Next.next;
        }
        return Previous;
    }
    private ListString SUBSTRING(TruePosition Start, TruePosition Finish) { // Вспомогательный метод, вырезающий подстроку по TruePosition
        if (Finish.Index == -1) {
            StringItem Last = this.last();
            Finish = new TruePosition(Last.size, Last);
        }
        else Finish.Item = (Finish.Item == null ? head : Finish.Item.next); // В этом методе не нужны прошлые блоки для стартового и финишного, поэтому сдвинем стартовый и финишный блоки
        Start.Item = (Start.Item == null ? head : Start.Item.next);
        ListString newList = new ListString(); // Создаём новый пустой ListString
        if (Start.Index != Finish.Index || Start.Item != Finish.Item) { // Случай, когда start = finish, тогда возвращаем пустой лист
            newList.head = new StringItem();
            if (Start.Item == Finish.Item) { // Случай, когда start и finish в одном блоке, тогда возвращаем лист из одного блока с копиями символов через ArrayCopy
                arrayCopy(Start.Item.symbols, newList.head.symbols, Start.Index, Finish.Index, 0);
                newList.head.size = (byte) (Finish.Index - Start.Index);
            }
            else {  // Иначе создаем копию первого блока
                arrayCopy(Start.Item.symbols, newList.head.symbols, Start.Index, Start.Item.size, 0);
                newList.head.size = (byte) (Start.Item.size-Start.Index);
                // Идем от стартового до финишного блока, добавляем несколько копий целых блоков (если они есть)
                StringItem currentInOriginalList = Start.Item.next; // Ссылка на текущий блок для копирования в оригинальном листе
                StringItem currentInCopy = newList.head; // Ссылка на последний блок в копии
                while (currentInOriginalList != Finish.Item){ // Последний блок копируем вручную, поэтому идём в while до него
                    currentInCopy.next = new StringItem(currentInOriginalList); // Создание нового блока через копирующий конструктор
                    currentInCopy = currentInCopy.next; // Обновили позиции текущих блоке
                    currentInOriginalList = currentInOriginalList.next;
                }
                StringItem Last = new StringItem(); // Создаём копию последнего блока
                arrayCopy(Finish.Item.symbols, Last.symbols, 0, Finish.Index, 0);
                Last.size = (byte) (Finish.Index);
                currentInCopy.next = Last; // Добавляем копию последнего блока
            }
        }
        // Возвращаем результат
        return newList;
    }
    public char charAt(int index) { // Вернуть символ в строке в позиции index
        if (index < 0) // Получаем объект от existOnIndex
            throw new ExceptionOutOfBounds(index);
        TruePosition X = existOnIndex(index);
        if (X.Index == -1)
            throw new ExceptionOutOfBounds(index);
        return (X.Item == null ? head : X.Item.next).symbols[X.Index]; // Если не исключение, возвращаем символ по блоку и индексу
    }
    public void setCharAt(int index, char ch) { // Заменить в строке символ в позиции index на символ ch
        if (index < 0) // Получаем объект от existOnIndex, если не исключение, то заменяем символ на нужный
            throw new ExceptionOutOfBounds(index);
        TruePosition X = existOnIndex(index);
        if (X.Index == -1)
            throw new ExceptionOutOfBounds(index);
        (X.Item == null ? head : X.Item.next).symbols[X.Index] = ch;
    }
    public ListString substring(int start, int finish){ // Взятие подстроки, от start до end, не включая end, возвращается новый объект ListString, исходный не изменяется, если end не существующая позиция, то возвращается подстрока от start до конца строки
        int maximum = Math.max(start,finish); // Если start > finish, то меняем их местами
        start = Math.min(start,finish);
        finish = maximum;
        if (start < 0) // Через existOnIndex проверяем наличие старта
            throw new ExceptionOutOfBounds(start);
        TruePosition Start = existOnIndex(start);
        if (Start.Index == -1)
            throw new ExceptionOutOfBounds(start);
        TruePosition Finish = existOnIndex(finish); // Через existOnIndex проверяем наличие финиша, если финиша нет, то записываем в финиш последний символ последнего блока
        return SUBSTRING(Start, Finish);
    }
    public void append(char ch) { // Добавить в конец строки символ (в конец символьного массива последнего блока, если там есть свободное место, иначе в начало символьного массива нового блока)
        if (head == null) { // Если начальный блок пустой, то переопределяем его, то есть создаем пустой блок и добавляем в него char
            head = new StringItem();
            head.symbols[0] = ch;
            head.size++;
        }
        else { // Иначе вызываем last, если он не заполнен, то добавляем туда
            StringItem Last = this.last();
            if (Last.size < 16){
                Last.symbols[Last.size] = ch;
                Last.size++;
            }
            else {
                StringItem newItem = new StringItem(); // Если блок уже переполнен, то создаем пустой блок и добавляем в него char
                newItem.symbols[0] = ch;
                newItem.size++;
                Last.next = newItem; // Присвоение ссылку на него последнему блоку
            }
        }
    }
    public void append(ListString string) { // Добавить в конец строку ListString (перекинуть указатель на следующий последнего блока исходной строки на голову добавляемой строки)
        if (string.head != null) { // Проверяем что строка не пустая, чтобы не пытаться добавить пустую строку
            ListString newList = new ListString(string); // Через копирующий конструктор делаем копию добавляемого лист стринг
            if (head == null) // Если начальный блок пустой, то переопределяем его
                head = newList.head;
            else { // Иначе head нового листа добавляем в last
                this.last().next = newList.head;
            }
        }
    }
    public void append(String string) { // Добавить в конец строку String (перекинуть указатель на следующий последнего блока исходной строки на голову добавляемой строки)
        if (!string.isEmpty()) { // Если string пустой, то не делаем ничего
            ListString newList = new ListString(string); // Если не пустой, то делаем из него лист стринг конструктором
            if (head == null) // Если начальный блок пустой, то переопределяем его
                head = newList.head;
            else { // Иначе head нового листа добавляем в last
                this.last().next = newList.head;
            }
        }
    }
    public void insert(int index, ListString string) { //  Вставить в строку в позицию index строку ListString (разбить блок на два по позиции index и строку вставить между этими блоками)
        if (string.head != null) { // Проверяем, что вставляемый лист не пустой
            if (index < 0) // Получаем объект из existOnIndex
                throw new ExceptionOutOfBounds(index);
            TruePosition Index = existOnIndex(index);
            if (Index.Index == -1)
                throw new ExceptionOutOfBounds(index);
            ListString Copy = new ListString(string); // Создаем копию string через конструктор
            INSERT(Index, Copy);
        }
    }
    public void insert(int index, String string) { // Вставить в строку в позицию index строку String (разбить блок на два по позиции index и строку вставить между этими блоками)
        if (!string.isEmpty()) { //  Если вставляемая строка не пустая
            if (index < 0) // existOnIndex
                throw new ExceptionOutOfBounds(index);
            TruePosition Index = existOnIndex(index);
            if (Index.Index == -1)
                throw new ExceptionOutOfBounds(index);
            ListString Copy = new ListString(string); // Создаем ListString из string через конструктор
            INSERT(Index, Copy);
        }
    }
    private void INSERT(TruePosition Position, ListString string) { // Вспомогательный метод, являющийся общим концом для всех insert
        if (Position.Index == 0 && Position.Item == null) { // Случай, когда нужно вставить в начало строки, значит в последний блок string записываем ссылку на лист, в который вставляли и head переставляем на head string
            string.last().next = head;
            head = string.head;
        }
        else { // Случай, когда нужно вставить между блоками
            if (Position.Index == 0) {
                string.last().next = Position.Item.next; // Конец строки ссылается на блок, в нулевой индекс которого хотим вставить
                Position.Item.next = string.head; // Предыдущий блок ссылается на начало строки
            }
            else { // Случай, когда нужно вставить в центр блока: разделить блок по индексу через split
                Position.Item = (Position.Item == null ? head : Position.Item.next); // Меняем блок на настоящий
                Position.Item.split(Position.Index);
                string.last().next = Position.Item.next; // Конец листа ссылается на вторую половину разрезанного блока
                Position.Item.next = string.head; // Первая половина разрезанного блока ссылается на начало вставляемого листа
            }
        }
    }
    public String toString() { // Строковое представление объекта ListString (переопределить метод)
        if (head == null) // Если лист пустой, то возвращаем null
            return "";
        char[] chars = new char[this.length()]; // Массив char для всех символов из листа
        int lastposition = 0; // Вспомогательный индекс для вставки в массив chars
        StringItem current = head;
        while (current != null) { // Пока строка не закончилась, походим по всему ListString и собираем в него все char
            arrayCopy(current.symbols, chars, 0, current.size, lastposition); //
            lastposition += current.size; // Обновление индекс вставки
            current = current.next; // Обновление текущий блок
        }
        return String.valueOf(chars); // Встроенным в java методом создаем из char строку и ее же возвращаем
    }
    public void doublingWords() { // Собственный метод: дублирование всех слов в объекте. Слово – последовательность символов между пробелами. Дубль слова помещаются непосредственно за словом (без пробела). Пробелы не дублируются
        TruePosition Start = new TruePosition(0,null); // Создаём позиции старта и финиша
        TruePosition Finish = new TruePosition(0,head);
        while (wordFinder(Start, Finish)) { // Пока есть слова в строке
            ListString copy = SUBSTRING(Start, new TruePosition(Finish.Index, Finish.Item)); // Делаем копию слова, так как substring изменяет TruePosition
            StringItem Last = copy.last(); // Границы слова. Последний блок во вставляемой строке - это блок перед новым стартом
            INSERT(new TruePosition(Finish.Index, Finish.Item), copy); // Вставка копии слова в строку
            Start.Item = Last; // Переопределение старта
            Start.Index = 0;
            Finish.Item = Last.next; // В финиш записываем настоящий старт
            Finish.Index = 0;
        }
    }
    // От старта надо двигаться, а финиш хранит настоящий старт, который мы впишем в RealStart
    private boolean wordFinder (TruePosition Start, TruePosition Finish) { // Вспомогательный метод, ищущий границы слов
        TruePosition RealStart = new TruePosition(Finish.Index,Finish.Item); // Настоящий старт
        if (RealStart.Item == null) // Если строка закончилась, то выходим из цикла
            return false;
        while(RealStart.Item.symbols[RealStart.Index] == ' ') { // В первом блоке старт будет в первом символе (не пробеле)
            if (RealStart.Index == RealStart.Item.size-1){ // Если это последний символ в блоке, то идём в следующий
                RealStart.Item = RealStart.Item.next;
                if (RealStart.Item == null) // Если блок закончился, то выходим
                    return false;
                Start.Item = (Start.Item == null ? head : Start.Item.next);
                RealStart.Index = 0;
            }
            else // Если не последний, то просто увеличиваем индекс
                RealStart.Index++;
        }
        Start.Index = RealStart.Index; // Присваиваем старту нужный индекс
        Finish.Item = Start.Item; // Финиш нужно считать от старта
        TruePosition RealFinish = new TruePosition(RealStart.Index, RealStart.Item); // Реальный финиш от реального старта
        while(RealFinish.Item.symbols[RealFinish.Index] != ' '){ // Финиш - это последний элемент символ (не пробел)
            if (RealFinish.Index == RealFinish.Item.size-1){ // Если это последний символ в блоке, то смотрим следующий
                StringItem Next = RealFinish.Item.next;
                if (Next == null && RealFinish.Item.symbols[RealFinish.Index] != ' ') { // если дошли до конца и в конце нет пробела

                    Finish.Index = RealFinish.Index+1; // Присваиваем финишу нужный индекс
                    ListString copy = SUBSTRING(Start, Finish); // вырезаем слово
                    this.last().next = copy.head; // вставляем копию в конец
                    return false; // слов больше не будет, поэтому возвращаем неправду

                }
                RealFinish.Item = RealFinish.Item.next;
                if(RealFinish.Item == null) {
                    return false;
                }
                Finish.Item = (Finish.Item == null ? head : Finish.Item.next);
                RealFinish.Index = 0;
            }
            else { // Иначе увеличиваем индекс
                RealFinish.Index++;
            }
        }
        Finish.Index = RealFinish.Index; // Присваиваем финишу нужный индекс
        return true; // Определили слово -> возвращаем true
    }
}