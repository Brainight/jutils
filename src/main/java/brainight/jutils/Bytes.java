package brainight.jutils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Github: https://github.com/Brainight
 *
 * @author Brainight
 */
public class Bytes {

    public static int bytesToIntBE(byte[] bytes) {
        return ((bytes[0] & 0xFF) << 24
                | (bytes[1] & 0xFF) << 16
                | (bytes[2] & 0xFF) << 8
                | (bytes[3] & 0xFF) << 0);
    }

    public static byte[] intToBytesBE(final int num) {
        return new byte[]{
            (byte) ((num >> 24) & 0xFF),
            (byte) ((num >> 16) & 0xFF),
            (byte) ((num >> 8) & 0xFF),
            (byte) ((num >> 0) & 0xFF)
        };
    }

    public static byte[] longToBytesBE(final long num) {
        return new byte[]{
            (byte) ((num >> 56) & 0xFF),
            (byte) ((num >> 48) & 0xFF),
            (byte) ((num >> 40) & 0xFF),
            (byte) ((num >> 32) & 0xFF),
            (byte) ((num >> 24) & 0xFF),
            (byte) ((num >> 16) & 0xFF),
            (byte) ((num >> 8) & 0xFF),
            (byte) ((num >> 0) & 0xFF)
        };
    }

    public static long bytesToLongBE(byte[] bytes) {
        return (((long) (bytes[0] & 0xFF)) << 56)
                | (((long) (bytes[1] & 0xFF)) << 48)
                | (((long) (bytes[2] & 0xFF)) << 40)
                | (((long) (bytes[3] & 0xFF)) << 32)
                | (((long) (bytes[4] & 0xFF)) << 24)
                | (((long) (bytes[5] & 0xFF)) << 16)
                | (((long) (bytes[6] & 0xFF)) << 8)
                | (((long) (bytes[7] & 0xFF)) << 0);
    }

    public static byte[] getSecureRandomBytes(int length) {
        SecureRandom sr = new SecureRandom();
        byte[] bytes = new byte[length];
        sr.nextBytes(bytes);
        return bytes;
    }

    public static void getSecureRandomBytes(byte[] buffer) {
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(buffer);
    }

    public static byte[] copyOf(byte[] input) {
        byte[] copy = new byte[input.length];
        System.arraycopy(input, 0, input, 0, input.length);
        return copy;
    }

    public static boolean contentEquals(byte[] b0, byte[] b1) {
        if (b0.length != b1.length) {
            return false;
        }

        boolean e = true;
        for (int i = 0; i < b0.length; i++) {
            if (b0[i] != b1[i]) {
                e = false;
                break;
            }
        }
        return e;
    }

    public static void zeroOut(byte[] array) {
        Arrays.fill(array, 0, array.length, (byte) 0x00);
    }

    public static void zeroOut(char[] array) {
        Arrays.fill(array, 0, array.length, (char) 0x00);
    }

    public static void zeroOut(short[] array) {
        Arrays.fill(array, 0, array.length, (short) 0x00);
    }

    public static void zeroOut(int[] array) {
        Arrays.fill(array, 0, array.length, 0x00);
    }

    public static void zeroOut(long[] array) {
        Arrays.fill(array, 0, array.length, 0x00);
    }

    public static void zeroOut(double[] array) {
        Arrays.fill(array, 0, array.length, 0x00);
    }

    public static void zeroOut(float[] array) {
        Arrays.fill(array, 0, array.length, 0x00);
    }

    /**
     *
     * Returns xored array result of xoring data with xorArray. Returns null if
     * xorArray length is less than 1.
     *
     * @param data
     * @param xorArray
     * @return
     */
    public static byte[] xor(byte[] data, byte[] xorArray) {
        if (xorArray.length < 1 || data == null) {
            return null;
        }

        byte[] xor = new byte[xorArray.length];
        System.arraycopy(xorArray, 0, xor, 0, xor.length);

        if (xorArray.length == 1) {
            xor = new byte[data.length];
            Arrays.fill(xor, xorArray[0]);
        }

        while (data.length > xor.length) {
            byte[] arr = new byte[xor.length * 2];
            System.arraycopy(xor, 0, arr, 0, xor.length);
            System.arraycopy(xor, 0, arr, xor.length, xor.length);
            xor = arr;
        }

        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = (byte) (data[i] ^ xor[i]);
        }

        return result;
    }

    public static byte[] getSHA256(byte[] data) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return null;
        }
        byte[] hash = digest.digest(data);
        return hash;
    }
}
