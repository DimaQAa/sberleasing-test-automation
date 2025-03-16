package com.example.tests.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SberLeasingUITest {

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        Configuration.browserSize = null;
        Configuration.timeout = 10000;
        Configuration.pageLoadTimeout = 20000;
    }

    @Test
    @DisplayName("Проверка подбора авто на сайте СберЛизинг")
    public void testCarSelection() {
        openGoogle();
        searchForSberLeasing();
        navigateToSberLeasingFromSearchResults();
        navigateToCarSelectionSection();
        fillCarSelectionParameters();
        clickShowAllOffers();
        String selectedBrand = selectCarFromList();
        String detailBrand = getCarDetailBrand();
        assertEquals(selectedBrand, detailBrand, "Марка выбранного авто должна соответствовать марке из списка");
    }
    
    @Step("Открываем Google")
    private void openGoogle() {
        open("https://www.google.com");
    }
    
    @Step("Ищем СберЛизинг в Google")
    private void searchForSberLeasing() {
        $("input[name='q']").setValue("СберЛизинг").pressEnter();
    }
    
    @Step("Переходим на сайт СберЛизинг из результатов поиска")
    private void navigateToSberLeasingFromSearchResults() {
        $$("a[href*='sberleasing.ru']").first().click();
        $("title").shouldHave(text("СберЛизинг"));
    }

    @Step("Переходим в раздел подбора авто")
    private void navigateToCarSelectionSection() {
        $("a[href*='catalog']").hover();
        $("a[href*='catalog/cars']").click();
    }

    @Step("Заполняем параметры для подбора автомобиля")
    private void fillCarSelectionParameters() {
        $("button:has(span:contains('Марка'))").click();
        $("div[role='listbox'] div:contains('Toyota')").click();
        $("button:has(span:contains('Модель'))").click();
        $("div[role='listbox'] div:contains('Camry')").click();
        $("input[placeholder*='от']").setValue("500000");
        $("input[placeholder*='до']").setValue("1500000");
    }

    @Step("Нажимаем кнопку 'Показать все предложения'")
    private void clickShowAllOffers() {
        $("button:contains('Подобрать по параметрам')").click();
    }

    @Step("Выбираем автомобиль из списка и получаем марку")
    private String selectCarFromList() {
        $("div.catalog-item").should(appear);
        SelenideElement firstCar = $$("div.catalog-item").first();
        String brand = firstCar.$("div.catalog-item__title").getText().split(" ")[0];
        firstCar.click();
        return brand;
    }

    @Step("Получаем марку автомобиля на детальной странице")
    private String getCarDetailBrand() {
        $("div.car-detail").should(appear);
        String fullTitle = $("h1.car-detail__title").getText();
        return fullTitle.split(" ")[0];
    }
}