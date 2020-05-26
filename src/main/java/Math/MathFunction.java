package Math;

import java.math.BigInteger;
import java.util.Random;

public class MathFunction {
    public static boolean simpleTest(BigInteger n, int round) {
        if (n.compareTo(BigInteger.TWO) <= 0) {
            return false;
        }
        int s = 0;
        BigInteger r = n.subtract(BigInteger.ONE);
        BigInteger t = r;
        while ((t.mod(BigInteger.TWO)).equals(BigInteger.ZERO) ) {
            t = t.divide(BigInteger.TWO);
            s++;
        }
        label:
        for (int k = 0; k < round; k++) {
            BigInteger a = new BigInteger(n.bitCount(), new Random());
            if (a.compareTo(BigInteger.ONE) <= 0) {
                a = BigInteger.TWO;
            }
            if (a.compareTo(r) >= 0) {
                a = r.subtract(BigInteger.ONE);
            }
            BigInteger x = a.modPow(t, n);
            if (x.equals(BigInteger.ONE) || x.equals(r)) continue;
            for (int i = 0; i < s; i++) {
                x = x.modPow(BigInteger.TWO, n);
                if (x.equals(BigInteger.ONE)) {
                    return false;
                }
                if (x.equals(r)) {
                    break label;
                }
            }
            return false;
        }
        return true;
    }

    private static TripleInt gcdWide(BigInteger a, BigInteger b) {
        BigInteger aCopy = a;
        BigInteger bCopy = b;
        BigInteger p = BigInteger.ONE;
        BigInteger q = BigInteger.ZERO;
        BigInteger r = BigInteger.ZERO;
        BigInteger s = BigInteger.ONE;
        BigInteger x;
        BigInteger y;
        while (!a.equals(BigInteger.ZERO) && !b.equals(BigInteger.ZERO)) {
            if (a.compareTo(b) >= 0) {
                a = a.subtract(b);
                p = p.subtract(r);
                q = q.subtract(s);
            } else {
                b = b.subtract(a);
                r = r.subtract(p);
                s = s.subtract(q);
            }
        }
        if (!a.equals(BigInteger.ZERO)) {
            x = p;
            y = q;
        } else {
            x = r;
            y = s;
        }
        if (x.compareTo(BigInteger.ZERO) < 0) {
            x = bCopy.add(x);
        }
        return new TripleInt(b, x, y);
    }

    public static TripleInt gcdRec(BigInteger a, BigInteger b) {
        TripleInt res;
        if (b.compareTo(BigInteger.ZERO) == 0) {
            res = new TripleInt(a, BigInteger.ONE, BigInteger.ZERO);
            return res;
        }
        res = gcdRec(b, a.mod(b));
        BigInteger s = res.getY();
        res.setY(res.getX().subtract(a.divide(b).multiply(res.getY())));
        res.setX(s);
        return res;
    }

    public static BigInteger gcd(BigInteger a, BigInteger b) {
        return gcdWide(a, b).getD();
    }

    public static BigInteger inverse(BigInteger a, BigInteger m) {
        BigInteger res = gcdRec(a, m).getX();
        if (res.compareTo(BigInteger.ZERO) < 0) {
            res = res.add(m);
        }
        return res;
    }
}

class TripleInt {
    private BigInteger d;
    private BigInteger x;
    private BigInteger y;

    public TripleInt(BigInteger a, BigInteger b, BigInteger c) {
        d = a;
        x = b;
        y = c;
    }

    public void setD(BigInteger d) {
        this.d = d;
    }

    public void setX(BigInteger x) {
        this.x = x;
    }

    public void setY(BigInteger y) {
        this.y = y;
    }

    public BigInteger getD() {
        return d;
    }

    public BigInteger getX() {
        return x;
    }

    public BigInteger getY() {
        return y;
    }
}
