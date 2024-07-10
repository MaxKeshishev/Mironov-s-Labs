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
        this(P.Group, P.Name, P.Amount, P.Cost);
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
        if (Group.compareTo(Second.Group) > 0) // если группа больше, то продукт точно больше
            return true;
        if (Group.compareTo(Second.Group) == 0) { // если два продукта в одной группе, то сравниваем по имени
            if (Name.compareTo(Second.Name) > 0) // если имя больше, то продукт больше
                return true;
            if (Name.compareTo(Second.Name) == 0) // если имена одинаковые, то сравниваем по стоимости
                if (Cost > Second.Cost) // если стоит больше, то продукт больше
                    return true;
        }
        return false; // иначе продукт меньше
    }

    // сортировка по невозрастанию
    public boolean DescendingSort (Product Second) { // передаём два продукта и сравниваем их
        if (Group.compareTo(Second.Group) < 0) // если группа меньше, то продукт точно меньше
            return true;
        if (Group.compareTo(Second.Group) == 0) { // если два продукта в одной группе, то сравниваем по имени
            if (Name.compareTo(Second.Name) < 0) // если имя меньше, то продукт меньше
                return true;
            if (Name.compareTo(Second.Name) == 0) // если имена одинаковые, то сравниваем по стоимости
                if (Cost < Second.Cost) // если стоит меньше, то продукт меньше
                    return true;
        }
        return false; // иначе продукт больше
    }

    // переопределение метода для вывода
    public String toString () {
        return Group+" "+Name+" "+Amount+" "+Cost;
    }

}
