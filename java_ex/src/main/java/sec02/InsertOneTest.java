package sec02;

import app.Database;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;

public class InsertOneTest {
    public static void main(String[] args) {
        // Database 유틸리티의 getCollection 으로 연결 객체 얻어오기
        MongoCollection<Document> collection = Database.getCollection("todo");

        // 문서(행) 정의 객체 생성(BSON)
        Document document = new Document();

        // 문서 객체에 필드(열) 추가
        document.append("title", "MongoDB");
        document.append("desc", "MongoDB 공부하기");
        document.append("done", false);

        // 컬렉션에 문서 1개 삽입 후 결과 반환 받기
        InsertOneResult result = collection.insertOne(document);

        // MongoDB가 자동으로 생성하는 _id 필드값 받아온다 -> (ObjectId) 라는 별도 타입
        System.out.println("==> InsertOneResult : " + result.getInsertedId());

        Database.close();
    }
}