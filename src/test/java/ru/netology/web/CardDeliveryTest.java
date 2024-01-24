package ru.netology.web;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.selector.ByText;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

class CardDeliveryTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }
    //LocalDate meetingDate = LocalDate.now().plusDays(10);
    //DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    //String meetingDateString = meetingDate.format(timeFormatter);

    @Test
    void shouldApplicationSuccessful() {
        $("[data-test-id='city'] input").setValue("Новосибирск");
        String meetingDate = generateDate(10, "dd.MM.yyyy");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(meetingDate);
        $("[data-test-id='name'] input").setValue("Красовский Алексей");
        $("[data-test-id='phone'] input").setValue("+79139999999");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldHave(exactText("Успешно!"), Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content").shouldHave(exactText("Встреча успешно забронирована на " + meetingDate));
    }

    @Test
    void shouldShowErrorIfCityEmpty() {
        $("[data-test-id='city'] input").setValue("");
        String meetingDate = generateDate(10, "dd.MM.yyyy");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Красовский Алексей");
        $("[data-test-id='phone'] input").setValue("+79139999999");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='city'] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldShowErrorIfCityUnknown() {
        $("[data-test-id='city'] input").setValue("Полывмимлом");
        String meetingDate = generateDate(10, "dd.MM.yyyy");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Красовский Алексей");
        $("[data-test-id='phone'] input").setValue("+79139999999");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='city'] .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldShowErrorIfCityNotCyrillic() {
        $("[data-test-id='city'] input").setValue("Novosibirsk");
        String meetingDate = generateDate(10, "dd.MM.yyyy");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Красовский Алексей");
        $("[data-test-id='phone'] input").setValue("+79139999999");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='city'] .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldShowErrorIfDateEmpty() {
        $("[data-test-id='city'] input").setValue("Новосибирск");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='name'] input").setValue("Красовский Алексей");
        $("[data-test-id='phone'] input").setValue("+79139999999");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='date'] .input__sub").shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void shouldShowErrorIfBeforePossibleDate() {

        //LocalDate meetingDate = LocalDate.now().plusDays(1);
        //DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        //String meetingDateString = meetingDate.format(timeFormatter);

        $("[data-test-id='city'] input").setValue("Новосибирск");
        String meetingDate = generateDate(10, "dd.MM.yyyy");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Красовский Алексей");
        $("[data-test-id='phone'] input").setValue("+79139999999");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='date'] .input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldShowErrorIfNameEmpty() {
        $("[data-test-id='city'] input").setValue("Новосибирск");
        String meetingDate = generateDate(10, "dd.MM.yyyy");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("");
        $("[data-test-id='phone'] input").setValue("+79139999999");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='name'] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldShowErrorIfNameNotCyrillic() {
        $("[data-test-id='city'] input").setValue("Новосибирск");
        String meetingDate = generateDate(10, "dd.MM.yyyy");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Krasovskiy Alexey");
        $("[data-test-id='phone'] input").setValue("+79139999999");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='name'] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldShowErrorIfPhoneEmpty() {
        $("[data-test-id='city'] input").setValue("Новосибирск");
        String meetingDate = generateDate(10, "dd.MM.yyyy");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Красовский Алексей");
        $("[data-test-id='phone'] input").setValue("");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='phone'] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldShowErrorIfPhoneInvalid() {
        $("[data-test-id='city'] input").setValue("Новосибирск");
        String meetingDate = generateDate(10, "dd.MM.yyyy");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Красовский Алексей");
        $("[data-test-id='phone'] input").setValue("+7956");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='phone'] .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotEndApplicationIfCheckboxEmpty() {
        $("[data-test-id='city'] input").setValue("Новосибирск");
        String meetingDate = generateDate(10, "dd.MM.yyyy");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Красовский Алексей");
        $("[data-test-id='phone'] input").setValue("+79130000000");
        $(".button").click();
        $("[data-test-id='agreement'] .checkbox__text").shouldBe(visible);
    }

    @Test
    void shouldSelectCityFromList() {
        $("[data-test-id='city'] input").setValue("Но");
        //$(byText("Новосибирск")).click();
        $$(".menu-item__control").findBy(text("Новосибирск")).click();
        String meetingDate = generateDate(10, "dd.MM.yyyy");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Красовский Алексей");
        $("[data-test-id='phone'] input").setValue("+79139999999");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='notification'] .notification__title").shouldHave(exactText("Успешно!"), Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content").shouldHave(exactText("Встреча успешно забронирована на " + meetingDate));
    }

    @Test
    void shouldSelectDateFromCalendar() {
        $("[data-test-id='city'] input").setValue("Но");
        $$(".menu-item__control").findBy(text("Новосибирск")).click();
        $("button .icon").click();
        $(".calendar__arrow_direction_right[data-step='1']").click();
        $$("[data-day]").findBy(text("10")).click();
        $("[data-test-id='name'] input").setValue("Красовский Алексей");
        $("[data-test-id='phone'] input").setValue("+79139999999");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='notification'] .notification__title").shouldHave(exactText("Успешно!"), Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content").find(withText("Встреча успешно забронирована"));
    }
}
