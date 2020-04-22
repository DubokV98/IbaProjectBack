package IbaWork.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Table {
    private String tableName;

    List<Structure> structureList;

    public Table(){ }


}
