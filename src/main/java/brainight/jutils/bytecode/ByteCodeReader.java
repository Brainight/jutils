package brainight.jutils.bytecode;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Github: https://github.com/Brainight
 *
 * @author Brainight
 */
public class ByteCodeReader {

    public ClassFileInfo getClassFileInfo(InputStream is) throws IOException{
        if (is == null) {
            return null;
        }

        byte[] chunk = new byte[1024];
        ByteBuffer bb = ByteBuffer.wrap(chunk);
        read(chunk, is);
        return null;
    }
    
    
    
    
    private int read(byte[] buffer, InputStream is) throws IOException{
        return is.read(buffer, 0, buffer.length);
    }
}
