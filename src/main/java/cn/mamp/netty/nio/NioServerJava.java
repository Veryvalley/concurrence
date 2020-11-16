package cn.mamp.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;

/**
 * jdk 原生的 NIO
 *
 * C10k
 * C10M
 * @author mamp
 * @data 2020/9/15
 */
public class NioServerJava {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8080));
        // 设置非阻塞
        ssc.configureBlocking(false);

        List<SocketChannel> clients = new LinkedList<>();


        // 每个客户端的连接都是一个socketChannel
        // channel(管道)，是双向的，限可以读取，也可以写
        // nc 192.168.92.1 8080 ： 模拟客户端口请求
        SocketChannel client;
        while (true) {
            // 这里不会阻塞
            client = ssc.accept();
            if (null == client) {
                //
                System.out.println("null...");
                Thread.sleep(1000);
            } else {
                // 客户端也要设置成非阻塞
                client.configureBlocking(false);
                // 获取客户端请求的端口
                int port = client.socket().getPort();
                System.out.println(port);

                clients.add(client);

            }

            // 在堆外申请 1024字节的空间
            // 作用: 1.缓冲区 2.减少内存碎片和GC
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
            for (SocketChannel sc : clients) {
                // i > 0： 有数据 ,i = 0 ,没有数据 ，i = -1 : 连接已经断开
                // 这里不阻塞，会马上返回 i的值
                int i = sc.read(buffer);
                if (i > 0) {
                    // buffer的指针（position）复位,limite = position, position = 0;
                    buffer.flip();
                    // 创建limite大小的字节数据 ，准备从buffer中取出数据
                    byte[] bytes = new byte[buffer.limit()];
                    // 从buffer中取出数据
                    buffer.get(bytes);

                    // 客户端发的消息内容
                    String msg = new String(bytes);
                    System.out.println(msg);
                    // 清空buffer
                    buffer.clear();
                }

            }
        }
    }
}
