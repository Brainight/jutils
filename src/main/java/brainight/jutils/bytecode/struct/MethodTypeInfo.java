

package brainight.jutils.bytecode.struct;

/**
 * Github: https://github.com/Brainight
 * @author Brainight
 */
public class MethodTypeInfo implements CpInfo{

    private char descriptorIndex;

    public MethodTypeInfo(char descriptorIndex) {
        this.descriptorIndex = descriptorIndex;
    }

    public char getDescriptorIndex() {
        return descriptorIndex;
    }

    public void setDescriptorIndex(char descriptorIndex) {
        this.descriptorIndex = descriptorIndex;
    }
    
    
    @Override
    public CpInfoConstantTypeValues getConstantType() {
        return CpInfo.CpInfoConstantTypeValues.CONSTANT_MethodType;
    }

}
