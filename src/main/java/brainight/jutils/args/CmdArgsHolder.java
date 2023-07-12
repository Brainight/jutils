package brainight.jutils.args;

/**
 *
 * @author Brainight
 */
public class CmdArgsHolder {

    private String[] args;
    private int pos;

    public CmdArgsHolder(String[] args) {
        this.args = args;
        pos = -1;
    }

    public CmdArgsHolder(String[] args, int ipos) {
        this.args = args;
        pos = -1 + ((ipos >= 0) ? ipos : 0);
    }

    public String getCurrent() {
        if (this.pos == -1) {
            return null;
        }
        return args[pos];
    }

    public void jump(int n) {
        if (pos + n <= args.length) {
            pos += n;
        } else {
            throw new IndexOutOfBoundsException("Cannot jump distance '" + n + "'. Current position is: " + pos + ". Num args is: " + args.length);
        }
    }

    public boolean hasNext() {
        return this.pos < args.length - 1;
    }

    public int remainig() {
        return this.args.length - this.pos - 1;
    }

    public String getNext() {
        jump(1);
        return getCurrent();
    }

    public boolean isFirst() {
        return this.pos == 0;
    }

}
