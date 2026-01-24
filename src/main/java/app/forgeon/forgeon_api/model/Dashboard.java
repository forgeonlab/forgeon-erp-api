package app.forgeon.forgeon_api.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "dummy_dashboard")
public class Dashboard {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
}

