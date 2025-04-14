package lab6;

import java.time.LocalDate;
import java.util.*;

public class UtilityCompany {
	private int newAccountNum = 100000;
	private String username;
	private String password;
	private int accountNum;
	private double nextBillCost;
	private LocalDate dueDate;
	private ArrayList<String> billHistory;
	
	//initialization
	public void createAccount(String user, String pass)
	{
		accountNum = newAccountNum;
		username = user;
		password = pass;
        this.billHistory = new ArrayList<>();
		newAccountNum++;
	}
	
	private void createBill()
	{
		nextBillCost = Math.random() * 100;
		dueDate = LocalDate.now();
	}
	
	//logic
	public boolean userLogin(String user, String pass)
	{
		return username.equals(user) && password.equals(pass);
	}
	
	public void addBillPayment(int amount)
	{
		 if (billHistory.size() >= 3) {
	            billHistory.remove(0); 
	        }
	        billHistory.add("Paid $" + amount + " on 2025-04-14");
	        createBill();
	    }

	
	
	//Getters 
	public int getAccountNum()
	{
		return accountNum;
	}
	public double nextBillCost()
	{
		return this.nextBillCost;
	}
	
	public LocalDate getDueDate()
	{
		return this.dueDate;
	}
	
	
	
	
}
