package sec01;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConnectionTest {
    public static void main(String[] args) {
        String uri = "mongodb://localhost:27017";
        String db = "todo_db";

        // 클라이언트 객체를 만들어서 -> 어떤 DB와 연결?
        try (MongoClient client = MongoClients.create(uri)) {
            MongoDatabase database = client.getDatabase(db);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
