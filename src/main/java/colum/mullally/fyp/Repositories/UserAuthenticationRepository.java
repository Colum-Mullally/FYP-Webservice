package colum.mullally.fyp.Repositories;

import colum.mullally.fyp.model.UserAuthentication;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthenticationRepository extends MongoRepository<UserAuthentication,String> {
    UserAuthentication findByUsername(String username);
}
