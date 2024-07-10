//Реализовать класс строки
//Полный набор операций + дополнительную операцию delete
public class ListString{
    private static class StringItem{
        private final static byte SIZE = 16; //размер символьного массива в блоке
        private char [] symbols; //массив будет хранить 16 символов
        private StringItem next; //ссылка на следующий айтем
        private byte size; //реальное количество символов в айтеме

        //Конструктор по умолчанию
        private StringItem(){
            // по умолчанию зануляем всё
            symbols = new char[SIZE];
            next = null;
            size = 0;
        }

        //Конструктор, копирующий целый блок
        private StringItem(StringItem A){
            //1. задаём размер копии
            size = A.size;
            //2. целиком копируем А через ArrayCopy
            symbols = new char[SIZE];
            ArrayCopy(A.symbols, symbols, 0, A.size, 0);
            next = null;
        }

        //Вспомогательный метод для разрезания одного блока на два по индексу
        private void split(int index){
            //1. Создаём пустой айтем
            StringItem B = new StringItem();
            //2. Задаём размер копии
            B.size = (byte)(this.size - index);
            //3. Копируем в него символы от индекса до size из айтема, на котором вызвали
            ArrayCopy(this.symbols, B.symbols, index, this.size, 0);
            //4. Обрезаем size айтема, на котором вызвали
            this.size = (byte)(index);
            //5. Ссылку из исходного айтема пишем в новый, а в исходный пишем ссылку на новый
            B.next = this.next;
            this.next = B;
        }

        //Вспомогательный метод для собирания нескольких блоков в один
        private int join(){
            //1. пока сумма длин следующего айтема с текущим не больше 16 или строка не кончилась
            StringItem Next = this.next;
            while(Next != null && this.size + Next.size <= 16){
                //2. переносим элементы из следующего в текущий через ArrayCopy
                ArrayCopy(Next.symbols, this.symbols, 0, Next.size, this.size);
                //3. увеличивает размер текущего
                this.size += Next.size;
                //4. переносим ссылку из следующего в текущий
                this.next = Next.next;
                Next = Next.next;
            }
            //5. возвращаем длину текущего элемента после всех сжатий
            return this.size;
        }

    }

    //вспомогательный класс, необходимый для передачи точного местоположения символа
    private static class TruePosition{
        private int Index;
        private StringItem Item;
        private TruePosition(int A, StringItem B){
            Index = A;
            Item = B;
        }
    }

    private StringItem head; // начало ListString

    //Конструктор по умолчанию
    public ListString(){
        head = null;
    }

    //Конструктор из строки
    public ListString(String string){
        int strlen = string.length();
        //1. если стринг пустой, то head = null
        if (strlen == 0)
            head = null;
        //2. иначе из стринга методом getChars() вырезаем массивы по 16 символов и делаем из них айтемы, пока не дойдём до конца
        else{
            //3. создаём голову сначала
            int copylen; // вспомогательная переменная для getChars
            copylen = Math.min(strlen, 16);
            head = new StringItem();
            string.getChars(0, copylen, head.symbols, 0); // в голову помещаем первые 16 символов или всю строку, если она короче 16 символов
            head.size = (byte)copylen;
            StringItem current = head; // текущим айтемом становится голова
            //4. все последующий айтемы создаются в цикле
            for (int i = 16; i < strlen; i+= 16){ // i - индекс символа, от которого мы начинаем копировать символы
                StringItem newItem = new StringItem(); // создаём новый айтем
                copylen = Math.min(strlen - i, 16); // если до конца строки осталось меньше 16 символов, то копируем до конца строки, иначе копируем 16
                string.getChars(i, i + copylen, newItem.symbols, 0);
                newItem.size = (byte)copylen; // задаём размер
                current.next = newItem; // добавляем новый айтем в лист
                current = newItem; // обновляем переменную, отвечающую за последний айтем в листе
            }
        }
    }

    //Копирующий конструктор
    public ListString(ListString string){
        //1. если стринг пустой, то новый head = null
        if (string.head == null)
            head = null;
        //2. иначе идём по всем айтемам и копируем через копирующий конструктор айтема
        else{
            head = new StringItem(string.head); // копируем голову
            StringItem CurrentInString = string.head.next; // переменная для ссылки на айтем, который будет скопирован из string
            StringItem CurrentInList = head; // переменная для ссылки на последний айтем в новом листе
            while (CurrentInString != null){
                StringItem CurrentCopy = new StringItem(CurrentInString); // делаем копию CurrentInString
                CurrentInList.next = CurrentCopy; // добавляем копию в новый лист
                CurrentInList = CurrentCopy; // обновляем позицию последнего айтема в листе
                CurrentInString = CurrentInString.next; // обновляем позицию последнего скопированного из string айтема
            }
        }
    }

