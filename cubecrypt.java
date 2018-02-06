import java.io.*;
import java.util.*;
import java.security.SecureRandom;

public class cubecrypt extends Cube {
    public static void main(String[] args) {
       String mode = args[0];
       String infile = args[1];
       String outfile = args[2];
       String input_key = args[3];
       byte[] key = input_key.getBytes();
       int nonce_length = 16;
       try {
	   File f = new File(infile);
	   byte[] data = new byte[(int)f.length()];
	   FileInputStream in = new FileInputStream(f);
	   in.read(data);
	   in.close();
           byte[] ctxt = new byte[(int)data.length];
	   Cube cube = new Cube();
	   byte[] nonce = new byte[nonce_length];
	   FileOutputStream fo = new FileOutputStream(outfile);
	   if (mode.equals("encrypt")) {
              SecureRandom sr = new SecureRandom();
	      sr.nextBytes(nonce);
              ctxt = cube.encrypt(data, key, nonce);
	      fo.write(nonce);
	      fo.write(ctxt);
	      fo.close();
	   }
	   else if (mode.equals("decrypt")) {
	      byte[] msg = new byte[(int)data.length - nonce_length];
	      nonce = Arrays.copyOfRange(data, 0, nonce_length);
	      msg = Arrays.copyOfRange(data, nonce_length, (data.length - 1));
              ctxt = cube.decrypt(msg, key, nonce);
	      fo.write(ctxt);
	      fo.close();
	   }
        }catch(IOException e) {
	    e.printStackTrace();
	}
    }
}
