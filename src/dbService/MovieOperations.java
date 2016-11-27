package dbService;

import java.sql.*;
import java.util.Calendar;
import oracle.jdbc.pool.OracleDataSource;

public class MovieOperations {

    private Connection conn;
    private ResultSet rset;
    private PreparedStatement pstmt;

    // This method opens a connection to the Oracle database
    public Connection openDB() {
        try {
            OracleDataSource ods = new OracleDataSource();


            ods.setURL("jdbc:oracle:thin:@//T450-PC:1521");
            ods.setUser("tymek");
            ods.setPassword("makki123");

            conn = ods.getConnection();
            System.out.println("connected.");
        } catch (Exception e) {
            System.out.print("Unable to load driver " + e);
            System.exit(1);
        }
        return conn;
    }

    public void dropMoviesTable() {
        System.out.println("Checking for existence of Movies table");
        try {
            String s1 = "DROP TABLE Movies";
            pstmt = conn.prepareStatement(s1);
            try {
                // Drop the Contacts table.
                pstmt.execute();
                System.out.println("Movies table dropped.");
            } catch (SQLException ex) {
                // No need to report an error.
                // The table simply did not exist.
            }
        } catch (SQLException ex) {
            System.out.println("Problem dropping the Movies table");
        }

    }

    public void dropUsersTable() {
        System.out.println("Checking for existence of Users table.");
        try {
            String s1 = "DROP TABLE Users";
            pstmt = conn.prepareStatement(s1);
            try {
                pstmt.execute();
                System.out.println("Users table dropped.");
            } catch (SQLException ex) {
                // No need to report an error.
                // The table does not exist.
            }
        } catch (SQLException ex) {
            System.out.println("Problem dropping the Users table");
        }
    }

