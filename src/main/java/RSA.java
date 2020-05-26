import java.math.BigInteger;

public class RSA {
    private Key key;
    public RSA(int keysize) {
        key = new Key(keysize);
    }

    public byte[] encoding(byte[] message) {
        BigInteger mes = new BigInteger(message);
        BigInteger c = mes.modPow(key.getOpenKey().getKey(), key.getOpenKey().getValue());
        return c.toByteArray();
    }

    public byte[] decoding(byte[] enc) {
        BigInteger encMes = new BigInteger(enc);
        BigInteger mess = encMes.modPow(key.getCloseKey().getKey(), key.getCloseKey().getValue());

        return mess.toByteArray();
    }

    public Pair<byte[], byte[]> signState(byte[] message) {
        byte[] h = MD4Hash.getHash(message);
        BigInteger hInt = new BigInteger(h);
        Pair <BigInteger,BigInteger> closedKey = key.getCloseKey();
        BigInteger sign = hInt.modPow(closedKey.getKey(), closedKey.getValue());
        return new Pair<>(message, sign.toByteArray());
    }

    public boolean signConfirm(byte[] message, byte[] sign) {
        BigInteger s = new BigInteger(sign);
        Pair<BigInteger, BigInteger> openKey = key.getOpenKey();
        BigInteger h = s.modPow(openKey.getKey(), openKey.getValue());
        BigInteger hCalc = new BigInteger(MD4Hash.getHash(message));
        hCalc = hCalc.mod(openKey.getValue());
        return h.equals(hCalc);
    }

    public static void main(String[] args) {
        String s = "The quick brown fox jumps over the lazy dog";
        RSA sign = new RSA(32);
        byte[] enc = sign.encoding(s.getBytes());
        byte[] res = sign.decoding(enc);
        System.out.println(new String(enc));
        System.out.println(new String(res));
        Pair<byte[], byte[]> p = sign.signState(s.getBytes());
        //p.getValue()[p.getValue().length-1] += 1;
        if (sign.signConfirm(p.getKey(), p.getValue()))
            System.out.println("Подпись верна");
        else
            System.out.println("Подпись неверна");
    }
}
