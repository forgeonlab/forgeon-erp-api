package app.forgeon.forgeon_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "dummy_dashboard")
public class Dashboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}

