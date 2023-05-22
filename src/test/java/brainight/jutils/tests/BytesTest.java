package brainight.jutils.tests;

import brainight.jutils.Bytes;
import java.nio.ByteBuffer;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 *
 * @author Brainight
 */
public class BytesTest {

    @BeforeAll
    static void setup() {
        System.out.println("Starting " + BytesTest.class + "tests");

    }

    @ParameterizedTest
    @ValueSource(ints = {0, 27, Integer.MAX_VALUE / 2, -1, -1000, Integer.MAX_VALUE + 2})
    @Order(0)
    void intToBytes(int num) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putInt(num);
        byte[] res = Bytes.intToBytesBE(num);
        assertTrue(Bytes.contentEquals(res, bb.array()));
    }

    @Test
    @Order(1)
    void toStringBytes() {
        byte[] b = new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        String res = Bytes.toString(b);
        assertTrue(res.equals("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]"));
    }

    @Test
    @Order(2)
    void getBytes() {
        byte[] b = new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        byte[] r0 = Bytes.getBytes(b);
        System.out.println("\ngetBytes -----------------");
        System.out.println(Bytes.toString(r0));
        assertTrue(Arrays.equals(b, r0));
    }

    @Test
    @Order(3)
    void getBytesFrom() {
        byte[] b = new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        byte[] r0 = Bytes.getBytes(b, 10);
        System.out.println("\ngetBytesFrom -----------------");
        System.out.println(Bytes.toString(r0));
        assertTrue(Arrays.equals(r0, new byte[]{10, 11, 12, 13, 14, 15}));
    }

    @Test
    @Order(4)
    void getBytesFromInverse() {
        byte[] b = new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        byte[] r0 = Bytes.getBytes(b, -10);
        System.out.println("\ngetBytesFromInverse -----------------");
        System.out.println(Bytes.toString(r0));
        assertTrue(Arrays.equals(r0, new byte[]{6, 7, 8, 9, 10, 11, 12, 13, 14, 15}));
    }

    @Test
    @Order(5)
    void getBytesFromTo() {
        byte[] b = new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        byte[] r0 = Bytes.getBytes(b, 10, 15);
        System.out.println("\ngetBytesFromTo -----------------");
        System.out.println(Bytes.toString(r0));
        assertTrue(Arrays.equals(r0, new byte[]{10, 11, 12, 13, 14}));
    }

    @Test
    @Order(6)
    void getBytesFromToInverse() {
        byte[] b = new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        byte[] r0 = Bytes.getBytes(b, 7, -12);
        System.out.println("\ngetBytesFromToInverse -----------------");
        System.out.println(Bytes.toString(r0));
        assertTrue(Arrays.equals(r0, new byte[]{7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3,}));
    }

    @Test
    @Order(7)
    void getBytesFromToJump() {
        byte[] b = new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        byte[] r0 = Bytes.getBytes(b, 2, 16, 5);
        System.out.println("\ngetBytesFromToJump -----------------");
        System.out.println(Bytes.toString(r0));
        assertTrue(Arrays.equals(r0, new byte[]{2, 7, 12}));
    }
}