    public void dropMovieTimes() {
        System.out.println("checking for existence of movieTimes table");
        try {
            String s1 = "DROP TABLE MOVIETIMES";
            pstmt = conn.prepareStatement(s1);
            try {
                pstmt.execute();
                System.out.println("Movie times table dropped.");
            } catch (SQLException e) {

            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void dropUserBookings() {
        System.out.println("Checking for existence of UserBooking table.");
        try {
            String s1 = "drop table UserBookings";
            pstmt = conn.prepareStatement(s1);
            try {
                pstmt.execute();
                System.out.println("UserBookings Table dropped.");
            } catch (SQLException e) {

            }
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public void dropMovieTimesSequence(){
        try{
            String s2 = "drop sequence times_seq";
            pstmt = conn.prepareStatement(s2);
            try{
                pstmt.execute();
                System.out.println("Movie Times sequence dropped");
            }catch(SQLException e){
                
            }
        }catch(SQLException e){
            
        }
    }
    
    public void dropMoviesSequence() {
        try {
            String s2 = "drop sequence movies_seq";
            pstmt = conn.prepareStatement(s2);
            try {
                pstmt.execute();
                System.out.println("Movies Sequence dropped");
            } catch (SQLException ex) {
                // No need to report an error.
                // The sequence does not exist.
            }
        } catch (SQLException ex) {
            System.out.println("Problem dropping the Movies sequence");
        }
    }

    public void dropUserSequence() {
        try {
            String s2 = "drop sequence user_seq";
            pstmt = conn.prepareStatement(s2);
            try {
                pstmt.execute();
                System.out.println("User Sequence dropped");
            } catch (SQLException ex) {
                // No need to report an error.
                // The sequence does not exist.
            }
        } catch (SQLException ex) {
            System.out.println("Problem dropping the User sequence");
        }
    }

    public void createMoviesSequence() {
        // Creating a sequence    
        try {
            String createseq2 = "create sequence movies_seq increment by 1 start with 1";
            pstmt = conn.prepareStatement(createseq2);
            pstmt.executeUpdate();
            System.out.println("Movies Sequence created");
        } catch (SQLException ex) {
            System.out.print("Problem with creating movie sequence " + ex.getMessage());
        }
    }

    public void createTimesSequence(){
        try{
            String createseq = "create sequence times_seq increment by 1 start with 1";
            pstmt = conn.prepareStatement(createseq);
            pstmt.executeUpdate();
            System.out.println("Times sequence created");
        }catch(SQLException e){
            System.out.println("Problem with creating times sequence" + e.getMessage());
        }
    }
    
    public void createUserSequence() {
        // Creating a sequence    
        try {
            String createseq2 = "create sequence user_seq increment by 1 start with 1";
            pstmt = conn.prepareStatement(createseq2);
            pstmt.executeUpdate();
            System.out.println("User Sequence created");
        } catch (SQLException ex) {
            System.out.print("Problem with creating user Sequence " + ex.getMessage());
        }
    }

    public void CreateMoviesTable() {
        try {

            // Create a Table
            String create = "CREATE TABLE Movies "
                    + "(mid NUMBER PRIMARY KEY, title VARCHAR2(40), genre VARCHAR2(30), price VARCHAR2(20))";
            pstmt = conn.prepareStatement(create);
            pstmt.executeUpdate();

            // Insert data into table
            String insertString1 = "INSERT INTO Movies(mid,title,genre,price) "
                    + "values(movies_seq.nextVal,?,?,?)";
            pstmt = conn.prepareStatement(insertString1);

            pstmt.setString(1, "Inferno");
            pstmt.setString(2, "action");
            pstmt.setString(3, "7.50");
            pstmt.execute();

            pstmt.setString(1, "Arrival");
            pstmt.setString(2, "action");
            pstmt.setString(3, "9.00");
            pstmt.execute();

            pstmt.setString(1, "Girl on the train");
            pstmt.setString(2, "drama");
            pstmt.setString(3, "7.50");
            pstmt.execute();

            pstmt.setString(1, "Trolls");
            pstmt.setString(2, "animation");
            pstmt.setString(3, "6.00");
            pstmt.execute();

            pstmt.setString(1, "Jack Reacher");
            pstmt.setString(2, "drama");
            pstmt.setString(3, "8.50");
            pstmt.execute();

            System.out.println("Movies table created");
        } catch (SQLException e) {
            System.out.print("SQL Exception creating and inserting values into Movies " + e.getMessage());
            System.exit(1);
        }
    }

    public void createUserTable() {
        // Create a Table           
        try {
            String create = "CREATE TABLE Users "
                    + "(u_id NUMBER PRIMARY KEY NOT NULL, name VARCHAR2(40), email VARCHAR2(40), password VARCHAR2(40), type VARCHAR2(40))";
            pstmt = conn.prepareStatement(create);
            pstmt.executeUpdate();

            // Insert data into table
            String insertString1 = "INSERT INTO Users(u_id, name, email, password, type) "
                    + "values(user_seq.nextVal,?,?,?,?)";
            pstmt = conn.prepareStatement(insertString1);

            pstmt.setString(1, "Jason");
            pstmt.setString(2, "j@gmail.com");
            pstmt.setString(3, "13245");
            pstmt.setString(4, "admin");

            pstmt.execute();

            pstmt.setString(1, "Tym");
            pstmt.setString(2, "t@gmail.com");
            pstmt.setString(3, "abcde");
            pstmt.setString(4, "admin");
            pstmt.execute();

            pstmt.setString(1, "John Smith");
            pstmt.setString(2, "jsmith@gmail.com");
            pstmt.setString(3, "hhh");
            pstmt.setString(4, "member");
            pstmt.execute();

            System.out.println("User table created");
        } catch (SQLException ex) {
            System.out.println("SQL Exception creating and inserting values into Users table" + ex.getMessage());
        }
    }

/*    
    public void addUserBookings(int uid, int mid, int tid, int qty){
        try{
            System.out.println("in method: "+uid+mid+tid+qty);
            String insertString1 = "INSERT INTO UserBookings(u_id,mid,tid,tickets_purchased) "
                    + "values(?,?,?,?)";
            
            pstmt = conn.prepareStatement(insertString1);
            
            pstmt.setInt(1, uid);
            pstmt.setInt(2, mid);
            pstmt.setInt(3, tid);
            pstmt.setInt(4, qty);
            pstmt.execute();
        }catch(SQLException e){
            System.out.println("adding data to userbooking table went wrong! error: "+e );
        }
    }
    */
    
    public void createUserBookings() {
        // Create a Table           
        try {
//            String createWANTED = "CREATE TABLE UserBookings "
 //                   + "(u_id NUMBER, mid NUMBER, tid NUMBER, PRIMARY KEY (u_id, mid, tid), FOREIGN KEY (u_id) REFERENCES Users (u_id),"
   //                 + "FOREIGN KEY (mid) REFERENCES Movies (mid), FOREIGN KEY (tid) REFERENCES MovieTimes (tid) ON DELETE CASCADE)";
//            
            
            String create = "CREATE TABLE UserBookings "
                    + "(u_id NUMBER, tid NUMBER, PRIMARY KEY (u_id, tid), FOREIGN KEY (u_id) REFERENCES Users (u_id),"
                    + "FOREIGN KEY (tid) REFERENCES MovieTimes (tid) ON DELETE CASCADE)";
            pstmt = conn.prepareStatement(create);
            pstmt.executeUpdate();
//add movietimes id here
            // Insert data into table
//            String insertString1 = "INSERT INTO UserBookings(u_id,mid,tid,tickets_purchased) "
//                    + "values(?,?,?,?)";
//            pstmt = conn.prepareStatement(insertString1);
//
//            pstmt.setInt(1, 1);
//            pstmt.setInt(2, 1);
//            pstmt.setInt(3, 2);
//            pstmt.setInt(4, 3);
//            pstmt.execute();
//            pstmt.setInt(1, 2);
//            pstmt.setInt(2, 2);
//            pstmt.setInt(3, 10);
//            pstmt.setInt(4, 1);
//            pstmt.execute();
//
//            pstmt.setInt(1, 3);
//            pstmt.setInt(2, 4);
//            pstmt.setInt(3, 13);
//            pstmt.setInt(4, 5);
//            pstmt.execute();

            System.out.println("UserBookings table created");
        } catch (SQLException ex) {
            System.out.println("SQL Exception creating and inserting values into UserBookings" + ex.getMessage());
        }
    }

    public void createMovieTimes() {
        try {
            //REFERENCES movies(mid)
            String create = "CREATE TABLE MovieTimes "
                    + "(tid NUMBER PRIMARY KEY, mid NUMBER NOT NULL, day VARCHAR2(20), time VARCHAR2(20), tickets_qty NUMBER)";

            pstmt = conn.prepareStatement(create);
            pstmt.executeUpdate();

            // Insert data into table
            String insertString1 = "INSERT INTO MovieTimes(tid, mid, day, time, tickets_qty) "
                    + "values(times_seq.nextVal,?,?,?,?)";
            pstmt = conn.prepareStatement(insertString1);

            Calendar dob1 = Calendar.getInstance();
            dob1.set(2016, 10, 16);
            
            pstmt.setInt(1, 1);
            pstmt.setString(2, "2016-10-11");
            pstmt.setString(3, "12:15");
            pstmt.setInt(4, 20);
            pstmt.execute();
            
            pstmt.setInt(1, 1);
            pstmt.setString(2, "2016-10-11");
            pstmt.setString(3, "14:15");
            pstmt.setInt(4, 30);
            pstmt.execute();
            
            pstmt.setInt(1, 1);
            pstmt.setString(2, "2016-10-12");
            pstmt.setString(3, "18:15");
            pstmt.setInt(4, 20);
            pstmt.execute();
            
            pstmt.setInt(1, 1);
            pstmt.setString(2, "2016-10-11");
            pstmt.setString(3, "12:10");
            pstmt.setInt(4, 20);
            pstmt.execute();
            
            pstmt.setInt(1, 1);
            pstmt.setString(2, "2016-10-11");
            pstmt.setString(3, "14:15");
            pstmt.setInt(4, 100);
            pstmt.execute();
            
            pstmt.setInt(1, 1);
            pstmt.setString(2, "2016-10-12");
            pstmt.setString(3, "18:15");
            pstmt.setInt(4, 85);
            pstmt.execute();
            
            pstmt.setInt(1, 1);
            pstmt.setString(2, "2016-10-11");
            pstmt.setString(3, "12:11");
            pstmt.setInt(4, 50);
            pstmt.execute();
            
            pstmt.setInt(1, 2);
            pstmt.setString(2, "2016-10-11");
            pstmt.setString(3, "17:15");
            pstmt.setInt(4, 60);
            pstmt.execute();
            
            pstmt.setInt(1, 2);
            pstmt.setString(2, "2016-10-12");
            pstmt.setString(3, "22:15");
            pstmt.setInt(4, 20);
            pstmt.execute();
            
            pstmt.setInt(1, 3);
            pstmt.setString(2, "2016-11-11");
            pstmt.setString(3, "09:15");
            pstmt.setInt(4, 35);
            pstmt.execute();
            
            pstmt.setInt(1, 3);
            pstmt.setString(2, "2016-11-11");
            pstmt.setString(3, "14:30");
            pstmt.setInt(4, 55);
            pstmt.execute();
            
            pstmt.setInt(1, 3);
            pstmt.setString(2, "2016-12-01");
            pstmt.setString(3, "11:45");
            pstmt.setInt(4, 75);
            pstmt.execute();
            
            pstmt.setInt(1, 4);
            pstmt.setString(2, "2016-11-21");
            pstmt.setString(3, "17:35");
            pstmt.setInt(4, 40);
            pstmt.execute();
            
            pstmt.setInt(1, 5);
            pstmt.setString(2, "2016-12-15");
            pstmt.setString(3, "22:20");
            pstmt.setInt(4, 50);
            pstmt.execute();



            System.out.println("Movie Times table created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // put it in JPA class
    public void createAccount(String name, String email, String password) {
        try {
            String insert = "INSERT INTO Users(u_id, name, email,password) "
                    + "values(user_seq,?,?,?)";
            pstmt = conn.prepareStatement(insert);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.execute();

            System.out.println("Account created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public ResultSet showMovies() {
        String sqlStatement = "SELECT m.title FROM Movies m";
        try {
            pstmt = conn.prepareStatement(sqlStatement, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rset = pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("showing movies.. " + e);
        }
        return rset;
    }

    public void closeDB() {
        try {
            pstmt.close();
            rset.close();
            conn.close();
            System.out.print("Connection closed");
        } catch (SQLException e) {
            System.out.print("Could not close connection ");
        }
    }

    public static void main(String[] args) {
        MovieOperations mo = new MovieOperations();
        mo.openDB();
        mo.dropMoviesSequence();
        mo.dropUserSequence();
        mo.dropMovieTimesSequence();
        
        
        mo.dropUsersTable();
        mo.dropMoviesTable();
        mo.dropUserBookings();
        mo.dropMovieTimes();

        mo.createMoviesSequence();
        mo.createUserSequence();
        mo.createTimesSequence();

        mo.CreateMoviesTable();
        mo.createUserTable();
        mo.createMovieTimes();
        mo.createUserBookings();
        
    }
}
