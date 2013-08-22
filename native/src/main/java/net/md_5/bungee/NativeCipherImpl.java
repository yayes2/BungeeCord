package net.md_5.bungee;

class NativeCipherImpl
{

    native void decrypt(byte[] key, long in, long out, int length);

    native void encrypt(byte[] key, long in, long out, int length);
}
