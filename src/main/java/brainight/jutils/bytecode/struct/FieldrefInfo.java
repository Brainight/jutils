

package brainight.jutils.bytecode.struct;

/**
 * 
 * Github: https://github.com/Brainight
 * @author Brainight
 */
public class FieldrefInfo implements CpInfo{

    private char classIndex;
    private char nameAndTypeIndex;

    public FieldrefInfo(char classIndex, char nameAndTypeIndex) {
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public char getClassIndex() {
        return classIndex;
    }

    public void setClassIndex(char classIndex) {
        this.classIndex = classIndex;
    }

    public char getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }

    public void setNameAndTypeIndex(char nameAndTypeIndex) {
        this.nameAndTypeIndex = nameAndTypeIndex;
    }
    
    
    @Override
    public CpInfoConstantTypeValues getConstantType() {
        return CpInfo.CpInfoConstantTypeValues.CONSTANT_Fieldref;
    }

}
