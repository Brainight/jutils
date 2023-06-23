

package brainight.jutils.bytecode.struct;

/**
 * Github: https://github.com/Brainight
 * @author Brainight
 */
public class IntegerInfo implements CpInfo{

    private int bytes;

    public IntegerInfo(int bytes) {
        this.bytes = bytes;
    }
    
    public int getBytes() {
        return bytes;
    }

    public void setBytes(int bytes) {
        this.bytes = bytes;
    }
    
    
    @Override
    public CpInfoConstantTypeValues getConstantType() {
        return CpInfo.CpInfoConstantTypeValues.CONSTANT_Integer;
    }

}
