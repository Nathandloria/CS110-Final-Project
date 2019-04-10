import java.util.Scanner;
import java.io.IOException;

public class EmailMain {
  public static void main(String[] args) {
    String username;
    String password;
    String recipient;
    String message;
    String subject;
    int limit;
    int num;
    int key;
    AlgorithmOne alg1;
    AlgorithmTwo alg2;
    AlgorithmAES alg3;
    EmailData email = new EmailData();
    Scanner scan = new Scanner(System.in);
    System.out.println("\nPlease enter your e-mail address: ");
    username = scan.next();
    System.out.println("\nPlease enter your app password: ");
    password = scan.next();
    System.out.println("\nWould you like to 1) Send a message or 2) Read emails? (1/2): ");
    num = scan.nextInt();
    email.setUsername(username);
    email.setPassword(password);
    email.createProperties();
    email.setMailStoreType("pop3");
    email.setHost("imap.gmail.com");
    email.createSession();
    email.imapsConnect();
    if (num == 2) {
      System.out.println("\nPlease enter the amount of e-mails to read: ");
      limit = scan.nextInt();
      email.setLimit(limit);
      email.createFolder();
      email.setMessageArray();
      email.setMessageContent();
      email.setMessageDate();
      email.setMessageSender();
      email.setMessageSubject();
      System.out.println();
      for(int i = limit - 1; i > -1; i--) {
        System.out.println("-----------------------------------------------------------------");
        System.out.println("\n<Message " + (i + 1) + ">");
        System.out.println("From: " + email.getSenders()[i]);
        System.out.println("On: " + email.getDates()[i]);
        System.out.println("Subject: " + email.getSubjects()[i]);
        System.out.println("Message: \n\n" + email.getMessageContent()[i]);
      }
      System.out.println("\n-----------------------------------------------------------------");
    } else if (num == 1) {
      System.out.println("\nPlease enter the address of the intended recipient: ");
      recipient = scan.next();
      scan.nextLine();
      System.out.println("\nPlease enter the subject of the message: ");
      subject = scan.nextLine();
      System.out.println("\nPlease enter the message you would like to send: ");
      message = scan.nextLine();
      System.out.println("\nPlease choose which algorithm to encrypt with: ");
      num = scan.nextInt();
      if (num == 1) {
        alg1 = new AlgorithmOne();
        message = alg1.encript(message);
      } else if (num == 2) {
        alg2 = new AlgorithmTwo();
        System.out.println("\nPlease enter the amount to encrypt by (1-26): ");
        key = scan.nextInt();
        while (key > 26 || key < 1) {
          System.out.println("\nPlease enter the amount to encrypt by (1-26): ");
          key = scan.nextInt();
        }
        message = alg2.encrypt(message, key);
      } else if (num == 3) {
        alg3 = new AlgorithmAES();
        alg3.setKey();
        try {
          message = alg3.encrypt(message);
        } catch (Exception x) {
          System.out.println(x);
        }
      }
      email.sendEmail(recipient, subject, message);
    }
  }
}
