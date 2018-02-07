# Cube256 implementation in Java v0.5
Cube256 is an advanced substition stream cipher.  This implementation aims to bring better perforance to the Cube family of ciphers.

# Class Usage:
Cube cube = new Cube();  
cube.encrypt(data, key, nonce);  
cube.decrypt(data, key, nonce);  

# Script usage:
cubecrypt <encrypt/decrypt> <inputfile> <outputfile> <password>

