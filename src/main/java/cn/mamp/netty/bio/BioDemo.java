package cn.mamp.netty.bio;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author mamp
 * @data 2020/4/27
 */
public class BioDemo {
}

class Client {

    public void connect() throws IOException {
        Socket socket = new Socket("localhost", 8888);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("hello".getBytes());
        outputStream.flush();
        //outputStream.close();
        System.out.println("发送消息完成...");
        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        while (inputStream.read(bytes) > 0) {
            System.out.println(new String(bytes));
            bytes = new byte[1024];
        }
        System.out.println("接收消息完成...");
        socket.close();
    }

    public static void main(String[] args) {
        try {
            new Client().connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Sever {
    public void start() throws IOException {
        System.out.println("starting ...");
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("started on port 8888, localhost");
        while (true) {

            Socket socket = serverSocket.accept();
            System.out.println("connect comming...");
            InputStream socketInputStream = socket.getInputStream();
            byte[] b = new byte[1024];
            System.out.println("receive message start...");
            while (socketInputStream.read(b) > 0) {
                String str = new String(b);
                System.out.println(str);
                b = new byte[1024];
            }
            System.out.println("receive message end...");
            OutputStream socketOutputStream = socket.getOutputStream();

            String welcome = "welcome";
            byte[] res = welcome.getBytes();
            socketOutputStream.write(res);
            socketOutputStream.flush();
            //socketOutputStream.close();
            // socketInputStream.close();
            socket.close();
        }
    }

    public static void main(String[] args) {
        try {
            new Sever().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

