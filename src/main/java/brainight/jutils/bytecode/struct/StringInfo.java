

package brainight.jutils.bytecode.struct;

/**
 * Github: https://github.com/Brainight
 * @author Brainight
 */
public class StringInfo implements CpInfo{

    private char stringIndex;

    public StringInfo(char stringIndex) {
        this.stringIndex = stringIndex;
    }

    public char getStringIndex() {
        return stringIndex;
    }

    public void setStringIndex(char stringIndex) {
        this.stringIndex = stringIndex;
    }
    
    
    
    @Override
    public CpInfoConstantTypeValues getConstantType() {
        return CpInfo.CpInfoConstantTypeValues.CONSTANT_String;
    }

}
