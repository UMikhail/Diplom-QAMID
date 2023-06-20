package tests;

import androidx.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import steps.AuthorizationSteps;
import steps.ControlPanelSteps;

@RunWith(AllureAndroidJUnit4.class)
public class AuthorizationTests {

    @Rule
    public ActivityTestRule<ru.iteco.fmhandroid.ui.AppActivity> activityTestRule =
            new ActivityTestRule<>(ru.iteco.fmhandroid.ui.AppActivity.class);

    String validLogin = "login2";
    String validPassword = "password2";

    String InvalidLogin = "login";
    String InvalidPassword = "password";

    @Test
    @DisplayName("Вход в личный кабинет с валидными данными")
    public void shouldLogInWithValidData() throws InterruptedException {
        Thread.sleep(5000);
        AuthorizationSteps.logIn(validLogin, validPassword);
        ControlPanelSteps.checkButtonLogIn();
        ControlPanelSteps.logOut();
    }

    @Test
    @DisplayName("Вход в личный кабинет с невалидными данными")
    public void shouldLogInWithInValidData() throws InterruptedException {
        Thread.sleep(5000);
        AuthorizationSteps.logIn(InvalidLogin, InvalidPassword);
        ControlPanelSteps.checkButtonLogIn();
        AuthorizationSteps.checkTextAuthorization(); //проверка осуществляется по неизменности окна авторизации, т.к.метод Toast на тестируемом устроитсве не поддерживается
    }

    @Test
    @DisplayName("Вход в личный кабинет с пустыми полями")
    public void shouldTryLogInWithEmptyField() throws InterruptedException {
        Thread.sleep(5000);
        AuthorizationSteps.clickSignInButton();
        AuthorizationSteps.checkTextAuthorization(); //проверка осуществляется по неизменности окна авторизации, т.к.метод Toast на тестируемом устроитсве не поддерживается
    }

    @Test
    @DisplayName("Выход из личного кабинета")
    public void shouldLogOut() throws InterruptedException {
        AuthorizationSteps.logIn(validLogin, validPassword);
        ControlPanelSteps.logOut();
    }
}
