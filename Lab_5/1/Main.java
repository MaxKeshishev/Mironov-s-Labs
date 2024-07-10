public class Main {
    public static void main(String[] args) { //отсчет начинается с 0
        ListString A = new ListString("This is a little pony"); // конструктор из строки
        System.out.println(A); // выводим оригинал строки
        ListString Acopy = new ListString(A); // копирующий конструктор
        System.out.println(Acopy);
        System.out.println(A.length()); //длина строки
        System.out.println(A.charAt(8)); //вернуть символ в строке в позиции index
        A.setCharAt(17, 'P');//заменить в строке символ в позиции index на символ ch
        System.out.println(A);
        ListString B = A.substring(10,16); //взятие подстроки от start до end
        System.out.println(B);
        A.append(" Rarity");//добавить в конец строку String
        System.out.println(A);
        A.append('!');//добавить в конец строки символ
        System.out.println(A);
        ListString C = new ListString("beautiful ");
        A.insert(10, C);//вставить в строку в позицию index строку ListString
        System.out.println(A);
        A.replace(B, "cute");
        System.out.println(A);
        ListString checkException = A.substring(100,55);
        System.out.println(checkException);
    }
}