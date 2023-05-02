# jutils
Provides a set of (kinda useless) utilities for developing in Java. 

## Encoding
Provides fast access to different encodings.

### UTF8
```java
byte[] bdata = Encoder.getUTF8("Hello World!");
String data = Encoder.getUTF8(bdata);
```

Has method to transform from char[] to byte[] and viceversa using utf-8.
```java
char[] cdata = "Hello World!".toCharArray();
byte[] bdata = Encoder.getUTF8("Hello World!");
        
byte[] cs = Encoder.toBytes(cdata);
char[] bs = Encoder.toChars(bdata);
```

### Base64
```java 
byte[] bdata = Encoder.getUTF8("Hello World!");

byte[] b64 = Encoder.encodeB64(bdata, true); // Params (byte[] data, boolean padded)
byte[] ddata = Encoder.decodeB64(b64);

String sb64 = Encoder.encodeB64Str(bdata); // With padding
ddata = Encoder.decodeB64(sb64);
```

## Arrays and Byte Manipulation

#### Easy way to copy an array...
```java
byte[] b = Bytes.copyOf(new byte[60]);
```

#### Save primitives such as int, long into byte array without using buffers.
```java
byte[] b = Bytes.intToBytesBE(0xABCD);
byte[] b1 = Bytes.longToBytesBE(1L);
```

#### Easy acces to secure random bytes
```java
byte[] sb = Bytes.getSecureRandomBytes(500);
byte[] sb1 = new byte[500];
Bytes.getSecureRandomBytes(sb1);
```

##### Zero out arrays utility for all primitive types
```java
byte[] sb = Bytes.getSecureRandomBytes(500);
Bytes.zeroOut(sb);
char[] cb = "P4ssw0rd".toCharArray();
Bytes.zeroOut(cb);
//...
```

### XOR
```java
byte[] data = Encoder.getUTF8("Hello World!");
byte[] aux = Encoder.getUTF8("Peaches");
byte[] res = Bytes.xor(data, aux);
```

### Easy access to SHA256 hashing
```java
byte[] data = Encoder.getUTF8("P4assw0rd");
byte[] hash = Bytes.getSHA256(data);
```


