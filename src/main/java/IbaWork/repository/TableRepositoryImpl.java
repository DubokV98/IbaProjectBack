package IbaWork.repository;

import com.mysql.jdbc.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TableRepositoryImpl implements TableRepository {

    @Autowired
    Connection connection;

    @PersistenceContext
    private EntityManager em;

   @Override
    public List<String> takeAllTable() {
       List<String> tableList = em.createNativeQuery("show tables from iba").getResultList();
       return tableList;
    }

    @Override
    public List<Object[]> takeStructureTable(String tableName){
        List <Object[]> structureObjList = em.createNativeQuery("show columns from " + tableName).getResultList();
        return structureObjList;
    }

    @Override
    public List<Object[]> takeCurrentTableStructure(String dbAndTableName) {
        List<Object[]> structureTableList = em.createNativeQuery("show fields from " + dbAndTableName).getResultList();
        return structureTableList;
    }

}
