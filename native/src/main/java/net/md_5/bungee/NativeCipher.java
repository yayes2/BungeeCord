package net.md_5.bungee;

import io.netty.buffer.ByteBuf;
import java.security.Key;

public class NativeCipher
{

    private final NativeCipherImpl nativeCipher = new NativeCipherImpl();

    public void cipher(boolean forEncryption, byte[] key, ByteBuf in, ByteBuf out)
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
        if ( forEncryption )
        {
            nativeCipher.encrypt( key, in.memoryAddress() + in.readerIndex(), out.memoryAddress() + out.writerIndex(), length );
        } else
        {
            nativeCipher.decrypt( key, in.memoryAddress() + in.readerIndex(), out.memoryAddress() + out.writerIndex(), length );
        }
        // Go to the end of the buffer, all bytes would of been read
        in.readerIndex( in.writerIndex() );
        // Add the number of ciphered bytes to our position
        out.writerIndex( out.writerIndex() + length );
    }
}
