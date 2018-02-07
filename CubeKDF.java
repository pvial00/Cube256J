import java.io.*;
import java.lang.*;
import java.util.*;

public class CubeKDF extends CubeSum {
    public int key_length = 16;
    public int iterations = 10;
    CubeSum cubesum = new CubeSum();
    public byte[] genkey(byte[] key, int key_length, int kdf_iterations) {
        byte[] h = new byte[key_length];
	h = cubesum.digest(key, null);
	for (int x = 0; x < this.iterations; x++) {
            h = cubesum.digest(h, key);
	}
	return h;
    }
}