    //Метод, копирующий символы из одного массива в другой
    private static void ArrayCopy(char[] from, char[] to, int start_index, int finish_index, int copy_start){
        //from - массив, откуда копируем, to - массив куда, start_index и finish_index - границы копирования
        //copy_start - индекс начала вставки
        for (int i = 0; i < finish_index - start_index; i++)
            to[copy_start + i] = from[start_index + i];
    }

    //реальная длина строки
    public int length(){
        //1. если head = null, то возвращаем 0
        if (head == null)
            return 0;
        //2. идём по всем StringItem строки, вызываем на них join и суммируем возвращаемые результаты
        else{
            int len = 0; // переменная для длины строки
            StringItem current = head; // переменная для ссылки на текущий айтем
            while (current != null){ // пока текущий айтем не null
                len += current.join(); // вызываем join, собираем все маленькие блоки в один большой и прибавляем его длину в общую сумму
                current = current.next; // обновляем позицию текущего айтема
            }
            //3. возвращаем длину
            return len;
        }
    }

    //вспомогательный приватный метод для понимания, существует ли элемент по индексу
    //если элемент существует, то возвращем предыдущий для него блок и индекс из нужного
    private TruePosition isAt(int index){
        //1. идём по блокам, пока не найдём нужный или не дойдём до конца
        StringItem Current = null, Next = head; // ссылка на предыдущий блок от нужного и на нужный блок
        while (Next != null){
            //2. если длина айтема больше, чем индекс искомого символа, то элемент в этом айтеме и возвращаем TruePosition
            if (index < Next.size)
                return new TruePosition(index, Current);
            else{
                // иначе уменьшаем index на длину блока, который мы смотрели только что
                index -= Next.size;
                Current = Next; // обновляем позицию текущего блока
                Next = Next.next;
            }
        }
        //3. если прошли весь лист, а index не кончился, то символ за длиной листа и возвращаем TruePostion с отрицательным индексом
        return new TruePosition(-1, null);
        //4. методы, вызывавшие isAt будут выбрасывать исключения в зависимости от индекса
    }

    //вспомогательный приватный метод, возвращающий последний айтем в листе
    private StringItem last(){
        StringItem Preview = null, Next = head;
        //идём до последнего метода и возвращаем его
        while (Next != null) {
            Preview = Next;
            Next = Next.next;
        }
        return Preview;
    }

    //вернуть символ в строке в позиции index
    public char charAt(int index){
        //1. получаем объект от isAt
        if (index < 0)
            throw new MyException(index);
        TruePosition A = isAt(index);
        if (A.Index == -1)
            throw new MyException(index);
        //2. если не исключение, возвращаем символ по блоку и индексу
        return (A.Item == null ? head : A.Item.next).symbols[A.Index];
    }

    //Заменить в строке символ в позиции index на символ ch
    public void setCharAt(int index, char ch){
        //1. получаем объект от isAt
        if (index < 0)
            throw new MyException(index);
        TruePosition A = isAt(index);
        if (A.Index == -1)
            throw new MyException(index);
        //2. если не исключение, то заменяем символ на нужный
        (A.Item == null ? head : A.Item.next).symbols[A.Index] = ch;
    }

