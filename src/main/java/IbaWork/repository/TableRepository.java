package IbaWork.repository;

import IbaWork.model.Table;

import java.sql.SQLException;
import java.util.List;

public interface TableRepository {
       List<Table> takeAllTable() throws SQLException;
       List<Table> takeAllTableAndStructure() throws SQLException;
}
