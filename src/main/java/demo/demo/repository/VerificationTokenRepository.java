package demo.demo.repository;

import demo.demo.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Integer> {

    public VerificationToken findByToken(String token);


}
