public class Product {

    private String Group, Name; // группа и имя
    private int Amount, Cost; // количество и цена

    // конструктор по всем параметрам
    public Product (String GROUP, String NAME, int AMOUNT, int COST) {
        Group = GROUP;
        Name = NAME;
        Amount = AMOUNT;
        Cost = COST;
    }

    // копирующий конструктор
    public Product (Product P) {
        Group = P.Group;
        Name = P.Name;
        Amount = P.Amount;
        Cost = P.Cost;
    }

    // вспомогательный метод, чтобы иметь доступ к группе продукта
    public String getGroup () {
        return Group;
    }

    // вспомогательный метод, чтобы иметь доступ к количеству продукта
    public int getAmount () {
        return Amount;
    }

    // сортировка по неубыванию
    public boolean AscendingSort (Product Second) { // передаём два продукта и сравниваем их
        if (Group.compareTo(Second.Group) > 0 ||
                Group.compareTo(Second.Group) == 0 && Name.compareTo(Second.Name) > 0 ||
                Group.compareTo(Second.Group) == 0 && Name.compareTo(Second.Name) == 0 &&
                        Cost > Second.Cost) // первый больше второго, возвращаем правду
            return true;
        return false;
    }

    // сортировка по невозрастанию
    public boolean DescendingSort (Product Second) { // передаём два продукта и сравниваем их
        if (Group.compareTo(Second.Group) < 0 ||
                Group.compareTo(Second.Group) == 0 && Name.compareTo(Second.Name) < 0 ||
                Group.compareTo(Second.Group) == 0 && Name.compareTo(Second.Name) == 0 &&
                        Cost < Second.Cost) // второй больше первого, возвращаем правду
            return true;
        return false;
    }

    // переопределение метода для вывода
    public String toString () {
        return Group+" "+Name+" "+Amount+" "+Cost;
    }

}
