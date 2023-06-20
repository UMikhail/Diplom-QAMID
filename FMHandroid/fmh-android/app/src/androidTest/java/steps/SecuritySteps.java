package steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;

import ru.iteco.fmhandroid.R;

public class SecuritySteps {
    //XSS-инъекции, проверка появления модального окна
    public static void xss() {
        ViewInteraction textView = onView(
                allOf(withId(android.R.id.message), withText("XSS"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        textView.check(doesNotExist());
    }

    //SQL-инъекции, проверка появления списка БД
    public static void sql() {
        ViewInteraction textView = onView(
                allOf(withId(android.R.id.message), withText("1"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        textView.check(doesNotExist());
    }

    //XSS-инъекции, проверка появления модального окна при создании заявки
    public static void xssCreatingClaim() throws InterruptedException {
        Thread.sleep(8000);
        onView(withId(R.id.add_new_claim_material_button)).perform(click());
        Thread.sleep(5000);
        onView(withId(R.id.title_edit_text)).perform(click(), replaceText("<script>alert(\"XSS\")</script>"), closeSoftKeyboard());
        onView(withId(R.id.date_in_plan_text_input_edit_text)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.time_in_plan_text_input_edit_text)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.description_edit_text)).perform(click(), replaceText("%3c script %3e alert(\"XSS\") %3c /script %3e"), closeSoftKeyboard());
        onView(withId(R.id.executor_drop_menu_auto_complete_text_view)).perform(click(), replaceText("Ivanov Ivan Ivanovich"), closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());
    }

    //SQL-инъекции, проверка появления списка БД при создании заявки
    public static void sqlCreatingClaim() throws InterruptedException {
        Thread.sleep(8000);
        onView(withId(R.id.add_new_claim_material_button)).perform(click());
        Thread.sleep(5000);
        onView(withId(R.id.title_edit_text)).perform(click(), replaceText("1’ or ‘1’ = ‘1"), closeSoftKeyboard());
        onView(withId(R.id.date_in_plan_text_input_edit_text)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.time_in_plan_text_input_edit_text)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.description_edit_text)).perform(click(), replaceText("1” or “1” = “1"), closeSoftKeyboard());
        onView(withId(R.id.executor_drop_menu_auto_complete_text_view)).perform(click(), replaceText("Ivanov Ivan Ivanovich"), closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());
    }

    //XSS-инъекции, проверка появления модального окна при создании новости
    public static void xssCreatingNews() throws InterruptedException {
        Thread.sleep(5000);
        onView(allOf(withId(R.id.edit_news_material_button),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container_list_news_include),
                                        0),
                                3),
                        isDisplayed())).perform(click());
        onView(allOf(withId(R.id.add_news_image_view), withContentDescription("Кнопка добавления новости"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                3),
                        isDisplayed())).perform(click());
        onView(withId(R.id.news_item_category_text_auto_complete_text_view)).perform(click());
        onView(
                allOf(withId(R.id.news_item_category_text_auto_complete_text_view),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.news_item_category_text_input_layout),
                                        0),
                                0),
                        isDisplayed())).perform(replaceText("Объявление"), closeSoftKeyboard());
        onView(allOf(withId(R.id.news_item_publish_date_text_input_edit_text),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.news_item_create_date_text_input_layout),
                                        0),
                                1),
                        isDisplayed())).perform(click());
        onView(allOf(withId(android.R.id.button1), withText("ОК"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3))).perform(scrollTo(), click());
        onView(allOf(withId(R.id.news_item_publish_time_text_input_edit_text),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.news_item_publish_time_text_input_layout),
                                        0),
                                1),
                        isDisplayed())).perform(click());
        onView(allOf(withId(android.R.id.button1), withText("ОК"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3))).perform(scrollTo(), click());
        onView(withId(R.id.news_item_title_text_input_edit_text)).perform(replaceText("<script>alert(\"XSS\")</script>"), closeSoftKeyboard());
        onView(withId(R.id.news_item_description_text_input_edit_text)).perform(replaceText("%3c script %3e alert(\"XSS\") %3c /script %3e"), closeSoftKeyboard());
        onView(allOf(withId(R.id.save_button), withText("Сохранить"), withContentDescription("Сохранить"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.card.MaterialCardView")),
                                        0),
                                6))).perform(scrollTo(), click());
    }

    //SQL-инъекции, проверка появления БД при создании новости
    public static void sqlCreatingNews() throws InterruptedException {
        Thread.sleep(5000);
        onView(allOf(withId(R.id.edit_news_material_button),
                childAtPosition(
                        childAtPosition(
                                withId(R.id.container_list_news_include),
                                0),
                        3),
                isDisplayed())).perform(click());
        onView(allOf(withId(R.id.add_news_image_view), withContentDescription("Кнопка добавления новости"),
                childAtPosition(
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                1),
                        3),
                isDisplayed())).perform(click());
        onView(withId(R.id.news_item_category_text_auto_complete_text_view)).perform(click());
        onView(
                allOf(withId(R.id.news_item_category_text_auto_complete_text_view),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.news_item_category_text_input_layout),
                                        0),
                                0),
                        isDisplayed())).perform(replaceText("Объявление"), closeSoftKeyboard());
        onView(allOf(withId(R.id.news_item_publish_date_text_input_edit_text),
                childAtPosition(
                        childAtPosition(
                                withId(R.id.news_item_create_date_text_input_layout),
                                0),
                        1),
                isDisplayed())).perform(click());
        onView(allOf(withId(android.R.id.button1), withText("ОК"),
                childAtPosition(
                        childAtPosition(
                                withClassName(is("android.widget.ScrollView")),
                                0),
                        3))).perform(scrollTo(), click());
        onView(allOf(withId(R.id.news_item_publish_time_text_input_edit_text),
                childAtPosition(
                        childAtPosition(
                                withId(R.id.news_item_publish_time_text_input_layout),
                                0),
                        1),
                isDisplayed())).perform(click());
        onView(allOf(withId(android.R.id.button1), withText("ОК"),
                childAtPosition(
                        childAtPosition(
                                withClassName(is("android.widget.ScrollView")),
                                0),
                        3))).perform(scrollTo(), click());
        onView(withId(R.id.news_item_title_text_input_edit_text)).perform(replaceText("1’ or ‘1’ = ‘1"), closeSoftKeyboard());
        onView(withId(R.id.news_item_description_text_input_edit_text)).perform(replaceText("1” or “1” = “1"), closeSoftKeyboard());
        onView(allOf(withId(R.id.save_button), withText("Сохранить"), withContentDescription("Сохранить"),
                childAtPosition(
                        childAtPosition(
                                withClassName(is("com.google.android.material.card.MaterialCardView")),
                                0),
                        6))).perform(scrollTo(), click());
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
}
