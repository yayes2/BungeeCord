package net.md_5.bungee;

class NativeCipherImpl
{

    /**
     * This method will decrypt some data in AES-CFB8 using the specified key.
     *
     * @param key the key to use for decryption and as the IV
     * @param in the starting memory address for reading data
     * @param out the starting memory address for writing data
     * @param length the length of data to read / write
     */
    native void decrypt(byte[] key, long in, long out, int length);

    /**
     * This method will encrypt some data in AES-CFB8 using the specified key.
     *
     * @param key the key to use for decryption and as the IV
     * @param in the starting memory address for reading data
     * @param out the starting memory address for writing data
     * @param length the length of data to read / write
     */
    native void encrypt(byte[] key, long in, long out, int length);
}
