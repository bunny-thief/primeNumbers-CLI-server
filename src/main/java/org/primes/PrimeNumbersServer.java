package org.primes;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ScatteringByteChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class PrimeNumbersServer {

    private UserInterface userInterface;

    public PrimeNumbersServer(UserInterface userInterface) {
        this.userInterface = userInterface;

        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.socket().bind(new InetSocketAddress(8189));

            boolean running = true;

            while (running) {
                System.out.println("Waiting for connection...");

                SocketChannel socketChannel = serverSocketChannel.accept();
                sendMessage(socketChannel, "Connected to server");
                System.out.println("Connected to client");

                while (true) {
                    userInterface.run();
                }
            }

        } catch (IOException e) {
            System.out.println("Can't start server");
            e.printStackTrace();
        }
    }

    public static void sendMessage(SocketChannel socketChannel, String message) {
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(message.length() + 1);
            byteBuffer.put(message.getBytes());
            byteBuffer.put((byte) 0x00);
            byteBuffer.flip();

            while (byteBuffer.hasRemaining()) {
                socketChannel.write(byteBuffer);
            }

            System.out.println("Sent: " + message);

        } catch (IOException e) {
            System.out.println("Channel can't write to byteBuffer");
            e.printStackTrace();
        }
    }

    public static String receiveMessage(SocketChannel socketChannel) {
        String message = "";

        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(16);

            while (socketChannel.read(byteBuffer) > 0) {
                char byteRead = 0x00;
                byteBuffer.flip();

                while (byteBuffer.hasRemaining()) {
                    byteRead = (char) byteBuffer.get();

                    if (byteRead == 0x00) {
                        break;
                    }

                    message += byteRead;
                }

                if (byteRead == 0x00) {
                    break;
                }

                byteBuffer.clear();
            }

            return message;

        } catch (IOException e) {
            System.out.println("Can't read from byteBuffer");
            e.printStackTrace();
        }

        return "";
    }

}
