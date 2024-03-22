package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    //@Column(nullable = false)
    //@NotEmpty(message = "Product name cannot be empty")
    public String name;

    @OneToMany
    @JoinColumn(name="seller_fk")
    public List<Product> products;


}
