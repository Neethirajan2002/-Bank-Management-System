package com.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lakhani.CheckingAccount;
import com.lakhani.SavingsAccount;
import com.lakhani.Transactions;

/**
 * Servlet implementation class OpenAccountServlet
 */
/*@WebServlet(name = "OpenAccountServlet1", urlPatterns = { "/OpenAccountServlet1" })*/
public class OpenAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private CheckingAccount ckAcc,nckAcc;
	private SavingsAccount svAcc, nsvAcc;
	private Transactions tr;
       
    public OpenAccountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String accountSelection = request.getParameter("OpenAccount");
		String customer_name = request.getParameter("customer_name");
		String acc_no = request.getParameter("Ac_no");
		String amount = request.getParameter("Amount");
		System.out.println("1");
		int account_no = Integer.parseInt(acc_no);
		double amt = Double.parseDouble(amount);
		
		//Getting UserName
		HttpSession session = request.getSession();
		String userName = (String)session.getAttribute("UName");
		System.out.println("2");
		
		if(accountSelection != "" && amount != "") {
			ckAcc = new CheckingAccount(account_no);
			svAcc = new SavingsAccount(account_no);
			System.out.println("3");
			/*if(accountSelection == "Checking") {*/
				System.out.println("4");
    			if(ckAcc.is_CheckingAccount_exist() || svAcc.is_SavingsAccount_exist()) {
    				System.out.println("5");
    				request.setAttribute("msg", "Account Number is already Exist!!");
    				RequestDispatcher rd = request.getRequestDispatcher("OpenAccount.jsp");
				    rd.forward(request, response);
    			}
    			else {
    				System.out.println("6");
				nckAcc = new CheckingAccount(account_no, customer_name, amt, userName);
				System.out.println("7");
				double bal = nckAcc.open_CheckingAccount();
				System.out.println("8");
				tr = new Transactions("Deposit", amt, "Checking", "Checking",userName);
			    tr.Record_Transactions();
			    
			    request.setAttribute("msg", "Account Created Successfully!!");
				RequestDispatcher rd = request.getRequestDispatcher("OpenAccount.jsp");
			    rd.forward(request, response);
    			/*}	*/
			} /*else if(accountSelection == "Savings") {*/
					if(ckAcc.is_CheckingAccount_exist() || svAcc.is_SavingsAccount_exist()) {
    				
    				request.setAttribute("msg", "Account Number is already Exist!!");
    				RequestDispatcher rd = request.getRequestDispatcher("OpenAccount.jsp");
				    rd.forward(request, response);
					}else {
				
						nsvAcc = new SavingsAccount(account_no, customer_name, amt, userName);
				
						double bal = nsvAcc.open_SavingsAccount();
				
						tr = new Transactions("Deposit", amt, "Savings", "Savings",userName);
						tr.Record_Transactions();
			    
						request.setAttribute("msg", "Account Created Successfully!!");
						RequestDispatcher rd = request.getRequestDispatcher("OpenAccount.jsp");
						rd.forward(request, response);
					}
			}
		
		/*else {
			//String msg = "Either your Account Selection or Amount is wrong. Please Refresh Page and Try Again!!";
			//request.getSession().setAttribute("Message", msg);
			response.sendRedirect("OpenAccount.jsp?Success=-1");
		}*/
	}

}
