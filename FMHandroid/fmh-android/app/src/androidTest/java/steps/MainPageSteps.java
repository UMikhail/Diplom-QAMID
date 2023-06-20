package steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import ru.iteco.fmhandroid.R;

public class MainPageSteps {

    //отображение главной страницы
    public static void homePageDisplay() {
        onView(allOf(withText("Новости"),
                        withParent(withParent(withId(R.id.container_list_news_include_on_fragment_main))),
                        isDisplayed())).check(matches(withText("Новости")));
        onView(allOf(withText("Заявки"),
                        withParent(withParent(withId(R.id.container_list_claim_include_on_fragment_main))),
                        isDisplayed())).check(matches(withText("Заявки")));
    }

    //нажать кнопку "свернуть/развернуть" новости
    public static void clickTheExpandNewsButton() {
        onView(allOf(withId(R.id.all_news_text_view), withText("ВСЕ НОВОСТИ"),
                        withParent(allOf(withId(R.id.container_list_news_include_on_fragment_main),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed())).check(matches(withText("ВСЕ НОВОСТИ")));
        onView(allOf(withId(R.id.expand_material_button),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container_list_news_include_on_fragment_main),
                                        0),
                                4),
                        isDisplayed())).perform(click());
        onView(allOf(withId(R.id.all_news_text_view), withText("ВСЕ НОВОСТИ"),
                        withParent(allOf(withId(R.id.container_list_news_include_on_fragment_main),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed())).check(doesNotExist());
    }

    //нажать кнопку "свернуть/развернуть" заявки
    public static void clickTheExpandClaimsButton() {
        onView(allOf(withId(R.id.all_claims_text_view), withText("ВСЕ ЗАЯВКИ"),
                        withParent(allOf(withId(R.id.container_list_claim_include_on_fragment_main),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed())).check(matches(withText("ВСЕ ЗАЯВКИ")));
        onView(allOf(withId(R.id.expand_material_button),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container_list_claim_include_on_fragment_main),
                                        0),
                                3),
                        isDisplayed())).perform(click());
        onView(allOf(withId(R.id.all_claims_text_view), withText("ВСЕ ЗАЯВКИ"),
                        withParent(allOf(withId(R.id.container_list_claim_include_on_fragment_main),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed())).check(doesNotExist());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

//    public static void goToFirstClaimFromMainBlock() {
//        Allure.step("Переход к первой заявке из главного блока");
//        MainScreen.firstClaim.perform(click());
//        ClaimScreen.titleTextOfClaim.check(matches(isDisplayed()));
//    }

//    public static void checkContentOfFirstClaimInMainBlock() {
//        Allure.step("Проверка видимости содержимого первой заявки в главном блоке: описание, исполнитель, плановые дата и время");
//        MainScreen.descriptionOfFirstClaim.check(matches(isDisplayed()));
//        MainScreen.executorOfFirstClaim.check(matches(isDisplayed()));
//        MainScreen.planeDateOfFirstClaim.check(matches(isDisplayed()));
//        MainScreen.planeTimeOfFirstClaim.check(matches(isDisplayed()));
//    }
//
//    public static void expandFirstNewsInMainBlock() {
//        Allure.step("Раскрыть первую новость в главном блоке");
//        MainScreen.buttonToExpandFirstNews.perform(click());
//        // проверка заложена в следующем методе
//    }
//
//    public static void checkDescriptionOfFirstNews() {
//        Allure.step("Проверка видимости описания первой новости в главном блоке");
//        MainScreen.descriptionOfFirstNews.check(matches(isDisplayed()));
//    }
//
//    public static void checkContentOfNotExpandedFirstNewsInMainBlock() {
//        Allure.step("Проверка видимости содержимого не раскрытой первой новости в главном блоке");
//        MainScreen.titleOfFirstNews.check(matches(isDisplayed()));
//        MainScreen.dateOfFirstNews.check(matches(isDisplayed()));
//    }
//
//    public static void checkIfNewsPartitionExists() {
//        Allure.step("Проверка видимости раздела новостей в главном блоке");
//        MainScreen.containerListForNews.check(matches(isDisplayed()));
//        onView(withText("News")).check(matches(isDisplayed()));
//    }
//
//    public static void checkIfClaimsPartitionExists() {
//        Allure.step("Проверка видимости раздела заявок в главном блоке");
//        MainScreen.containerListForClaims.check(matches(isDisplayed()));
//        onView(withText("Claims")).check(matches(isDisplayed()));
//    }
//
//    public static void expandOrHideNewsPart() {
//        Allure.step("Свернуть/раскрыть раздел с новостями");
//        MainScreen.buttonToExpandOrHideNewsPart.perform(click());
//        // проверка шага невозможна (результат разный в зависимости от предыдущего состояния)
//    }
//
//    public static void claimTitleIsDisplayedWithSwipe(Integer claimPosition) {
//        Allure.step("Проверка, что виден заголовок заявки");
//        onView((withId(R.id.claim_list_recycler_view))).perform(actionOnItemAtPosition(claimPosition, swipeUp()));
//        onView(MainHelper.withIndex(withId(R.id.description_material_text_view), claimPosition)).check(matches(isDisplayed()));
//    }
}
