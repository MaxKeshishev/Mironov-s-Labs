
public class Main {

    // метод для слияния
    public static <T extends Comparable<T>> void mergeLists(List<T> A, List<T> B, List<T> C) {

        Position a = A.First(); // берем первые элементы из списков
        Position b = B.First();

        while ((a.position != A.End().position) && b.position != B.End().position ) { //
            if (A.Retrieve(a).compareTo(B.Retrieve(b)) < 0) {
                C.Insert(A.Retrieve(a), C.End());
                a = A.Next(a);
            } else {
                if (A.Retrieve(a).compareTo(B.Retrieve(b)) > 0){
                    C.Insert(B.Retrieve(b), C.End());
                    b = B.Next(b);
                }
                else{
                    C.Insert(A.Retrieve(a), C.End());
                    C.Insert(B.Retrieve(b), C.End());
                    a = A.Next(a);
                    b = B.Next(b);
                }
            }
        }
        while (a.position != A.End().position){
            C.Insert(A.Retrieve(a), C.End());
            a = A.Next(a);
        }
        while (b.position != B.End().position) {
            C.Insert(B.Retrieve(b), C.End());
            b = B.Next(b);
        }
        A.Makenull();
        B.Makenull();
    }

    public static void main(String[] args) {

        // INTEGER ----------------------------------
        System.out.println("Список чисел");

        Integer[] ai = new Integer[12];
        Integer[] bi = new Integer[12];
        Integer[] ci = new Integer[ai.length + bi.length];

        List<Integer> Ai = new List<Integer>(ai); // создаём лист для чисел
        List<Integer> Bi = new List<Integer>(bi); // создаём лист для чисел
        List<Integer> Ci = new List<Integer>(ci); // создаём лист для чисел

        System.out.println("Первый случай");
        Ai.Insert(45, Ai.End()); // показываем, что работает добавление в пустой список
        Ai.Insert(67, Ai.End()); // показываем, что работает добавление в конец
        Ai.Insert(1, Ai.First()); // показываем, что работает добавление в начало
        Ai.Insert(23, Ai.Next(Ai.Locate(1))); // показываем, что работает Next и Locate
        Bi.Insert(2, Bi.End()); // показываем, что работает добавление в пустой список

        System.out.print("Ai: ");
        Ai.Printlist();
        System.out.print("Bi: ");
        Bi.Printlist();
        mergeLists(Ai, Bi, Ci);
        System.out.print("Ci: ");
        Ci.Printlist();
        Ci.Makenull(); // опустошаем

        System.out.print("Ci: ");
        Ci.Printlist();
        System.out.println("\n");

        // STRING ----------------------------------
        System.out.println("Список строк");

        String[] as = new String[12];
        String[] bs = new String[12];
        String[] cs = new String[as.length + bs.length];

        List<String> As = new List<String>(as); // создаём лист для строк
        List<String> Bs = new List<String>(bs); // создаём лист для строк
        List<String> Cs = new List<String>(cs); // создаём лист для строк

        As.Insert("Август" , As.End());
        As.Insert("богат", As.End());
        As.Insert("дынями,", As.End()); // добавляем строки
        As.Insert("виноградом,", As.Locate("дынями,"));
        As.Insert("ежевикой.", As.End());
        Bs.Insert("грушами,", As.End());

        System.out.print("As: ");
        As.Printlist();
        System.out.print("Bs: ");
        Bs.Printlist();
        mergeLists(As, Bs, Cs); //
        System.out.print("Cs: ");
        Cs.Printlist();

        Cs.Delete(Cs.Next(Cs.Next(Cs.First()))); // показываем, что работает отдельное удаление по позиции
        Cs.Delete(Cs.Locate("дынями,")); // показываем, что работает отдельное удаление по позиции
        System.out.print("Cs: ");
        Cs.Printlist(); // выводим результат
        Cs.Makenull();
        System.out.print("Cs: ");
        Cs.Printlist(); // выводим результат
        System.out.println("\n");

        // ELEMENT ----------------------------------
        System.out.println("Список координат");

        Element[] ac = new Element[12];
        Element[] bc = new Element[12];
        Element[] cc = new Element[ac.length + bc.length];

        List<Element> Ac = new List<Element>(ac); //
        List<Element> Bc = new List<Element>(bc); //
        List<Element> Cc = new List<Element>(cc); //


        Ac.Insert(new Element(0,3), Ac.End());
        Ac.Insert(new Element(3,1), Ac.End());
        Ac.Insert(new Element(-2,-4), Ac.End());
        Ac.Insert(new Element(5,6), Ac.End());
        Ac.Insert(new Element(0,0.1), Ac.First());

        Bc.Insert(new Element(0,-0.02), Bc.End());
        Bc.Insert(new Element(-4,3), Bc.End());
        Bc.Insert(new Element(-5,4), Bc.End());
        Bc.Insert(new Element(-4,-2), Bc.Previous(Bc.Locate(new Element(-5,4)))); // показываем, что работает Previous


        System.out.print("Ac: ");
        Ac.Printlist();
        System.out.print("Bc: ");
        Bc.Printlist();
        mergeLists(Ac, Bc, Cc); //
        System.out.print("Cc: ");
        Cc.Printlist();

    }

}