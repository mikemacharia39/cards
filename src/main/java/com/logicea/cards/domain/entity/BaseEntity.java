package com.logicea.cards.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import java.time.LocalDateTime;

@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@Getter
abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "date_created", nullable = false, updatable = false, columnDefinition = "DATETIME")
    private LocalDateTime dateCreated;

    @LastModifiedDate
    @Column(name = "date_modified", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime dateModified;

    @PrePersist
    public void prePersist() {
        dateCreated = LocalDateTime.now();
        dateModified = LocalDateTime.now();
    }
}
