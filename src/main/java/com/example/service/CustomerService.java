package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Customer;
import com.example.repo.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	public Optional<Customer> getOneCustomer(int id) {
		return customerRepository.findById(id);
	}

	public void addCustomer(Customer customer) {
		customerRepository.save(customer);
	}

	public void updateCustomer(int id, Customer customer) {
		customer.setId(id);
		customerRepository.save(customer);
	}

	public void deleteCustomer(int id) {
//		if (customerRepository.existsById(id)) {
			customerRepository.deleteById(id);
		
	}

	public void deposit(int id, float amount) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Customer not found"));
		float newBalance = customer.getBalance() + amount;
		customer.setBalance(newBalance);
		customerRepository.save(customer);
	}

	public void withdrawal(int id, float amount) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Customer not found"));
		float currentBalance = customer.getBalance();
		if (currentBalance >= amount) {
			float newBalance = currentBalance - amount;
			customer.setBalance(newBalance);
			customerRepository.save(customer);
		} else {
			throw new RuntimeException("Insufficient balance for withdrawal");
		}
	}

	public float checkBalance(int id) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Customer not found"));
		return customer.getBalance();
	}

	public void editProfile(int id,Customer cust) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Customer not found"));
		customer.setName(cust.getName());
		customer.setAddress(cust.getAddress());
		customer.setEmail(cust.getEmail());
		customer.setAge(cust.getAge());
		customer.setPhoneNumber(cust.getPhoneNumber());
		
		customerRepository.save(customer);
	}


	public void changePassword(int id, Customer customer) {
		customer.setId(id);
		customerRepository.save(customer);
	}
}

