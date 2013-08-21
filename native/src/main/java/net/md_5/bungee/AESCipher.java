package net.md_5.bungee;

import io.netty.buffer.ByteBuf;
import java.security.Key;

public class AESCipher
{

    private long cipherAddress;

    public void init(Key key, boolean forEncryption)
    {
        // Just init with the encoded form of the key, we can assume OpenSSL will be able to do *something* with it
        init( key.getEncoded(), forEncryption );
    }

    public void cipher(ByteBuf in, ByteBuf out)
    {
        // Smoke tests
        in.memoryAddress();
        out.memoryAddress();
        // Store how many bytes we can caipher
        int length = in.readableBytes();
        // It is important to note that in AES CFB-8 mode, the number of read bytes, is the number of outputted bytes
        if ( out.writableBytes() < length )
        {
            out.capacity( length );
        }
        // Cipher the bytes
        cipher( cipherAddress, in.memoryAddress() + in.readerIndex(), length, out.memoryAddress() + out.writerIndex() );
        // Go to the end of the buffer, all bytes would of been read
        in.readerIndex( in.writerIndex() );
        // Add the number of ciphered bytes to our position
        out.writerIndex( out.writerIndex() + length );
    }

    private native void init(byte[] key, boolean forEncryption);

    private native void cipher(long cipherAddress, long inAddress, int inLength, long outAddress);
}
