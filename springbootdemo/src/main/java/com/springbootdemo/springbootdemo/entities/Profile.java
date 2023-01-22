package com.springbootdemo.springbootdemo.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;


import lombok.Data;


@Data
@Entity
@Table(name="profiles")
public class Profile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(name="_id")
    private String _id;

    @Column(name="name")
    @NotEmpty(message = "Empty value Not allowed!")
    private String name;

    @Column(name="age")
    @NotEmpty(message = "Empty value Not allowed!")
    private String age;

    @Column(name="dob")
    @NotEmpty(message = "Empty value Not allowed!")
    private String dob;

    @Column(name="title")
    @NotEmpty(message = "Empty value Not allowed!")
    private String title;

    @Column(name="company")
    @NotEmpty(message = "Empty value Not allowed!")
    private String company;

    @Column(name="job")
    @NotEmpty(message = "Empty value Not allowed!")
    private String job;

}