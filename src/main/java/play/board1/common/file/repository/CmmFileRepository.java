package play.board1.common.file.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import play.board1.common.entity.CmmFile;

@Repository
@RequiredArgsConstructor
public class CmmFileRepository {

    private final EntityManager em;

    public void saveFile(CmmFile cmmFile) {
        em.persist(cmmFile);
    }
}
