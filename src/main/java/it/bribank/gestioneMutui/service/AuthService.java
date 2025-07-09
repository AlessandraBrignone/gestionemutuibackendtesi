package it.bribank.gestioneMutui.service;

import it.bribank.gestioneMutui.entity.Utente;
import it.bribank.gestioneMutui.repository.UtenteRepository;
import it.bribank.gestioneMutui.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UtenteRepository utenteRepository;

    public Map<String, Object> loginWithUser(String email, String password) {
        // Verifica credenziali
        authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        // Recupera utente
        Utente utente = utenteRepository.findByEmail(email).orElse(null);
        if (utente == null) {
            throw new RuntimeException("Utente non trovato");
        }

        // Genera token JWT
        String token = jwtUtil.generateToken(email);

        // Costruisci risposta
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("utente", utente);

        return response;
    }
}
