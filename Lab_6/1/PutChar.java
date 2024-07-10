import java.util.Random;

public class PutChar implements Runnable{
    private Stack stack;
    public PutChar(Stack st, char N){
        stack = st;
        new Thread(this,"Putting"+N).start();
    }
    public void run(){
        try {
            // берем рандомный символ из алфавита
            // считаем сколько раз добавим символ
            // добавляем символ столько раз сколько выпало в рандоме
            while (true){
            Random random = new Random();
            //char x = alphabetA[random.nextInt(alphabetA.length)];
            int in = random.nextInt()%4+2;
            int i =0;
            while (i<=in){
                char x = (char) ('A'+ random.nextInt(25));
                stack.pushRandomChar(x);
                i++;
               }
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
