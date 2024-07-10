// 15. Создайте класс Product с переменными группа, наименование, количество и стоимость за единицу.
// Напишите класс Store, который будет содержать помимо массива товаров методы для добавления товара
// в конец массива, для упорядочивания товаров по различным переменным с использованием ссылок на
// методы и фильтрации по различным критериям с использованием лямбда-выражений.
// В методе main() добавьте несколько товаров, примените упорядочивание и фильтрацию и
// выведите результаты на экран. Упорядочивание должно выполняться без создания нового объекта
// Store методом простого выбора по неубыванию и невозрастанию товара с группировкой по группам
// и по стоимости за единицу. При фильтрации создаются новые объекты Store, критерии отбора:
// заданная группа товаров, количество товара не больше указанного.

public class Main {

    public static void main (String[] args) {

        Store shop = new Store(10); // создаём магазин

        // заполняем магазин
        shop.push(new Product("Milk","Jogurt",300,100));
        shop.push(new Product("Milk","Sour Cream",200,250));
        shop.push(new Product("Milk","Jogurt",500,150));
        shop.push(new Product("Oil","Olive",150,300));
        shop.push(new Product("Bread","Bun",100,250));
        shop.push(new Product("Milk","Jogurt",400,200));
        shop.push(new Product("Milk","Sour Cream",600,300));
        shop.push(new Product("Milk","Sour Cream",200,100));
        shop.push(new Product("Bread","Bun",100,100));
        shop.push(new Product("Bread","Bun",199,100));

        shop.print(); // выводим магазин

        shop.sort(Product::AscendingSort); // сортируем по неубыванию
        shop.print(); // выводим магазин

        shop.sort(Product::DescendingSort); // сортируем по невозрастанию
        shop.print(); // выводим магазин

        Store A = shop.filter("Milk", (GROUP, PRODUCT) -> PRODUCT.getGroup().compareTo(GROUP) == 0); // фильтруем по группе
        A.print(); // выводим отфильтрованный

        Store B = shop.filter(150, (AMOUNT, PRODUCT) -> PRODUCT.getAmount() <= AMOUNT); // фильтруем по количеству
        B.print(); // выводим отфильтрованный

        Store C = B.filter("Oil", (GROUP, PRODUCT) -> PRODUCT.getGroup().compareTo(GROUP) == 0); // фильтруем по группе
        C.print(); // выводим дважды отфильтрованный

    }

}