

package brainight.jutils.bytecode;

import brainight.jutils.bytecode.struct.CpInfo;
import java.util.List;

/**
 * Refer to: https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.4
 * Github: https://github.com/Brainight
 * @author Brainight
 */
public class ClassFileInfo {

    protected int magicNumber; //u4
    protected int minorVersion; //u2
    protected int majorVersion; //u2
    protected int cpCount; //u4 (Constant Pool)
    protected List<CpInfo> constantPool; // ?
    protected int accessFlags; //u2
    
}
