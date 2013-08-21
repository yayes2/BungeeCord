package net.md_5.bungee;

public class AESCipher
{

    public native void init(byte[] key, boolean forEncryption);

    public native void cipher(long inAddress, int inLength, long outAddress);
}
