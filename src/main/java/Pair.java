public class Pair<T,C> {
    private T a;
    private C b;

    public Pair(T x, C y) {
        a = x;
        b = y;
    }

    public void setKey(T a) {
        this.a = a;
    }

    public void setValue(C b) {
        this.b = b;
    }

    public T getKey() {
        return a;
    }

    public C getValue() {
        return b;
    }
}
