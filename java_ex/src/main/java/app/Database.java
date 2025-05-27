package app;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import lombok.Getter;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Database {
    static MongoClient client;

    @Getter
    static MongoDatabase database;

    static {
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
        client = MongoClients.create(connectionString);
        // database = client.getDatabase("todo_db");


        /* ================ POJO ================ */
        // POJO 코덱 프로바이더 생성 - 자동 매핑 활성화
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder()
                .automatic(true).build();

        // 코덱 레지스트리 생성 - 기본 + POJO 코덱 결합
        CodecRegistry pojoCodecRegistry = fromRegistries(
                getDefaultCodecRegistry(),
                fromProviders(pojoCodecProvider)
        );
        // 데이터베이스에 POJO 코덱 레지스트리 적용
        database = client.getDatabase("todo_db").withCodecRegistry(pojoCodecRegistry);
        /* ================ POJO ================ */

    }

    public static void close() {
        client.close();
    }

    // Document - Map
    public static MongoCollection<Document> getCollection(String colName) {
        MongoCollection<Document> collection = database.getCollection(colName);
        return collection;
    }


    /* ================ POJO ================ */
    // POJO 타입 컬렉션 반환 - 제네릭 타입 활용
    public static <T> MongoCollection<T> getCollection(String colName, Class<T> clazz) {
        MongoCollection<T> collection = database.getCollection(colName, clazz);
        return collection;
    }
    /* ================ POJO ================ */
}
