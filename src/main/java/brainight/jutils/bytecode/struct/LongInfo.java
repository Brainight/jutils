

package brainight.jutils.bytecode.struct;

/**
 * Github: https://github.com/Brainight
 * @author Brainight
 */
public class LongInfo implements CpInfo{

    private int highBytes;
    private int lowBytes;

    public LongInfo(int highBytes, int lowBytes) {
        this.highBytes = highBytes;
        this.lowBytes = lowBytes;
    }

    public int getHighBytes() {
        return highBytes;
    }

    public void setHighBytes(int highBytes) {
        this.highBytes = highBytes;
    }

    public int getLowBytes() {
        return lowBytes;
    }

    public void setLowBytes(int lowBytes) {
        this.lowBytes = lowBytes;
    }
    
    @Override
    public CpInfoConstantTypeValues getConstantType() {
        return CpInfo.CpInfoConstantTypeValues.CONSTANT_Long;
    }

}