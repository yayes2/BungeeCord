#include "net_md_5_bungee_NativeCipherImpl.h"
#include <openssl/aes.h>
#include <jni.h>

void Java_net_md_15_bungee_NativeCipherImpl_decrypt(JNIEnv* env, jobject obj, jbyteArray key, jlong in, jlong out, jint length) {
    AES_KEY aes_key;
    aes_key = AES_set_decrypt_key(key, env->GetArrayLength(key), &aes_key);
    AES_cfb8_encrypt(in, out, key, length, AES_DECRYPT);
}

void Java_net_md_15_bungee_NativeCipherImpl_encrypt(JNIEnv* env, jobject obj, jbyteArray key, jlong in, jlong out, jint length) {
    AES_KEY aes_key;
    AES_set_encrypt_key(key, env->GetArrayLength(key), &aes_key);
}
