package io.izitrak.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String companyName;
    private String profilePicture;
    private String address;
    @OneToMany
    private List<Client> clients;


    public void addClients(Client... newClients){
        if (clients == null){
            this.clients = new ArrayList<>();
        }
        this.clients.addAll(Arrays.asList(newClients));
    }
}
