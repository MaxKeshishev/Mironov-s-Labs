// Grass, в этом классе мы описываем траву, наследуется от пустой клетки,
// можно вывести и обновить по правилам из условия
public class Grass extends Cell{
    // конструктор травы, наследующий x и y из супер-класса
    public Grass (int x, int y){
        super(x,y);
    }
    // переопределённый WhoAmI из пустой клетки
    public int WhoAmI(){
        return grass;
    }
    // переопределённый update из пустой клетки
    public Cell update(Cell[][] X, Cell A) {
        WhoAround(X); // считаем, кто находится вокруг клетки
        if (Around[rabbit] > Around[grass]) // если кроликов вокруг больше, чем травы, то её съедают
            return new Cell(Xcoord, Ycoord);
        return A; // иначе ничего не меняется
    }
    // переопределённый print из пустой клетки
    public void print(){
        System.out.print(" G ");
    }
}
