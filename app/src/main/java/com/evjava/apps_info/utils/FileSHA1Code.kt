package com.evjava.apps_info.utils

import java.io.FileInputStream
import java.io.IOException
import java.security.DigestInputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Locale


/**
 * User: zeroleaf
 * Date: 13-10-3
 * Time: 21:04
 *
 * Generate a sha1 hash code of a file.
 */
object FileSHA1Code {
    /**
     * Generate a file 's sha1 hash code.
     * https://gist.github.com/zeroleaf/6809843
     * @param filePath file path
     * @return sha1 hash code of this file
     * @throws IOException if file doesn't or other IOException
     * @throws NoSuchAlgorithmException
     */
    @Throws(IOException::class, NoSuchAlgorithmException::class)
    fun sha1Code(filePath: String?): String {
        val fileInputStream = FileInputStream(filePath)
        val digest = MessageDigest.getInstance("SHA-1")
        val digestInputStream = DigestInputStream(fileInputStream, digest)
        val bytes = ByteArray(1024)
        // read all file content
        @Suppress("ControlFlowWithEmptyBody")
        while (digestInputStream.read(bytes) > 0);

//        digest = digestInputStream.getMessageDigest();
        val resultByteArry = digest.digest()
        return bytesToHexString(resultByteArry)
    }

    /**
     * Input file name: testfile.txt
     * Input file content: (only one line bellow)
     * This is a file for test sha1 hash code
     *
     * Output:
     * 7465503EADC8799AE6F64E03EE87AB747B9D08F5
     *
     */
    @Throws(IOException::class, NoSuchAlgorithmException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val fileName = args[0]
        // String fileName = "testfile.txt";
        println(sha1Code(fileName))
    }

    /**
     * Convert a array of byte to hex String. <br></br>
     * Each byte is covert a two character of hex String. That is <br></br>
     * if byte of int is less than 16, then the hex String will append <br></br>
     * a character of '0'.
     *
     * @param bytes array of byte
     * @return hex String represent the array of byte
     */
    fun bytesToHexString(bytes: ByteArray): String {
        val sb = StringBuilder()
        for (b in bytes) {
            val value = b.toInt() and 0xFF
            if (value < 16) {
                // if value less than 16, then it's hex String will be only
                // one character, so we need to append a character of '0'
                sb.append("0")
            }
            sb.append(Integer.toHexString(value).uppercase(Locale.getDefault()))
        }
        return sb.toString()
    }
}