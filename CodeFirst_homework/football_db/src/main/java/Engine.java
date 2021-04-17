import javax.persistence.EntityManager;

public class Engine implements  Runnable {
    public final EntityManager entityManager;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void run() {

    }
}
