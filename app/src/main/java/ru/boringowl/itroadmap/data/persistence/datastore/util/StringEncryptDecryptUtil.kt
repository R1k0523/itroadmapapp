package ru.boringowl.itroadmap.data.persistence.datastore.util

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties.BLOCK_MODE_GCM
import android.security.keystore.KeyProperties.ENCRYPTION_PADDING_NONE
import android.security.keystore.KeyProperties.KEY_ALGORITHM_AES
import android.security.keystore.KeyProperties.PURPOSE_DECRYPT
import android.security.keystore.KeyProperties.PURPOSE_ENCRYPT
import android.util.Base64
import ru.boringowl.itroadmap.data.common.AppConfig
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject

class StringEncryptDecryptUtil @Inject constructor() {
    private val securityKeyAlias = AppConfig.securityKeyAlias
    private val provider = "AndroidKeyStore"
    private val ivLength = 12
    private val tagLength = 128

    private val keyStore by lazy {
        KeyStore.getInstance(provider).apply {
            load(null)
        }
    }
    private val keyGenerator by lazy {
        KeyGenerator.getInstance(KEY_ALGORITHM_AES, provider)
    }

    private fun createCipher() =
        Cipher.getInstance("$KEY_ALGORITHM_AES/$BLOCK_MODE_GCM/$ENCRYPTION_PADDING_NONE")

    fun encryptData(text: String): String {
        val secretKey = getSecretKey() ?: generateSecretKey()
        val cipher = createCipher()
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val iv = cipher.iv.copyOf()
        val ciphertext = cipher.doFinal(text.toByteArray(Charsets.UTF_8))
        return joinIvAndCipherText(iv, ciphertext)
    }

    fun decryptData(encrypted: String): String {
        val (iv, cipherText) = extractIvAndCipherText(encrypted) ?: error("Invalid data stored")
        val secretKey = getSecretKey()
        val cipher = createCipher()
        cipher.init(Cipher.DECRYPT_MODE, secretKey, GCMParameterSpec(tagLength, iv))
        return cipher.doFinal(cipherText).toString(Charsets.UTF_8)
    }

    private fun extractIvAndCipherText(encryptedText: String): Pair<ByteArray, ByteArray>? =
        try {
            val combined = encryptedText.decodeBase64()
            val iv = combined.copyOfRange(0, ivLength)
            val ciphertext = combined.copyOfRange(ivLength, combined.size)
            iv to ciphertext
        } catch (t: Throwable) {
            null
        }

    private fun joinIvAndCipherText(iv: ByteArray, ciphertext: ByteArray): String =
        (iv + ciphertext).encodeBase64()

    private fun generateSecretKey(): SecretKey {
        return keyGenerator.apply {
            init(
                KeyGenParameterSpec
                    .Builder(securityKeyAlias, PURPOSE_ENCRYPT or PURPOSE_DECRYPT)
                    .setBlockModes(BLOCK_MODE_GCM)
                    .setEncryptionPaddings(ENCRYPTION_PADDING_NONE)
                    .build()
            )
        }.generateKey()
    }

    private fun getSecretKey(): SecretKey? =
        (keyStore.getEntry(securityKeyAlias, null) as KeyStore.SecretKeyEntry?)?.secretKey

    private fun ByteArray.encodeBase64(): String = Base64.encodeToString(this, Base64.DEFAULT)

    private fun String.decodeBase64(): ByteArray = Base64.decode(this, Base64.DEFAULT)
}