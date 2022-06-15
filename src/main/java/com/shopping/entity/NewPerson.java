package com.shopping.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "Persons")
@Getter
@Setter
@ToString
public class NewPerson {
    @Id
    private String id ;

    @Column(nullable = false, length = 30)
    private String name;

//    @Lob
//    private String address;

//    @Lob
    @Column(nullable = true, length = 255)
    private String address;

    @Column(nullable = false)
    private Integer salary ;
}
