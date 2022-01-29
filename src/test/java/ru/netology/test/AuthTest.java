package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthTest {

    @BeforeEach
    public void setUp() {
        Configuration.headless = true;
        open("http://localhost:9999");
    }

    @Test
    public void shouldValidUserActive() {
        val user = DataGenerator.Registration.registrationDto("active");
        $("[data-test-id=login] input").val(user.getLogin());
        $("[data-test-id=password] input").val(user.getPassword());
        $("[data-test-id=action-login]").click();
        $("h2").shouldBe(visible).shouldHave(text("Личный кабинет"));
    }

    @Test
    public void shouldValidUserBlocked() {
        val user = DataGenerator.Registration.registrationDto("blocked");
        $("[data-test-id=login] input").val(user.getLogin());
        $("[data-test-id=password] input").val(user.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]")
                .shouldBe(visible).shouldHave(text("Ошибка! Пользователь заблокирован"), Duration.ofSeconds(150));
    }

    @Test
    public void shouldInvalidLoginActive() {
        val user = DataGenerator.Registration.registrationDto("active");
        $("[data-test-id=login] input").val(DataGenerator.Registration.generateInvalidLogin());
        $("[data-test-id=password] input").val(user.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldBe(visible).shouldHave(text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(150));
    }

    @Test
    public void shouldInvalidPasswordActive() {
        val user = DataGenerator.Registration.registrationDto("active");
        $("[data-test-id=login] input").val(user.getLogin());
        $("[data-test-id=password] input").val(DataGenerator.Registration.generateInvalidPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldBe(visible).shouldHave(text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(150));
    }

    @Test
    public void shouldEmptyLoginActive() {
        val user = DataGenerator.Registration.registrationDto("active");
        $("[data-test-id=password] input").val(DataGenerator.Registration.generateInvalidPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=login].input_invalid").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldEmptyPasswordActive() {
        val user = DataGenerator.Registration.registrationDto("active");
        $("[data-test-id=login] input").val(user.getLogin());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=password].input_invalid").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldInvalidLoginBlocked() {
        val user = DataGenerator.Registration.registrationDto("blocked");
        $("[data-test-id=login] input").val(DataGenerator.Registration.generateInvalidLogin());
        $("[data-test-id=password] input").val(user.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldBe(visible).shouldHave(text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(150));
    }

    @Test
    public void shouldInvalidPasswordBlocked() {
        val user = DataGenerator.Registration.registrationDto("blocked");
        $("[data-test-id=login] input").val(user.getLogin());
        $("[data-test-id=password] input").val(DataGenerator.Registration.generateInvalidPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldBe(visible).shouldHave(text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(150));
    }
}