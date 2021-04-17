import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class App {
    private static final String HOSTIPAL = "hospital";


    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory(HOSTIPAL);

//        EntityManager entityManager = factory.createEntityManager();
//        Engine engine = new Engine(entityManager);
//
//        engine.run();

    }
}
