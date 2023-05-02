package brainight.jutils.tests;

import brainight.jutils.Bytes;
import java.nio.ByteBuffer;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 *
 * @author Brainight
 */
public class BytesTester {

    @BeforeAll
    static void setup() {
        System.out.println("Starting " + BytesTester.class + "tests");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 27, Integer.MAX_VALUE / 2, -1, -1000, Integer.MAX_VALUE + 2})
    void intToBytes(int num) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putInt(num);
        byte[] res = Bytes.intToBytesBE(num);
        assertTrue(Bytes.contentEquals(res, bb.array()));
    }
}
