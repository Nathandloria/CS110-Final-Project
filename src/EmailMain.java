import java.util.Scanner;
import javax.mail.*;
import java.io.IOException;
import java.lang.Object;

public class EmailMain {
  public static void main(String[] args) {
    String username;
    String password;
    String recipient;
    String message;
    String subject;
    String AESKey;
    String response;
    int num;
    int key;
    AlgorithmOne alg1 = new AlgorithmOne();
    AlgorithmTwo alg2;
    AlgorithmAES alg3;
    String id = null;
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
      System.out.println("\nPrinting encrypted messages!\n");
      email.createFolder();
      email.setMessageArray();
      email.setCount();
      email.setLimit(email.getCount());
      email.setMessageContent();
      email.setMessageDate();
      email.setMessageSender();
      email.setMessageSubject();
      for (int i = 0; i < email.getCount(); i++) {
        System.out.println("-----------------------------------------------------------------");
        System.out.println("\n<Message " + (email.getCount() - i) + ">");
        System.out.println("From: " + email.getSenders()[i]);
        System.out.println("On: " + email.getDates()[i]);
        System.out.println("Subject: " + email.getSubjects()[i]);
        System.out.println("Message: \n\n" + email.getMessageContent()[i]);
      }
      System.out.println("-----------------------------------------------------------------");
    } else if (num == 1) {
      do {
        System.out.println("\nPlease enter the address of the intended recipient: ");
        recipient = scan.next();
        scan.nextLine();
        System.out.println("\nPlease enter the subject of the message: ");
        subject = scan.nextLine();
        System.out.println("\nPlease enter the message you would like to send: ");
        message = scan.nextLine();
        System.out.println("\nPlease choose which algorithm to encrypt with (1, 2 or 3): ");
        num = scan.nextInt();
        if (num == 1) {
          id = alg1.encript("cs101alg1");
          message = alg1.encript(message);
          email.sendEmail(recipient, subject, message, id);
        } else if (num == 2) {
          alg2 = new AlgorithmTwo();
          System.out.println("\nPlease enter the amount to encrypt by (1-26): ");
          key = scan.nextInt();
          while (key > 26 || key < 1) {
            System.out.println("\nPlease enter the amount to encrypt by (1-26): ");
            key = scan.nextInt();
          }
          id = alg1.encript("cs101alg2" + key);
          message = alg2.encrypt(message, key);
          email.sendEmail(recipient, subject, message, id);
        } else if (num == 3) {
          try {
            alg3 = new AlgorithmAES();
            AESKey = alg3.setKey();
            id = alg1.encript("cs101alg3" + AESKey);
            message = alg3.encrypt(message, AESKey);
          } catch (Exception x) {
            System.out.println(x);
          }
          email.sendEmail(recipient, subject, message, id);
        }
        System.out.println("\nWould you like to send another email? (y/n)");
        response = scan.next();
      } while (response.toUpperCase().equals("Y"));
      System.out.println("\nBye!\n");
    }
  }
}
