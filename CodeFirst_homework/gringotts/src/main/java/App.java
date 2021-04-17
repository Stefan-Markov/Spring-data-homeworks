import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class App {
    private static final String GRINGOTTS_PU = "gringotts";
    private static final String SALES_PU = "sales";
    private static final String UNIVERSITY_SYSTEM = "university_system";

    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory(GRINGOTTS_PU);

//        EntityManager entityManager = factory.createEntityManager();
//        Engine engine = new Engine(entityManager);
//
//        engine.run();

    }
}
