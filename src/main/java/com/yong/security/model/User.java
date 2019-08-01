package com.yong.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author  LiangYong
 * @createdDate 2017/10/1.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class User implements UserDetails,CredentialsContainer {

    @Id
    private String openid;
    @JsonIgnore
    private String encryptSessionKey;
    @JsonIgnore
    private String sessionKey;
    private String avatarUrl;
    private String country;
    private Integer gender;
    private String language;
    private String nickName;
    private String province;

    private Set<GrantedAuthority> authorities;
    private  boolean accountNonExpired;
    private  boolean accountNonLocked;
    private  boolean credentialsNonExpired;
    private  boolean enabled;

    @Override
    @JsonIgnore
    public String getPassword() {
        return this.encryptSessionKey;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.openid;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public void eraseCredentials() {
    }

    public void init(){
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.enabled = true;
        this.credentialsNonExpired = true ;
        Set<GrantedAuthority> authorities = new CopyOnWriteArraySet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        this.authorities = authorities;
    }

    public void updateInfo(User user){
        this.avatarUrl=user.getAvatarUrl();
        this.country=user.getCountry();
        this.gender=user.getGender();
        this.language=user.getLanguage();
        this.nickName=user.getNickName();
        this.province=user.getProvince();
    }


}
