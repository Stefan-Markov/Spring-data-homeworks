package spring.mappingexcercise.services;

import java.io.IOException;

public interface CustomerService {
    void seedCustomers() throws IOException;

    String orderCustomers();
}
