

package brainight.jutils.bytecode.struct;

/**
 * Github: https://github.com/Brainight
 * @author Brainight
 */
public class NameAndTypeInfo implements CpInfo{

    
    private char nameIndex;
    private char descriptorIndex;

    public NameAndTypeInfo(char nameIndex, char descriptorIndex) {
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
    }

    public char getNameIndex() {
        return nameIndex;
    }

    public void setNameIndex(char nameIndex) {
        this.nameIndex = nameIndex;
    }

    public char getDescriptorIndex() {
        return descriptorIndex;
    }

    public void setDescriptorIndex(char descriptorIndex) {
        this.descriptorIndex = descriptorIndex;
    }
    
    
    @Override
    public CpInfoConstantTypeValues getConstantType() {
        return CpInfo.CpInfoConstantTypeValues.CONSTANT_NameAndType;
    }

}