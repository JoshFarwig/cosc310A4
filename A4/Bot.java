package A4;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;
import A4.Pathways;
import javax.swing.*;


public class Bot extends JFrame {
    private JTextArea Chatarea = new JTextArea();
    private JTextField chatbox = new JTextField();
    private JButton b = new JButton("Book a ticket");
    private JButton b2 = new JButton("Amend your booking");
    private JButton b3 = new JButton("Cancel your booking");
    private JButton c = new JButton("Cancel");
    DBconnection db = new DBconnection(); 
    String g = "";
    int count = 0;

    	// Intializing Movies
	static ArrayList<Movie> allMovies = new ArrayList<Movie>() {
		{
			add(new Movie("Black Adam", "9:30pm", "September 14, 2022"));
			add(new Movie("Smile", "7:15pm", "May 29, 2022"));
			add(new Movie("Thor", "8:45pm", "October 14, 2022"));
		}
	};
	// Intializing Temporary Customer Storage Array
	static ArrayList<Customer> customers = new ArrayList<Customer>() {
		{
			add(new Customer("Zeyad", 1234, 'M', null, "zee@gmail.com"));
		}
	};
	// intializing MovieTicket Storage Array
	static ArrayList<MovieTicket> ticket_array = new ArrayList<MovieTicket>();

public Bot(){
    //setting up JFrame
    res("Hi, how can I help you?");
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    frame.setVisible(true);
    frame.setResizable(false);
    frame.setLayout(null);
    frame.setSize(600, 600);
    frame.setTitle("Virtual Assistant");
   

    //adding all textfield and buttons
    frame.add(Chatarea);
    frame.add(chatbox);
    frame.add(b);
    frame.add(b2);
    frame.add(b3);

    Chatarea.setSize(600,400);
    Chatarea.setLocation(2,2);

    chatbox.setSize(580,50);
    chatbox.setLocation(2,500);

    b.setSize(198,70);
    b.setLocation(2, 402);

    b2.setSize(198,70);
    b2.setLocation(202, 402);

    b3.setSize(198,70);
    b3.setLocation(402, 402);

    //Actions
chatbox.addActionListener(new ActionListener() {
            String custName = null;
			char custGender = 0;
			String movieName = null;
			String movieTime = null;
            String email = "";
        @Override
        public void actionPerformed(ActionEvent e) {
            g = chatbox.getText();
            Chatarea.append("You: " + g + "\n");
            chatbox.setText("");
            
            if (count == 1) {
                email = g;
                /*
                if (db.checkExistingCust(email)){
                    res("Welcome back!");
                        custName = customers.get(i).getName();
						custGender = customers.get(i).getGender();
                }
                else {
                   res("Welcome");
                        res("Please enter your name: ");
                        count++;
						break;  
                }
                */

                for (int i = 0; i < customers.size(); i++) {
                    if (customers.get(i).getEmail().compareTo(g) == 0) {
                        res("Welcome back!");
                        custName = customers.get(i).getName();
						custGender = customers.get(i).getGender();
                    } else {
                        res("Welcome");
                        res("Please enter your name: ");
                        count++;
						break;
                    }
                }
            }
            else if (count == 2) {
                custName = g;
                res("Please enter your gender: (M/F)");
                count++;
            }
            else if (count == 3) {
                custGender = g.charAt(0); 
                customers.add((new Customer(custName, 0, custGender, null, email)));
                res("Account created!");
                res("Select a movie: ");
                for (int i = 0; i < allMovies.size(); i++) {
                    res2(i + 1 + ". " + allMovies.get(i).getMovieName() + " ");
                count++;
                }
            }
            else if (count == 4) {
                int mov = Integer.parseInt(g);
                res("test");
                boolean isValid = true;
                Movie custMovie = null;
                while (isValid) {
                    // if valid
                    if (mov <= allMovies.size() && mov > 0) {

                        custMovie = allMovies.get(mov-1);
                        movieName = custMovie.getMovieName();
                        movieTime = custMovie.getReleaseDate() + " " + custMovie.getShowTime();
                        res("Available Timings: " + movieTime);
                        isValid = false;
                        count++;
                    } else {
                        res("Invalid Input. Please try again: ");
                        count = 4;
                    }
                }	
					}
            }
        }
    );
//booking a ticket
b.addActionListener(new ActionListener() {
			
        @Override
        public void actionPerformed(ActionEvent e) {
            if (b.getText().equals("Cancel")){
                b.setText("Book a Ticket");
                count = 0;
                res("Returning to main menu.");
                return;
            }
            count++;
            frame.remove(b2);
            frame.remove(b3);
            b.setText("Cancel");

            res("Okay, I can help you with that. \nEnter your email: ");
            String email = g;
           }
        
        });
}



private void res(String string){
    Chatarea.append("Sally: " + string + "\n");
}
private void res2(String string){
    Chatarea.append(string + "\n");
}


public static void main(String[] args) {
    new Bot();
    
}

}