    //взятие подстроки, от start до end, не включая end, возвращается новый объект ListString, исходный не изменяется.
    //Если end не существующая позиция, то возвращается подстрока от start до конца строки
    public ListString substring(int start, int finish){
        //1. если start > finish, то меняем их местами
        int maximum = Math.max(start,finish);
        start = Math.min(start,finish);
        finish = maximum;
        //2. через isAt проверяем наличие старта
        if (start < 0)
            throw new MyException(start);
        TruePosition Start = isAt(start);
        if (Start.Index == -1)
            throw new MyException(start);
        //3. через isAt проверяем наличие финиша, если финиша нет, то записываем в финиш последний символ последнего блока
        TruePosition Finish = isAt(finish);
        if (Finish.Index == -1) {
            StringItem Last = this.last();
            Finish = new TruePosition(Last.size-1, Last);
        }
        // в этом методе нам ни разу не нужны прошлые айтемы для стартового и финишного, поэтому сдвинем стартовый и финишный айтемы
        else Finish.Item = Finish.Item.next;
        Start.Item = (Start.Item == null ? head : Start.Item.next);
        //4. создаём новый пустой ListString
        ListString copyList = new ListString();
        //5. Рассматриваем случаи:
        //5.0 start = finish, тогда возвращаем пустой лист
        if (start != finish) {
            copyList.head = new StringItem();
            //5.1 start и finish в одном блоке, тогда возвращаем лист из одного блока с копиями символов через ArrayCopy
            if (Start.Item == Finish.Item){
                ArrayCopy(Start.Item.symbols, copyList.head.symbols, Start.Index, Finish.Index, 0);
                copyList.head.size = (byte) (finish - start);
            }
            //5.2 иначе делаем обрезанную(или целую) копию первого блока
            else{
                ArrayCopy(Start.Item.symbols, copyList.head.symbols, Start.Index, Start.Item.size, 0);
                copyList.head.size = (byte) (Start.Item.size-Start.Index);
                //идем от стартового до финишного блока, добавляем несколько копий целых блоков(если они есть)
                StringItem currentInOriginalList = Start.Item.next; // ссылка на текущий айтем для копирования в оригинальном листе
                StringItem currentInCopy = copyList.head; // ссылка на последний айтем в копии
                while (currentInOriginalList != Finish.Item){ // последний блок копируем вручную, поэтому идём в while до него
                    //создали новый блок через копирующий конструктор
                    //добавили новый блок в сабстринг
                    currentInCopy.next = new StringItem(currentInOriginalList);
                    //обновили позиции текущих айтемов
                    currentInCopy = currentInCopy.next;
                    currentInOriginalList = currentInOriginalList.next;
                }
                //создаём обрезанную(или целую) копию последнего блока
                StringItem Last = new StringItem();
                ArrayCopy(Finish.Item.symbols, Last.symbols, 0, Finish.Index, 0);
                Last.size = (byte) (Finish.Index);
                //добавляем копию последнего блока
                currentInCopy.next = Last;
            }
        }
        // возвращаем результат
        return copyList;
    }

    //добавить в конец строки символ (в конец символьного массива последнего блока, если там есть свободное место, иначе в начало символьного массива нового блока)
    public void append(char ch){
        //1. если head = null, то переопределяем head, для этого делаем пустой айтем и кладём в него char
        if (head == null){
            head = new StringItem();
            head.symbols[0] = ch;
            head.size++;
        }
        else{
            //2. иначе вызываем last и смотрим его длину, если есть возможность, то добавляем туда
            StringItem Last = this.last();
            if (Last.size < 16){
                Last.symbols[Last.size] = ch;
                Last.size++;
            }
            else{
                //3. если возможности нет, то делаем пустой айтем
                StringItem newItem = new StringItem();
                // кладём в него char
                newItem.symbols[0] = ch;
                newItem.size++;
                // и записываем ссылку на него в последний айтем
                Last.next = newItem;
            }
        }
    }

    //добавить в конец строку ListString (перекинуть указатель на следующий последнего блока исходной строки на голову добавляемой строки)
    public void append(ListString string){
        //1. проверяем что строка не пустая, чтобы не пытаться добавить пустую строку
        if (string.head != null){
            //2. через копирующий конструктор делаем копию добавляемого лист стринг
            ListString newList = new ListString(string);
            //3. если head = null, то переопределяем head
            if (head == null)
                head = newList.head;
            //4. иначе голову нового листа добавляем в last
            else
                this.last().next = newList.head;
        }
    }

    //добавить в конец строку String (перекинуть указатель на следующий последнего блока исходной строки на голову добавляемой строки)
    public void append(String string){
        //1. если string пустой, то не делаем ничего
        if (!string.isEmpty()) {
            //2. если не пустой, то делаем из него лист стринг конструктором
            ListString newList = new ListString(string);
            //3. если head = null, то переопределяем head
            if (head == null)
                head = newList.head;
            //4. иначе голову нового листа добавляем в last
            else{
                this.last().next = newList.head;
            }
        }
    }

