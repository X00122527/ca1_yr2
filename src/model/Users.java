/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author T450
 */
import java.util.List;
import javax.persistence.*;
@Entity
@Table(name = "Users") // don't really need it
@SuppressWarnings("SerializableClass")
@SequenceGenerator(name = "user_seq", initialValue = 1, allocationSize = 1)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private int u_id;
    private String name;
    private String email;
    private String password;
    private String type;
    @ManyToMany (cascade = CascadeType.PERSIST)
    //@JoinTable(name="UserBookings", joinColumns = @JoinColumn(name="u_id"), inverseJoinColumns=@JoinColumn(name="mid"))
    //private List<Movies> mlist;
    @JoinTable(name="UserBookings", joinColumns = @JoinColumn(name="u_id"), inverseJoinColumns=@JoinColumn(name="tid"))
    private List<MovieTimes> timeslist;
    
    
    public Users() {       
       
    }

    public Users(String name, String email, String password, String type) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
        
    }

    public int getId() {
        return u_id;
    }

    public List<MovieTimes> getTimeslist() {
        return timeslist;
    }

    public void setId(int id) {
        this.u_id = id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public Movies getMovies(int i) {
//        return mlist.get(i);
//    }

    public String getUser() {
        return name;
    }
    
    public String getType() {
        return type;
    }


//    public List<Movies> getMlist() {
//        return mlist;
//    }
    
//    public void addMovie(){
//        rset = mo.getMovies();
//        
//        try{
//            if(rset.last()){
//                Movies m = new Movies(rset.getString(1),
//                        rset.getString(2), rset.getString(3), rset.getString(4));
//                mlist.add(m);
//            }
//        }catch(Exception e){
//            System.out.println(e);
//        }
//    }
    
    
}
