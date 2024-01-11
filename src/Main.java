import java.util.Scanner;

public class Main {

    private SocketHandler socket;
    private MysqlDatabase database;
    private static Main main;

    public Main() {

        main = this;
        socket = new SocketHandler();
        database = new MysqlDatabase();

    }

    public static Main getMain() {
        return main;
    }

    public MysqlDatabase getDatabase() {
        return database;
    }

    public SocketHandler getSocket() {
        return socket;
    }

    public static void main(String[] args) {

        Main server = new Main();
        server.getSocket().enable();
        server.getSocket().acceptForClient();

        new Thread(new Runnable() {
            @Override
            public void run() {

                Scanner sc = new Scanner(System.in);

                while (true) {

                    String command = sc.nextLine();
                    System.out.print("command is " + command);

                    if("stop".equals(command)) {

                        sc.close();
                        server.getDatabase().close();
                        server.getSocket().close();
                        break;

                    }

                }

            }
        }).start();

    }
}