    //вставить в строку в позицию index строку ListString (разбить блок на два по позиции index и строку вставить между этими блоками)
    public void insert(int index, ListString string){
        //1. проверяем, что вставляемый лист не пустой
        if (string.head != null) {
            //2. получаем объект от isAt
            if (index < 0)
                throw new MyException(index);
            TruePosition Index = isAt(index);
            if (Index.Index == -1)
                throw new MyException(index);
            //3. делаем копию string через конструктор
            ListString Copy = new ListString(string);
            //4. переходим в общую концовку
            INSERT(Index, Copy);
        }
    }

    //вставить в строку в позицию index строку String (разбить блок на два по позиции index и строку вставить между этими блоками)
    public void insert(int index, String string){
        //1. проверяем, что вставляемая строка не пустая
        if (!string.isEmpty()) {
            //2. запускаем isAt
            if (index < 0)
                throw new MyException(index);
            TruePosition Index = isAt(index);
            if (Index.Index == -1)
                throw new MyException(index);
            //3. делаем ListString из string через конструктор
            ListString Copy = new ListString(string);
            //4. переходим в общую концовку
            INSERT(Index, Copy);
        }
    }

    //вспомогательный метод, являющийся общим концом для всех insert
    private void INSERT(TruePosition Position, ListString string){
        //1. Рассматриваем случаи
        //1.1 Хотим вставить в начало, значит в последний блок string записываем ссылку на лист, в который вставляли и head переставляем на head string
        if (Position.Index == 0 && Position.Item == null){
            string.last().next = head;
            head = string.head;
        }
        else{
            //1.2 Хотим вставить ровно между блоками
            if (Position.Index == 0){
                string.last().next = Position.Item.next; // конец строки ссылается на айтем, в нулевой индекс которого хотим вставить
                Position.Item.next = string.head; // предыдущий блок ссылается на начало строки
            }
            else{
                //1.3 Хотим вставить в центр блока, значит разделяем айтем по индексу через split
                Position.Item = (Position.Item == null ? head : Position.Item.next); // меняем айтем на настоящий
                Position.Item.split(Position.Index);
                //конец листа ссылается на вторую половину разрезанного блока
                string.last().next = Position.Item.next;
                //первая половина разрезанного блока ссылается на начало вставляемого листа
                Position.Item.next = string.head;
            }
        }
    }

    //Строковое представление объекта ListString (переопределить метод)
    public String toString(){
        //1. если лист пустой, то возвращаем null
        if (head == null)
            return "";
        //2. делаем массив char по длине ListString
        char[] AllSymb = new char[this.length()]; // массив для всех символов из листа
        int lastposition = 0; // вспомогательный индекс для вставки в массив AllSymb
        //3. проходим по всему ListString и собираем в него все char
        StringItem current = head;
        while (current != null){ // пока не дошли до конца
            ArrayCopy(current.symbols, AllSymb, 0, current.size, lastposition);
            lastposition += current.size; // обновляем индекс вставки
            current = current.next; // обновляем текущий блок
        }
        //4. встроенным в java методом делаем из массива char одну строку и возвращаем её
        return String.valueOf(AllSymb);
    }

    //Удаление всех вхождений строки string(ListString) из объекта, если строки string в объекте нет, то объект не изменяется
    public void delete(ListString string){
        int StringLen = string.length(); // длина строки
        if (StringLen != 0 && this.length() >= StringLen){
            // проверяем, что string не пустой и его длина не больше длины листа
            TruePosition Start = new TruePosition(0, null), Finish = isAt(StringLen-1); // границы будущей подстроки в листе
            // старт и финиш всегда хранят прошлый для себя блок
            int StringHash = string.hash(new TruePosition(0, null), StringLen); // сумма хэша string
            int ListHash = hash(Start, StringLen); // сумма хэша первых StringLen элементов в листе
            while(find(StringLen, StringHash, ListHash, string, Start, Finish)){ // если подстрока нашлась
                cutout(Start, Finish); // то вырезаем подстроку и выставляем новый Start
                ListHash = hash(Start, StringLen); // считаем сумму хэша следующих StringLen элементов в листе
                shift(Start, Finish, StringLen); // считаем новый финиш для подстроки
            }
        }
    }

