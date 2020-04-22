package IbaWork.repository;

import com.mysql.jdbc.Connection;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Transactional
@Repository
public class RequestRepositoryImpl implements RequestRepository {

    @Autowired Connection connection;
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Object[]> selectRequest(String request) {
        List<Object[]> list = em.createNativeQuery(request).getResultList();
        return list;
    }

    public String executeRequest(String request) throws ConstraintViolationException {
        Query query = em.createNativeQuery(request);
        String answer = String.valueOf(query.executeUpdate());
        answer = answer + " row(s) affected";
        return answer;
    }
}
