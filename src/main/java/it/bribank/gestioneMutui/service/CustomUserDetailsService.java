package it.bribank.gestioneMutui.service;

import it.bribank.gestioneMutui.entity.Utente;
import it.bribank.gestioneMutui.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UtenteRepository utenteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Utente utente = utenteRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato con email: " + email));

        // Trasformazione del ruolo per Spring Security
        String ruoloSpring = "ROLE_" + utente.getRuolo()
                .getDescrizioneRuolo()
                .toUpperCase()
                .replace("[^A-Z0-9]", "_"); // Es: "Gestore del mutuo" → "ROLE_GESTORE_DEL_MUTUO"

        return new org.springframework.security.core.userdetails.User(
                utente.getEmail(),
                utente.getPassword(),
                List.of(new SimpleGrantedAuthority(ruoloSpring))
        );
    }

}

