package dbService;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.QEncoderStream;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import model.MovieTimes;
import model.Users;
import model.Movies;

public class JPAService {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("CinemaPU");
    EntityManager em = emf.createEntityManager();

    public int findMovieID(String title) {
        int movieID = 0;
        try{
            Query query = em.createNativeQuery("SELECT mid FROM MOVIES WHERE title='" + title + "'");

        List<Object> results = query.getResultList();
        
        for (Object result : results) {
            movieID = Integer.valueOf(String.valueOf(result));
            System.out.println("id: "+movieID);
        }
        }catch(Exception e){
            System.out.println("finding movie id error..");
        }
        
        

        return movieID;
    }
    
    
    
    public void printAllMovies() {
        
        Query q = em.createNativeQuery("SELECT * from MOVIES", Movies.class);

        List<Movies> results = q.getResultList();

        for (Movies result : results) {
            System.out.println(result.getName());
        }

    }

    public void printOrder(String email){ // doesnt work yet
        int id = findUserID(email);
        
        Query q = em.createNativeQuery("SELECT m.title, mt.day, mt.time, ub.TICKETS_PURCHASED from movies m, movietimes mt, USERBOOKINGS ub where ub.u_id = "+id+
                " AND m.mid = mt.mid AND ub.TID = mt.TID");
        List<Object[]> results = q.getResultList();
        
        for (Object[] result : results) {
            String title = (String.valueOf(result[0]));
                String date = String.valueOf(result[1]);
                String time = String.valueOf(result[2]);
                int qty = Integer.valueOf(String.valueOf(result[3]));
                System.out.println("Title:\t"+title+ "\nDate:\t"+date+"\nTime:\t"+time+"\nTickets purchased:\t"+qty);
        }
    }
    
    public void printMoviesByGenre(String genre) {
        Query q = em.createNativeQuery("SELECT * from MOVIES where genre = '" + genre + "'", Movies.class);

        List<Movies> list = q.getResultList();

        for (Movies list1 : list) {
            System.out.println(list1.getID() + " " + list1.getName());
        }

    }

    public int findUserID(String email){
     int userID = 0;
        try{
            Query query = em.createNativeQuery("SELECT u_id FROM users WHERE email='" + email + "'");
//price = Double.valueOf(String.valueOf(result));
        List<Object> results = query.getResultList();
        
        for (Object result : results) {
            userID = Integer.valueOf(String.valueOf(result));
            System.out.println("user id: "+userID);
        }
        }catch(Exception e){
            System.out.println("finding user id error..");
        }
        
       

        return userID;   
    }
    
    public boolean findUser(String email, String password) {
        boolean found = false;

        Query q = em.createNativeQuery("SELECT U.U_ID\n"
                + "FROM USERS U\n"
                + " where U.EMAIL = '" + email + "'"
                + "and U.PASSWORD = '" + password + "'");

        List<Users> results = q.getResultList();

        if (!results.isEmpty()) {
            found = true;
            System.out.println("found that user");
        }

        return found;
    }

    public void printTimes(String title) {
        try {

            String query = "SELECT MT.TID, MT.DAY, MT.TIME\n"
                    + "FROM MOVIETIMES MT, MOVIES M\n"
                    + " where M.MID = MT.MID AND M.TITLE ='" + title + "'";
            Query q = em.createNativeQuery(query);

            //System.out.println("query: " + query);

            List<Object[]> results = q.getResultList();

            for (Object[] result : results) {
                Long id = Long.valueOf(String.valueOf(result[0]));
                String date = String.valueOf(result[1]);
                String time = String.valueOf(result[2]);
                System.out.println(id + " " + date + " " + time);
            }

//            for (Object result : q.getResultList()) {
//                list.add((MovieTimes)result);
//                
//            }
//            
//            
//            for (MovieTimes list1 : list) {
//                System.out.println(list1);
//            }
        } catch (Exception e) {
            System.out.println("Error with printing times for " + e);
        }

    }

