

package brainight.jutils.bytecode.struct;

/**
 * Github: https://github.com/Brainight
 * @author Brainight
 */
public class Utf8Info implements CpInfo{

    private char length;
    private byte[] data;

    public Utf8Info(char length, byte[] data) {
        this.length = length;
        this.data = data;
    }

    public char getLength() {
        return length;
    }

    public void setLength(char length) {
        this.length = length;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
    
    
    @Override
    public CpInfoConstantTypeValues getConstantType() {
        return CpInfo.CpInfoConstantTypeValues.CONSTANT_Utf8;
    }

}
