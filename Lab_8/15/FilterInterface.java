// обобщённый функциональный интерфейс для лямбда-выражений фильтрации
public interface FilterInterface <T> {
    boolean func(T filter, Product P);
}