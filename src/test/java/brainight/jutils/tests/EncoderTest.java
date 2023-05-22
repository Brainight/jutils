package brainight.jutils.tests;

import brainight.jutils.Bytes;
import brainight.jutils.Encoder;
import java.util.Base64;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 *
 * @author Brainight
 */
public class EncoderTest {

    @BeforeAll
    static void setup() {
        System.out.println("Starting " + EncoderTest.class + "tests");
    }

    @Test
    void testToHex() {
        String res = "48656c6c6f";
        byte[] bhex = Encoder.toHex("Hello".getBytes());
        String bhexS = Encoder.getUTF8(bhex);
        assertEquals(res, bhexS);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Hello World", "This is a test", "!\"·$%&/()=?¿^*¨Ç'¡`+´ç.-,;:_", "áéíúóàèùìòäëüïö", "Hexadecimal notation is used as a human-friendly representation of binary values in computer programming and digital electronics. Most programming languages such as Java, ASP.NET, C++, Fortran etc have built-in functions that convert to and from hex format."})
    void testEncodeB64(String text) {
        System.out.println("Executing b64 encoding test");
        byte[] btext = Encoder.getUTF8(text);
        long s, e;
        s = System.nanoTime();
        byte[] d0 = Encoder.encodeB64(btext, true);
        e = System.nanoTime();
        System.out.println("jutils => " + (e - s) + "ns");

        s = System.nanoTime();
        byte[] d1 = Base64.getEncoder().encode(btext);
        e = System.nanoTime();
        System.out.println("java => " + (e - s) + "ns");
        System.out.println("--------------------------");

        assertTrue(Bytes.contentEquals(d0, d1));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Hello World", "This is a test", "!\"·$%&/()=?¿^*¨Ç'¡`+´ç.-,;:_", "áéíúóàèùìòäëüïö", "Hexadecimal notation is used as a human-friendly representation of binary values in computer programming and digital electronics. Most programming languages such as Java, ASP.NET, C++, Fortran etc have built-in functions that convert to and from hex format."})
    void testEncodeB64Str(String text) {
        System.out.println("Executing b64 encoding test string");
        byte[] btext = Encoder.getUTF8(text);
        long s, e;
        s = System.nanoTime();
        String d0 = Encoder.encodeB64Str(btext);
        e = System.nanoTime();
        System.out.println("jutils => " + (e - s) + "ns");

        s = System.nanoTime();
        String d1 = Base64.getEncoder().encodeToString(btext);
        e = System.nanoTime();
        System.out.println("java => " + (e - s) + "ns");
        System.out.println("--------------------------");

        assertEquals(d0, d1);
    }

    @ParameterizedTest
    @ValueSource(strings = {"SGVsbG8gV29ybGQ=", "VGhpcyBpcyBhIHRlc3Q=", "ISLCtyQlJi8oKT0/wr9eKsKow4cnwqFgK8K0w6cuLSw7Ol8=", "w6HDqcOtw7rDs8Ogw6jDucOsw7LDpMOrw7zDr8O2", "SGV4YWRlY2ltYWwgbm90YXRpb24gaXMgdXNlZCBhcyBhIGh1bWFuLWZyaWVuZGx5IHJlcHJlc2VudGF0aW9uIG9mIGJpbmFyeSB2YWx1ZXMgaW4gY29tcHV0ZXIgcHJvZ3JhbW1pbmcgYW5kIGRpZ2l0YWwgZWxlY3Ryb25pY3MuIE1vc3QgcHJvZ3JhbW1pbmcgbGFuZ3VhZ2VzIHN1Y2ggYXMgSmF2YSwgQVNQLk5FVCwgQysrLCBGb3J0cmFuIGV0YyBoYXZlIGJ1aWx0LWluIGZ1bmN0aW9ucyB0aGF0IGNvbnZlcnQgdG8gYW5kIGZyb20gaGV4IGZvcm1hdC4="})
    void testDecodeB64(String text) {
        System.out.println("Executing b64 decoding test");
        byte[] btext = Encoder.getUTF8(text);
        long s, e;
        s = System.nanoTime();
        byte[] d0 = Encoder.decodeB64(btext);
        e = System.nanoTime();
        System.out.println("jutils => " + (e - s) + "ns");

        s = System.nanoTime();
        byte[] d1 = Base64.getDecoder().decode(btext);
        e = System.nanoTime();
        System.out.println("java => " + (e - s) + "ns");
        System.out.println("--------------------------");

        assertTrue(Bytes.contentEquals(d0, d1));
    }
}
