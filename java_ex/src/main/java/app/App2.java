package app;

import app.domain.Todo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class App2 {
    public static void main(String[] args) {
        // Todo 타입 컬렉션 얻어오기
        MongoCollection<Todo> collection = Database.getCollection("todo", Todo.class);

        // 1개 삽입
        // insertTodo(collection, "수업 시간에 집중하기", "딴 짓 그만");

        // 여러 행 삽입
        //insertManyTodo(collection, "샘플", 3);

        // 셀렉트
        // selectAllTodo(collection);
        // selectTodo(collection, "68354e64f872b2aec1f9ae6c");

        // 업데이트
        // updateTodo(collection, "68354e64f872b2aec1f9ae6c", true);
        // updateAllTodo(collection, true);

        // 삭제
        // deleteTodo(collection, "68354e64f872b2aec1f9ae6c");
        // deleteAllTodo(collection);

        Database.close();
    }

    /**
     * Todo 문서 1개 삽입
     * @param collection
     * @param title
     * @param desc
     */
    private static void insertTodo(MongoCollection<Todo> collection, String title, String desc) {
        // Todo 객체 생성
        Todo todo = new Todo(null, title, desc, false);

        InsertOneResult result = collection.insertOne(todo);
        System.out.println("todo 추가 성공 : " + result.getInsertedId());
    }

    /**
     * Todo 문서 여러개 삽입
     * @param collection
     * @param str
     */
    private static void insertManyTodo(MongoCollection<Todo> collection, String str, int count) {
        // str을 제목, 설명으로 가지는 샘플 Todo 문서 count 만큼 삽입
        List<Todo> todoList = new ArrayList<>(); // 빈 List 먼저 생성
        for (int i = 0; i < count; i++) {
            Todo todo = new Todo(null, str + i, str + i + " 설명", false);
            todoList.add(todo);
        }
        InsertManyResult result = collection.insertMany(todoList);

        System.out.println("샘플 데이터 추가 성공 : " + result.getInsertedIds());
    }

    /**
     * 전체 조회
     * @param collection
     */
    private static void selectAllTodo(MongoCollection<Todo> collection) {
        List<Todo> todoList = new ArrayList<>();

        collection.find().into(todoList);
        todoList.forEach(System.out::println);
    }

    /**
     * 단일 조회
     * @param collection
     * @param id
     */
    private static void selectTodo(MongoCollection<Todo> collection, String id) {
        Bson query = Filters.eq("_id", new ObjectId(id));
        Todo todo = collection.find(query).first();
        if (todo == null) {
            System.out.println("_id 일치 document가 없습니다.");
            return;
        }
        System.out.println("_id: " + todo.getId());
        System.out.println("title: " + todo.getTitle());
        System.out.println("desc: " + todo.getDescription());
        System.out.println("done: " + todo.isDone());
    }

    /**
     * id가 일치하는 todo 문서의 done 값을 수정
     * @param collection
     * @param id
     * @param done
     */
    private static void updateTodo(MongoCollection<Todo> collection, String id, boolean done) {
        // query selector (조건) 생성
        Bson query = Filters.eq("_id", new ObjectId(id));

        // update 할 내용 - done 값 바꾸기
        Bson update = Updates.set("done", done);

        // 실행 후 결과 객체 반환(updateOne(), UpdateResult)
        // -> 수정된 문서의 개수 얻을 수 있음
        UpdateResult result = collection.updateOne(query, update);

        // 개수 == 0
        // 실패 메시지 출력
        if(result.getModifiedCount() == 0) {
            System.out.println("_id 를 찾지 못했습니다.");
        }

        // 개수 > 0
        // 성공 메시지 출력 + 상세 조회 selectTodo(collection, id) 호출
        System.out.println("updateOne 성공: " + result.getModifiedCount() + "건 수정");
        selectTodo(collection, id);
    }

    /**
     * 모든 항목 업데이트
     * @param collection
     * @param done
     */
    private static void updateAllTodo(MongoCollection<Todo> collection, boolean done) {
        Bson query = Filters.empty(); // 비어있는 쿼리 셀렉터 -> 모든 선택
        UpdateResult result = collection.updateMany(query, Updates.set("done", done));
        if(result.getModifiedCount() == 0) {
            System.out.println("수정할 문서를 찾지 못했습니다.");
        } else {
            System.out.println("updateMany 성공: " + result.getModifiedCount() + "건 수정");
            selectAllTodo(collection);
        }
    }

    /**
     * 하나의 문서 삭제
     * @param collection
     * @param id
     */
    private static void deleteTodo(MongoCollection<Todo> collection, String id) {
        Bson query = Filters.eq("_id", new ObjectId(id));
        DeleteResult result = collection.deleteOne(query);

        if(result.getDeletedCount() == 0) {
            System.out.println("일치하는 _id를 찾지 못했습니다.");
        } else {
            System.out.println("삭제 완료했습니다. id: " + id);
        }
    }

    /**
     * 모든 Todo 문서 삭제
     * @param collection
     */
    private static void deleteAllTodo(MongoCollection<Todo> collection) {
        Bson query = Filters.empty();
        DeleteResult result = collection.deleteMany(query);

        if(result.getDeletedCount() == 0) {
            System.out.println("삭제 할 Document가 없습니다.");
        } else {
            System.out.println("삭제 " + result.getDeletedCount() + "건 완료했습니다.");
        }
    }
}
