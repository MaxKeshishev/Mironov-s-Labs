import java.util.Random;

// класс НАБОР, через него мы создаём заполненный массив доминошек, выводим весь набор доминошек,
// можем превратить набор в последовательность, а также получить ссылку на сформированный массив доминошек
public class StartBox {
    private Domino[] N;
    // создаём целый заполненный массив при инициализации
    public StartBox (){
        N = new Domino[28]; // пустой набор из доминошек
        for (int i = 0; i < 28; i++) {
            N[i] = new Domino(i);
        }
    }
    // метод возвращает ссылку на сформированный массив доминошек
    public Domino[] returnDomino (){
        return N;
    }
    // метод переводит набор в последовательность
    public void toSequence(Sequence X){ // в качестве аргумента получает саму последовательность
        Random r = new Random();
        int finish = Math.abs(r.nextInt())%28; // в переменную finish запоминаем индекс последнего элемента последовательность
        //X.changeFinish(finish); // делаем finish концом последовательности
        X.addElement(N[finish]); // добавляем в последовательность элемент с индексом finish
        int index; // индекс добавляемого случайного элемента
        int i = 0;
        while (i < 27){
            index = Math.abs(r.nextInt())%28;
            if (N[index].getLink() == -1) { // если нашли недобавленную доминошку, то добавляем и увеличиваем счётчик
                i++;
                X.addElement(N[index]); // добавляем новый элемент
            }
        }
        //X.changeFinish(finish); // возвращаем конец последовательности к исходному состоянию
    }
    // метод выводит весь массив доминошек по порядку
    public void print (){
        for (int i = 0; i < 28; i++)
            N[i].print();
        System.out.println();
    }
}
