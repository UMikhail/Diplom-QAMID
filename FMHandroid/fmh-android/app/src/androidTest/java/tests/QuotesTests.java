package tests;

import androidx.test.rule.ActivityTestRule;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import steps.AuthorizationSteps;
import steps.ControlPanelSteps;
import steps.QuotesSteps;
import ru.iteco.fmhandroid.ui.AppActivity;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

@RunWith(AllureAndroidJUnit4.class)
public class QuotesTests {

    @Rule
    public ActivityTestRule<AppActivity> activityTestRule =
            new ActivityTestRule<>(AppActivity.class);

    String validLogin = "login2";
    String validPassword = "password2";

    @Test
    @DisplayName("Открытие страницы \"Цитата дня\"")
    public void authPageQuotes() throws InterruptedException {
        Thread.sleep(5000);
        AuthorizationSteps.logIn(validLogin, validPassword);
        ControlPanelSteps.checkButtonLogIn();
        Thread.sleep(5000);
        QuotesSteps.openPageQuote();
    }

    @Test
    @DisplayName("Нажать на кнопку \"Развернуть/свернуть\"")
    public void clickExpandButton() throws InterruptedException {
        Thread.sleep(5000);
        QuotesSteps.buttonExpand();
    }
}
