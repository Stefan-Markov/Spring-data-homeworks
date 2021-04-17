import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        System.out.println();
        Homework homework = new Homework();

        //                      enter yours username and password!
        homework.setConnection("root", "Leonkov33$");

        // second task
        // homework.getVillainsNamesEx2();

        // third task
        //   homework.getMinionsNameEx3();

        // fourth task
        //  homework.addMinionEx4();

        // fifth task
        // homework.changeTownNameCasingEx5();

        //sixth task
        homework.removeVillainEx6();

        // seventh task
        // homework.reverseOrderOfMinionNamesEx7();

        //eight task
        // homework.increaseMinionsAgeEx8();

        // ninth task
        // homework.increaseAgeWithStoreProcedureEx9();


    }
}
