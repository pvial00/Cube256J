import java.io.*;
import java.lang.*;
import java.util.*;

public class Cube {
   public int size_factor = 3;
   public int alphabet_size = 256;
   public ArrayList<ArrayList<ArrayList<Integer>>> state = new ArrayList<ArrayList<ArrayList<Integer>>>();
   public void gen_cube(int depth, int width, int length) {
      for (int z = 0; z < depth; z++) {
          ArrayList<ArrayList<Integer>> section = new ArrayList<ArrayList<Integer>>();
	  for (int y = 0; y < width; y++) {
              ArrayList<Integer> alphabet = new ArrayList<Integer>();
	      for (int x = 0; x < length; x++) {
	          alphabet.add(x);
	      }
	      for (int mod=0; mod < y; mod++) {
                 int shift;
		 shift = alphabet.get(0);
		 alphabet.remove(0);
		 alphabet.add(shift);
		 shift = alphabet.get(2);
		 alphabet.remove(2);
		 alphabet.add(127, shift);
	      }
	      section.add(alphabet);
	  }
	  this.state.add(section);
      }
  }
  public void key_cube(byte[] key) {
     int key_sub;
     int sized_pos;
     int shuffle;
     for (int z = 0; z < this.state.size(); z++) {
        for (int i = 0; i < key.length; i++) {
	    for (int x = 0; x < this.state.get(z).size(); x++) {
	        key_sub = this.state.get(z).get(x).get(((int)key[i] & 0xff));
		this.state.get(z).get(x).remove((int)key[i] & 0xff);
		this.state.get(z).get(x).add(key_sub);
		for (int y = 0; y < (int)key[i]; y++) {
                    if (y % 2 == 0) {
                        shuffle = this.state.get(z).get(x).get(0);
                        this.state.get(z).get(x).remove(0);
			this.state.get(z).get(x).add(shuffle);
                        shuffle = this.state.get(z).get(x).get(2);
                        this.state.get(z).get(x).remove(2);
			this.state.get(z).get(x).add(127, shuffle);
                     }
		}
	    }
        }
    }

    ArrayList<ArrayList<Integer>> section;
    for (int i = 0; i < key.length; i++) {
       sized_pos = (int)key[i] % size_factor;
       for (int y = 0; y < (int)key[i]; y++) {
          section = this.state.get(sized_pos);
	  this.state.remove(sized_pos);
	  this.state.add(section);
       }
   }
  }
  public byte[] key_scheduler (byte[] key) {
       int sized_pos, sub;
       ArrayList<ArrayList<Integer>> section;
       ArrayList<Integer> sub_alpha;
       byte[] sub_key = new byte[key.length];
       for (int i = 0; i < key.length; i++) {
           sized_pos = ((int)key[i] & 0xff) % size_factor;
	   sub = this.state.get(sized_pos).get(sized_pos).get((int)key[i] & 0xff);
	   this.state.get(sized_pos).get(sized_pos).remove((int)key[i] & 0xff);
	   this.state.get(sized_pos).get(sized_pos).add(sub);
	   sub_key[i] = (byte)sub;
       }
       return sub_key;
   }

   public void morph_cube (int counter, byte[] k) {
       int shift;
       int ke = 0;
       ArrayList<ArrayList<Integer>> section_shift;
       int mod_value = counter % alphabet_size;
       for (int z = 0; z < this.state.size(); z++) {
          for (int i = 0; i < k.length; i++) {
             for (int y = 0; y < this.state.get(z).size(); y++) {
		 Collections.swap(this.state.get(z).get(y), mod_value, ((int)k[i] & 0xff));
                 ke = (int)k[i] & 0xff;
             }
	  }
	  shift = ke % this.size_factor;
	  section_shift = this.state.get(shift);
	  this.state.remove(shift);
	  this.state.add(section_shift);
       }
   }
   
   public byte[] encrypt(byte[] data, byte[] key, byte[] nonce) {
       this.gen_cube(this.size_factor, this.size_factor, this.alphabet_size);
       this.key_cube(key);
       this.key_cube(nonce);
       int sub, sub_pos, shift;
       byte[] sub_key = new byte[key.length];
       sub_key = key;
       int ctr = 0;
       for (Byte b: data) {
          sub = b.intValue() & 0xff;
	  for (int z = 0; z < this.state.size(); z++) {
             for (int y = 0; y < this.state.get(z).size(); y++) {
	        sub_pos = sub;
		sub = this.state.get(z).get(y).get(sub_pos);
		shift = this.state.get(z).get(y).get(0);
		this.state.get(z).get(y).remove(0);
		this.state.get(z).get(y).add(shift);
	     }
	  }
	  sub_key = this.key_scheduler(sub_key);
	  this.morph_cube(ctr, sub_key);
	  data[ctr] = (byte)sub;
	  ctr += 1;
       }
       return data;
    }

   public byte[] decrypt(byte[] data, byte[] key, byte[] nonce) {
       this.gen_cube(this.size_factor, this.size_factor, this.alphabet_size);
       this.key_cube(key);
       this.key_cube(nonce);
       int sub, shift;
       byte[] sub_key = new byte[key.length];
       sub_key = key;
       int ctr = 0;
       for (Byte b: data) {
          sub = b.intValue() & 0xff;
	  for (int z = (this.state.size() - 1); z >= 0; z--) {
             for (int y = (this.state.get(z).size() - 1); y >= 0; y--) {
		sub = this.state.get(z).get(y).indexOf(sub);
		shift = this.state.get(z).get(y).get(0);
		this.state.get(z).get(y).remove(0);
		this.state.get(z).get(y).add(shift);
	     }
	  }
	  sub_key = this.key_scheduler(sub_key);
	  this.morph_cube(ctr, sub_key);
	  data[ctr] = (byte)sub;
	  ctr += 1;
       }
       return data;
    }
}
