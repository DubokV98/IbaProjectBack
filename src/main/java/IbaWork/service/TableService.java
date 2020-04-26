package IbaWork.service;

import IbaWork.config.ValidatorBean;
import IbaWork.model.Structure;
import IbaWork.model.Table;
import IbaWork.repository.TableRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TableService {
    @Autowired
    TableRepositoryImpl tableRepositoryImpl;

    public List<Object[]> takeCurrentTable(String dbAndTableName){
       List<Object[]> objStructure = tableRepositoryImpl.takeCurrentTableStructure(dbAndTableName);
       return objStructure;
    }

    public List<Table> selectTablesAndStructure(){
        List<Table> resultTableList = new ArrayList<>();
        List<String> list = tableRepositoryImpl.takeAllTable();
        String db = "iba.";

        for (String tableName: list) {

            boolean flagProhibitedTable = checkProhibitedTables(db+tableName);

            if(!flagProhibitedTable){
                Table table = new Table();

                table.setTableName(tableName);
                List <Object[]> structureObjList =  tableRepositoryImpl.takeStructureTable((db+tableName));

                List<Structure> structureList = new ArrayList<>();

                for (Object[] obj: structureObjList) {
                    structureList.add(takeStructure(obj));
                }

                table.setStructureList(structureList);
                resultTableList.add(table);
            }
        }
        return resultTableList;
    }

    public Structure takeStructure(Object[] obj){

        Structure structure = new Structure();

        structure.setField(obj[0].toString());
        structure.setType(obj[1].toString());

        return structure;
    }

    public boolean checkProhibitedTables(String tableName){
        tableName = tableName.toUpperCase();
        for (String prohibitedTables: ValidatorBean.prohibitedTables) {
            if(tableName.equals(prohibitedTables)){
                return true;
            }
        }
        return false;
    }

    public String takeDbTableFromRequest(String request){
        List<String> list = tableRepositoryImpl.takeAllTable();
        String findDbAndTable = findDbTableFromRequest(list, request);
        return findDbAndTable;
    }

    public String findDbTableFromRequest(List<String> listTable, String request){
        for (String dbName:ValidatorBean.validDB) {
            for (String table:listTable) {
                String findDbAndTable = (dbName + "." + table).toUpperCase();
                if(request.contains(findDbAndTable)){
                    return findDbAndTable;
                }
            }
        }
        return "";
    }
}
