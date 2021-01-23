package NIO;

import java.nio.ByteBuffer;

public class TestBuffer {

    public static void test(){
        String s = "abcde";
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        System.out.println("-----new-----");
        System.out.println("position: "+byteBuffer.position());
        System.out.println("limit: "+byteBuffer.limit());
        System.out.println("capacity: "+byteBuffer.capacity());

        System.out.println("-----put-----");
        byteBuffer.put(s.getBytes());
        System.out.println("position: "+byteBuffer.position());
        System.out.println("limit: "+byteBuffer.limit());
        System.out.println("capacity: "+byteBuffer.capacity());
    }

    public static void main(String[] args) {
        test();
    }
}
