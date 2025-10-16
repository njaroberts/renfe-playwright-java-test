package com.amaris.pages;

import java.util.List;

import com.amaris.base.BasePage;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;


public class HomePage extends BasePage {


    private final Locator originField;
    private final Locator destinationField;
    private final Locator calendarButton;
    private final Locator oneWayButton;
    private final Locator searchButton;
    private final Locator cookieAcceptButton;
    private final Locator calendarAccept;

    private static final List<String> KNOWN_STATIONS = List.of(
    "VALÈNCIA-JOAQUÍN SOROLLA",
    "BARCELONA-SANTS"
);

 // Constructor
    public HomePage(Page page) {
        super(page);

       
        originField = page.getByPlaceholder("Selecciona tu origen");
        destinationField = page.getByPlaceholder("Selecciona tu destino");
        calendarButton = page.getByText("Fecha ida");
        calendarAccept = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Aceptar"));
        oneWayButton = page.getByText("Viaje solo ida");
        searchButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Buscar billete"));
        cookieAcceptButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Permitir solo cookies técnicas"));
    }

    // Methods--------------------

    //check whether station Exists
    public boolean isStationKnown(String station) {
    return station != null && KNOWN_STATIONS.contains(station.trim());
    }

    //check station is selected after menu
    public boolean originSelected(String origin) {
    page.waitForTimeout(500);
    String currentValue = page.getByPlaceholder("Selecciona tu origen").inputValue();
    return currentValue.equals(origin);
    }

    //check station is selected after menu
    public boolean DestinationSelected(String destination) {
    page.waitForTimeout(500);
    String currentValue = page.getByPlaceholder("Selecciona tu destino").inputValue();
    return currentValue.equals(destination);
    }

    //click origin input
    public void clickOrigin(){
    page.getByPlaceholder("Selecciona tu origen").click();
    }

    //click Destination input
    public void clickDestino(){
    page.getByPlaceholder("Selecciona tu destino").click();
    }

    //checkListVisible of origin
    public boolean isOriginListVisible() {
    Locator list = page.locator("//*[@id='awesomplete_list_1']");
    return list.isVisible(); 
    }
    
    //checkListVisible of destination
    public boolean isDestListVisible() {
    Locator list = page.locator("//*[@id='awesomplete_list_2']");
    return list.isVisible(); 
    }
    
    //fill name of origin
    public void enterOrigin(String origin) {
            if (!isStationKnown(origin)) {
        throw new IllegalArgumentException("Unknown station (accents must match): " + origin);
        }
        fill(originField, origin);
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(origin)).click();
    }

    //fill name of destin
    public void enterDestination(String destination) {
                    if (!isStationKnown(destination)) {
        throw new IllegalArgumentException("Unknown station (accents must match): " + destination);
        }
        fill(destinationField, destination);
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(destination)).click();
    
    }

    public void clickCalendar(){
        page.getByText("Fecha ida").click();
    }

    public void clickSoloDia(){
        page.getByText("Viaje solo ida").click();
    }

    public boolean isOneCalendarVisible() {
    return page.locator("#datepickerv2").isVisible()
        && !page.locator("#daterangev2").isVisible();
    }


    public boolean isCalendarVisible() {
    Locator section = page.locator("section")
        .filter(new Locator.FilterOptions()
            .setHasText("Viaje solo ida Viaje de ida y"));
    return section.isVisible();
    }

    public void clickValidDate(){
    page.locator("(//span[@class='day-price'])[1]/ancestor::div[contains(@class,'lightpick__day')][1]")
    .click();

    }

    public void clickAcceptCalendarDate(){
        calendarAccept.click();
    }

    public void clickSearch() {
        click(searchButton);
    }
    public void acceptCookies() {
        clickIfVisible(cookieAcceptButton);
    }

    public boolean isClickSearchClickable() {
    searchButton.waitFor(new Locator.WaitForOptions().setTimeout(5000));
    page.waitForCondition(() -> searchButton.isEnabled(), new Page.WaitForConditionOptions().setTimeout(5000));
    return searchButton.isVisible() && searchButton.isEnabled();
    }


}
