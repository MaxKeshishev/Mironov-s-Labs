// Island, в этом классе описывается остров, на который можно добавить удавов, кроликов и траву
// остров можно вывести и обновить в ходе симуляции жизни на нём
import java.util.Random;
public class Island {
    private Cell Current[][], Next[][]; // два двумерных массива клеток, отвечающих за текущее и будущее состояние острова
    // конструктор острова
    public Island(){
        Current = new Cell[22][22]; // массивы 22*22, чтобы было удобно всё считать у крайних клеток
        Next = new Cell[22][22];
        int i, j; // итераторы
        for (i = 0; i<22; i++){
            for (j = 0; j<22; j++){
                Current[i][j] = new Cell(i, j); // заполняем массивы пустыми клетками
                Next[i][j] = new Cell(i, j);
            }
        }
        RandomFill();
    }
    private void RandomFill(){
        Random r = new Random();
        int HowMuch = Math.abs(r.nextInt())%20, x, y, i = 0;
        for (i = 0; i < HowMuch; i++) {
            x = Math.abs(r.nextInt())%20+1;
            y = Math.abs(r.nextInt())%20+1;
            Current[x][y] = new Grass(x,y);
        }
        HowMuch = Math.abs(r.nextInt())%20;
        for (i = 0; i < HowMuch; i++) {
            x = Math.abs(r.nextInt())%20+1;
            y = Math.abs(r.nextInt())%20+1;
            Current[x][y] = new Rabbit(x,y);
        }
        HowMuch = Math.abs(r.nextInt())%20;
        for (i = 0; i < HowMuch; i++) {
            x = Math.abs(r.nextInt())%20+1;
            y = Math.abs(r.nextInt())%20+1;
            Current[x][y] = new Snake(x,y);
        }
    }
    // метод для добавления одного объекта на остров, получает имя объекта и его координаты
    public void add(String Name, int x, int y){
        if (Name == "Snake")
            Current[x][y] = new Snake(x,y);
        else
            if (Name == "Rabbit")
                Current[x][y] = new Rabbit(x,y);
        else
            if (Name == "Grass")
                Current[x][y] = new Grass(x,y);
    }
    // метод для вывода текущего состояния острова
    public void print(){
        int i, j; // итераторы
        for (i = 1; i<21; i++){ // не выводим рамку, которую добавили сами
            for (j = 1; j<21; j++){
                Current[i][j].print();
            }
            System.out.println();
        }
    }
    // метод для обновления всего острова
    public void renew(){
        int i, j; // итераторы
        for (i = 1; i<21; i++){
            for (j = 1; j<21; j++){
                Next[i][j] = Current[i][j].update(Current, Current[i][j]);
                // проходимся по всему массиву Current и в массив Next записываем поэлементно обновлённые клетки
            }
        }
        // меняем местами Next и Current, делая все обновлённые значения текущими
        Cell[][] Temporary = Next; // чтобы не потерять данные создаём временный массив
        Next = Current;
        Current = Temporary;
    }
}
