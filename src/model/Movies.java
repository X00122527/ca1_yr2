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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "Movies")
@SuppressWarnings("SerializableClass")
@SequenceGenerator(name = "movies_seq", initialValue = 1, allocationSize = 1)
public class Movies {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movies_seq")
    private int mid;
    private String title;         
    private String genre;
    private String price;

//    @ManyToMany(mappedBy = "mlist")
//    private List<Users> ulist = new ArrayList<>();
    
    
    
    @OneToMany(mappedBy ="m", orphanRemoval = true)
    private List<MovieTimes> timesList = new ArrayList<>();   

    public Movies() {
        
    }

    public Movies(String t, String g, String p) {
        title = t;
        genre = g;
        price = p;
    }

    public void setTitle(String t) {
        title = t;
    }

    public void setDesc(String d) {
        genre = d;
    }



    public void setID(int id) {
        this.mid = id;
    }

    public void setPrice(String p) {
        price = p;
    }

    public String getName() {
        return title;
    }

    public String getGenre() {
        return genre;
    }


    public int getID() {
        return mid;
    }

    public String getPrice() {
        return price;
    }

//    public void addUser(Users u) {
//        ulist.add(u);
//        u.getMlist().add(this);
//    }
    
    public void addTime(MovieTimes mt){
        this.timesList.add(mt);
        mt.setM(this);
    }
    
    public List<MovieTimes> getTimesList(){
        return timesList;
    }
    
    public void setTimesList(List<MovieTimes> times){
        timesList = times;
    }

    
    
    @Override
    public String toString() {
        String ss = "Movie ID: " + mid + " Title: " + title + "\n";
        for (int i = 0; i < timesList.size(); i++) {
            ss += timesList.get(i);
        
        }
        return ss;
    }
}
