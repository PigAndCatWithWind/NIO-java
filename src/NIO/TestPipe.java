package NIO;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

public class TestPipe {
    public static void main(String[] args) throws IOException {
        Pipe pipe = Pipe.open();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        Pipe.SinkChannel sinkChannel = pipe.sink();
        byteBuffer.put("pipe senk".getBytes());
        byteBuffer.flip();
        sinkChannel.write(byteBuffer);

        Pipe.SourceChannel sourceChannel = pipe.source();
        byteBuffer.flip();
        int length = sourceChannel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array(),0,length));

        sourceChannel.close();
        sinkChannel.close();
    }
}
