import java.io.*;
import java.util.*;
import java.security.SecureRandom;

public class cubecrypt extends Cube {
    public static void main(String[] args) {
       int nonce_length = 16;
       int key_length = 16;
       int kdf_iterations = 69;
       String mode = args[0];
       String infile = args[1];
       String outfile = args[2];
       String input_key = args[3];
       byte[] key = new byte[key_length];
       try {
	   File f = new File(infile);
	   byte[] data = new byte[(int)f.length()];
	   FileInputStream in = new FileInputStream(f);
	   in.read(data);
	   in.close();
           byte[] ctxt = new byte[(int)data.length - 1];
	   Cube cube = new Cube();
	   CubeKDF cubekdf = new CubeKDF();
	   key = cubekdf.genkey(input_key.getBytes(), key_length, kdf_iterations);
	   byte[] nonce = new byte[nonce_length];
	   FileOutputStream fo = new FileOutputStream(outfile);
	   if (mode.equals("encrypt")) {
	      byte[] msg = new byte[(int)(data.length -1)];
              SecureRandom sr = new SecureRandom();
	      sr.nextBytes(nonce);
	      msg = Arrays.copyOfRange(data, 0, (data.length - 1));
              ctxt = cube.encrypt(msg, key, nonce);
	      fo.write(nonce);
	      fo.write(ctxt);
	      fo.close();
	   }
	   else if (mode.equals("decrypt")) {
	      byte[] msg = new byte[(int)data.length - nonce_length];
	      nonce = Arrays.copyOfRange(data, 0, nonce_length);
	      msg = Arrays.copyOfRange(data, nonce_length, data.length);
              ctxt = cube.decrypt(msg, key, nonce);
	      fo.write(ctxt);
	      fo.close();
	   }
        }catch(IOException e) {
	    e.printStackTrace();
	}
    }
}
