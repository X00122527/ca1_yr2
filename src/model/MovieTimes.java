/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author T450
 */
@Entity
@Table(name = "MovieTimes")
public class MovieTimes {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "times_seq")
    private int tid;
    // references to movies MID
    //private int mid;
    private String day;
    private String time;
    private int tickets_qty;
    
    @ManyToOne()
    @JoinColumn(name = "mid")
    private Movies m;
    
    @ManyToMany(mappedBy = "timeslist")
    private List<Users> ulist = new ArrayList<>();
    
    public MovieTimes(){
        
    }

    public MovieTimes(String day, String time, int tickets_qty) {
        this.day = day;
        this.time = time;
        this.tickets_qty = tickets_qty;
    }

    public void setTickets_qty(int tickets_qty) {
        this.tickets_qty = tickets_qty;
    }

    

    

    public int getId() {
        return tid;
    }

    public String getDate() {
        return day;
    }

    public String getTime() {
        return time;
    }


    public void setId(int id) {
        this.tid = id;
    }

    public void setDate(String date) {
        day = date;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
    public void setM(Movies m){
        this.m = m;
    }
    
    public Movies getM(){
        return m;
    }
    
        public void addUser(Users u) {
        ulist.add(u);
        u.getTimeslist().add(this);
    }



    @Override
    public String toString() {
        String s = tid+", "+day+", "+time;
        System.out.println("String "+s);
        return s;
    }
    
    
}
