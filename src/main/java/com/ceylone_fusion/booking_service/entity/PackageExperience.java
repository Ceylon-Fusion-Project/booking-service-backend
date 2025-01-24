package com.ceylone_fusion.booking_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "package_experiences")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PackageExperience {

    @Id
    @Column(name = "package_experience_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long packageExperienceId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name="package_id", nullable=false)
    private Package packages;

    @ManyToOne
    @JoinColumn(name="experience_id", nullable=false)
    private ExperienceCenter experience;

}
