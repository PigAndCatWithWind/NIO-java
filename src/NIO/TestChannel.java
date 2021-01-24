package NIO;

import java.io.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class TestChannel {

    public static void testBuffer(){
        try (FileChannel input = new FileInputStream("src/NIO/TestChannel.java").getChannel();
             FileChannel output = new FileOutputStream("Copy-Channel.java").getChannel()){
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while (input.read(byteBuffer)!=-1){
                byteBuffer.flip();
                output.write(byteBuffer);
                byteBuffer.clear();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initBuffer() throws IOException {
        FileChannel input = FileChannel
                .open(Paths.get("src/","NIO/","TestChannel.java"), StandardOpenOption.READ);
        FileChannel output = FileChannel
                .open(Paths.get("Copy-Channel-with-map.java"),StandardOpenOption.READ,StandardOpenOption.WRITE,StandardOpenOption.CREATE);

        MappedByteBuffer inMappedBuffer = input
                .map(MapMode.READ_ONLY, 0, input.size());
        MappedByteBuffer outMappedBuffer = output
                .map(MapMode.READ_WRITE,0,input.size());
        byte[] bytes = new byte[inMappedBuffer.limit()];
        inMappedBuffer.get(bytes);
        outMappedBuffer.put(bytes);
        input.close();
        output.close();
    }

    public static void copyWithChannel() throws IOException {
        FileChannel input = FileChannel
                .open(Paths.get("src/","NIO/","TestChannel.java"), StandardOpenOption.READ);
        FileChannel output = FileChannel
                .open(Paths.get("Copy-Channel-with-transfer.java"),StandardOpenOption.READ,StandardOpenOption.WRITE,StandardOpenOption.CREATE);
//        input.transferTo(0,input.size(),output);
        output.transferFrom(input,0,input.size());
    }

    public static ByteBuffer[] scatterReturn() throws IOException {
        RandomAccessFile  randomAccessFile = new RandomAccessFile("Copy-Channel.java", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        ByteBuffer byteBuffer1 = ByteBuffer.allocate(100);
        ByteBuffer byteBuffer2 = ByteBuffer.allocate(200);
        ByteBuffer byteBuffer3 = ByteBuffer.allocate(300);
        ByteBuffer[] byteBuffers = {byteBuffer1,byteBuffer2,byteBuffer3};
        fileChannel.read(byteBuffers);
        return byteBuffers;
    }

    public static void scatter() throws IOException {
        ByteBuffer[] byteBuffers = scatterReturn();
        for (ByteBuffer byteBuffer : byteBuffers)
            byteBuffer.flip();
        System.out.println("-------byteBuffers[0]-------");
        System.out.println(new String(byteBuffers[0].array(),0,byteBuffers[0].limit()));
        System.out.println("-------byteBuffers[1]-------");
        System.out.println(new String(byteBuffers[1].array(),0,byteBuffers[1].limit()));
        System.out.println("-------byteBuffers[2]-------");
        System.out.println(new String(byteBuffers[2].array(),0,byteBuffers[2].limit()));
    }

    public static void gather() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("gather.java", "rw");
        ByteBuffer[] byteBuffers = scatterReturn();
        FileChannel fileChannel = randomAccessFile.getChannel();
        for (ByteBuffer byteBuffer : byteBuffers)
            byteBuffer.flip();
        fileChannel.write(byteBuffers);
    }

    public static void charset() throws CharacterCodingException {
        Charset charset = Charset.forName("GBK");
        CharsetEncoder charsetEncoder = charset.newEncoder();
        CharsetDecoder charsetDecoder = charset.newDecoder();
        CharBuffer charBuffer = CharBuffer.allocate(1024);
        charBuffer.put("learn charset");
        charBuffer.flip();
        ByteBuffer byteBuffer = charsetEncoder.encode(charBuffer);
        for (int i=0;i<byteBuffer.position();i++){
            System.out.println(byteBuffer.get());
        }
        byteBuffer.flip();
        CharBuffer charBuffer1 = charsetDecoder.decode(byteBuffer);
        System.out.println(charBuffer1.toString());
    }
    public static void main(String[] args) throws IOException {
//        testBuffer();
//        initBuffer();
//        copyWithChannel();
//        scatter();
        gather();
//        charset();
    }
}