    public void createUser(String nameAdd, String email, String password) {
        boolean found = findUser(email, password);
        //System.out.println("status of found: " + found);
        if (found == false) {
            em.getTransaction().begin();
            Users u = new Users(nameAdd, email, password, "member");
            em.persist(u);
            em.getTransaction().commit();
        } else {
            System.out.println("User already exists!");
        }

    }

    public double getTicketPrice(String title) {
        double price = 0;
        try {
            String query = "SELECT M.PRICE\n"
                    + "FROM MOVIES M\n"
                    + "where M.TITLE ='" + title + "'";
            Query q = em.createNativeQuery(query);
            List<Object> results = q.getResultList();

            for (Object result : results) {
                price = Double.valueOf(String.valueOf(result));
                //System.out.println(price);
            }

        } catch (Exception e) {
            System.out.println("problem with getting ticket price" + e);
        }
        return price;
    }

    public void purchaseTickets(String title, int qty, int option, String email) {
        int ticketsqty = getTicketQty(title, qty, option); // works
        int ticketsleft = ticketsqty - qty;         //works
        //System.out.println("tickets left: " + ticketsleft+"\nTickets available"+ticketsqty);
        int mid = findMovieID(title); // works
        int u_id = findUserID(email);
        System.out.println("usera id w purchase method to -> "+u_id);
//        if(ticketsqty >= qty ){
//            Query q = em.createNativeQuery("UPDATE MOVIES"
//                    + "SET tickets_purchased ="+ticketsleft+
//                    "WHERE title = '"+title+"'");
//        }
        
        
        Users u = em.find(Users.class, u_id);
        em.getTransaction().begin();
        Movies m = em.find(Movies.class, mid);
        
        //m.addUser(u); heheczki, before changes
        
        MovieTimes mt = em.find(MovieTimes.class, option);
        m.addTime(mt); // add here object movietimes, find TID first
        mt.setTickets_qty(ticketsleft);
        mt.addUser(u); // before changes this didnt exist XD
        em.getTransaction().commit();
        System.out.println(qty+" ticket/s for "+title+" was purchased.");
       
    }

    public int getTicketQty(String title, int qty, int tid) {
        int qtty = 0;
        // int qtty = results.get(0).getTickets();
        String query = "SELECT tickets_qty\n"
                + "FROM MOVIETIMES \n"
                + " where TID = '" + tid + "'";
        Query q = em.createNativeQuery(query);
        //System.out.println("query: " + query);

        List<Object> results = q.getResultList();

        for (Object result : results) {
            qtty = Integer.valueOf(String.valueOf(result));
        }

//        List<Movies> results = q.getResultList();
//       
//        System.out.println("number of tickets available: "+qtty);
//        if (qtty>=qty) {
//            System.out.println("enough tickets to purchase!");
//        }
        return qtty;

    }

    public String findUserType(String email){
        String type = "";
        String query = "SELECT type\n"
                + "FROM users \n"
                + " where email = '" + email + "'";
        Query q = em.createNativeQuery(query);
        //System.out.println("query: " + query);

        List<Object> results = q.getResultList();

        for (Object result : results) {
            type = (String.valueOf(result));
        }

     return type;   
    }
    
    // to do
    public void addMovie(String title, String genre, String price) {
        em.getTransaction().begin();
            Movies m = new Movies(title, genre,price);
            em.persist(m);
            em.getTransaction().commit();
            System.out.println("New movie added.");
    }
    
    public void removeMovie(String title){
        int mid = findMovieID(title);
        
        
        Movies m = em.find(Movies.class, mid);
     em.getTransaction().begin();
     em.remove(m);
     em.getTransaction().commit();
        System.out.println("Movie removed.");
    }
    
    

    public void addTime(String title, String day, String time, int qty) {
        int id = findMovieID(title);
        em.getTransaction().begin();
        // in theory 
        MovieTimes mt = new MovieTimes(day, time, qty);
        Movies m = em.find(Movies.class, id);
        em.persist(mt);
        m.addTime(mt);
        em.getTransaction().commit();
        System.out.println("New time added.");

    }

}
