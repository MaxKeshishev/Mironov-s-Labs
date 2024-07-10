// Cell, в этом классе мы описываем пустую клетку, её можно вывести, узнать её соседей, её тип
// и обновить по правилам из условия
public class Cell {
    protected int Xcoord, Ycoord; // переменные отвечают за координату клетки
    protected static int[] Around = new int[4]; // массив для хранения информации о соседях клетки
    protected static int cell = 0, snake = 1, rabbit = 2, grass = 3; // переменные для читаемости, сопоставляют типы клеток с индексами
    // конструктор клетки, получает на вход координаты
    public Cell (int x, int y){
        Xcoord = x;
        Ycoord = y;
    }
    // чтобы не хранить высчитываемые данные, делаем метод, возвращающий тип клетки
    public int WhoAmI(){
        return cell;
    }
    // метод обновления клетки по правилам, получает на вход массив текущего поля и саму клетку
    public Cell update(Cell[][] X, Cell A){
        WhoAround(X); // считаем, кто находится вокруг клетки
        if (Around[snake] >= 2) // если хотя бы два удава, то клетка станет удавом
            return new Snake(Xcoord,Ycoord);
        else
        if (Around[rabbit] >= 2) // если хотя бы два кролика, то клетка станет кроликом
            return new Rabbit(Xcoord,Ycoord);
        else
        if (Around[grass] > 0) // если есть трава, то станет травой
            return new Grass(Xcoord, Ycoord);
        return A; // если ни одно условие не выполнилось, то клетка останется пустой
    }
    // метод получает массив текущего состояния острова и считает соседей клетки
    protected void WhoAround(Cell[][] Mass){
        Clean(); // сначала чистим массив, потом идём по всем соседям клетки
        Around[Mass[Xcoord-1][Ycoord-1].WhoAmI()]++;
        Around[Mass[Xcoord-1][Ycoord].WhoAmI()]++;
        Around[Mass[Xcoord-1][Ycoord+1].WhoAmI()]++;
        Around[Mass[Xcoord][Ycoord-1].WhoAmI()]++;
        Around[Mass[Xcoord][Ycoord+1].WhoAmI()]++;
        Around[Mass[Xcoord+1][Ycoord-1].WhoAmI()]++;
        Around[Mass[Xcoord+1][Ycoord].WhoAmI()]++;
        Around[Mass[Xcoord+1][Ycoord+1].WhoAmI()]++;
    }
    // вспомогательный метод для очистки массива с информацией о соседях
    private static void Clean(){
        Around[cell] = 0;
        Around[snake] = 0;
        Around[rabbit] = 0;
        Around[grass] = 0;
    }
    // вывод клетки
    public void print(){
        System.out.print(" . ");
    }
}
