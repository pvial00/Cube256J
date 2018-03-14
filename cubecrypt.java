import java.io.*;
import java.util.*;
import java.security.SecureRandom;

public class cubecrypt extends CubeFile {
    public static void main(String[] args) {
       int nonce_length = 16;
       int key_length = 16;
       int kdf_iterations = 69;
       int buffersize = 4096;
       String mode = args[0];
       String infile = args[1];
       String outfile = args[2];
       String input_key = args[3];
       CubeFile cube = new CubeFile();
       CubeKDF cubekdf = new CubeKDF();
       byte[] key = cubekdf.genkey(input_key.getBytes(), key_length, kdf_iterations);
       byte[] nonce = new byte[nonce_length];
       if (mode.equals("encrypt")) {
           SecureRandom sr = new SecureRandom();
           sr.nextBytes(nonce);
           cube.encrypt(infile, outfile, key, nonce, buffersize);
       }
       else if (mode.equals("decrypt")) {
          cube.decrypt(infile, outfile, key, nonce_length, buffersize);
       }
    }
}
