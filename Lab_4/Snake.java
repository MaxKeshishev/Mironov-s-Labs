// Snake, в этом классе мы описываем удава, наследуется от пустой клетки с добавлением возраста,
// можно вывести и обновить по правилам из условия
public class Snake extends Cell{
    protected int Age; // переменная отвечает за возраст удава
    // конструктор удава, наследующий x и y из супер-класса и добавляющий возраст равный 1
    public Snake (int x, int y){
        super(x,y);
        Age = 1;
    }
    // переопределённый WhoAmI из пустой клетки
    public int WhoAmI(){
        return snake;
    }
    // переопределённый update из пустой клетки
    public Cell update(Cell[][] X, Cell A) {
        WhoAround(X); // считаем, кто находится вокруг клетки
        Age++; // увеличиваем возраст удава на 1
        if (Around[snake] > 5 || Age > 6) // если слишком много удавов вокруг или возраст больше 6, то клетка пустеет
            return new Cell(Xcoord, Ycoord);
        return A; // если ни одно условие не выполнилось, то клетка останется пустой
    }
    // переопределённый print из пустой клетки
    public void print(){
        System.out.print(" S ");
    }
}
