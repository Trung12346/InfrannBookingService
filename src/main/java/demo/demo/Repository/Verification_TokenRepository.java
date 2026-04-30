package demo.demo.Repository;

import demo.demo.Entity.Verification_token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Verification_TokenRepository extends JpaRepository<Verification_token,Integer> {

    public Verification_token findByToken(String token);


}
