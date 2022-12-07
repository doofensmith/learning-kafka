package security.util;

import auth.domain.dao.AccountDao;
import auth.repository.AccountRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Log4j2
public class UserService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountDao account = accountRepository.getDistinctTopByUsername(username);
        if (account!=null) {
            return account;
        }else {
            throw new UsernameNotFoundException("User not found.");
        }
    }

}
