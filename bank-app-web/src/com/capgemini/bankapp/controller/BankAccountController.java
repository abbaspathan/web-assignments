package com.capgemini.bankapp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.capgemini.bankapp.client.BankAccount;
import com.capgemini.bankapp.exception.AccountNotFoundException;
import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.service.BankAccountService;
import com.capgemini.bankapp.service.impl.BankAccountServiceImpl;

@WebServlet(urlPatterns = { "*.do" }, loadOnStartup = 1)
public class BankAccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BankAccountService bankService;

	public BankAccountController() {
		super();

		bankService = new BankAccountServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		String path = request.getServletPath();

		if (path.equals("/displayAllBankAccounts.do")) {

			List<BankAccount> bankAccounts = bankService.findAllBankAccountsDetails();
			RequestDispatcher dispatcher = request.getRequestDispatcher("displayAllBankAccounts.jsp");
			request.setAttribute("accounts", bankAccounts);
			dispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		String path = request.getServletPath();
		PrintWriter out = response.getWriter();

		if (path.equals("/addNewBankAccount.do")) {
			String accountHolderName = request.getParameter("customer_name");
			String accountType = request.getParameter("account_type");
			double balance = Double.parseDouble(request.getParameter("account_balance"));

			BankAccount account = new BankAccount(accountHolderName, accountType, balance);

			if (bankService.addNewBankAccount(account)) {

				out.println("<h2>Account Created Successfully...</h2>");
				out.println("<h2><a href='index.html'>|Home|</h2>");
				out.close();
			} else {

			}

		} else if (path.equals("/withdraw.do")) {

			long accountId = Long.parseLong(request.getParameter("account_id"));
			double amount = Double.parseDouble(request.getParameter("amount"));
			try {
				double result = bankService.withdraw(accountId, amount);
				out.println("<h2>Transaction Successful....<br>Your Balance Is :" + result + "</h2>");
				out.println("<h2><a href='index.html'>|Home|</h2>");
				out.close();
			} catch (AccountNotFoundException | LowBalanceException e) {
				out.println("<h2>" + e.getMessage() + "</h2>");
				out.println("<h2><a href='index.html'>|Home|</h2>");
				out.close();
			}

		} else if (path.equals("/deposit.do")) {

			long accountId = Long.parseLong(request.getParameter("account_id"));
			double amount = Double.parseDouble(request.getParameter("amount"));
			try {
				double result = bankService.deposit(accountId, amount);
				out.println("<h2>Transaction Successful....<br>Your Balance Is :" + result + "</h2>");
				out.println("<h2><a href='index.html'>|Home|</h2>");
				out.close();
			} catch (AccountNotFoundException e) {

				out.println("<h2>" + e.getMessage() + "</h2>");
				out.println("<h2><a href='index.html'>|Home|</h2>");
				out.close();
			}
		} else if (path.equals("/fundtransfer.do")) {
			long senderAccountId = Long.parseLong(request.getParameter("sender_account_id"));
			long reciverAccountId = Long.parseLong(request.getParameter("reciver_account_id"));
			double amount = Double.parseDouble(request.getParameter("amount"));
			try {
				double result = bankService.fundTransfer(senderAccountId, reciverAccountId, amount);
				out.println("<h2>Transaction Successful....<br>Your Balance Is :" + result + "</h2>");
				out.println("<h2><a href='index.html'>|Home|</h2>");
				out.close();
			} catch (AccountNotFoundException | LowBalanceException e) {
				out.println("<h2>" + e.getMessage() + "</h2>");
				out.println("<h2><a href='index.html'>|Home|</h2>");
				out.close();
			}
		} else if (path.equals("/checkBalance.do")) {
			long accountId = Long.parseLong(request.getParameter("account_id"));
			try {
				double result = bankService.checkBalance(accountId);
				out.println("<h2>Transaction Successful....<br>Your Balance Is :" + result + "</h2>");
				out.println("<h2><a href='index.html'>|Home|</h2>");
				out.close();
			} catch (AccountNotFoundException e) {

				out.println("<h2>" + e.getMessage() + "</h2>");
				out.println("<h2><a href='index.html'>|Home|</h2>");
				out.close();
			}
		} else if (path.equals("/deleteaccount.do")) {
			long accountId = Long.parseLong(request.getParameter("account_id"));

			try {
				boolean result = bankService.deleteBankAccount(accountId);
				if (result) {
					out.println("<h2>Account Deleted Successful</h2>");
				} else {
					out.println("<h2>Account can't delete</h2>");
				}
				out.println("<h2><a href='index.html'>|Home|</h2>");
				out.close();

			} catch (AccountNotFoundException e) {
				out.println("<h2>" + e.getMessage() + "</h2>");
				out.println("<h2><a href='index.html'>|Home|</h2>");
				out.close();
			}

		} else if (path.equals("/displayBankAccounts.do")) {
			long accountId = Long.parseLong(request.getParameter("account_id"));

			try {
				BankAccount account = bankService.searchAccountDetails(accountId);
				RequestDispatcher dispatcher = request.getRequestDispatcher("displayBankAccounts.jsp");
				request.setAttribute("account", account);
				dispatcher.forward(request, response);

			} catch (AccountNotFoundException e) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("displayBankAccountsException.jsp");
				dispatcher.forward(request, response);
			}

		} else if (path.equals("/getBankAccount.do")) {
			long accountId = Long.parseLong(request.getParameter("account_id"));
			try {
				BankAccount account = bankService.searchAccountDetails(accountId);
				RequestDispatcher dispatcher = request.getRequestDispatcher("getBankAccountDetails.jsp");
				request.setAttribute("account", account);
				dispatcher.forward(request, response);
			} catch (AccountNotFoundException e) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("displayBankAccountsException.jsp");
				dispatcher.forward(request, response);
			}
		} else if (path.equals("/updateBankDetails.do")) {

			long accountId = Long.parseLong(request.getParameter("accountId"));
			String accountHolderName = request.getParameter("accountHolderName");
			String accountType = request.getParameter("accountType");

			boolean result = bankService.updateBankAccountDetails(accountId, accountHolderName, accountType);
			if (result) {
				out.println("<h2>Account updated successfully...!");
				out.println("<h2><a href='index.html'>|Home|</h2>");
				out.close();
			} else {
				out.println("<h2>Fail to updated details...!");
				out.println("<h2><a href='index.html'>|Home|</h2>");
				out.close();
			}
		}

	}

}
