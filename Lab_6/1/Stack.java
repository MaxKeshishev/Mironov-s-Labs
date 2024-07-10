import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Stack {

        public char[] s;
        private int up;
        private Condition fullStack;
        private Condition EmptyStack;
        private Condition TwoSymbols;
        private Lock Stacklock;

        public Stack(){
            s  = new char[15];
            up  = -1;
            Stacklock = new ReentrantLock();
            fullStack = Stacklock.newCondition();
            EmptyStack = Stacklock.newCondition();
            TwoSymbols = Stacklock.newCondition();
        }
        public void push(char x){
            s[++up]=x;
        }
        public char pop(){
            return s[up--];
        }
        public char top(){
            return s[up];
        }
        public boolean full(){
            return up == s.length-1;
        }
        public boolean empty(){
        return up==-1;
        }
        public boolean checkSymb(){
            return up<2;
        }
        public void print(){
            // Пока i меньше или равно up выводим i
            int i;
            for(i=0;i<=up;i++){
                System.out.print(s[i]+" ");
            }

        }
        public void pushRandomChar(char x) throws InterruptedException{
            // пока стек фулл fullStack ждет, сигнал к EmptyStack
            // вызываем push
            // выводим все что нужно
            // вызываем потоки EmptyStack
            Stacklock.lock();
            try {
                while (full()){
                    EmptyStack.signalAll();
                    TwoSymbols.signalAll();
                    fullStack.await();
                }
                push(x);
                System.out.println(" ");
                System.out.println(Thread.currentThread()+ " " +x);
                System.out.println(" ");
                print();
                TwoSymbols.signalAll();
                EmptyStack.signalAll();

            }
            finally {
                Stacklock.unlock();
            }
        }
        public void pushRandomKiller( char x) throws InterruptedException {
            // пока стек пустой вызываем потоки из FullStack, потоки EmptyStack ждут
            // Если попадается # то один раз вызываем pop
            // Если попадается @ то один раз вызываем pop, проверяем, что стек не пустой и еще раз вызываем pop
            Stacklock.lock();
            try {
                while (empty()){
                    EmptyStack.await();
                }
                if(x=='#'){
                    pop();
                }
                else {
                    char tp = top();
                    pop();
                    if(empty()){
                        push(tp);
                    while (checkSymb()) {
                        TwoSymbols.await();}
                        pop();

                    }
                    pop();
                    }




                System.out.println(" ");
                System.out.println(Thread.currentThread()+" "+x);
                System.out.println(" ");
                print();
                fullStack.signalAll();
            }
            finally {
                Stacklock.unlock();
            }

        }


    }
// В стеке пишем условия для работы потоков
// В потоках запускаем методы из Stack
// и что-то про кондишоны
// и что-то про Lock

