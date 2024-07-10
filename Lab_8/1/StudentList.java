public class StudentList {
    private static final int SIZE = 20;
    private final Student[] arrayStudent = new Student[SIZE];
    private int space = 0;

    public void append(Student std) {
        arrayStudent[space++] = std;
    }


    public void sorted(SortInterface sort) {
        int j;
        Student cur;
        for ( int i = 1; i < space; i++){
            cur = arrayStudent[i]; // текущий студент
            j = i - 1; // чтобы идти от конца к началу
            while (j >= 0 && sort.sortFunc(cur, arrayStudent[j])){ // пока существует j и выполняется условие сортировки
                arrayStudent[j+1] = arrayStudent[j]; // вставляем
                j--;
            }
            arrayStudent[j+1] = cur;
        }
    }

    public <T> StudentList filtration(T filter, FilterInter<T> func) { // на вход сам фильтр и функция
        StudentList newList = new StudentList(); // создаем новый спсиок студентов
        for (int i = 0; i < space; i++) // идем по всем студентам старого списка
            if (func.filter(filter, arrayStudent[i]))  // если студент соответствует критерию
                newList.append(arrayStudent[i]); // добавляем его в новый список
        return newList;
    }



    public void print() {
        for (int i = 0; i < space; i++) {
            System.out.println(arrayStudent[i]);
        }
        System.out.println('\n');
    }
}
