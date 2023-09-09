package org.primes;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class PrimeNumbersServer {

    private UserInterface userInterface;

    public PrimeNumbersServer(UserInterface userInterface) {
        this.userInterface= userInterface;

        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.socket().bind(new InetSocketAddress(8189));

            boolean running = true;

            while (running) {
                System.out.println("Waiting for connection...");

                SocketChannel socketChannel = serverSocketChannel.accept();
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

}
