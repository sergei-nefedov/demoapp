package pers.nefedov.demoapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="phones")
public class Phone {
    @Id
    @Column(name = "number", nullable = false)
    private String number;

    @ManyToOne
    @JoinColumn(name = "user_login", nullable = false)
    private User user;
}
