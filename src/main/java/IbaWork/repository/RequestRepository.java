package IbaWork.repository;

import java.util.List;

public interface RequestRepository {
    List<Object []> selectRequest(String request);
    Object executeRequest(String request);
}
