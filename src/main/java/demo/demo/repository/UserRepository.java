package demo.demo.repository;

import demo.demo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Integer> {

    public Users findByUsername(String name);

    public boolean existsByEmail(String email);

    public Users findByEmail(String email);
}
