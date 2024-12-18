package fit.localchef.service;

import fit.localchef.models.Customer;
import fit.localchef.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer findByEmail(String email) {
        return customerRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found for email: " + email));
    }
}
