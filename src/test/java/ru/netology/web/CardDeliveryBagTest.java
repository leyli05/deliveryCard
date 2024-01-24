package ru.netology.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryBagTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    LocalDate meetingDate = LocalDate.now().plusDays(10);
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    String meetingDateString = meetingDate.format(timeFormatter);

    @Test
    void possibleAbilityDateDistantFuture() {
        $("[data-test-id='city'] input").setValue("Новосибирск");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue("05.07.5023");
        $("[data-test-id='name'] input").setValue("Красовский Алексей");
        $("[data-test-id='phone'] input").setValue("+79139999999");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldHave(exactText("Успешно!"), Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content").shouldHave(exactText("Встреча успешно забронирована на 05.07.5023"));
    }

    @Test
    void showsErrorIfInNameЁ() {
        $("[data-test-id='city'] input").setValue("Новосибирск");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDateString);
        $("[data-test-id='name'] input").setValue("Аксёнов Андрей");
        $("[data-test-id='phone'] input").setValue("+79139999999");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='name'] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void registrationPossibleIfNoSurnameOrName() {
        $("[data-test-id='city'] input").setValue("Новосибирск");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDateString);
        $("[data-test-id='name'] input").setValue("Алексей");
        $("[data-test-id='phone'] input").setValue("+79139999999");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldHave(exactText("Успешно!"), Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content").shouldHave(exactText("Встреча успешно забронирована на " + meetingDateString));
    }

}
