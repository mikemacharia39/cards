package com.logicea.cards.domain.entity;

import com.logicea.cards.domain.enumeration.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "card")
public class Card extends BaseEntity {

    @Column(nullable = false)
    private String name;
    private String description;
    private String color;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.TODO;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
