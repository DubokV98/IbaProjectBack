package IbaWork.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Select {

    private Structure[] structures;
    private List<Value> valuesList;

    public Select(){}
}
