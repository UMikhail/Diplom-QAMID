package tests;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import steps.AuthorizationSteps;
import steps.ControlPanelSteps;

import androidx.test.rule.ActivityTestRule;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import ru.iteco.fmhandroid.ui.AppActivity;
import steps.EditingNewsSteps;
import steps.NewsCreationSteps;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

@RunWith(AllureAndroidJUnit4.class)
public class EditingNewsTests {

    @Rule
    public ActivityTestRule<AppActivity> activityTestRule =
            new ActivityTestRule<>(AppActivity.class);
    String validLogin = "login2";
    String validPassword = "password2";

    @Test
    @DisplayName("Изменение статуса новости с \"Активна\" на \"Не активна\"")
    public void activToInactiv() throws InterruptedException {
        Thread.sleep(5000);
        AuthorizationSteps.logIn(validLogin, validPassword);
        ControlPanelSteps.checkButtonLogIn();
        Thread.sleep(5000);
        NewsCreationSteps.creatingNewsOpening();
        EditingNewsSteps.newsOpeningEditor();
        EditingNewsSteps.fromActiveToInactive();
    }

    @Test
    @DisplayName("Отмена удаления новости (статус \"Не активна\")")
    public void canceledDeleteNews() throws InterruptedException {
        Thread.sleep(5000);
        NewsCreationSteps.creatingNewsOpening();
        EditingNewsSteps.newsOpeningEditor();
        EditingNewsSteps.cancelDeleteNews();
    }

    @Test
    @DisplayName("Удаление новости (статус \"Не активна\")")
    public void deleteNews() throws InterruptedException {
        Thread.sleep(5000);
        NewsCreationSteps.creatingNewsOpening();
        EditingNewsSteps.newsOpeningEditor();
        EditingNewsSteps.deleteNewsInactiv();
    }

    @Test
    @DisplayName("Редактирование новости")
    public void editingNews() throws InterruptedException {
        Thread.sleep(5000);
        NewsCreationSteps.creatingNewsOpening();
        EditingNewsSteps.newsOpeningEditor();
        EditingNewsSteps.editNews();
    }

    @Test
    @DisplayName("Отмена редактирования новости")
    public void editingNewsCancel() throws InterruptedException {
        Thread.sleep(5000);
        NewsCreationSteps.creatingNewsOpening();
        EditingNewsSteps.newsOpeningEditor();
        EditingNewsSteps.cancelEditNews();
    }
}
