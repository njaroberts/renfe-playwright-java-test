package com.amaris.tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;

import org.testng.Assert;
import org.testng.annotations.*;

import com.amaris.pages.ConfirmTripPage;
import com.amaris.pages.CreditCardPage;
import com.amaris.pages.HomePage;
import com.amaris.utils.ConfigReader;

public class TC_NEG_01 {
    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;
    private HomePage homePage;
    private ConfirmTripPage confirmTripPage;
    private CreditCardPage creditCardPage;


  // ---- Test data (all from testdata.properties) ----
  private String baseUrl;
  private String origin;
  private String destination;

  private String personName;
  private String personLastName;
  private String personSecondLastName;
  private String personEmail;
  private String personDocType;
  private String personDocNumber;
  private String personPhone;

  private String cardNumber;
  private String cardExpiryMonth;
  private String cardExpiryYear;
  private String cardCvv;
  private String cardHolder;

  @BeforeClass
  public void setUp() {
    // Load data
    baseUrl              = ConfigReader.get("base.url");
    origin               = ConfigReader.get("origin");
    destination          = ConfigReader.get("destination");

    personName           = ConfigReader.get("person.name");
    personLastName       = ConfigReader.get("person.lastname");
    personSecondLastName = ConfigReader.get("person.secondlastname");
    personEmail          = ConfigReader.get("person.email");
    personDocType        = ConfigReader.get("person.documentType");
    personDocNumber      = ConfigReader.get("person.documentNumber");
    personPhone          = ConfigReader.get("person.phone");

    cardNumber           = ConfigReader.get("card.number");
    cardExpiryMonth      = ConfigReader.get("card.expiryMonth/Year");
    cardExpiryYear       = ConfigReader.get("card.expiryYear");
    cardCvv              = ConfigReader.get("card.cvv");
    cardHolder           = ConfigReader.get("card.holderName");

    playwright = Playwright.create();
    browser = playwright.chromium().launch(
        new BrowserType.LaunchOptions()
            .setHeadless(false)   // show Chromium
            .setSlowMo(500)       // slow down for visibility (optional)
    );
    context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1920,953));
    page = context.newPage();
    confirmTripPage = new ConfirmTripPage(page);
    homePage = new HomePage(page);
    creditCardPage = new CreditCardPage(page);
    

    context.setDefaultTimeout(10_000);
    page.setDefaultTimeout(10_000);

  }
  @AfterClass(alwaysRun = true)
  public void tearDown() {
    if (context != null) context.close();
    if (browser != null) browser.close();
    if (playwright != null) playwright.close();
  }
@Test(description = "TC_NEG_01 — Validate and fill origin station only")
  public void originStation_only() {
    // 1) Navigate
    page.navigate(baseUrl);
      page.waitForLoadState(LoadState.NETWORKIDLE);
    page.keyboard().press("F11");
    page.waitForTimeout(2000);

    // 2) Click Origin
    page.keyboard().press("F11");
    homePage.acceptCookies();
    homePage.clickOrigin();
    Assert.assertTrue(homePage.isOriginListVisible(),
            "Expected the origin station list to be visible after clicking origin.");

    // 3) Fill origin
     Assert.assertTrue(homePage.isStationKnown(origin),
        "Origin not in known stations (accents must match): " + origin);
    homePage.enterOrigin(origin);
    Assert.assertTrue(homePage.originSelected(origin),
    "Origin value did not match expected: " + origin);
    
    // 4) Click Destination
    homePage.clickDestino();
    Assert.assertTrue(homePage.isDestListVisible(),
            "Expected the Destin. station list to be visible after clicking origin.");

    // 5) Fill Destination
    Assert.assertTrue(homePage.isStationKnown(destination),
        "Origin not in known stations (accents must match): " + destination);
    homePage.enterDestination(destination);
    Assert.assertTrue(homePage.DestinationSelected(destination),
    "Origin value did not match expected: " + destination);

    // 6)Click Calendar
    homePage.clickCalendar();
    Assert.assertTrue(homePage.isCalendarVisible(),
    "The 'Viaje solo ida / Viaje de ida y vuelta' section should be visible on the homepage.");

    // 7. Click Solo Dia
    homePage.clickSoloDia();
    Assert.assertTrue(homePage.isOneCalendarVisible(),
    "Expected only one calendar (datepickerv2) to be visible after selecting 'Solo ida'."
    );

    // 8. Click Date
    homePage.clickValidDate();
    homePage.clickAcceptCalendarDate();
    Assert.assertFalse(
    homePage.isCalendarVisible(),
    "Expected the calendar section to be hidden, but it is visible."
    );

    //9. 10 Check search button works and press
    Assert.assertTrue(homePage.isClickSearchClickable(),
    "Expected 'Buscar billete' to be visible and enabled."
    );
    homePage.clickSearch();

    // 11. Click first avail train
    confirmTripPage.clickFirstAvailableTrain();
    Assert.assertTrue(confirmTripPage.isTripDetailsVisible(),
    "Expected 'Ver detalles del trayecto...' section to be visible."
    );

    //12. Click first travel option of first train
    confirmTripPage.clickFirstDivAfterPlanesOpciones();
    double total = confirmTripPage.getTotalTripPrice();
    Assert.assertTrue(total > 0.0, "Expected total price to be above 0 €, but was: " + total + "€");

    //13 + 14 Click Selecionnar
    confirmTripPage.clickSeleccionarButton();
    Assert.assertTrue(confirmTripPage.isTravelerDataSectionVisible(),
    "Expected 'DATOS DEL VIAJERO' section to be visible on the page.");

    //15. Fill in

    confirmTripPage.inputDetails(personName, personLastName, personSecondLastName,personDocNumber, personEmail, personPhone);
    Assert.assertTrue(confirmTripPage.isPersonalizeTripVisible(),
    "Expected 'Personaliza tu viaje' section to be visible.");


    //16. Click Next of extras
    confirmTripPage.clickNextButtonExtras();
    Assert.assertTrue(confirmTripPage.isPaymentMethodVisible(),
    "Expected 'Método de pago' section to be visible on the payment page.");

    //17. 18 19. Add email again and select Credit Card
    confirmTripPage.Fill_and_click(personEmail, personPhone);

    //20 21 22
    creditCardPage.Pay(cardNumber, cardExpiryMonth, cardCvv);

    Assert.assertTrue(creditCardPage.isAcceptButtonEnabled(),
    "Expected 'Aceptar' button (#divImgAceptar) to be enabled."
);



  }
}
