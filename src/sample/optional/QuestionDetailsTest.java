package sample.optional;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class QuestionDetailsTest {

    private QuestionDetails questionDetails;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void testQUESTIONSpreadsheetWithScoreAndExportValue() {

        //String questionXml = FileUtils.readContents("/Users/a.nigam/git.cvent/surveys/surveys-service/src/test/resources/question/QUESTIONSpreadsheet-score.xml");
        String questionXml = null;
        questionDetails = new QuestionDetails(questionXml, SurveyQuestionType.MATRIX_SPREAD_SHEET.getSurveyQuestionType());
        Assertions.assertThat(questionDetails.getCategories()).isNotNull();
        Assertions.assertThat(questionDetails.getChoices()).isNotNull();
        Assertions.assertThat(questionDetails.getFactors()).isNull();

        Assertions.assertThat(questionDetails.getQuestionMaxScore(SurveyQuestionType.MATRIX_SPREAD_SHEET,
                new Double(0.0), "Birthday", "Friend", "factor"
        )).isEqualTo(new Double(5));


        Assertions.assertThat(questionDetails.getQuestionMaxScore(SurveyQuestionType.MATRIX_SPREAD_SHEET,
                new Double(0.0), "Friend", "Birthday", "factor"
        )).isEqualTo(new Double(5));
    }

    @Test
    public void testQuestionMCMWithScoreAndExportValue() {

        // String questionXml = FileUtils.readContents("/Users/a.nigam/git.cvent/surveys/surveys-service/src/test/resources/question/QUESTIONMCMatrix.xml");;
        String questionXml = null;
        questionDetails = new QuestionDetails(questionXml, SurveyQuestionType.MATRIX_MULTIPLE_CHOICE.getSurveyQuestionType());
        Assertions.assertThat(questionDetails.getCategories()).isNotNull();
        Assertions.assertThat(questionDetails.getChoices()).isNull();
        Assertions.assertThat(questionDetails.getFactors()).isNull();

        Assertions.assertThat(questionDetails.getQuestionMaxScore(SurveyQuestionType.MATRIX_MULTIPLE_CHOICE,
                new Double(0.0), "White goods", "Home", "factor"
        )).isEqualTo(new Double(15));



        System.out.println("Score value ...."+questionDetails.getQuestionMaxScore(SurveyQuestionType.MATRIX_MULTIPLE_CHOICE,
                new Double(0.0), "White goods", "Home", "factor"
        ));


        Assertions.assertThat(questionDetails.getExportValueForCategory("White goods")).isEqualTo("EX-CA-MMAPR-1");

        thrown.expect(RuntimeException.class);
        questionDetails.getQuestionMaxScore(SurveyQuestionType.COMMENT_BOX,
                new Double(0.0), "cat", "choice", "factor"
        );

    }


}
