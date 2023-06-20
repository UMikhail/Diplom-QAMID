package tests;

import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import steps.AuthorizationSteps;
import steps.ClaimCreationSteps;
import steps.ControlPanelSteps;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.iteco.fmhandroid.ui.AppActivity;
import steps.NewsCreationSteps;

@RunWith(AllureAndroidJUnit4.class)
public class NewsCreationTests {

    @Rule
    public ActivityTestRule<AppActivity> activityTestRule =
            new ActivityTestRule<>(AppActivity.class);
    String validLogin = "login2";
    String validPassword = "password2";

    @Test
    @DisplayName("Создание новости с валидными значениями")
    public void allCreatingNewsWithValidValues() throws InterruptedException {
        Thread.sleep(5000);
        AuthorizationSteps.logIn(validLogin, validPassword);
        ControlPanelSteps.checkButtonLogIn();
        Thread.sleep(5000);
        NewsCreationSteps.creatingNewsOpening();
        NewsCreationSteps.creatingNewsWithValidVal();
    }

    @Test
    @DisplayName("Создание новости с пустой формой") //Проверка осуществляется по наличию надписи формы "Создание", т.к. метод Toast начиная с 30 SDK не поддерживается (на моём устрйстве SDK > 30)
    public void creatingNewsWithEmptyForm() throws InterruptedException {
        Thread.sleep(5000);
        NewsCreationSteps.creatingNewsOpening();
        NewsCreationSteps.creatingNewsClear();
    }

    @Test
    @DisplayName("Создание новости с публикацией через год")
    public void creatingNewsWithValidValuesAyearLater() throws InterruptedException {
        Thread.sleep(5000);
        NewsCreationSteps.creatingNewsOpening();
        NewsCreationSteps.creatingNewsWithValidValAyearLater();
    }

    @Test
    @DisplayName("Фильтрация новостей")
    public void filteringNews() throws InterruptedException {
        Thread.sleep(5000);
        NewsCreationSteps.creatingNewsOpening();
        NewsCreationSteps.checkFilteringCreatingNews();
    }

    @Test
    @DisplayName("Фильтрация новостей несуществующей категории")
    public void filteringNewsNonExistentCategory() throws InterruptedException {
        Thread.sleep(5000);
        NewsCreationSteps.creatingNewsOpening();
        NewsCreationSteps.checkFilteringNewsNonExistentCategory();
    }
}
