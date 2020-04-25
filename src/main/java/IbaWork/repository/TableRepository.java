package IbaWork.repository;

import java.sql.SQLException;
import java.util.List;

public interface TableRepository {
       List<String> takeAllTable() throws SQLException;

       List<Object[]> takeStructureTable(String tableName);

       List<Object[]> takeCurrentTableStructure(String dbAndTableName);
}
