import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DatabaseConnectionTest {

    @Test
    public void testDatabaseConnection() {
        ConnectionFactory connectionFactory = ConnectionFactories.get("r2dbc:postgresql://{username}:{password}@localhost:5432/{DatabaseName}");

        Mono.from(connectionFactory.create())
                .doOnNext(connection -> {
                    System.out.println("Connection established successfully!");
                    assertNotNull(connection);
                    connection.close();//.subscribe(null);
                })
                .block();
    }
}