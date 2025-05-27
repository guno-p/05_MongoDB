package sec05;

import app.Database;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;

public class DeleteManyTest {
    public static void main(String[] args) {
        MongoCollection<Document> collection = Database.getCollection("todo");

        // 심화 : int age = 15
        // Filters.gt("age", age)
        Bson query = Filters.eq("done", true);

        DeleteResult result = collection.deleteMany(query);
        System.out.println("==> DeleteManyResult : " + result.getDeletedCount());

        Database.close();
    }
}
