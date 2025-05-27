package sec03;

import app.Database;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.Iterator;

public class FindTest {
    public static void main(String[] args) {
        MongoCollection<Document> collection = Database.getCollection("todo");

        // FindIterable : 조회 결과를 반복자 (Iterator) 형태로 반환해주는 객체
        // collection.find() == db.todo.find()
        FindIterable<Document> doc = collection.find();
        Iterator itr = doc.iterator();

        while (itr.hasNext()) { // 다음 행이 있으면 true
            // itr.next() : 다음 행 (문서) 반환
            System.out.println("==> findResultRow : " + itr.next());
        }

        Database.close();
    }
}
