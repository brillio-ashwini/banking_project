package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.model.Customer;
import com.example.service.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins="*")


public class CustomerController {
 
	@Autowired
	private CustomerService customerService;

	@GetMapping("/read")
	public List<Customer> getAllCustomers() {
		return customerService.getAllCustomers();
	}

	@GetMapping("/{id}")
	public Optional<Customer> getOneCustomer(@PathVariable int id) {
		return customerService.getOneCustomer(id);
	}

	@PostMapping("/add")
	public void addCustomer(@RequestBody Customer customer) {
		customerService.addCustomer(customer);
	}

	@PutMapping("/update/{id}")
	public void updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
		customerService.updateCustomer(id, customer);
	}

	@DeleteMapping("/delete/{id}")
	public void deleteCustomer(@PathVariable int id) {
		customerService.deleteCustomer(id);
	}

	@PostMapping("/{id}/deposit")
	public void deposit(@PathVariable int id,  @RequestParam  float amount) {
		customerService.deposit(id, amount);
	}

	@PostMapping("/{id}/withdrawal")
	public void withdrawal(@PathVariable int id, @RequestParam float amount) {
		customerService.withdrawal(id, amount);
	}

	@GetMapping("/{id}/balance")
	public float checkBalance(@PathVariable int id) {
		return customerService.checkBalance(id);
	}

	@PutMapping("/editprofile/{id}")
	public void editProfile(@PathVariable int id,@RequestBody Customer cust) {
		customerService.editProfile(id,cust);
	}
	
	@PutMapping("/changePassword/{id}")
	public void changePassword(@PathVariable int id, @RequestBody Customer customer) {
		customerService.changePassword(id, customer);
	}
}
