package fi.haagahelia.moviedatabase.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fi.haagahelia.moviedatabase.domain.User;
import fi.haagahelia.moviedatabase.domain.UserRepository;

@Service
@Transactional
public class UserServices {
	
	@Autowired
	private UserRepository uRepo;

    public void updateResetPasswordToken(String token, String email) throws Exception {
        User user = uRepo.findByUserEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            uRepo.save(user);
        } else {
            throw new Exception("Could not find any customer with the email " + email);
        }
    }
     
    public User getByResetPasswordToken(String token) {
        return uRepo.findByResetPasswordToken(token);
    }
     
    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPasswordHash(encodedPassword);
         
        user.setResetPasswordToken(null);
        uRepo.save(user);
    }
}
