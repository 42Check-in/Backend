package check_in42.backend.auth.oauth;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class TokenRepository {

    private final EntityManager em;


    /*
    * 추후에 findByName 이런 식으로 통일해도 괜찮을듯..
    * */
    public Token findByName(String name) {
        try {
            return em.createQuery("select t from Token t where t.userName = :name", Token.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public String saveRefreshToken(String name, OauthToken oauthToken) {
        Token token = this.findByName(name);

        if (token == null) {
            token = new Token(name, oauthToken.getAccess_token(), oauthToken.getRefresh_token());
            em.persist(token);
        }

        return token.getUUID();
    }

    public Token findByKey(String key) {
        try {
            return em.createQuery("select t from Token t where t.UUID = :key", Token.class)
                    .setParameter("key", key)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}

