// Вспомогательный класс для позиций в листе
public class Position {
    int position; // храним только один int без типа доступа, чтобы было удобно обращаться
    public Position (int num) {
        position = num;
    }

    // Метод для проверки равенства двух позиций
    public boolean equal (Position pos) {
        return pos.position == this.position; // если содержимое равно, то возвращает правду
    }

}
