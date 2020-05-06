package cn.mamp.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author mamp
 * @data 2020/4/27
 */
public class NioDemo {


}

class Server {

    public static void main(String[] args) {
        try {
            new Server().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws IOException {
        // 开启一个ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 绑定端口号
        serverSocketChannel.bind(new InetSocketAddress(8888));
        // 设置为非阻塞模式
        serverSocketChannel.configureBlocking(false);
        // 开启一个多路复用器
        Selector selector = Selector.open();

        // 将serverSocket 注册到　selector上
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.keys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                // 获取key后,在迭代器中删除,否则会重复迭代
                //iterator.remove();
                if (key.isAcceptable()) {
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    channel.configureBlocking(false);
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    sc.register(key.selector(), SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    SocketChannel channel = (SocketChannel) key.channel();
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_WRITE, buffer);
                    while (true) {
                        if (channel.read(buffer) > 0) {
                            System.out.println(new String(buffer.array()));
                        } else if (channel.read(buffer) == 0) {
                            break;
                        } else {
                            channel.close();
                        }
                    }
                    buffer.flip();
                    channel.write(buffer);
                }
            }

        }
    }

}

class Client {

}