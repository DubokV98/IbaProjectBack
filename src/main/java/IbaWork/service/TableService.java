package IbaWork.service;

import IbaWork.model.Table;
import IbaWork.repository.TableRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class TableService {
    @Autowired
    TableRepositoryImpl tableRepositoryImpl;

    public List<Table> selectTables(){
        return tableRepositoryImpl.takeAllTable();
    }

    public List<Table> takeAllTableAndStructure() throws SQLException {
        List<Table> tableList = tableRepositoryImpl.takeAllTableAndStructure();
        return tableList;
    }
}
