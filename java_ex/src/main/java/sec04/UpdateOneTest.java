package sec04;

import app.Database;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import static com.mongodb.client.model.Filters.eq;

public class UpdateOneTest {
    public static void main(String[] args) {
        MongoCollection<Document> collection = Database.getCollection("todo");

        String id = "68353dfe33b0ae21901a4081";
        Bson query = eq("_id", new ObjectId(id));
        // -> {"_id" : ObjectId("68353dfe33b0ae21901a4081")}

        // update 할 내용을 묶어서 보내야 하기 때문에 combine 메서드 사용
        Bson updates = Updates.combine(
                // name 필드 값을 "modify name" 으로 설정 - 없으면 새로 추가
                Updates.set("name", "modify name"),
                // lastUpdated 필드 값으로 현재 시각을 설정
                Updates.currentTimestamp("lastUpdated")
        );

        UpdateResult result = collection.updateOne(query, updates);
        System.out.println("==> UpdateResult : " + result.getModifiedCount());

        Database.close();
    }
}
