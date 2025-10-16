package com.amaris.pages;


import com.amaris.base.BasePage;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

public class ConfirmTripPage extends BasePage {

    private final Locator seleccionarButton1;
    private final Locator travelerDataSection;
    private final Locator NameInput;
    private final Locator surname1Input;
    private final Locator surname2Input;
    private final Locator DNIInput;
    private final Locator EmailInput;
    private final Locator TelephoneInput;
    private final Locator NextButtonExtras;
    private final Locator MethodPayEmail;
    private final Locator MethodPayTelef;
    private final Locator CardOption;
    private final Locator NewCardOpt;
    private final Locator AcceptCondBox;
    private final Locator FinaliseButton;



    // Constructor
    public ConfirmTripPage(Page page) {
        super(page);

    
        seleccionarButton1  = page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Seleccionar"));
        travelerDataSection = page.getByRole(AriaRole.ARTICLE);              
        NameInput = page.getByLabel("Nombre");       
        surname1Input = page.getByLabel("Primer apellido");        
        surname2Input = page.getByLabel("Segundo apellido"); 
        DNIInput =  page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Número de documento"));       
        EmailInput = page.getByLabel("Correo electronico");   
        TelephoneInput = page.getByLabel("Teléfono"); 
        NextButtonExtras = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continúa con la compra"));
        MethodPayEmail = page.getByPlaceholder("Correo electrónico");
        MethodPayTelef = page.getByPlaceholder("Teléfono");
        CardOption = page.locator("#datosPago_cdgoFormaPago_tarjetaRedSys");
        NewCardOpt = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Nueva Tarjeta Introducirás la"));
        AcceptCondBox = page.locator("#aceptarCondiciones");
        FinaliseButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Finaliza tu compra"));
    }

   public void clickFirstAvailableTrain() {
        Locator link = page.getByLabel("Tren con salida a").getByRole(AriaRole.LINK);
        link.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        link.first().click();
    }

    // submenu after choosing which train appears
    public boolean isTripDetailsVisible() {
    Locator tripDetails = page.getByText("Ver detalles del trayecto Detalles del trayecto × Itinerario Prestaciones Bá");
    tripDetails.waitFor(new Locator.WaitForOptions().setTimeout(5000));
    return tripDetails.isVisible();
    }

    public void clickFirstDivAfterPlanesOpciones() {
    Locator nextDiv = page.locator("//*[@id='planes-opciones_i_1']/div[1]");
    nextDiv.scrollIntoViewIfNeeded(null);
    nextDiv.click();
    }

    public double getTotalTripPrice() {
    Locator totalPrice = page.locator("#totalTrayecto");
    totalPrice.waitFor(new Locator.WaitForOptions().setTimeout(7000));
    long start = System.currentTimeMillis();
    String text = "";
    while (System.currentTimeMillis() - start < 7000) {
        text = totalPrice.textContent().trim();
        if (!text.isEmpty() && !text.startsWith("0")) break;
        page.waitForTimeout(300); // poll
    }

    String numeric = text.replace("€", "").replace(",", ".").trim();
    return Double.parseDouble(numeric);
}



    public void clickSeleccionarButton() {
    seleccionarButton1.waitFor(new Locator.WaitForOptions().setTimeout(7000));
    seleccionarButton1.click();
    Locator windowpopup= page.getByText("No, quiero continuar con Bá");
    windowpopup.waitFor(new Locator.WaitForOptions().setTimeout(7000));
    windowpopup.click();
    clickCloseIfVisible();
    }

    public boolean isTravelerDataSectionVisible() {
    travelerDataSection.waitFor(new Locator.WaitForOptions().setTimeout(5000));
    return travelerDataSection.textContent().contains("DATOS DEL VIAJERO");
    }

    public void inputDetails(String Name, String Surname1, String Surname2, String DNI, String email, String Telef){
    TelephoneInput.fill(Telef);
    NameInput.click();
    NameInput.fill(Name);
    surname1Input.fill(Surname1);
    surname2Input.fill(Surname2);
    DNIInput.fill(DNI);
    EmailInput.fill(email);
    NameInput.click();

    page.getByLabel("Ir a la siguiente ventana de").scrollIntoViewIfNeeded();
    page.getByLabel("Ir a la siguiente ventana de").click();
    page.getByLabel("Ir a la siguiente ventana de").click();
    }


    public boolean isPersonalizeTripVisible() {
    Locator personalizeSection = page.getByRole(AriaRole.ARTICLE);
    personalizeSection.waitFor(new Locator.WaitForOptions().setTimeout(5000));
    return personalizeSection.textContent().contains("Personaliza tu viaje");
    }   

    public void clickNextButtonExtras() {
    NextButtonExtras.waitFor(new Locator.WaitForOptions().setTimeout(7000));
    NextButtonExtras.scrollIntoViewIfNeeded();
    NextButtonExtras.click();
    }

    public boolean isPaymentMethodVisible() {
    Locator paymentForm = page.locator("#formBean");
    paymentForm.waitFor(new Locator.WaitForOptions().setTimeout(5000));
    return paymentForm.textContent().contains("Método de pago");
    }

    public void Fill_and_click(String email, String Telef){
        MethodPayEmail.fill(email);
        MethodPayTelef.fill(Telef);

        CardOption.click();
        NewCardOpt.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        NewCardOpt.click();
        AcceptCondBox.scrollIntoViewIfNeeded();
        AcceptCondBox.check();
        FinaliseButton.click();

    }
}


