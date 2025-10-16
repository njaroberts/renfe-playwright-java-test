# Test Case: TC_NEG_01 — Invalid Bank Card Payment on Renfe Website

**Objective:**  
Verify an **invalid bank card number**, the system prevents the payment by keeping the **“Pay”** button disabled.

**Test Type:** Negative Test Case 
**Labels:**  Payment

**Preconditions:**
- None
---

### **Test Steps and Expected Results**

| Step No. | Action | Expected Result |
|-----------|---------|----------------|
| 1 | Open `https://www.renfe.com` | The Renfe home page is displayed. |
| 2 | Click on **Origin** field | The list of stations appears. |
| 3 | Type `VALENCIA JOAQUÍN SOROLLA` | The corresponding station option appears. |
| 4 | Click on **Destination** field | The list of destination stations appears. |
| 5 | Type `BARCELONA-SANTS` | The corresponding station option appears. |
| 6 | Click on the **Calendar** field | A full calendar pop-up appears, showing two calendars (for departure and return). |
| 7 | Click **Solo ida** (One-way) | The pop-up updates to display only one calendar (for departure). |
| 8 | Select a valid departure date | The selected date is highlighted in the calendar. |
| 9 | Select the displayed option | The **Search Ticket (Buscar billete)** button becomes enabled. |
| 10 | Click **Buscar billete** | The available trip options are shown. |
| 11 | Click the first available travel option | A dropdown with fare types appears. |
| 13 | Select the **Básico** fare | The price and **Seleccionar** button are displayed. |
| 13 | Click **Seleccionar** | A pop-up window appears. |
| 14 | Click **No quiero** | A loading spinner appears, followed by the “Passenger details” form. |
| 15 | Enter a **non-real passenger name** and click the confirmation button | The next booking page appears. |
| 16 | Click **Continuar** | A new page is displayed successfully. |
| 17 | Select **Tarjeta** (Card payment option) | A dropdown for payment details appears. |
| 18 | Choose **Nueva tarjeta** and check the “He leído” (I have read) checkbox | The **Pay** button becomes enabled. |
| 19 | Click the payment button | The bank payment form appears. |
| 20 | Enter correctly formatted **expiration date** and **CVV** | Green validation ticks are displayed. |
| 21| Enter invalid card number `0000 0000 0000 0000` | A red warning message appears indicating an invalid card. |
| 22 | Verify that the **Pay** button remains disabled | The user cannot proceed with payment using an invalid card. |
