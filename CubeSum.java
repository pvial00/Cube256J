import java.io.*;
import java.lang.*;
import java.util.*;

public class CubeSum extends Cube {
    public int hash_length = 16;
    Cube cube = new Cube();
    byte[] iv = new byte[hash_length];
    public byte[] digest(byte[] data, byte[] key) {
        byte[] dig = new byte[hash_length];
        if (key != null) {
           dig = this.cube.encrypt(this.iv, data, key);
        }
        else {
           dig = this.cube.encrypt(this.iv, data, this.iv);
        }
        return dig;
    }
}
