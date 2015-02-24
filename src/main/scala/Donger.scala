/**
 * Created by techy on 2/24/15.
 */

import javax.crypto.Cipher
import javax.crypto.spec.{IvParameterSpec, SecretKeySpec}

trait Donger {
  def encrypt(data: Array[Byte], secret:Array[Byte], iv:Array[Byte]): Array[Byte] = {
    val key = new SecretKeySpec(secret, "AES/CBC/PKCS5Padding")
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv))
    cipher.doFinal(data)
  }
  def decrypt(data: Array[Byte], secret:Array[Byte], iv:Array[Byte]): Array[Byte] = {
    val key = new SecretKeySpec(secret, "AES/CBC/PKCS5Padding")
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv))
    cipher.doFinal(data)
  }
  def toByteArray(fileName: String): Array[Byte] = {
    val source = scala.io.Source.fromFile(fileName)
    val byteArray = source.map(_.toByte).toArray
    source.close()
    byteArray
  }

}
object Donger {
  def main(args: Array[String]) {
    println("Hello, world!")
  }
}

class WindersDonger(key: String, iv:String) extends Donger {

}
class LinuxDonger(key: String, iv:String) extends Donger {

  val LINUX_WHITELIST = List[String](
    "cat", "wall", "echo", "bash", "ifconfig", "ls", "chmod", "rm",  "openssl"
  )
}