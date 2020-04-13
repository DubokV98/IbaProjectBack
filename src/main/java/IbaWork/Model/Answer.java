package IbaWork.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Answer {
    private String content;
    public Answer(){}
    public Answer(String content){
        this.content = content;
    }
}