    //вспомогательный метод для нахождения TruePosition символа через len от текущего
    private void shift(TruePosition Start, TruePosition Finish, int StringLen){
        StringItem Real_Start = (Start.Item == null ? head : Start.Item.next);
        // переменная, чтобы запомнить настоящий айтем старта, в TP Start и Finish останутся прошлые айтемы
        if (Real_Start != null) { // если мы стоим не в конце строки или не в пустой строке
            // если финиш в одном блоке со стартом
            if (Start.Index + StringLen <= Real_Start.size) {
                Finish.Item = Start.Item; // даём финишу тот же айтем
                Finish.Index = Start.Index + StringLen - 1; // считаем нужный индекс
            }
            else { // если в разных блоках, то
                // встаём в блок, находящийся после стартового
                Finish.Item = Real_Start;
                // убираем длину куска строки, находящегося в стартовом блоке
                StringLen -= Finish.Item.size - Start.Index + 1;
                // шагаем, пока в следующем айтеме не найдём финиш или не дойдём до конца
                StringItem Next = Finish.Item.next;
                while (Next != null && StringLen >= Next.size) {
                    StringLen -= Next.size;
                    Finish.Item = Next;
                    Next = Next.next;
                }
                Finish.Index = StringLen; // остаток StringLen и будет нужным индексом
            }
        }
    }

    //Вспомогательный метод для подсчёта хэша подстроки определённой длины от заданного старта
    private int hash(TruePosition Start, int StringLen){
        int HashSum = 0; // искомая сумма
        TruePosition Current = new TruePosition(Start.Index, head); // создаём копию старта
        if (Start.Item != null) // если он null, то останемся в head, иначе попадём в следующий
            Current.Item = Start.Item.next;
        for (int i = 0; i < StringLen; i++){
            if (Current.Item == null) // если мы дошли до конца строки, а i < StringLen, то возвращаем -1 вместо суммы
                return -1;
            HashSum += Current.Item.symbols[Current.Index]; // прибавляем хэш к сумме
            // обновляем положение текущего символа
            if (Current.Index == Current.Item.size - 1) {
                // если символ был последним в айтеме, то идём в следующий айтем
                Current.Index = 0;
                Current.Item = Current.Item.next;
            }
            else
                // иначе просто увеличиваем индекс
                Current.Index++;
        }
        return HashSum; // возвращаем результат
    }

    //Вспомогательный метод ищет вхождение по значению хэша
    private boolean find(int StringLen, int StringHash, int ListHash, ListString string, TruePosition Start, TruePosition Finish){
        StringItem Real_Start = (Start.Item == null ? head : Start.Item.next), Real_Finish = (Finish.Item == null ? head : Finish.Item.next);
        // переменные, чтобы запомнить настоящие айтемы старта и финиша, в TP Start и Finish останутся прошлые айтемы
        while(Real_Finish != null && ListHash != -1){ // пока не дошли до конца или пока ListHash не дошёл до конца без результата
            if (StringHash == ListHash && equal(Start, string))
                // если совпали хэши и посимвольное сравнение вернуло правду, то возвращаем правду
                return true;
            else { // иначе
                ListHash -= Real_Start.symbols[Start.Index]; // вычитаем из суммы хэш старта
                // сдвигаем позицию старта
                if (Start.Index == Real_Start.size - 1) {
                    // если символ был последним в айтеме, то идём в следующий айтем
                    Start.Index = 0;
                    Start.Item = Real_Start;
                    Real_Start = Real_Start.next;
                }
                else
                    // иначе просто увеличиваем индекс
                    Start.Index++;
                // сдвигаем позицию финиша
                if (Finish.Index == Real_Finish.size - 1) {
                    // если символ был последним в айтеме, то идём в следующий айтем
                    Finish.Index = 0;
                    Finish.Item = Real_Finish;
                    Real_Finish = Real_Finish.next;
                }
                else
                    // иначе просто увеличиваем индекс
                    Finish.Index++;
                // если финишем не вышли за границу строки, то прибавляем к сумме хэш финиша
                if (Real_Finish != null) {
                    ListHash += Real_Finish.symbols[Finish.Index];
                }
            }
        }
        // когда дошли до конца листа, возвращаем ложь
        return false;
    }

