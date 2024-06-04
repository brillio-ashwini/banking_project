package com.example.Service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
 
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.model.Customer;
import com.example.repo.CustomerRepository;
import com.example.service.CustomerService;
 
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
	@Mock
	private CustomerRepository customerRepository;
	
	@InjectMocks
	private CustomerService customerService;
	
	private Customer cust1;
	private Customer cust2;
	
	@BeforeEach
	void setUp() {
		cust1 = new Customer();
		cust1.setId(1);
		cust1.setName("Akash");
		cust1.setAddress("Pune,Maharashtra");
		cust1.setEmail("akash@gmail.com");
		cust1.setUsername("akash_12");
		cust1.setPassword("akash_12");
		cust1.setAge(24);
		cust1.setSsn("112-224-33");
		cust1.setPhoneNumber("9089674556");
		cust1.setBalance(100000);
		
		cust2 = new Customer();
		cust2.setId(2);
		cust2.setName("Akansha");
		cust2.setAddress("Pune,Maharashtra");
		cust2.setEmail("akansha@gmail.com");
		cust2.setUsername("akansha_12");
		cust2.setPassword("akansha_12");
		cust2.setAge(25);
		cust2.setSsn("112-224-34");
		cust1.setPhoneNumber("9089674556");
		cust2.setBalance(100000);
	}
	
	@Test
	void testRead() {
		List<Customer> customer = Arrays.asList(cust1,cust2);
		when(customerRepository.findAll()).thenReturn(customer);
		
		List<Customer>result=customerService.getAllCustomers();
		assertEquals(2,result.size());
		assertEquals("Akash",result.get(0).getName());
		assertEquals("Akansha",result.get(1).getName());
	}
	
	@Test
	void testReadOne(){
		when(customerRepository.findById(1)).thenReturn(Optional.of(cust1));
		
		Optional<Customer> result=customerService.getOneCustomer(1);
		assertTrue(result.isPresent());
		assertEquals("Akash",result.get().getName());
	}
	
	@Test
	void testAdd() {
        customerService.addCustomer(cust1);
		verify(customerRepository).save(cust1);
	}

	 @Test
	    void testUpdateCustomer() {
	        Customer updatedCustomer = new Customer();
	        updatedCustomer.setName("John Doe");
	        updatedCustomer.setAddress("New Address");
	        updatedCustomer.setEmail("john.doe@example.com");
	        updatedCustomer.setAge(30);
	        updatedCustomer.setPhoneNumber("1234567890");

	        when(customerRepository.save(updatedCustomer)).thenReturn(updatedCustomer);

	        customerService.updateCustomer(1, updatedCustomer);

	        assertEquals(1, updatedCustomer.getId());
	        assertEquals("John Doe", updatedCustomer.getName());
	        assertEquals("New Address", updatedCustomer.getAddress());
	        assertEquals("john.doe@example.com", updatedCustomer.getEmail());
	        assertEquals(30, updatedCustomer.getAge());
	        assertEquals("1234567890", updatedCustomer.getPhoneNumber());
	    }

	    @Test
	    void testDeleteCustomer() {
	    	customerService.deleteCustomer(1);
			verify(customerRepository).deleteById(1);
			
//			int id = 1;
//	        when(customerRepository.existsById(id)).thenReturn(true);
//
//	        boolean result = customerService.deleteCustomer(id);
//
//	        assertTrue(result);
//	        verify(customerRepository, times(1)).deleteById(id);
	    }

	    @Test
	    void testDeposit() {
	    	 int id = 1;
	         float amount = 100;

	         Customer customer = new Customer();
	         customer.setId(id);
	         customer.setBalance(500);

	         when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

	         customerService.deposit(id, amount);
	         assertEquals(600, customer.getBalance());
	         verify(customerRepository).save(customer);
	    }

	    @Test
	    void testWithdrawal() {
	        int id = 1;
	        float amount = 100;

	        Customer customer = new Customer();
	        customer.setId(id);
	        customer.setBalance(500);
	        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
	        customerService.withdrawal(id, amount);
	        assertEquals(400, customer.getBalance());
	        verify(customerRepository).save(customer);
	    }

	    @Test
	    void testWithdrawalInsufficientBalance() {
	        int id = 1;
	        float amount = 600;

	        Customer customer = new Customer();
	        customer.setId(id);
	        customer.setBalance(500);

	        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

	        assertThrows(RuntimeException.class, () -> customerService.withdrawal(id, amount));
	        assertEquals(500, customer.getBalance());
	        verify(customerRepository, never()).save(customer);
	    }
	  

	    @Test
	    void testCheckBalance() {
	        int id = 1;
	        float balance = 1000;

	        Customer customer = new Customer();
	        customer.setId(id);
	        customer.setBalance(balance);

	        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

	        assertEquals(balance, customerService.checkBalance(1));
	    }

	    @Test
	    void testEditProfile() {
	        int id = 1;
	        Customer customer = new Customer();
	        customer.setName("John Doe");
	        customer.setAddress("New Address");
	        customer.setEmail("john.doe@example.com");
	        customer.setAge(30);
	        customer.setPhoneNumber("1234567890");

	        Customer existingCustomer = new Customer();
	        existingCustomer.setId(id);

	        when(customerRepository.findById(id)).thenReturn(Optional.of(existingCustomer));

	        customerService.editProfile(id, customer);

	        assertEquals("John Doe", existingCustomer.getName());
	        assertEquals("New Address", existingCustomer.getAddress());
	        assertEquals("john.doe@example.com", existingCustomer.getEmail());
	        assertEquals(30, existingCustomer.getAge());
	        assertEquals("1234567890", existingCustomer.getPhoneNumber());
	        verify(customerRepository, times(1)).save(existingCustomer);
	    }

	    @Test
	    void testChangePassword() {
	        int id = 1;
	        String newPassword = "newpassword";

	        Customer customer = new Customer();
	        customer.setId(id);
	        customer.setPassword(newPassword);

	        when(customerRepository.save(customer)).thenReturn(customer);

	        customerService.changePassword(id, customer);

	        assertEquals(newPassword, customer.getPassword());
	        verify(customerRepository, times(1)).save(customer);
	    }
	}