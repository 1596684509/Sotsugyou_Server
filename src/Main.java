public class Main {
    public static void main(String[] args) {

        SocketHandler server = new SocketHandler();
        server.enable();
        server.acceptForClient();

    }
}