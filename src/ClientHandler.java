import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{

    private Socket client;
    private InputStream in;
    private OutputStream out;

    public ClientHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {

        if(client != null) {

            sendMessage("hello this is server");

            while (true) {

                System.out.println(rcvMsg());
                if(rcvMsg().equals(-1)) {

                    break;

                }

            }

        }

    }

    private void sendMessage(String message) {

        try {

            if(out == null) {

                out = client.getOutputStream();

            }

            out.write(message.getBytes());

        }catch (IOException e) {

            e.printStackTrace();
            closeAll();

        }

    }

    public String rcvMsg() {

        byte[] msgByte = new byte[128];
        int msg_len;
        String msgStr = null;

        try {

            if(in == null) {

                in = client.getInputStream();

            }

            msg_len = in.read(msgByte);

            if(msg_len == -1) {

                closeAll();
                return null;

            }

            msgStr = new String(msgByte, 0, msg_len);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return msgStr;

    }



    private void closeAll() {

        try {

            if (in != null) {
                in.close();
            }

            if (out != null) {
                out.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void close() {

        try {

            if(client != null) {

                client.close();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
