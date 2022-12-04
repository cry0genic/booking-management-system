package Model;

public interface User extends Runnable{
    String getName();
    Thread getThread();
    void setThread();
}
