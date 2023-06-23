

package brainight.jutils.bytecode.struct;

/**
 * Github: https://github.com/Brainight
 * @author Brainight
 */
public class ClassInfo implements CpInfo{

    /**
     * //The value of the name_index item must be a valid index into the constant_pool table.
     * The constant_pool entry at that index must be a CONSTANT_Utf8_info structure 
     * representing a valid binary class or interface name encoded in internal form. 
     */
    private char nameIndex; // u2

    public ClassInfo(char nameIndex) {
        this.nameIndex = nameIndex;
    }

    public char getNameIndex() {
        return nameIndex;
    }

    public void setNameIndex(char nameIndex) {
        this.nameIndex = nameIndex;
    }
    
    
    @Override
    public CpInfoConstantTypeValues getConstantType() {
        return CpInfo.CpInfoConstantTypeValues.CONSTANT_Class;
    }

}
