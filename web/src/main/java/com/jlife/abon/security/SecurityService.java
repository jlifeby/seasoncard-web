package com.jlife.abon.security;


import com.jlife.abon.dto.UserData;
import com.jlife.abon.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Khralovich Dzmitry
 */
@Service
public class SecurityService implements UserDetailsService {

    public static final Pattern EMAIL_ADDRESS_REGEX
            = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    public static final Pattern CARD_UUID_REGEX
            = Pattern.compile("[0-9]{1,8}");


    @Autowired
    private UserFacade userFacade;

    /**
     * @param username email or cardUUID for client
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("Invalid user name: '" + username + "'");
        }
        UserData user = null;
        if (isValidEmail(username)) {
            user = userFacade.findByEmail(username);
        } else if (isValidCardUUID(username)) {
            Long cardUUID = Long.parseLong(username);
            user = userFacade.findOneByCardUUID(cardUUID);
        } else {
            throw new UsernameNotFoundException("Username should be valid email or cardUUID ");
        }
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, user.getPassword(), true, true, true, true, getAuthorities(user));
        return userDetails;
    }

    public Collection<GrantedAuthority> getAuthorities(UserData user) {
        List<GrantedAuthority> authList = getGrantedAuthorities(user);
        return authList;
    }

    public List<GrantedAuthority> getGrantedAuthorities(UserData user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    // todo move to utils class
    public static boolean isValidEmail(String email) {
        Pattern pattern = EMAIL_ADDRESS_REGEX;
        return pattern.matcher(email).matches();
    }

    public static boolean isValidCardUUID(String cardUUID) {
        Pattern pattern = CARD_UUID_REGEX;
        return pattern.matcher(cardUUID).matches();
    }
}
