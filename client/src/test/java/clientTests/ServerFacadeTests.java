package clientTests;

import org.junit.jupiter.api.*;
import server.Server;


public class ServerFacadeTests {

    private static Server server;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    void register() {
    }

    @Test
    void login() {
    }

    @Test
    void logout() {
    }

    @Test
    void listgames() {
    }

    @Test
    void joinGame() {
    }

    @Test
    void createGame() {
    }

}