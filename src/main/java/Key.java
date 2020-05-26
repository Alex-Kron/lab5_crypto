import java.math.BigInteger;
import java.util.Random;
import Math.*;

public class Key {
    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger eiler;
    private BigInteger e;
    private BigInteger d;

    public Key(int size) {
        byte[] pb = new byte[size];
        byte[] qb = new byte[size];
        Random rand = new Random();
        rand.nextBytes(pb);
        rand.nextBytes(qb);
        p = new BigInteger(pb);
        q = new BigInteger(qb);
        while (!MathFunction.simpleTest(p,3)) {
            rand.nextBytes(pb);
            p = new BigInteger(pb);
        }
        while (!MathFunction.simpleTest(q,3)) {
            rand.nextBytes(qb);
            q = new BigInteger(qb);
        }
        n = p.multiply(q);
        eiler = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        e = new BigInteger("65537");
        d = MathFunction.inverse(e, eiler);
    }

    public Pair<BigInteger,BigInteger> getOpenKey() {
        return new Pair<>(e, n);
    }

    public Pair<BigInteger,BigInteger> getCloseKey() {
        return new Pair<>(d, n);
    }
}
