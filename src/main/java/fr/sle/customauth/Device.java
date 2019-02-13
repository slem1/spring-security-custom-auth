package fr.sle.customauth;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Objects;

/**
 * @author slemoine
 */
public class Device {

    private String deviceId;

    private List<GrantedAuthority> authorities;

    public String getDeviceId() {
        return deviceId;
    }

    public String token;

    public Device(String deviceId, List<GrantedAuthority> authorities) {
        this.deviceId = deviceId;
        this.authorities = authorities;
    }

    public Device setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Device setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
        return this;
    }

    public String getToken() {
        return token;
    }

    public Device setToken(String token) {
        this.token = token;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return Objects.equals(deviceId, device.deviceId) &&
                Objects.equals(authorities, device.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceId, authorities);
    }

    @Override
    public String toString() {
        return "Device{" +
                "deviceId='" + deviceId + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
