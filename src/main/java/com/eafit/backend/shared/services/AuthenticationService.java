package com.eafit.backend.shared.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import com.eafit.backend.shared.dtos.*;
import com.eafit.backend.shared.entity.Auth;
import com.eafit.backend.shared.entity.Usuario;
import com.eafit.backend.shared.repository.AuthRepository;
import com.eafit.backend.shared.repository.UsuarioRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    @Value("${jwt.secret}")
    private String JWT_SECRET;
    @Value("${jwt.ttl}")
    private long JWT_EXPIRATION;
    private static final String REDIS_PREFIX = "auth:";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    // private RedisTemplate<String, Object> redisTemplate;

    private Usuario getUserAuthenticated(UserCredentials loginRequest) throws NoSuchAlgorithmException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(loginRequest.dni());

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            Auth auth = authRepository.findByUserId(usuario.getId())
                    .orElseThrow(() -> new RuntimeException("No auth record found for user"));

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String saltedPassword = loginRequest.password() + auth.getSalt();
            byte[] hashedPassword = digest.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));
            String encodedPassword = Base64.getEncoder().encodeToString(hashedPassword);

            if (encodedPassword.equals(auth.getHash())) {
                return usuarioOptional.get();
            }
        }

        return null;
    }

    public User login(UserCredentials userCredentials) throws NoSuchAlgorithmException {
        Usuario usuario = getUserAuthenticated(userCredentials);

        if (usuario == null) {
            return null;
        }

        // Genera el token JWT
        List<String> roles = Collections.singletonList(usuario.getRole());
        String token = generateJwtToken(usuario.getId(), roles);

        // if (Objects.nonNull(token)) {
        //     saveInRedis(token, userCredentials.dni());
        // }


        return new User(usuario.getId(), usuario.getName(), usuario.getRole(), token);
    }

    public String register(NewUserDto newUserDto) throws NoSuchAlgorithmException {
        Usuario nuevoUsuario = newUserDto.toEntity();

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String saltedPassword = newUserDto.password() + encodedSalt;
        byte[] hashedPassword = digest.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));
        String encodedPassword = Base64.getEncoder().encodeToString(hashedPassword);
        usuarioRepository.save(nuevoUsuario);

        Auth auth = new Auth();
        auth.setUser(nuevoUsuario);
        auth.setHash(encodedPassword);
        auth.setSalt(encodedSalt);
        authRepository.save(auth);


        return null;
    }

    // public void saveInRedis(String token, String username) {
    //     String key = REDIS_PREFIX + token;
    //     redisTemplate.opsForValue().set(key, username);
    //     redisTemplate.expire(key, 1, TimeUnit.HOURS);
    // }

    public Claims validateTokenAndAuthenticate(String token) {
        try {
            String key = REDIS_PREFIX + token;
            // String username = (String) redisTemplate.opsForValue().get(key);

            // if (Objects.nonNull(username)) {
                return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
            // }

        } catch (Exception e) {
        }
        return null;
    }

    private String generateJwtToken(String dni, List<String> roles) {
        List<GrantedAuthority> grantedAuthorities = roles.stream()
                .map(AuthorityUtils::commaSeparatedStringToAuthorityList)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject(dni)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .claim("authorities",
                        grantedAuthorities.stream().map(GrantedAuthority::getAuthority)
                                .toList())
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }
    
}
