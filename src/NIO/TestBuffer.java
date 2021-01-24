package NIO;

import java.nio.ByteBuffer;

public class TestBuffer {

    public static void test(){
        String s = "abcde";
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        System.out.println("-----new-----");
        System.out.println("position(位置): "+byteBuffer.position());
        System.out.println("limit(界限): "+byteBuffer.limit());
        System.out.println("capacity(容量): "+byteBuffer.capacity());

        System.out.println("-----put-----");
        byteBuffer.put(s.getBytes());
        System.out.println("put--->position(位置): "+byteBuffer.position());
        System.out.println("put--->limit(界限): "+byteBuffer.limit());
        System.out.println("put--->capacity(容量): "+byteBuffer.capacity());

        byteBuffer.flip();
        System.out.println("-----flip-----");
        System.out.println("flip--->position(位置): "+byteBuffer.position());
        System.out.println("flip--->limit(界限): "+byteBuffer.limit());
        System.out.println("flip--->capacity(容量): "+byteBuffer.capacity());

        byte[] bytes = new byte[byteBuffer.limit()];
        byteBuffer.get(bytes);
        System.out.println("get()--->"+new String(bytes,0,bytes.length));
        System.out.println("-----get-----");
        System.out.println("get--->position(位置): "+byteBuffer.position());
        System.out.println("get--->limit(界限): "+byteBuffer.limit());
        System.out.println("get--->capacity(容量): "+byteBuffer.capacity());

        byteBuffer.rewind();
        System.out.println("-----rewind-----");
        System.out.println("rewind--->position(位置): "+byteBuffer.position());
        System.out.println("rewind--->limit(界限): "+byteBuffer.limit());
        System.out.println("rewind--->capacity(容量): "+byteBuffer.capacity());

        byteBuffer.clear();
        System.out.println("-----clear-----");
        System.out.println("clear--->position(位置): "+byteBuffer.position());
        System.out.println("clear--->limit(界限): "+byteBuffer.limit());
        System.out.println("clear--->capacity(容量): "+byteBuffer.capacity());

    }

    public static void test1(){
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        System.out.println(byteBuffer.isDirect());
        byteBuffer = byteBuffer.allocate(1024);
        System.out.println(byteBuffer.isDirect());
    }

    public static void main(String[] args) {
        test();
    }
}
