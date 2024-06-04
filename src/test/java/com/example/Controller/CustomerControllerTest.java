package com.example.Controller;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
 
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.controller.CustomerController;
import com.example.model.Customer;
import com.example.repo.CustomerRepository;
import com.example.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.junit.jupiter.MockitoExtension;
 
 
@ExtendWith(MockitoExtension.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CustomerService customerService;
	
	@MockBean
	private CustomerRepository customerRepository;
	
	@InjectMocks
	private CustomerController customerController;
	
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
	void testRead() throws Exception {
		List<Customer> customers = Arrays.asList(cust1, cust2);
		when(customerService.getAllCustomers()).thenReturn(customers);
		
		mockMvc.perform(get("/customers/read"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].name").value("Akash"))
		.andExpect(jsonPath("$[1].name").value("Akansha"));
		
	}
		@Test
		void testGetOneCustomer() throws Exception {
			when(customerService.getOneCustomer(1)).thenReturn(Optional.of(cust1));
		
			mockMvc.perform(get("/customers/1"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.name").value("Akash"));
		}

	
	@Test
	void testAdd() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String teamJson = objectMapper.writeValueAsString(cust1);
		
		mockMvc.perform(post("/customers/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(teamJson))
				.andExpect(status().isOk());
		
		verify(customerService).addCustomer(cust1);
	}
	
	@Test
	void testUpdate() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String teamJson = objectMapper.writeValueAsString(cust1);
		
		mockMvc.perform(put("/customers/update/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(teamJson))
				.andExpect(status().isOk());
		
		verify(customerService).updateCustomer(1, cust1);
	}
	
	@Test
	void testDelete() throws Exception {
		mockMvc.perform(delete("/customers/delete/1"))
		.andExpect(status().isOk());
		
		verify(customerService).deleteCustomer(1);
		
	}
	
	@Test
	void testDeposit() throws Exception {
	    mockMvc.perform(post("/customers/1/deposit")
	            .param("amount", "200"))
	            .andExpect(status().isOk());

	    verify(customerService).deposit(1, 200);
	}
	
	@Test
	void testWithdrawal() throws Exception {
	    mockMvc.perform(post("/customers/1/withdrawal")
	            .param("amount", "200"))
	            .andExpect(status().isOk());

	    verify(customerService).withdrawal(1, 200);
	}
	
	@Test
	void testBalance() throws Exception {
	    float expectedBalance = cust1.getBalance();
	    when(customerService.checkBalance(1)).thenReturn(expectedBalance);
	    mockMvc.perform(get("/customers/1/balance"))
	            .andExpect(status().isOk())
	            .andExpect(content().string(String.valueOf(expectedBalance)));

	    verify(customerService).checkBalance(1);
	}
	
	@Test
	void testEditProfile() throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		String teamJson = objectMapper.writeValueAsString(cust1);
		
		mockMvc.perform(put("/customers/editprofile/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(teamJson))
				.andExpect(status().isOk());
		
		verify(customerService).editProfile(1, cust1);
	}
	
	
	@Test
	void testChangePassword() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String teamJson = objectMapper.writeValueAsString(cust1);
		
		mockMvc.perform(put("/customers/changePassword/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(teamJson))
				.andExpect(status().isOk());
		
		verify(customerService).changePassword(1, cust1);
	}

}
