public class Student {
    private final String name; // имя
    private final int age; // возраст
    private final int gpa; // средний балл
    private final String specialization; // направление

    public Student(String name, int age, int gpa, String specialization) { // конструктор студента
        this.name = name;
        this.age = age;
        this.gpa = gpa;
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return name + ": " + age + " лет, " + gpa + " GPA, " + specialization;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public float getGpa() {
        return gpa;
    }

    public String getSpecialization() {
        return specialization;
    }

    public boolean nonDecreasingName(Student std) {
        int compName = this.name.compareTo(std.name);
        if (compName < 0) return true; // если имя "меньше", возвращает истину
        if (compName == 0) return this.age <= std.age; // если имена одинаковые сравнивает по возрасту
        return false;
    }

    public boolean nonIncreasingName(Student std) {
        int compName = this.name.compareTo(std.name);
        if (compName > 0) return true; // если имя "больше", возвращает истину
        if (compName == 0) return this.age >= std.age; // если имена одинаковые сравнивает по возрасту
        return false;
    }
}
