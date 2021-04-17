import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class App {
    private static final String BILL_SYSTEM = "bill_system";


    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory(BILL_SYSTEM);

//        EntityManager entityManager = factory.createEntityManager();
//        Engine engine = new Engine(entityManager);
//
//        engine.run();

    }
}
