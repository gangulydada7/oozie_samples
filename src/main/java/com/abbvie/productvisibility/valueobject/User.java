package com.abbvie.productvisibility.valueobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by selvakx1 on 8/4/2016.
 */


@Entity
@XmlRootElement
public class User {

    public  User(){};

    @Column(name="user_id")
    private String user_id;

    @Column(name="user_name")
    private String user_name;

    @Column(name="first_name")
    private String first_name;

    @Column(name="last_name")
    private String  last_name;

    @Column(name="role_name")
    private String role_name;

    @Column(name="email_id")
    private String  email_id;

    @Column(name="phone_no")
    private String phone_no;

    @Column(name="nonabbvie_user")
    private String nonabbvie_user;

    @Column(name="country")
    private String country;

    @Column(name="time_stamp")
    private String time_stamp;

    @Column(name="is_active")
    private String is_active;

    @Column(name="created_by")
    private String  created_by;

    @Column(name="created_date")
    private String created_date;

    @Column(name="modified_by")
    private String  modified_by;

    @Column(name="modified_date")
    private String modified_date;

    @Column(name="last_login_date")
    private String last_login_date;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getNonabbvie_user() {
        return nonabbvie_user;
    }

    public void setNonabbvie_user(String nonabbvie_user) {
        this.nonabbvie_user = nonabbvie_user;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public String getModified_date() {
        return modified_date;
    }

    public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }

    public String getLast_login_date() {
        return last_login_date;
    }

    public void setLast_login_date(String last_login_date) {
        this.last_login_date = last_login_date;
    }



}
