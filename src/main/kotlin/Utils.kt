@file:Suppress("unused")

import java.math.BigInteger
import java.nio.file.Paths
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun loadResource(name: String): List<String> {
    return Paths.get(ClassLoader.getSystemResource("$name.txt").toURI()).toFile().readLines()
}

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')
