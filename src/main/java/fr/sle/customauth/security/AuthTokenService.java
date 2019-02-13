package fr.sle.customauth.security;

import fr.sle.customauth.Device;

import java.util.Optional;

/**
 * @author slemoine
 */
public interface AuthTokenService {

    /**
     * Try to authenticate a device by its raw authentication token.
     * @param raw raw authentication token.
     * @return An {@link Optional} of {@link Device}. If empty it means that the device cannot be retrieve from the token.
     */
    Optional<Device> authenticate(String raw);
}
