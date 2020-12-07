package fi.haagahelia.moviedatabase.domain;



import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
	
	public User findByUserEmail(String user);
	public User findByResetPasswordToken(String token);
}