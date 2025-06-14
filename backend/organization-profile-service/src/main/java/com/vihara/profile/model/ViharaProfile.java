package com.vihara.profile.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vihara_profile") // Nama tabel di database
@Data // Anotasi Lombok untuk getters, setters, toString, equals, hashCode
@NoArgsConstructor // Anotasi Lombok untuk constructor tanpa argumen
@AllArgsConstructor // Anotasi Lombok untuk constructor dengan semua argumen
public class ViharaProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Lob
    private String history;

    private String contactEmail;
    private String contactPhone;
    private String province;
    private String district;
    private String address;
    private String city;
    private String postalCode;
    private String imageUrl;

    private String facebookLink;
    private String instagramLink;
    private String googleMapsEmbedLink;
    private String bankAccountDetails;
}
   
