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
    void lregister() {
    }

    @Test
    void login() {
    }

    @Test
    void dlogin() {
    }

    @Test
    void logout() {
    }

    @Test
    void flogout() {
    }

    @Test
    void listgames() {
    }

    @Test
    void flistgames() {
    }

    @Test
    void joinGame() {
    }

    @Test
    void fjoinGame() {
    }

    @Test
    void createGame() {
    }

    @Test
    void fcreateGame() {
    }

}