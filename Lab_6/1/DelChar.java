import java.util.Random;

public class DelChar implements Runnable{
    private Stack stack;
    public DelChar(Stack st, char N){
        stack = st;
        new Thread(this,"Delliting"+N).start();
    }
    public void run(){
        try {
            // находим рандомный символ
            // вызываем функцию из стека pushRandomKiller от 1 до 2 раз
            // спросить: рандомно добавляем несколько раз один сивол или каждый раз разный?
            while (true){

            Random random = new Random();
            //char x = alphabetA[random.nextInt(alphabetA.length)];
            int in = random.nextInt()%2+1;
            int i =0;
            while (i<=in){
                char x = random.nextInt(2)==0?'#':'@';
                stack.pushRandomKiller(x);
                i++;
                }
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
