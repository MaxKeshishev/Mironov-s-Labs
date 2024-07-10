// 6. Дан целочисленный массив размера M*N, заполненный построчно случайными значениями
// в диапазоне от -99 до 99. Этот массив вывести на экран. Заменить исходный массив новым,
// в котором перед первым столбцом, содержащим только положительные элементы, вставлен столбец
// из единиц (номер стобца, содержащего только положиельные элементы, может быть любым от 0 до N-1!).
// Получившийся массив вывести на экран. Если требуемого столбца нет, то вывести массив без изменений.

import java.util.Random;

public class Main{
    public static void main(String[] args){
        int m = 4, n = 6; // размеры исходного массива
        int a[][] = create(m,n);
        init(a);
        output(a);
        System.out.println();
        a = transpon(a);
        output(a);
    }

    // метод для транспонирования массива
    public static int[][] transpon(int x[][]){
        if (x[0].length != x.length) { // если массив не квадратный
            int mass[][] = create(x[0].length, x.length);
            int i, j; // итераторы
            for (i = 0; i < mass.length; i++)
                for (j = 0; j < mass[0].length; j++)
                    mass[i][j] = x[j][i];
            return mass;
        }
        else // если массив квадратный, то применяем отдельный метод, не создающий копии
            return sqrttranspon(x);
    }

    // вспомогательный метод для транспонирования квадратного массива
    private static int[][] sqrttranspon(int x[][]){
        int i, j; // итераторы
        int copy; // переменная для копирования
        for (i=0; i<x.length; i++)
            for (j=0; j<i; j++) {
                copy = x[i][j];
                x[i][j] = x[j][i];
                x[j][i] = copy;
            }
        return x;
    }

    // создаёт пустой массив, получает количество строк и столбцов, возвращает пустой массив размером sz1*sz2
    public static int[][] create(int sz1, int sz2){
        int x[][]; // создаваемый массив
        x = new int[sz1][];
        int i; // итератор
        for (i=0; i<x.length; i++) x[i] = new int[sz2];
        return x;
    }

    // выводит массив, получает массив, ничего не возвращает
    public static void output(int x[][]){
        int i, j; // итераторы
        for (i=0; i<x.length; i++) {
            for (j=0; j < x[i].length; j++)
                System.out.printf("%4d",x[i][j]); // форматированный вывод
            System.out.println("");
        }
    }

    // заполняет массив случайными числами, получает массив, ничего не возвращает
    public static void init(int x[][]){
        Random r = new Random();
        int i, j; // итераторы
        for (i=0; i<x.length; i++)
            for (j=0; j<x[0].length; j++)
                x[i][j] = r.nextInt()%100; // заполняем массив случайными числами от -99 до 99
    }

    // ищет первый положительный столбец в массиве, получает массив, возвращает индекс столбца, если столбца нет, то возращает -1
    public static int positive(int x[][]){
        int i, j; // итераторы
        for (j = 0; j < x[0].length; j++) {
            for (i = 0; i < x.length; i++)
                if (x[i][j] <= 0)
                    break;
            // идём по всем элементам каждого столбца, если есть что-то <= 0, то делаем break, если break не произошёл
            // ни разу, то это нужный нам стобец, поэтому возвращаем его индекс и выходим
            if (i == x.length) return j;
        }
        // если до этого не вышли из метода, то нужного столбца не было, поэтому возвращаем -1
        return -1;
    }

    // удаляет столбец по индексу
    public static void deleteone(int x[][], int index){
        int i,j;
        for (i = 0; i < x.length; i++){
            int k[] = new int [x[i].length-1];
            for (j = 0; j < index; j++) k[j] = x[i][j];
            for (; j < k.length; j++) k[j] = x[i][j+1];
            x[i] = k;
        }
    }

    // добавляет в массив столбец единиц, если индекс не -1, получает массив и индекс, возвращает массив с дополнительным стобцом
    public static int[][] addone(int x[][], int index){
        int copy[][] = create(x.length,x[0].length+1); // создаём массив - копию с дополнительным столбцом
        int i, j; // итераторы
        for (i = 0; i < copy.length; i++) {
            for (j = 0; j < index; j++) // если j меньше index
                copy[i][j] = x[i][j];

            copy[i][j] = 1; // если j = index, то этот элемент равен 1
            j++;

            for (; j < copy[0].length; j++) // если j больше index
                copy[i][j] = x[i][j - 1];
        }
        return copy;
    }

}
