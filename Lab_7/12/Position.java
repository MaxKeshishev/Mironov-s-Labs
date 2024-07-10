// вспомогательный класс для позиций в листе
public class Position {

    int position; // храним только один int без типа доступа, чтобы было удобно обращаться

    public Position (int num) {
        position = num;
    }

    // метод для проверки, что две позиции являются одинаковыми
    public boolean equal (Position p) {
        // если содержимое равно, то возвращает правду
        return p.position == this.position;
    }

}
