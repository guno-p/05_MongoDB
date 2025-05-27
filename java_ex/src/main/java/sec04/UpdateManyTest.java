package sec04;

import app.Database;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import static com.mongodb.client.model.Filters.gt;

public class UpdateManyTest {
    public static void main(String[] args) {
        MongoCollection<Document> collection = Database.getCollection("todo");

        // 심화 : int age = 16
        // Filters.gt("age", age);
        boolean done = false;
        Bson query = Filters.eq("done", done);

        Bson updates = Updates.combine(
                Updates.set("done", true),
                Updates.currentTimestamp("lastUpdated")
        );

        // done 필드 값이 false 인 모든 문서를 true 로 변경
        UpdateResult result = collection.updateMany(query, updates);
        System.out.println("==> UpdateManyResult : " + result.getModifiedCount());

        Database.close();
    }
}
