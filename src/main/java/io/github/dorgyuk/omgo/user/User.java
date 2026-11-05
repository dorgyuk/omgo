package io.github.dorgyuk.omgo.user;

import java.time.Instant;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Google 인증 후 닉네임을 확정한 사용자 정보.
 * 닉네임은 표시할 때 사용할 영문 대소문자를 그대로 저장한다.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) 
@Entity 
@Table(name = "users")
public class User {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 12)
    private String nickname;

    @Column(name = "google_id", nullable = false, unique = true)
    private String googleId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private java.time.Instant createdAt;

    /**
     * 신규 사용자 정보를 생성한다. DB 저장은 수행하지 않는다.
     *
     * @param nickname 사용자가 확정한 닉네임
     * @param googleId Google 인증으로 확인한 사용자 식별자
     */
    public User(String nickname, String googleId) {
        this.nickname = nickname;
        this.googleId = googleId;
    }
}
