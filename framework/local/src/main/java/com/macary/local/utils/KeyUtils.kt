package com.macary.local.utils

import android.content.Context
import android.os.Build
import android.util.Base64
import androidx.security.crypto.MasterKey
import com.macary.local.BuildConfig
import com.macary.local.KeyStorable
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

object KeyUtils {

    fun generateMasterKey(context: Context): MasterKey =
        MasterKey.Builder(context, BuildConfig.KEY_ALIAS).apply {
            setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            setRequestStrongBoxBacked(true)
        }.build()

    fun generateRawDbKey(): ByteArray = generateSecureKey(BuildConfig.BYTE_SIZE)

    fun getRawDbKey(storable: KeyStorable): ByteArray {
        val iv = Base64.decode(storable.iv, Base64.DEFAULT)
        val key = Base64.decode(storable.key, Base64.DEFAULT)
        val salt = Base64.decode(storable.salt, Base64.DEFAULT)
        val secret: SecretKey = generateSecretKey(salt)

        val cipher = Cipher.getInstance(BuildConfig.CIPHER)
        cipher.init(Cipher.DECRYPT_MODE, secret, IvParameterSpec(iv))
        return cipher.doFinal(key)
    }

    fun toKeyStorable(dbKey: ByteArray): KeyStorable {
        val salt = generateSecureKey(BuildConfig.SALT_SIZE)
        val secretKey = generateSecretKey(salt)

        val cipher = Cipher.getInstance(BuildConfig.CIPHER)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)

        val cipherText = cipher.doFinal(dbKey)

        return KeyStorable(
            Base64.encodeToString(cipher.iv, Base64.DEFAULT),
            Base64.encodeToString(cipherText, Base64.DEFAULT),
            Base64.encodeToString(salt, Base64.DEFAULT)
        )
    }

    private fun generateSecureKey(size: Int): ByteArray =
        ByteArray(size).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                SecureRandom.getInstanceStrong().nextBytes(this)
            } else {
                SecureRandom().nextBytes(this)
            }
        }

    private fun generateSecretKey(salt: ByteArray): SecretKeySpec {
        val factory = SecretKeyFactory.getInstance(BuildConfig.ALGORITHM)
        val spec = PBEKeySpec(BuildConfig.KEY_DB.toCharArray(), salt, 65536, 256)
        val tmp = factory.generateSecret(spec)

        return SecretKeySpec(tmp.encoded, BuildConfig.ALGORITHM_TYPE)
    }
}