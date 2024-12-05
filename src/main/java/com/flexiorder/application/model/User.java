package com.flexiorder.application.model;

import com.flexiorder.application.enums.RoleEnum;
import com.flexiorder.security.config.dtos.CreateUserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users",
    uniqueConstraints = {
        @UniqueConstraint(name = "UC_USER__EMAIL", columnNames = "email"),
        @UniqueConstraint(name = "UC_USER__PHONE_NUMBER", columnNames = "phoneNumber")
    })
public class User implements UserDetails {

    @Id
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @NotNull
    protected UUID id;

    @CreationTimestamp
    @Column
    private Instant creationDate;

    @CreationTimestamp
    @Column
    private Instant lastUpdateDate;

    @NotBlank
    @Size(max = 120)
    @Column
    private String name;

    @NotBlank
    @Size(max = 60)
    @Email
    @Column
    private String email;

    @Size(max = 14)
    @Column
    private String phoneNumber;
    @Column
    private String document;

    @NotBlank
    @Size(max = 120)
    @Column
    private String password;

    @ElementCollection(targetClass = RoleEnum.class)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            foreignKey = @ForeignKey(name = "FK_USER_ROLES__USER_ID"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    @Fetch(FetchMode.JOIN)
    private List<RoleEnum> roles = new ArrayList<>();

    public User(CreateUserDTO dto){
        this.name = dto.getName();
        this.password = dto.getPassword();
        this.email = dto.getEmail();
        this.phoneNumber = dto.getPhoneNumber();
        this.document = dto.getDocument();
        this.roles = dto.getRoles() != null ? new ArrayList<>(dto.getRoles()) : new ArrayList<>();
    }

    public User(String name, String email, String phoneNumber, String password) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(UUID loggedUserId) {
        this.id = loggedUserId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (this.roles.contains(RoleEnum.ROOT)) {
            authorities.add(new SimpleGrantedAuthority("ROOT"));
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
            authorities.add(new SimpleGrantedAuthority("CLIENT"));
        } else {
            authorities.add(new SimpleGrantedAuthority("CLIENT"));
        }

        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
