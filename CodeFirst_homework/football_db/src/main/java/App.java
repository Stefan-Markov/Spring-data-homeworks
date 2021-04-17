import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class App {
    private static final String FOOTBALL = "football_db";


    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory(FOOTBALL);

//        EntityManager entityManager = factory.createEntityManager();
//        Engine engine = new Engine(entityManager);
//
//        engine.run();

    }
}
