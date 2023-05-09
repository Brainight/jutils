package brainight.jutils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Github: https://github.com/Brainight
 *
 * @author Brainight
 */
public class Encoder {

    // ### HEX 
    private static final byte[] HEX_ARRAY = "0123456789abcdef".getBytes(StandardCharsets.UTF_8);
    private static final String HEX_STR = "0123456789abcdef";

    public static String toHexStr(byte[] bytes) {
        byte[] hexChars = toHex(bytes);
        return new String(hexChars, StandardCharsets.UTF_8);
    }

    public static byte[] toHex(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int i = 0, k = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[k++] = HEX_ARRAY[v >>> 4];
            hexChars[k++] = HEX_ARRAY[v & 0x0F];
        }
        return hexChars;
    }

    public static byte[] fromHex(String hexString) {

        char[] hexChar = hexString.toCharArray();
        byte[] result = new byte[hexChar.length >>> 1];
        for (int i = 0, k = 0; i < hexChar.length >>> 1; i++, k += 2) {
            result[i] = (byte) ((HEX_STR.indexOf(hexChar[k]) << 4) + (HEX_STR.indexOf(hexChar[k + 1]) & 0x0F));
        }

        return result;
    }

    // #### BASE64 (UTF-8)
    private static final byte PADDING_BYTE = "=".getBytes(StandardCharsets.UTF_8)[0];
    private static final String B64_STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    private static final byte[] B64_ARRAY = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".getBytes(StandardCharsets.UTF_8);

    public static byte[] encodeB64(byte[] bytes, boolean padded) {

        if (bytes == null || bytes.length == 0) {
            return new byte[0];
        }
        float fl = bytes.length * 1.3333f;
        int length = ((int) fl) + ((fl == (int) fl) ? 0 : 1);

        int forLength = (length / 4) + ((length & 3) == 0 ? 0 : 1);
        int lengthCheck = length - 1;

        if (padded) {
            int rem = (length & 0b11);
            length += (rem == 3 || rem == 1) ? 1 : rem;
        }

        byte[] b64result = new byte[length];
        for (int i = 0, bc = 0, k = 0, v = 0, hv = 0; i < forLength; i++) {

            v = bytes[k++] & 0xFF;

            b64result[bc++] = B64_ARRAY[v >>> 2];

            hv = (v & 0b11) << 4;
            if (bc < lengthCheck) {
                v = bytes[k++] & 0xFF;
                b64result[bc++] = B64_ARRAY[hv | v >>> 4];
            } else {
                b64result[bc++] = B64_ARRAY[hv];
                if (padded) {
                    b64result[bc++] = PADDING_BYTE;
                    b64result[bc] = PADDING_BYTE;
                }
                break;
            }

            hv = (v & 0b1111) << 2;
            if (bc < lengthCheck) {
                v = bytes[k++] & 0xFF;
                b64result[bc++] = B64_ARRAY[hv | v >>> 6];
            } else {
                b64result[bc++] = B64_ARRAY[hv];
                if (padded) {
                    b64result[bc] = PADDING_BYTE;
                }
                break;
            }

            b64result[bc++] = B64_ARRAY[v & 0b111111];
        }

        return b64result;
    }

    /**
     * Returns padded base64 String.
     *
     * @param data
     * @return
     */
    public static String encodeB64Str(byte[] data) {
        return Encoder.getUTF8(Encoder.encodeB64(data, true));
    }

    public static byte[] decodeB64(String b64) {
        return decodeB64(b64.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] decodeB64(byte[] b64) {
        if (b64 == null || b64.length < 2) {
            return new byte[]{};
        }

        int b64Length = b64.length;
        int paddingLength = 0;
        if (b64[b64Length - 1] == ((byte) '=')) {
            paddingLength++;
            if (b64[b64Length - 2] == ((byte) '=')) {
                paddingLength++;
            }
        }

        int noPaddingLength = b64.length - paddingLength;
        int resLength = (int) (noPaddingLength / 1.3333f);
        byte[] data = new byte[resLength];

        int index = 0;
        int b0 = 0;
        int b1 = 0;

        for (int i = 0, k = 0; i < resLength;) {

            if (i >= resLength) {
                break;
            }

            index = getIndex((char) b64[k], B64_STR, k);

            b0 = index << 2;
            index = getIndex((char) b64[++k], B64_STR, k);
            b1 = index >>> 4;
            data[i++] = (byte) (b0 | b1);

            if (i >= resLength) {
                break;
            }

            b0 = (index & 0b1111) << 4;
            index = getIndex((char) b64[++k], B64_STR, k);
            b1 = index >>> 2;
            data[i++] = (byte) (b0 | b1);

            if (i >= resLength) {
                break;
            }

            b0 = (index & 0b11) << 6;
            b1 = getIndex((char) b64[++k], B64_STR, k);
            data[i++] = (byte) (b0 | b1);
            k++;

        }

        return data;
    }

    private static int getIndex(char c, String source, int indexOfChar) {
        int index = -1;
        index = B64_STR.indexOf(c);
        if (index == -1) {
            throw new IllegalArgumentException("Illegal char '" + c + "' encountered at index " + indexOfChar);
        }

        return index;
    }

    public static int getB64LengthForInputLength(int inputLength, boolean padded) {
        float fl = inputLength * 1.3333f;
        int length = ((int) fl) + ((fl == (int) fl) ? 0 : 1);

        if (padded) {
            length += (length & 0b11);
        }

        return length;
    }

    // ##############################################################
    /**
     * Returns null if input string is null.
     *
     * @param str
     * @return
     */
    public static byte[] getUTF8(String str) {
        return str != null ? str.getBytes(StandardCharsets.UTF_8) : null;
    }

    public static String getUTF8(byte[] data) {
        return new String(data, StandardCharsets.UTF_8);
    }

    /**
     * Uses UTF-8 encoding.
     *
     * @param arr
     * @return
     */
    public static byte[] toBytes(char[] arr) {
        ByteBuffer bb = Encoder.getCharset("UTF-8").encode(CharBuffer.wrap(arr));
        byte[] b = new byte[bb.remaining()];
        bb.get(b);
        Bytes.zeroOut(bb.array());
        return b;
    }

    /**
     * Input byte array must be UTF-8 encoded.
     *
     * @param arr
     * @return
     */
    public static char[] toChars(byte[] arr) {
        ByteBuffer bb = ByteBuffer.wrap(arr);
        CharBuffer cb = Encoder.getCharset("UTF-8").decode(bb);
        char[] c = new char[cb.remaining()];
        cb.get(c, cb.position(), cb.remaining());
        Bytes.zeroOut(cb.array());
        return c;
    }

    public static Charset getCharset(String charSet) {
        return charSet.equals(StandardCharsets.UTF_8.name()) || StandardCharsets.UTF_8.aliases().contains(charSet) ? StandardCharsets.UTF_8
                : charSet.equals(StandardCharsets.ISO_8859_1.name()) || StandardCharsets.ISO_8859_1.aliases().contains(charSet) ? StandardCharsets.ISO_8859_1
                : charSet.equals(StandardCharsets.US_ASCII) || StandardCharsets.US_ASCII.aliases().contains(charSet) ? StandardCharsets.US_ASCII
                : charSet.equals(StandardCharsets.UTF_16.name()) || StandardCharsets.UTF_16.aliases().contains(charSet) ? StandardCharsets.UTF_16
                : charSet.equals(StandardCharsets.UTF_16BE.name()) || StandardCharsets.UTF_16BE.aliases().contains(charSet) ? StandardCharsets.UTF_16BE
                : charSet.equals(StandardCharsets.UTF_16LE.name()) || StandardCharsets.UTF_16LE.aliases().contains(charSet) ? StandardCharsets.UTF_16LE : null;
    }
}
