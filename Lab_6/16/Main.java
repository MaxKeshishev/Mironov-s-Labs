/*16. В ограниченный стек (максимум 15 значений) два потока помещают целочисленные случайные значения от 1 до 9.
Два других потока, производящие символы-операции (+, /), извлекают пару верхних значений из стека производят над
ними соответствующую операцию, затем полученный результат помещают обратно в стек. При этом первое извлеченное из
стека значение правый операнд, второе - левый операнд. Каждый из потоков, производящий целочисленные значения,
может подряд произвести и занести в стек от 2 до 5 значений (определяется случайно). Каждый из потоков, производящий
символы-операции, может подряд произвести 1 или 2 операции (определяется случайно). Целочисленные значения не могут
быть помещены в стек, если стек полный. Операция не может быть выполнена, если стек пустой или в нем только одно
значение.Операция деления (/) также не может быть выполнена, если на вершине стека находится значение 0 (ноль). В
процессе выполнения программы после каждого добавления значения, в том числе после выполнения операции, необходимо
выводить на экран имя потока, значение или символ-операцию. А в следующей строке отображать состояние стека
(его заполненной части) после занесения значения в стек, в том числе после выполнения операции, в порядке от основания

стека до его вершины.*/
// Главный класс Main запускает программу.
public class Main {
    public static void main(String[] args) {
        Stack A = new Stack(); // Создание стека.
        PutNum P1 = new PutNum('1', A); // Запуск первого потока добавления чисел.
        PutNum P2 = new PutNum('2', A); // Запуск второго потока добавления чисел.
        Operation O1 = new Operation('1', A); // Запуск первого потока выполнения операций.
        Operation O2 = new Operation('2', A); // Запуск второго потока выполнения операций.
    }
}