package fr.sle.customauth.security;

import fr.sle.customauth.Device;
import fr.sle.customauth.DeviceDao;
import org.springframework.util.Assert;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class AuthTokenServiceImpl implements AuthTokenService {

    private final DeviceDao deviceDao;

    private final TokenHasher tokenHasher;

    public AuthTokenServiceImpl(DeviceDao deviceDao, TokenHasher tokenHasher) {
        this.deviceDao = deviceDao;
        this.tokenHasher = tokenHasher;
    }

    @Override
    public Optional<Device> authenticate(String raw) {
        Assert.notNull(raw, "token.is.mandatory");
        String hashed = tokenHasher.hashToken(raw);
        return deviceDao.findByToken(hashed);
    }


}
