import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import saperPackage.Ranking;
import saperPackage.RankingElement;

public class RankingTest {

    @Test
    @DisplayName("Create Ranking")
    void shouldCreateRanking(){
        Assertions.assertNotNull(new Ranking());
    }

    @Test
    @DisplayName("Add elemnet to Ranking")
    void shouldCanAddElementToRanking(){
        Ranking ranking = new Ranking();
        ranking.addElement(new RankingElement());
        Assertions.assertEquals(1, ranking.getListaRankingowa().size());
    }

}
