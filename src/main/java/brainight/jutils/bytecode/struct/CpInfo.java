

package brainight.jutils.bytecode.struct;

/**
 * Github: https://github.com/Brainight
 * @author Brainight
 */
public interface CpInfo {

    public enum CpInfoConstantTypeValues{
        CONSTANT_Class((byte)7),
        CONSTANT_Fieldref((byte)9),
        CONSTANT_Methodref((byte)10),
        CONSTANT_InterfaceMethodref((byte)11),
        CONSTANT_String((byte)8),
        CONSTANT_Integer((byte)3),
        CONSTANT_Float((byte)4),
        CONSTANT_Long((byte)5),
        CONSTANT_Double((byte)6),
        CONSTANT_NameAndType((byte)12),
        CONSTANT_Utf8((byte)1),
        CONSTANT_MethodHandle((byte)15),
        CONSTANT_MethodType((byte)16),
        CONSTANT_InvokeDynamic((byte)18);
        
        public final byte value;
        private CpInfoConstantTypeValues(byte value){
            this.value = value;
        }
    }
    
    CpInfoConstantTypeValues getConstantType();
    
}
