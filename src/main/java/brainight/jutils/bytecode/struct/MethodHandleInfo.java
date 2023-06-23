

package brainight.jutils.bytecode.struct;

/**
 * Github: https://github.com/Brainight
 * @author Brainight
 */
public class MethodHandleInfo implements CpInfo{

    private byte referenceKind;
    private char referenceIndex;

    public MethodHandleInfo(byte referenceKind, char referenceIndex) {
        this.referenceKind = referenceKind;
        this.referenceIndex = referenceIndex;
    }

    public byte getReferenceKind() {
        return referenceKind;
    }

    public void setReferenceKind(byte referenceKind) {
        this.referenceKind = referenceKind;
    }

    public char getReferenceIndex() {
        return referenceIndex;
    }

    public void setReferenceIndex(char referenceIndex) {
        this.referenceIndex = referenceIndex;
    }
    
    
    
    @Override
    public CpInfoConstantTypeValues getConstantType() {
        return CpInfo.CpInfoConstantTypeValues.CONSTANT_MethodHandle;
    }

}
