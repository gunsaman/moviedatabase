package fi.haagahelia.moviedatabase.web;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.haagahelia.moviedatabase.domain.MovieList;
import fi.haagahelia.moviedatabase.domain.User;
import fi.haagahelia.moviedatabase.domain.UserRepository;
import fi.haagahelia.moviedatabase.domain.Utility;
import fi.haagahelia.moviedatabase.service.UserServices;
import net.bytebuddy.utility.RandomString;

// This controller is showing error:: couldnot fix it. 
//I cannot connect to my mail server to send email to the user who want to reset password

@Controller
public class ForgotPasswordController {
	
	 @Bean
	    public JavaMailSender javaMailSender() {
	        return new JavaMailSenderImpl();
	    }
	@Autowired
    private JavaMailSender mailSender;
     
    @Autowired
    private UserServices userService;
    
    @Autowired
    private UserRepository uRepo;
    
    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "forgot_password_form";
    }
    
    
    
    @RequestMapping(value = "/forgot_password", method = RequestMethod.GET)
    public String find(User user){
        uRepo.findByUserEmail("");
        return "redirect:movieList";
    }  
    //@PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) throws Exception {
        String email = request.getParameter("email");
        String token = RandomString.make(30);
         
        try {
            userService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            sendEmail(email, resetPasswordLink);
            
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
             
        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }
             
        return "forgot_password_form";
    }
 
    private void sendEmail(String email, String resetPasswordLink) throws UnsupportedEncodingException, MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom("contact@support.com", "movie Support");
		helper.setBcc(email);
		String subject = "Here's the link to reset your password";
		String content="<p>Dear User, </p>" + "You have requested to reset your password."+"<a href =\""+resetPasswordLink+"\">reset password</a>";
		helper.setSubject(subject);
		helper.setText(content, true);
		mailSender.send(message);
			
		
	}
    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        User user = userService.getByResetPasswordToken(token);
        model.addAttribute("token", token);
         
        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }
         
        return "reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
         
        User user = userService.getByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");
         
        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        } else {           
            userService.updatePassword(user, password);
             
            model.addAttribute("message", "You have successfully changed your password.");
        }
         
        return "message";
    }
     
   
}
	

