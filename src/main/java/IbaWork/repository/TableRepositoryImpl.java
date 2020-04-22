package IbaWork.repository;

import IbaWork.config.ValidatorBean;
import IbaWork.model.Structure;
import IbaWork.model.Table;
import com.mysql.jdbc.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TableRepositoryImpl implements TableRepository {
    private String databaseName = "iba";

    @Autowired
    Connection connection;

    @PersistenceContext
    private EntityManager em;

   @Override
    public List<Table> takeAllTable() {
       List<Table> tableList = em.createNativeQuery("show tables from iba").getResultList();
       return tableList;
    }

    public List<Table> takeAllTableAndStructure() throws SQLException {

        ResultSet resultSet;
        ResultSet resultSetStruct;
        List<Table> tableList = new ArrayList<>();
        Table table;

        String[] types = {"TABLE"};

        resultSet = connection.getMetaData().getTables(databaseName, null, "%", types);

        DatabaseMetaData meta = connection.getMetaData();
        String tableName = "";
       /* while(resultSet.next()){
            tableName = resultSet.getString(3);
            tableName = "iba." + tableName;
            tableName = tableName.toUpperCase();
            for (String prohibitedTableName: ValidatorBean.prohibitedTables) {
                if(prohibitedTableName.equals(tableName)) {
                    resultSet.re
                }
            }
        }
        resultSet.beforeFirst();*/

        int i = 0;
        while (resultSet.next()) {
            table = new Table();
            List<Structure> structureList = new ArrayList<>();
            int j=0;

            tableName = resultSet.getString(3);
            table.setTableName(tableName);
            tableList.add(table);

            resultSetStruct = meta.getColumns(databaseName, null, tableName, "%");

            while (resultSetStruct.next()) {

                Structure structure = new Structure();
                structure.setField(resultSetStruct.getString(4));
                structureList.add(structure);
                j++;
            }
            tableList.get(i).setStructureList(structureList);
            i++;

        }
        return tableList;
    }
}
