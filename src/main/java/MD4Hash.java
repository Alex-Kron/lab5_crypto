import org.bouncycastle.crypto.digests.MD4Digest;
import org.bouncycastle.util.encoders.Hex;

public class MD4Hash {
    public static byte[] getHash(byte[] b) {
        MD4Digest d = new MD4Digest();
        d.update(b, 0, b.length);
        byte[] res = new byte[d.getDigestSize()];
        d.doFinal(res, 0);
        res = Hex.encode(res);
        return res;
    }
}
