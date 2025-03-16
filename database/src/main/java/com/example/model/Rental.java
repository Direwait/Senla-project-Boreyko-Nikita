package com.example.model;

import com.example.model.enums.RentalStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Date;

@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "rental")
@Entity

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private int rentalId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Enumerated(EnumType.STRING)
    @Column(name = "rental_status")
    private RentalStatus rentalStatus;

    @Column(name = "rental_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date rentalDate;

    @Column(name = "rental_return_date")
    @Temporal(TemporalType.DATE)
    private Date rentalReturnDate;

    @Column(name = "rental_actual_return_date")
    @Temporal(TemporalType.DATE)
    private Date rentalActualReturnDate;

}
