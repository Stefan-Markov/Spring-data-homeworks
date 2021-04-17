import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class Homework {
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String MINIONS_TABLE_NAME = "minions_db";
    private Connection connection;

    public void setConnection(String user, String password) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);

        connection = DriverManager.getConnection(CONNECTION_STRING + MINIONS_TABLE_NAME,
                properties);
    }

    public void getVillainsNamesEx2() throws SQLException {
        String query = "select v.name, count(mv.minion_id) as count from villains as v join minions_villains mv on v.id = mv.villain_id\n" +
                "group by v.id\n" +
                "having count > 15\n" +
                "order by count desc;\n";
        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();


        while (resultSet.next()) {
            System.out.printf("%s %d%n", resultSet.getString("name"),
                    resultSet.getInt("count"));
        }
    }

    public void getMinionsNameEx3() throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter villain id!");
        int villainId = Integer.parseInt(reader.readLine());

        String villainName = getEntityName(villainId, "villains");
        if (villainName == null) {
            System.out.printf("Not existing villain! - %d", villainId);
            return;
        }
        System.out.printf("Villain: %s%n", villainName);

        String query = "select m.name, m.age\n" +
                "from minions as m\n" +
                "         join minions_villains mv on m.id = mv.minion_id\n" +
                "where mv.villain_id = ?;";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, villainId);

        ResultSet resultSet = statement.executeQuery();

        int counter = 1;
        while (resultSet.next()) {
            System.out.printf("%d. %s %d%n", counter++, resultSet.getString("name"),
                    resultSet.getInt("age"));

        }
    }

    private String getEntityName(int entityId, String tableName) throws SQLException {
        String query = String.format("Select name from %s where id = ?", tableName);

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, entityId);
        ResultSet resultSet = statement.executeQuery();

        return resultSet.next() ? resultSet.getString("name") : null;
    }

    public void addMinionEx4() throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter minions info: name, age, town name!");
        String[] minionsInfo = reader.readLine().split("\\s+");
        String minionName = minionsInfo[0];
        int minionAge = Integer.parseInt(minionsInfo[1]);
        String townName = minionsInfo[2];

        int townId = getEntityIdByName(townName, "towns");


        if (townId < 0) {
            insertEntityInTowns(townName);
            System.out.println("New town added: " + townName);
        } else {
            System.out.println("Town already exist!");
        }

        int nameIdMinion = getEntityIdByName(minionName, "minions");
        if (nameIdMinion < 0) {
            insertEntityInMinions(minionName, minionAge, townId);
            System.out.println("Insert new minion successfully: " + minionName + " " +
                    minionAge + " " + townId);
        } else {
            System.out.println("Minion already exist!");
        }
        System.out.println("Please enter a Villain!");
        String villainName = reader.readLine();

        int villainId = getEntityIdByName(villainName, "villains");
        if (villainId < 0) {
            insertVillain(villainName, "villains");
            System.out.println("Successfully added villain: " + villainName);
        } else {
            System.out.println("Already exist villain!");
        }
        int realVillainId = getVillainID(villainName, "villains");
        insertIntoMappingTableMinnions_Villains(nameIdMinion, villainId);
        System.out.println("Inserted into mapping table: minion ID " + nameIdMinion +
                " villain ID: " + villainId);
    }

    private int getVillainID(String villainName, String villains) throws SQLException {
        String query = "select id from villains where name = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, villainName);
        statement.execute();
        ResultSet resultSet = statement.executeQuery();

        return resultSet.next() ? resultSet.getInt(1) : -1;
    }

    private void insertIntoMappingTableMinnions_Villains(int minionId, int villainId) throws SQLException {
        String query = "insert into minions_villains (minion_id,villain_id)" +
                "values (?,?)";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, minionId);
        statement.setInt(2, villainId);
        statement.execute();
    }

    private void insertVillain(String villainName, String villains) throws SQLException {
        String query = "insert into villains (name,evilness_factor) values (?,?)";
        String evilnessFactor = "evil";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, villainName);
        statement.setString(2, evilnessFactor);
        statement.execute();
    }

    private void insertEntityInMinions(String minionName, int minionAge, int townId) throws SQLException {
        String query = "Insert into minions (name,age,town_id) values (?,?,? )";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, minionName);
        statement.setInt(2, minionAge);
        statement.setInt(3, townId);

        statement.execute();
    }

    private void insertEntityInTowns(String townName) throws SQLException {
        String query = "insert into towns (name) values (?)";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, townName);
        statement.execute();
    }

    private int getEntityIdByName(String entityName, String tableName) throws SQLException {
        String query = String.format("select id from %s where name = ?", tableName);

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, entityName);

        ResultSet resultSet = statement.executeQuery();

        return resultSet.next() ? resultSet.getInt(1) : -1;
    }

    public void changeTownNameCasingEx5() throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter country");
        String countryName = reader.readLine();

        String query = "update towns set name = UPPER(name) where country  = ?";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, countryName);
        int townsNum = statement.executeUpdate();
        System.out.printf("%d town names where affected.%n", townsNum);

        if (townsNum > 0) {
            String queryName = "select name from towns where country = ?";
            PreparedStatement statement1 = connection.prepareStatement(queryName);
            statement1.setString(1, countryName);
            ResultSet resultSet = statement1.executeQuery();

            while (resultSet.next()) {
                System.out.printf("%s, ", resultSet.getString("name"));
            }
        }
    }

    public void increaseAgeWithStoreProcedureEx9() throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Please enter ID for minion!");
        int minionId = Integer.parseInt(reader.readLine());

        String query = "call usp_get_older(?)";

        CallableStatement callableStatement = connection.prepareCall(query);
        callableStatement.setInt(1, minionId);
        callableStatement.executeQuery();

        String queryMinion = "select name,age from minions where id = ?";
        PreparedStatement statementMinion = connection.prepareStatement(queryMinion);
        statementMinion.setInt(1, minionId);

        ResultSet resultSet = statementMinion.executeQuery();

        while (resultSet.next()) {
            System.out.printf("Name: %s - Age: %d.", resultSet.getString("name"),
                    resultSet.getInt("age"));
        }
    }

    public void reverseOrderOfMinionNamesEx7() throws SQLException {

        String query = "select name from minions\n" +
                "order by id desc;";
        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString("name"));
        }
    }

    public void increaseMinionsAgeEx8() throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter ids of minions:");
        String[] minionsIds = reader.readLine().split("\\s+");
        Map<String, Integer> minionsInfo = new LinkedHashMap<>();

        for (int i = 0; i < minionsIds.length; i++) {
            int minionID = Integer.parseInt(minionsIds[i]);


            String queryProcedure = "call usp_get_older_and_change_name(?)";

            CallableStatement procedure = connection.prepareCall(queryProcedure);
            procedure.setInt(1, minionID);
            procedure.executeQuery();

            String query = "select name, age from minions where id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, minionID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                minionsInfo.put(resultSet.getString("name"),
                        resultSet.getInt("age"));
            }
        }
        for (Map.Entry<String, Integer> entry : minionsInfo.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    public void removeVillainEx6() throws IOException, SQLException {
        System.out.println("Enter villain ID!");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int villainID = Integer.parseInt(reader.readLine());

        String queryVillain = "Select name from villains where id = ?";

        PreparedStatement statementVillain = connection.prepareStatement(queryVillain);
        statementVillain.setInt(1, villainID);
        ResultSet resultSet = statementVillain.executeQuery();
        String villainName = "";
        while (resultSet.next()) {
            villainName = resultSet.getString("name");
        }

        String queryMappingTable = "SELECT COUNT(*) as count FROM minions_villains where villain_id = ?";
        PreparedStatement statement = connection.prepareStatement(queryMappingTable);
        statement.setInt(1, villainID);
        ResultSet resultNumber = statement.executeQuery();


        int numberOfMinions = 0;
        while (resultNumber.next()) {
            numberOfMinions = resultNumber.getShort("count");
        }

        if (villainName.trim().isEmpty()) {
            System.out.println("No such villain!");
        } else {
            System.out.printf("%s was deleted from table!%n", villainName);
            System.out.printf("%d minions were released!%n", numberOfMinions);
        }

        // Маркирано е като коментар за да се тества по лесно, иначе ще изтрие данните!

//        String queryCommand = "delete from minions_villains where villain_id = ?";
//        PreparedStatement statementDeleteFromMappingTable =
//                connection.prepareStatement(queryCommand);
//        statementDeleteFromMappingTable.executeQuery();
//
//        String queryDelete = "delete from villains where id = ?\n";
//        PreparedStatement statementDelete = connection.prepareStatement(queryDelete);
//        statementDelete.executeQuery();
    }
}

