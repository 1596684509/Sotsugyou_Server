import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

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

            System.out.println(rcvMsg());

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
            close();

        }

    }

    public String rcvMsg() {

        StringBuffer stringBuffer;

        try {

            if(in == null) {

                in = client.getInputStream();

            }

            stringBuffer = new StringBuffer();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            String line;
            while((line = bufferedReader.readLine()) != null) {

                stringBuffer.append(line);

            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return stringBuffer.toString();

    }

//    public String rcvMsg() {
//
//        byte[] msgByte = new byte[];
//        int msg_len;
//        String msgStr = null;
//
//        try {
//
//            if(in == null) {
//
//                in = client.getInputStream();
//
//            }
//
//            msg_len = in.read(msgByte);
//
//            if(msg_len == -1) {
//
//                close();
//                return null;
//
//            }
//
//            msgStr = new String(msgByte, 0, msg_len);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return msgStr;
//
//    }



    public void close() {

        try {

            if (in != null) {
                in.close();
            }

            if (out != null) {
                out.close();
            }

            if(client != null) {

                client.close();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
