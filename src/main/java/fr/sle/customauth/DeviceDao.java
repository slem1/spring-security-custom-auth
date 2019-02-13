package fr.sle.customauth;

import fr.sle.customauth.security.TokenHasher;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Sample device DAO.
 * <p>
 * Use sha-256 hashing for token storage. Create a default device identified by token abcd-efgh.
 *
 * @author slemoine
 */
public class DeviceDao {

    private List<Device> devices = new ArrayList<>();

    private final TokenHasher tokenHasher;

    public DeviceDao(TokenHasher tokenHasher) {
        this.tokenHasher = tokenHasher;
        createDevice(new Device("id1", new ArrayList<>()), "abcd-efgh");
    }

    public Optional<Device> findByToken(String hashed) {
        return devices.stream().filter(d -> d.getToken().equals(hashed)).findFirst();
    }


    public void createDevice(Device device, String raw) {
        Assert.notNull(device, "device.is.mandatory");
        Assert.notNull(device, "device.raw.is.mandatory");
        String hashed = tokenHasher.hashToken(raw);
        device.setToken(hashed);
        devices.add(device);
    }

}
