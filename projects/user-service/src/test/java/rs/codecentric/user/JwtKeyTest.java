package rs.codecentric.user;

import io.jsonwebtoken.impl.Base64Codec;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.junit.Test;

import javax.crypto.SecretKey;

import static org.junit.Assert.*;


public class JwtKeyTest {

    @Test
    public void generateJwtKey() {
        SecretKey key = MacProvider.generateKey();
        System.out.println(Base64Codec.BASE64.encode(key.getEncoded()));
    }

}