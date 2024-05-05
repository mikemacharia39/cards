package com.logicea.cards.domain.entity;

import com.logicea.cards.domain.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


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
