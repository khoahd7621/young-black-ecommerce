package com.khoahd7621.youngblack.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.khoahd7621.youngblack.constants.EAccountStatus;
import com.khoahd7621.youngblack.constants.ERoles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_tbl")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phone;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private ERoles role;
    private EAccountStatus status;
}