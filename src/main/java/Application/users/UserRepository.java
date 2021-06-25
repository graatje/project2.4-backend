package Application.users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    User findByName(String name);
    User findByEmail(String email);

    //@Override
    //public User findById(Long id);
}
