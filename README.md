# Cube256 implementation in Java v0.5
Cube256 is an advanced substition stream cipher.  This implementation aims to bring better perforance to the Cube family of ciphers.

This cipher is categorized as an Expensive Cipher.  If you have the resources to encrypt large files with Cube, you'll be sure your secret will be safe for ages to come.

No attack of any kind has been proposed against Cube.  Cube passes NIST and DieHarder statistical tests.

(Warning: Side channel attacks are possible against Cube. I could think of a few.)

# Class Usage:
Cube cube = new Cube();  
cube.encrypt(data, key, nonce);  
cube.decrypt(data, key, nonce);  

# Script usage:
cubecrypt <encrypt/decrypt> <inputfile> <outputfile> <password>

