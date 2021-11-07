package com.techelevator.tenmo;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.services.TransferService;
import com.techelevator.view.ConsoleService;

public class App {

	private static final String API_BASE_URL = "http://localhost:8080/";
    
    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	
    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;

    public static void main(String[] args) {
    	App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL));
    	app.run();
    }

    public App(ConsoleService console, AuthenticationService authenticationService) {
		this.console = console;
		this.authenticationService = authenticationService;
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");
		
		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while(true) {
			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	// the below methods will call to TransferService and AccountService
	// and print relevant results to console using ConsoleService

	private void viewCurrentBalance() {
		AccountService accountService = new AccountService(currentUser.getToken());
		System.out.println("You (" + currentUser.getUser().getUsername() +
				") have a balance of " +
				 + accountService.viewCurrentBalance(currentUser.getUser().getId()) +
				" TE bucks.");
	}

	private void viewTransferHistory() {
		AccountService accountService = new AccountService(currentUser.getToken());
		TransferService transferService = new TransferService((currentUser.getToken()));

		System.out.println(currentUser.getUser().getUsername() + " has a transfer history of:");
		console.printTransfers(accountService.viewTransferHistory(currentUser.getUser().getId()));

		long transferID = console.getUserInputInteger("Please enter transfer ID to view details (0 to cancel)");
		if (transferID == 0) {
			return;
		}
		Transfer transfer = transferService.viewTransferDetails(transferID);

		if (currentUser.getUser().getUsername().equals(transfer.getFromUsername())) {
			console.printOneTransfer(transfer, "from");
		} else if (currentUser.getUser().getUsername().equals(transfer.getToUsername())) {
			console.printOneTransfer(transfer, "to");
		} else {
			System.out.println("Something went wrong: transfer not found.");
		}
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		// optional
	}

	private void sendBucks() {
		AccountService accountService = new AccountService(currentUser.getToken());
		TransferService transferService = new TransferService(currentUser.getToken());

		// list users -- userID + username
		console.printUsers(accountService.getListOfAccounts());

		// select a user to send money to
		long toUserID = console.getUserInputInteger("Enter the user ID you are sending to");

		// enter amount
		long amount = console.getUserInputInteger("Enter the amount of money to send");

		// setup transfer and send
		Transfer transfer = new Transfer();

		transfer.setToUserID(toUserID);
		transfer.setFromUserID(currentUser.getUser().getId());

		transfer.setTransferType(2);
		transfer.setTransferStatus(2);
		transfer.setAmount(amount);

		transferService.sendTransfer(transfer);
	}

	private void requestBucks() {
		// TODO Auto-generated method stub
		// optional
	}
	
	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
            	authenticationService.register(credentials);
            	isRegistered = true;
            	System.out.println("Registration successful. You can now login.");
            } catch(AuthenticationServiceException e) {
            	System.out.println("REGISTRATION ERROR: "+e.getMessage());
				System.out.println("Please attempt to register again.");
            }
        }
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
		    try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: "+e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}
	
	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}
}
