package cn.mamp.j2se.io;

import java.io.*;

/**
 * @author mamp
 * @data 2020/5/8
 */
public class TestFileInputStream {
    public static void main(String[] args) throws IOException {
        File sourceFile = new File("E:/test1.docx");
        File targetFile = new File("E:/test1_copy.docx");
        copyFile(sourceFile, targetFile);

    }

    /**
     * 复制文件
     *
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     */
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        // 文件输入流: 字节流, 节点流
        FileInputStream inputStream = new FileInputStream(sourceFile);
        // 缓冲流: 处理流
        BufferedInputStream bis = new BufferedInputStream(inputStream);

        // 文件输出流: : 字节流, 节点流
        FileOutputStream outputStream = new FileOutputStream(targetFile);
        // 缓冲流: 处理流
        BufferedOutputStream bos = new BufferedOutputStream(outputStream);

        byte[] bytes = new byte[1024];
        while (bis.read(bytes) > 0) {
            bos.write(bytes);
            bytes = new byte[1024];
        }
        // 关闭流,只需要关闭最外层的流
        bis.close();
        bos.close();
    }
}
