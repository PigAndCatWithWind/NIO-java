package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TestBlockingNIO {
    public void celitcelit() throws IOException {
        SocketChannel socketChannel = SocketChannel
                .open(new InetSocketAddress("127.0.0.1",9898));

        FileChannel fileChannel = FileChannel
                .open(Paths.get("src/NIO/TestBlockingNIO.java"), StandardOpenOption.READ);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (fileChannel.read(byteBuffer)!=-1){
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            byteBuffer.clear();
        }

        socketChannel.shutdownOutput();

        //requset
        int length = 0;
        while ((length=socketChannel.read(byteBuffer))!=-1){
            byteBuffer.flip();
            System.out.println(new String(byteBuffer.array(),0,length));
            byteBuffer.clear();
        }


        fileChannel.close();
        socketChannel.close();
    }
    public void server() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        FileChannel fileChannel = FileChannel
                .open(Paths.get("SocketChannel.java"), StandardOpenOption.WRITE,StandardOpenOption.CREATE);
        serverSocketChannel.bind(new InetSocketAddress(9898));
        SocketChannel socketChannel = serverSocketChannel.accept();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (socketChannel.read(byteBuffer)!=-1){
            byteBuffer.flip();
            fileChannel.write(byteBuffer);
            byteBuffer.clear();
        }
        //request
        byteBuffer.put("ok!!!".getBytes());
        byteBuffer.flip();
        socketChannel.write(byteBuffer);

        socketChannel.close();
        fileChannel.close();
        serverSocketChannel.close();
    }
}
