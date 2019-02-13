package fr.sle.customauth.security;

import fr.sle.customauth.Device;
import fr.sle.customauth.DeviceDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Optional;

/**
 * @author slemoine
 */
@RunWith(JUnit4.class)
public class AuthTokenHasherServiceTest {

    private static final String RAW = "a13451e0-aac3-4d1f-9ecf-4430816e108a";

    @Test
    public void authenticate() throws NoSuchAlgorithmException {

        TokenHasher tokenHasher = new TokenHasher();

        Device device = new Device("device0001", Collections.emptyList());
        DeviceDao dao = Mockito.mock(DeviceDao.class);
        String hashed = tokenHasher.hashToken(RAW);
        Mockito.when(dao.findByToken(hashed)).thenReturn(Optional.of(device));

        AuthTokenService tokenService =
                new AuthTokenServiceImpl(dao, tokenHasher);

        Optional<Device> result = tokenService.authenticate(RAW);
        Assert.assertEquals(device, result.get());
    }

    @Test(expected = IllegalArgumentException.class)
    public void authenticateAssertArgs() throws NoSuchAlgorithmException {

        DeviceDao dao = Mockito.mock(DeviceDao.class);
        AuthTokenService tokenService =
                new AuthTokenServiceImpl(dao, new TokenHasher());
        tokenService.authenticate(null);
    }

}
