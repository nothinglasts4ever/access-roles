package com.github.nl4.accessroles.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccessRole implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String personId;
    @ManyToOne
    private Location location;
    private LocalDateTime start;
    private LocalDateTime end;
}