import Utils.ClientCode;
import Utils.JsonHandler;
import Utils.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientHandler implements Runnable{

    private Socket client;
    private InputStream in;
    private OutputStream out;
    private MysqlDatabase mysqlDatabase;
    private JSONObject jsonObject;

    public ClientHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {

        if(client != null) {

            mysqlDatabase = new MysqlDatabase();
            mysqlDatabase.connectDatabase();

            jsonObject = JsonHandler.jsonFromString(rcvMsg());

            try {

                if(jsonObject != null) {

                    int datatype = jsonObject.getInt("datatype");

                    if(datatype == ClientCode.DATATYPE_USERREGISTER_CODE) {

                        registerUser();
                        closeDatabase();

                    }else if(datatype == ClientCode.DATATYPE_DOLLUP_CODE) {

                        Log.i("ClientHandler", "run", "datatype: " + ClientCode.DATATYPE_DOLLUP_CODE);
                        updataDoll();
                        closeDatabase();

                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    private void updataDoll() {

        try {

            if(mysqlDatabase.insertDoll(JsonHandler.jsonToDoll(jsonObject), jsonObject.getString("userid"))) {

                Log.i("ClientHandler", "registerUser", "doll updata end");

            }else {

                Log.i("ClientHandler", "registerUser", "doll updata error");

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void registerUser() {

        if(mysqlDatabase.insertUser(JsonHandler.jsonToUser(jsonObject))) {

            Log.i("ClientHandler", "registerUser", "user register end");

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

        }finally {
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
        }finally {
            close();
        }

        return stringBuffer.toString();

    }

    public void closeDatabase() {

        if(mysqlDatabase != null) {

            mysqlDatabase.close();
            Log.i("ClientHandler", "close", "database を閉じてる");

        }

    }

    public void close() {

        Log.i("ClientHandler", "close", "client を閉じてる");

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

            Log.i("ClientHandler", "close", "client を閉じました");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
