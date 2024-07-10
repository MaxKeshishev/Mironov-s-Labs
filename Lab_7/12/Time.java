// класс реализует объект время
public class Time implements Comparable<Time> {

    private int time; // храним время в секундах и потом переводим в объект

    public Time (int hours, int minutes, int seconds) {
        time = (hours*60*60 + minutes * 60 + seconds) % 86400;
    }

    // переопределяем метод compareTo для сравнения двух времен
    public int compareTo (Time A) {
        //меньше 0, если a<b, 0, если a==b и больше 0 a>b.
        return this.time - A.time;
    }

    // переопределяем метод toString для вывода времени
    public String toString () {
        return time/3600 + ":" + (time%3600)/60 + ":" + (time%3600)%60;
    }

}
