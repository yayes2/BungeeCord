package net.md_5.bungee;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.junit.Assert;
import org.junit.Test;

public class NativeCipherTest
{

    private final byte[] plainBytes = "This is a test".getBytes();
    private final byte[] cipheredBytes = new byte[]
    {
        50, -7, 89, 1, -11, -32, -118, -48, -2, -72, 105, 97, -70, -81
    };
    private final SecretKey secret = new SecretKeySpec( new byte[ 16 ], "AES" );

    @Test
    public void testJDK() throws Exception
    {
        // Create JDK cipher
        Cipher cipher = Cipher.getInstance( "AES/CFB8/NoPadding" );
        AlgorithmParameterSpec iv = new IvParameterSpec( secret.getEncoded() );

        // Encrypt the bytes
        cipher.init( Cipher.ENCRYPT_MODE, secret, iv );
        byte[] ciphered = cipher.doFinal( plainBytes );
        // Assert they were encrypted correctly
        Assert.assertArrayEquals( cipheredBytes, ciphered );

        // Decrypt the bytes
        cipher.init( Cipher.DECRYPT_MODE, secret, iv );
        ciphered = cipher.doFinal( ciphered );
        // Assert that they were decrypted correctly
        Assert.assertArrayEquals( plainBytes, ciphered );
    }

    @Test
    public void testOpenSSL()
    {
        // Create input buf
        ByteBuf nativePlain = Unpooled.directBuffer( plainBytes.length );
        nativePlain.writeBytes( plainBytes );
        // Create expected buf
        ByteBuf nativeCiphered = Unpooled.directBuffer( cipheredBytes.length );
        nativeCiphered.writeBytes( cipheredBytes );
        // Create output buf
        ByteBuf out = Unpooled.directBuffer();
        // Create the cipher
        NativeCipher cipher = new NativeCipher();

        // Encrypt the bytes
        cipher.cipher( true, secret.getEncoded(), nativePlain, out );
        Assert.assertEquals( nativeCiphered, out );

        // Decrypt the bytes
        cipher.cipher( false, secret.getEncoded(), out.copy(), out );
        Assert.assertEquals( nativePlain, out );
    }
}
