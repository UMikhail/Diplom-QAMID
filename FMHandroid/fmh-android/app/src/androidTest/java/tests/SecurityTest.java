package tests;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;

import androidx.test.rule.ActivityTestRule;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import ru.iteco.fmhandroid.ui.AppActivity;
import steps.AuthorizationSteps;
import steps.ControlPanelSteps;
import steps.NewsCreationSteps;
import steps.SecuritySteps;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

@RunWith(AllureAndroidJUnit4.class)
public class SecurityTest {

    @Rule
    public ActivityTestRule<AppActivity> activityTestRule =
            new ActivityTestRule<>(AppActivity.class);

    String validLogin = "login2";
    String validPassword = "password2";
    String invalidLoginXss = "<script>alert(\"XSS\")</script>";
    String invalidPasswordXss = "%3c script %3e alert(\"XSS\") %3c /script %3e";
    String invalidLoginSql = "1’ or ‘1’ = ‘1";
    String invalidPasswordSql = "1” or “1” = “1";

    @Test
    @DisplayName("Защита от XSS-инъекций в полях авторизации")
    public void alertXSSInjections() throws InterruptedException {
        Thread.sleep(5000);
        AuthorizationSteps.logIn(invalidLoginXss, invalidPasswordXss);
        ControlPanelSteps.checkButtonLogIn();
        Thread.sleep(5000);
        SecuritySteps.xss();
    }

    @Test
    @DisplayName("Защита от SQL-инъекций в полях авторизации")
    public void alertSQLInjections() throws InterruptedException {
        Thread.sleep(5000);
        AuthorizationSteps.logIn(invalidLoginSql, invalidPasswordSql);
        ControlPanelSteps.checkButtonLogIn();
        Thread.sleep(5000);
        SecuritySteps.sql();
    }

    @Test
    @DisplayName("Защита от SQL-инъекций при создании заявки")
    public void sqlInjectionsClaim() throws InterruptedException {
        Thread.sleep(5000);
        AuthorizationSteps.logIn(validLogin, validPassword);
        ControlPanelSteps.checkButtonLogIn();
        Thread.sleep(5000);
        SecuritySteps.sqlCreatingClaim();
        SecuritySteps.xss();
    }

    @Test
    @DisplayName("Защита от XSS-инъекций при создании заявки")
    public void xssInjectionsClaim() throws InterruptedException {
        Thread.sleep(5000);
        SecuritySteps.xssCreatingClaim();
        SecuritySteps.xss();
    }

    @Test
    @DisplayName("Защита от SQL-инъекций при создании новости")
    public void sqlInjectionsNews() throws InterruptedException {
        Thread.sleep(5000);
        NewsCreationSteps.creatingNewsOpening();
        SecuritySteps.sqlCreatingNews();
        SecuritySteps.xss();
    }

    @Test
    @DisplayName("Защита от XSS-инъекций при создании новости")
    public void xssInjectionsNews() throws InterruptedException {
        Thread.sleep(5000);
        NewsCreationSteps.creatingNewsOpening();
        SecuritySteps.xssCreatingNews();
        SecuritySteps.xss();
    }

}