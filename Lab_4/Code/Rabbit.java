// Rabbit, в этом классе мы описываем кролика, наследуется от удава, чтобы не добавлять ещё раз возраст,
// можно вывести и обновить по правилам из условия
public class Rabbit extends Snake{
    // конструктор кролика, наследующий x, y и возраст, равный 1, у удава
    public Rabbit (int x, int y){
        super(x,y);
    }
    // переопределённый WhoAmI из пустой клетки
    public int WhoAmI(){
        return rabbit;
    }
    // переопределённый update из пустой клетки
    public Cell update(Cell[][] X, Cell A) {
        WhoAround(X); // считаем, кто находится вокруг клетки
        Age++; // увеличиваем возраст кролика на 1
        if (Around[snake] >= Around[rabbit] && Around[snake]!=0 || Age > 3) // если вокруг удавов не меньше, чем кроликов,
            // и хотя бы один удав есть, то кролика съедают. Или кролик может умереть от старости
            return new Cell(Xcoord, Ycoord);
        return A; // если ни одно условие не выполнилось, то клетка останется пустой
    }
    // переопределённый print из пустой клетки
    public void print(){
        System.out.print(" R ");
    }
}
