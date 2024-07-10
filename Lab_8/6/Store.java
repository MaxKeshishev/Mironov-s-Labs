public class Store {

    private Product[] products; // массив продуктов
    private int up; // индекс последнего элемента в массиве

    // конструктор магазина
    public Store (int size) { // получает размер магазина
        products = new Product[size];
        up = -1; // изначально магазин пустой, поэтому индекс -1
    }

    // метод для добавления продукта в конец массива
    public void push (Product element) {
        products[++up] = element;
    }

    // сортировка методом простого выбора через ссылки на методы
    public void sort (SortInterface Inter) { // получает ссылку на метод
        Product MinMax; // переменная для хранения минимального или максимального продукта из неотсортированной части массива
        int MinMaxI; // переменная для хранения индекса минимального или максимального продукта из неотсортированной части массива
        for (int i = 0; i < up+1; i++) { // сначала ищем элемент для индекса 0, потом 1 ...
            MinMax = products[i]; // выставляем минимум или максимум в первый неотсортированный элемент
            MinMaxI = i;
            for (int j = i + 1; j < up+1; j++) { // идём по всей неотсортированной части массива
                if (Inter.func(MinMax, products[j])) { // сравниваем два продукта по нужной функции, запоминаем, если надо
                    MinMax = products[j];
                    MinMaxI = j;
                }
            }
            products[MinMaxI] = products[i]; // меняем местами первый неотсортированный элемент и найденный минимум или максимум
            products[i] = MinMax;
        }
    }

    // метод для фильтрации по группе
    public <T> Store filter (FilterInterface <T> Inter, T obj) { // получаем объект для сравнения и функцию сравнения
        Store shop = new Store(products.length); // создаём магазин для фильтрации, размер массива берём из оригинального
        for (int i = 0; i < up+1; i++){ // идём по всем существующим продуктам
            if (Inter.func(obj, products[i])) // если объект совпал по условию фильтрации, то добавляем в новый массив
                shop.push(new Product(products[i])); // добавляем в новый список копию через конструктор
        }
        return shop; // возвращаем магазин
    }

    // метод для вывода магазина
    public void print () {
        for (int i = 0; i < up+1; i++)
            System.out.println(products[i]);
        System.out.println();
    }

}