    //Вспомогательный метод для посимвольного сравнения подстроки объекта и строки
    private boolean equal(TruePosition Start, ListString string){
        //1. посимвольно сравниваем string и подстроку между стартом и финишем
        TruePosition CurrentInList = new TruePosition(0, null); // позиция текущего для сранения символа в листе
        CurrentInList.Index = Start.Index;
        CurrentInList.Item = (Start.Item == null ? head : Start.Item.next);
        TruePosition CurrentInString = new TruePosition(0, string.head); // позиция текущего для сранения символа в строке
        while (CurrentInString.Item != null){ // проходим StringLen символов в поисках противоречий
            if (CurrentInList.Item.symbols[CurrentInList.Index] != CurrentInString.Item.symbols[CurrentInString.Index])
                // если символ не совпал, то возвращаем ложь
                return false;
            // обновляем позицию текущего элемента в листе
            if (CurrentInList.Index == CurrentInList.Item.size - 1) {
                // если символ был последним в айтеме, то идём в следующий айтем
                CurrentInList.Index = 0;
                CurrentInList.Item = CurrentInList.Item.next;
            }
            else
                // иначе просто увеличиваем индекс
                CurrentInList.Index ++;
            // обновляем позиицию текущего элемента в строке
            if (CurrentInString.Index == CurrentInString.Item.size - 1) {
                // если символ был последним в айтеме, то идём в следующий айтем
                CurrentInString.Index = 0;
                CurrentInString.Item = CurrentInString.Item.next;
            }
            else
                // иначе просто увеличиваем индекс
                CurrentInString.Index ++;
        }
        // если ни разу до этого не вернули ложь, то противоречий нет и возвращаем правду
        return true;
    }

    //Вспомогательный метод для удаления всего между символами Start и Finish
    private void cutout(TruePosition Start, TruePosition Finish){
        StringItem Real_Start = (Start.Item == null ? head : Start.Item.next), Real_Finish = (Finish.Item == null ? head : Finish.Item.next);
        // переменные, чтобы запомнить настоящие айтемы старта и финиша, в TP Start и Finish останутся прошлые айтемы
        if (Start.Item == Finish.Item) { // если подстрока находится в рамках одного блока
            int ItemSizeCopy = Real_Finish.size; // сохраняем длину этого блока
            if (Start.Index == 0 && Finish.Index == ItemSizeCopy - 1){ // если вырезаем целый блок
                if (Real_Start == head){ // если это голова, то
                    head = head.next; // переносим голову в следующий блок
                    // айтем в старте так и останется null, индекс равен 0 по if
                }
                else{ // если это не голова, то
                    // в ссылку для прошлого блока от старта записываем следующий после старта блок, стартовый забывается
                    Start.Item.next = Real_Start.next;
                    // айтем в старте так и останется на своём месте, индекс равен 0 по if
                }
            }
            else { // если вырезаем не целый блок
                ArrayCopy(Real_Start.symbols, Real_Start.symbols, Finish.Index + 1, Real_Start.size, Start.Index); // элементы после финиша уйдут к старту
                Real_Start.size -= (byte) (Finish.Index - Start.Index + 1); // обновляем размер айтема
                if (Finish.Index == ItemSizeCopy - 1) { // если мы удаляем последний элемент в айтеме, то новый старт будет первым элементом в следующем
                    Start.Index = 0;
                    Start.Item = Real_Start;
                } // иначе новый старт будет иметь ту же позицию, что и старый
            }
        }
        else { // иначе концы подстроки в разных айтемах
            ArrayCopy(Real_Finish.symbols, Real_Finish.symbols, Finish.Index + 1, Real_Finish.size, 0); // элементы после финиша переносим к 0
            Real_Finish.size -= (byte) (Finish.Index + 1); // обновляем размер финишного айтема
            if (Real_Finish.size == 0) // если айтем финиша будет пустым после обрезания, то переносим финиш в следующий
                Real_Finish = Real_Finish.next;
            Real_Start.size = (byte) (Start.Index); // обрезаем длину блока, содержащего старт
            if (Real_Start.size == 0){ // если блок стал пустым
                if (Start.Item == null) { // если блок был головой, то переносим голову в финиш
                    head = Real_Finish;
                }
                //иначе в ссылку прошлого от старта блока записываем блок финиша
                else {
                    Start.Item.next = Real_Finish;
                }
            }
            else { // если в айтеме остались символы
                Real_Start.next = Real_Finish; // перекидываем ссылку от старта к финишу
                Start.Item = Real_Start;
                Start.Index = 0;
            }
        }
    }

}