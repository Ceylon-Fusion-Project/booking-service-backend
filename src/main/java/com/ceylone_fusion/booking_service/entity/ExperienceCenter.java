package com.ceylone_fusion.booking_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "experience_centers")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExperienceCenter {

    @Id
    @Column(name = "experience_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long experienceId;

    @Column(name="experience_code", nullable = false)
    private String experienceCode;

    @Column(name="name", nullable = false)
    private String experienceName;

    @Column(name="description")
    private String experienceDescription;

    @Column(name="location", nullable = false)
    private String location;

    @Column(name = "exp_center_map_link", nullable = false)
    private String expCenterMapLink;

    @Column(name="total_price")
    private Double totalPrice;

    @Column(name = "exp_demo_video_link")
    private String expDemoVideoLink;

    @Column(name="is_available" , nullable = false, columnDefinition = "boolean default true")
    private boolean isAvailable = true;

    @OneToMany(mappedBy="experienceCenter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events;

    @OneToMany(mappedBy="experience", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PackageExperience> packageExperiences;

}
