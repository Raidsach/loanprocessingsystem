package com.loanprocessingsystem.controller;

import java.time.LocalDate; 

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loanprocessingsystem.entities.Customer;
import com.loanprocessingsystem.exception.CustomerNotFoundException;
import com.loanprocessingsystem.service.ICustomerService;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;

/*Customer Service REST Controller
 * Customer service controller contains various methods to interact with the loan user table
 * "/loan" is to check the  page 
 * "/add" is to add new customer to the database
 * "/view/{cust_id}" to view the customer details by using the customer ID
 * "/update" to update the customer details or make any specific changes to details
 * "/delete" to delete the customer from the database
 * "/viewall" to viewall the customers available in the database
 * "/viewbydate/{date_of_applicaton}"
 * */

@Validated
@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
@ApiModel(value = "Customer Service Rest Controller" , description = "Holds all APIs related to the customer")
public class CustomerServiceController {
	Logger logger = Logger.getLogger(CustomerServiceController.class.getName());
	@Autowired
	private ICustomerService customerService;
	
	
	
	public CustomerServiceController() {
		logger.log(Level.INFO,"-----> Customer Rest Controller Working!");
		
	}

	@GetMapping("/loan")
	public String loanRequest() {
		return "Welcome : Loan Processing System)";
	}
	
	@ApiOperation(value = "POST mapping to add new customers to the LOAN_USER table in the Database", response = Customer.class)
	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody @Valid Customer customer) {
		this.customerService.addCustomer(customer);
		return customer;
	}
	@ApiOperation(value = "GET mapping to view customers by entering the customer ID", response = Customer.class )	
	@GetMapping("/view/{custid}")
	public Customer viewCustomer(@PathVariable("custid") int custid) throws CustomerNotFoundException {
		return this.customerService.viewCustomer(custid);
		
	}
	@ApiOperation(value = "PUT mapping to update customer details by customer ID", response = Customer.class )
	@PutMapping("/update")
	public Customer updateCustomer(@RequestBody @Valid Customer customer) throws CustomerNotFoundException{
		return this.customerService.updateCustomer(customer);
		
	}
	@ApiOperation(value = "DELETE mapping to delete the customer from the LOAN_USER table by customer ID", response = Customer.class )
	@DeleteMapping("/delete/{custid}")
	public Customer deleteCustomer(@PathVariable("custid") int custid) throws CustomerNotFoundException{
		return this.customerService.deleteCustomer(custid);
		
	}
	@ApiOperation(value = "GET mapping to get all customers from the database", response = List.class )
	@GetMapping("/viewall")
	public List<Customer> viewAllCustomers(){
		return this.customerService.viewAllCustomers();
		
	}
	@ApiOperation(value = "GET mapping to view all customers by date of application", response = List.class )
	@GetMapping("/viewbydate/{date}")
	public List<Customer> viewCustomerList(@PathVariable("date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate dateOfApplication){
		return this.customerService.viewCustomerList(dateOfApplication);
		
	}

}
