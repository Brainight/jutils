

package brainight.jutils.bytecode.struct;

/**
 * Github: https://github.com/Brainight
 * @author Brainight
 */
public class InvokeDynamicInfo implements CpInfo{

    private char bootStrapMethodAttrIndex;
    private char nameAndTypeIndex;

    public InvokeDynamicInfo(char bootStrapMethodAttrIndex, char nameAndTypeIndex) {
        this.bootStrapMethodAttrIndex = bootStrapMethodAttrIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    
    public char getBootStrapMethodAttrIndex() {
        return bootStrapMethodAttrIndex;
    }

    public void setBootStrapMethodAttrIndex(char bootStrapMethodAttrIndex) {
        this.bootStrapMethodAttrIndex = bootStrapMethodAttrIndex;
    }

    public char getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }

    public void setNameAndTypeIndex(char nameAndTypeIndex) {
        this.nameAndTypeIndex = nameAndTypeIndex;
    }
    
    
    @Override
    public CpInfoConstantTypeValues getConstantType() {
        return CpInfo.CpInfoConstantTypeValues.CONSTANT_InvokeDynamic;
    }

}
