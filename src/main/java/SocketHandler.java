import Utils.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketHandler {

    private ServerSocket server;

    public static final int SERVER_PORT = 1000;

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    public boolean enable() {

        boolean isEnable = false;

        try {

            if (server == null) {

                server = new ServerSocket(SERVER_PORT);
                Log.i("SocketHandler", "enable", "Server is enableed");
                Log.i("SocketHandler", "server info", "----------------");
                Log.i("SocketHandler", "server ip", String.valueOf(server.getLocalSocketAddress()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return isEnable;

    }

    public void acceptForClient() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                if(server == null) {

                    Log.i("SocketHandler", "acceptForClient", "server is not start");
                    return;

                }

                try {


                    Log.i("SockedHandler", "acceptForClient", "携帯からの繋ぎを待ってる");
                    while (true) {

                        if(server.isClosed()) {
                            break;

                        }



                        Socket socket = server.accept();
                        System.out.println(socket.getInetAddress());
                        executorService.submit(new ClientHandler(socket));

                    }

                } catch (IOException e) {
                }
            }
        }).start();

    }

    public void close() {

        Log.i("SocketHandler", "close", "サーバを閉じています");

        if(server != null) {

            try {
                server.close();
                Log.i("SocketHandler", "close", "サーバを閉じました");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
