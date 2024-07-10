public class Main {
    public static void main(String[] args) {
        StudentList list = new StudentList();
        list.append(new Student("Олег", 19, 234 / 3, "Data-аналитик"));
        list.append(new Student("Влад", 18, 266 / 3, "Веб-разработчик"));
        list.append(new Student("Дима", 19, 262 / 3, "Бэкэнд-разработчик"));
        list.append(new Student("Аня", 18, 273 / 3, "Веб-разработчик"));
        list.append(new Student("Макс", 18, 300 / 3, "Системный администратор"));
        list.append(new Student("Алина", 20, 246 / 3, "Гейм-дизайнер"));
        list.append(new Student("Антон", 19, 272 / 3, "Бэкэнд-разработчик"));
        list.append(new Student("Алина", 19, 294 / 3, "ML-инженер"));
        list.print();
        System.out.println("Сортировка по неубыванию:");
        list.sorted(Student::nonDecreasingName);
        list.print();
        System.out.println("Сортировка по невозрастанию:");
        list.sorted(Student::nonIncreasingName);
        list.print();
        System.out.println("Фильтр по специализации Веб-разработчик");
        list.filtration("Веб-разработчик", (a, b) -> b.getSpecialization().compareTo(a) == 0).print();
        System.out.println("Фильтр по среднему баллу не менее 90");
        list.filtration(90, (cnst, std) -> std.getGpa() >= cnst).print();

    }
}
