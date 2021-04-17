import model.*;

import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Engine implements Runnable {

    private final EntityManager entityManager;
    private final BufferedReader reader;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }


    @Override
    public void run() {

        //ex2
//        changeCasingEx2();

        //ex3
        try {
            containsEmployeeEx3();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //ex4
//        employeesWithSalaryOver5000_Ex4();

        //ex5
//        employeesFromDepartmentsEx5();

        // ex6
//        try {
//            addingNewAddressAndUpdateEmployeeEx6();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // ex7
//        addressesWithEmployeeCountEx7();

        // ex8
//        try {
//            getEmployeeWithProjectEx8();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // ex9
//        findLatest10ProjectsEx9();


        // ex10
//        increaseSalariesEx10();

        // ex.11
//        try {
//            findEmployeesByFirstNameEx11();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // ex.12
//        employeesMaximumSalariesEx12();

        // ex.13
//        try {
//            removeTownsEx13();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void removeTownsEx13() throws IOException {

        System.out.println("Exercise 13");
        System.out.println("Please enter a city name!");

        String targetTown = reader.readLine();
        int addressesSize = 0;
        entityManager.getTransaction().begin();

        List<Address> addresses = (List<Address>) entityManager
                .createQuery("SELECT a FROM Address a Where a.town.name = :townName")
                .setParameter("townName", targetTown)
                .getResultList();
        addressesSize = addresses.size();

        for (Address address : addresses) {
            for (Employee e : address.getEmployees()) {
                e.setAddress(null);
            }
            entityManager.flush();
            entityManager.remove(address);
        }
        Town town = addresses.get(0).getTown();
        entityManager.remove(town);
        entityManager.getTransaction().commit();

        System.out.println(String.format("%d address in %s deleted", addressesSize, targetTown));

    }

    private void employeesMaximumSalariesEx12() {
        System.out.println("Exercise 12");

        List<Employee> employees = new ArrayList<>();

        for (int i = 1; i < 17; i++) {
            List<Employee> inDepartment = entityManager
                    .createQuery("select e from Employee as e " +
                            "where e.department.id =:id " +
                            "order by  e.salary desc ", Employee.class).setParameter("id", i)
                    .getResultList();
            Employee current = inDepartment.get(0);

            if (current.getSalary().compareTo(BigDecimal.valueOf(30000)) < 0 ||
                    current.getSalary().compareTo(BigDecimal.valueOf(70000)) > 0) {
                employees.add(current);
            }

        }

        for (Employee employee : employees) {
            System.out.printf("%-30s  %-15.2f %n", employee.getDepartment().getName(), employee.getSalary());
        }
    }


    private void findEmployeesByFirstNameEx11() throws IOException {
        System.out.println("Exercise 11");
        System.out.println("Please write a pattern something like - \"SA\" ");
        String pattern = reader.readLine();
        pattern = pattern + "%";

        entityManager.createQuery("select e from Employee  e " +
                "where e.firstName like ?1 ", Employee.class).setParameter(1, pattern)
                .getResultStream().forEach(employee -> {
            System.out.printf("%s %s %s %s%n", employee.getFirstName(), employee.getLastName(),
                    employee.getJobTitle(), employee.getSalary());
        });
    }

    private void findLatest10ProjectsEx9() {
        System.out.println("Exercise 9");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.s");

        entityManager.createQuery("select p from Project p " +
                "order by p.name asc ", Project.class).setMaxResults(10).getResultStream()
                .forEach(project -> {
                    System.out.printf("Name: %s %n", project.getName());
                    System.out.printf("%s %n", project.getDescription());
                    System.out.printf("%s %n", project.getStartDate() == null ? null : formatter.format(project.getStartDate()));
                    System.out.printf("%s %n", project.getEndDate() == null ? null : formatter.format(project.getEndDate()));
                });

    }

    private void increaseSalariesEx10() {
        System.out.println("Exercise 10");
        entityManager.getTransaction().begin();

        int affectedRows =
                entityManager
                        .createQuery("update Employee  e set  e.salary = e.salary*1.12 " +
                                "where e.department.id in (1,2,4,11)")
                        .executeUpdate();

        entityManager.getTransaction().commit();

        System.out.println("Affected rows " + affectedRows);

        entityManager
                .createQuery("select e from Employee " +
                        " e where e.department.id in (1,2,4,11)", Employee.class)
                .getResultStream().forEach(employee -> {
            System.out.printf("%s %s ($%.2f)%n", employee.getFirstName(), employee.getLastName(),
                    employee.getSalary());
        });
    }

    private void getEmployeeWithProjectEx8() throws IOException {
        System.out.println("Exercise 8");
        System.out.println("Enter valid employee ID:");

        int id = Integer.parseInt(reader.readLine());
        Employee employee = entityManager.find(Employee.class, id);

        System.out.printf("%s %s - %s%n", employee.getFirstName(), employee.getLastName(),
                employee.getJobTitle());

        employee.getProjects()
                .stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(project -> {
                    System.out.printf("\t %s%n", project.getName());
                });
    }


    private void addressesWithEmployeeCountEx7() {
        System.out.println("Exercise 7");
        List<Address> addresses =
                entityManager.createQuery("select a from Address a " +
                        "order by a.employees.size desc ", Address.class)
                        .setMaxResults(10)
                        .getResultList();

        addresses.forEach(address -> {
            System.out.printf("%s, %s, - %d employees.%n", address.getText(),
                    address.getTown().getName(), address.getEmployees().size());
        });


    }

    private void addingNewAddressAndUpdateEmployeeEx6() throws IOException {
        System.out.println("Exercise 6");
        Address address = createAddress("Vitoshka 15");
        System.out.println("Enter employee ID: ");

        String lastName = reader.readLine();
        Employee employee = entityManager.find(Employee.class, 291);

        entityManager.getTransaction().begin();

        employee.setAddress(address);

        entityManager.getTransaction().commit();
    }

    private Address createAddress(String addressText) {
        Address address = new Address();
        address.setText(addressText);

        entityManager.getTransaction().begin();

        entityManager.persist(address);

        entityManager.getTransaction().commit();
        return address;
    }

    private void employeesFromDepartmentsEx5() {
        System.out.println("Exercise 5");
        entityManager.createQuery("select  e from Employee " +
                        " e where e.department.name ='Research and Development'" +
                        "order by e.salary, e.id",
                Employee.class).getResultStream().forEach(employee -> {
            System.out.printf("%s %s from Research and Development - $%.2f%n",
                    employee.getFirstName(), employee.getLastName(), employee.getSalary());
        });
    }

    private void employeesWithSalaryOver5000_Ex4() {
        System.out.println("Exercise 4");
        entityManager.createQuery("select e from " +
                "Employee  e where e.salary > 50000", Employee.class).getResultStream()
                .map(Employee::getFirstName).forEach(System.out::println);
    }

    private void containsEmployeeEx3() throws IOException {
        System.out.println("Exercise 3");
        System.out.println("Problem #3: Enter employee full name: ");
        String fullName = reader.readLine();

        List<Employee> employees = entityManager
                .createQuery("select e from Employee e where" +
                        " concat(e.firstName,' ',e.lastName)= :name", Employee.class)
                .setParameter("name", fullName)
                .getResultList();

        System.out.println(employees.size() == 0 ? "No" : "Yes");
    }

    private void changeCasingEx2() {
        System.out.println("Exercise 2");
        List<Town> towns = entityManager.createQuery("select  t from Town t" +
                " where length( t.name) <=5", Town.class)
                .getResultList();

        entityManager.getTransaction().begin();
        towns.forEach(entityManager::detach);


        for (Town town : towns) {
            town.setName(town.getName().toLowerCase());
        }

        towns.forEach(entityManager::merge);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }
}
