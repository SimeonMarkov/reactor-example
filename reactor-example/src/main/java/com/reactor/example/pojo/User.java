package com.reactor.example.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private long id;
    @Column
    private String name;
    private String location;
    private int followersCount;
    private int friendsCount;
    private long createdAt;
    private String timeZone;
    private boolean verified;

    @JsonIgnore
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
