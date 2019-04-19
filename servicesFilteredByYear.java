package main.tests.allTests.widget.widget_Web.servicesFilteredByYear;

import main.all.common.objects.tireSize.TireSize;
import main.api.Api;
import main.api.v2.requests.routes.location.getLocation.objects.data.Location;
import main.api.v2.requests.routes.quote.abstractQuote.parameters.QuoteRequestParameters;
import main.api.v2.requests.routes.quote.quoteDisplay.DisplayQuoteRequest;
import main.api.v2.requests.routes.tire.search.searches.bySize.response.objects.data.tires.Tire_SearchBySize;
import main.api.v2.requests.routes.vehicle.tireSizes.TireSizesRequest;
import main.environments.classes.entities.main.ApiKey;
import main.environments.data.clients.allClients.all.Clients;
import main.tests.allTests.widget.SettingsForDriver;
import main.widget.Widget;
import main.widget.pages.CommonWidgetMethods;
import main.widget.pages.wigetPages.summaryPage.SummaryPage;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class servicesFilteredByYear extends SettingsForDriver {

    private ApiKey apiKey = new Clients().clientDefault().dealerServicesFilterByYear().getApiKeys().getWidgetApiKey_en_US();
    private Location location = new Api().v2().locationList().list(apiKey).getResponse().getRandomLocation();
    private Tire_SearchBySize tire;

    private String carTireId;
    private QuoteRequestParameters quoteRequestParameters;
    private DisplayQuoteRequest display;


    @Test
    public void checkServicesFilteredByYear_2016(){

        getCarTireId("2016", "BMW", "228i", "Coupe");
        setTire("205", "50", "17");

        SummaryPage summaryPage = new Widget().web().summaryPage().byVehicle().getPage(driver, apiKey, location.getId().toString(),
                tire.getId(), "2", "2016", "BMW", "228i", "Coupe", carTireId);

        parametersForRequest("2");
        quoteDisplayRequestToApi();
        Assert.assertTrue(display.getResponse().getData().getServices().size()==3);

        checkServicesFromApi(display, 0, "99.0", "Year_Testing_Service >= 2016");
        checkServicesFromApi(display, 1, "80.0", "Year_Testing_Service >= 1998-2016");
        checkServicesFromApi(display, 2, "100.0", "Year_Testing_Services >= 1980 - 2018");

        checkServicesFront(summaryPage, "2016");
        checkServicesFront(summaryPage, "1998-2016");
        checkServicesFront(summaryPage, "1980 - 2018");
    }

    @Test
    public void checkServicesFilteredByYear_2015(){

        getCarTireId("2015", "BMW", "740i", "Base");
        setTire("195", "65", "15");

        SummaryPage summaryPage = new Widget().web().summaryPage().byVehicle().getPage(driver, apiKey, location.getId().toString(),
                tire.getId(), "2", "2015", "BMW", "740i", "Base", carTireId);

        parametersForRequest("2");
        quoteDisplayRequestToApi();
        Assert.assertTrue(display.getResponse().getData().getServices().size()==2);

        checkServicesFromApi(display, 0, "80.0", "Year_Testing_Service >= 1998-2016");
        checkServicesFromApi(display, 1, "100.0", "Year_Testing_Services >= 1980 - 2018");

        checkServicesFront(summaryPage, "1998-2016");
        checkServicesFront(summaryPage, "1980 - 2018");
    }

    @Test
    public void checkServicesFilteredByYear_1990(){

        getCarTireId("1990", "BMW", "525i", "Base");
        setTire("205", "65", "15");

        SummaryPage summaryPage = new Widget().web().summaryPage().byVehicle().getPage(driver, apiKey, location.getId().toString(),
                tire.getId(), "2", "1990", "BMW", "525i", "Base", carTireId);

        parametersForRequest("2");
        quoteDisplayRequestToApi();
        Assert.assertTrue(display.getResponse().getData().getServices().size()==3);

        checkServicesFromApi(display, 0, "80.0", "Year_Testing_Services =< 2000");
        checkServicesFromApi(display, 1, "70.0", "Year_Testing_Services >= 1980 - 1993");
        checkServicesFromApi(display, 2, "100.0", "Year_Testing_Services >= 1980 - 2018");

        checkServicesFront(summaryPage, "2000");
        checkServicesFront(summaryPage, "1980 - 1993");
        checkServicesFront(summaryPage, "1980 - 2018");
    }

    @Test
    public void checkServicesFilteredByYear_2004(){

        getCarTireId("2004", "Audi", "A6", "3.0");
        setTire("205", "55", "16");

        SummaryPage summaryPage = new Widget().web().summaryPage().byVehicle().getPage(driver, apiKey, location.getId().toString(),
                tire.getId(), "2", "2004", "Audi", "A6", "3.0", carTireId);

        parametersForRequest("2");
        quoteDisplayRequestToApi();
        Assert.assertTrue(display.getResponse().getData().getServices().size()==3);

        checkServicesFromApi(display, 0, "80.0", "Year_Testing_Service >= 1998-2016");
        checkServicesFromApi(display, 1, "100.0", "Year_Testing_Services >= 1980 - 2018");
        checkServicesFromApi(display, 2, "110.0", "Year_Testing_Services >= 1998 - 2004");

        checkServicesFront(summaryPage, "1998-2016");
        checkServicesFront(summaryPage, "1980 - 2018");
        checkServicesFront(summaryPage, "1998 - 2004");
    }

    // tests with select vehicle on summary page

    @Test
    public void checkServicesFilteredByYear_2004_WithSelectVehicle(){

        setTire("205", "55", "16");
        SummaryPage summaryPage = new Widget().web().summaryPage().getPage(driver, apiKey, tire);
        summaryPage.waitForLoadPageSelectVehicle();

        setVehicle(driver, "2004", "Audi", "A6", "3.0");

        summaryPage.clickSeeOutDoorThePriceOnThePageSelectVehicle();
        summaryPage.changeQuantity("2");

        checkServicesFront(summaryPage, "1998-2016");
        checkServicesFront(summaryPage, "1980 - 2018");
        checkServicesFront(summaryPage, "1998 - 2004");
    }

    @Test
    public void checkServicesFilteredByYear_1990_WithSelectVehicle(){

        setTire("205", "65", "15");
        SummaryPage summaryPage = new Widget().web().summaryPage().getPage(driver, apiKey, tire);
        summaryPage.waitForLoadPageSelectVehicle();

        setVehicle(driver, "1990", "BMW", "525i", "Base");

        summaryPage.clickSeeOutDoorThePriceOnThePageSelectVehicle();
        summaryPage.changeQuantity("2");

        checkServicesFront(summaryPage, "2000");
        checkServicesFront(summaryPage, "1980 - 1993");
        checkServicesFront(summaryPage, "1980 - 2018");
    }

    @Test
    public void checkServicesFilteredByYear_2015_WithSelectVehicle(){

        setTire("195", "65", "15");
        SummaryPage summaryPage = new Widget().web().summaryPage().getPage(driver, apiKey, tire);
        summaryPage.waitForLoadPageSelectVehicle();

        setVehicle(driver, "2015", "BMW", "740i", "Base");

        summaryPage.clickSeeOutDoorThePriceOnThePageSelectVehicle();
        summaryPage.changeQuantity("2");

        checkServicesFront(summaryPage, "1998-2016");
        checkServicesFront(summaryPage, "1980 - 2018");
    }

    @Test
    public void checkServicesFilteredByYear_2016_WithSelectVehicle(){

        setTire("205", "50", "17");
        SummaryPage summaryPage = new Widget().web().summaryPage().getPage(driver, apiKey, tire);
        summaryPage.waitForLoadPageSelectVehicle();

        setVehicle(driver, "2016", "BMW", "228i", "Coupe");

        summaryPage.clickSeeOutDoorThePriceOnThePageSelectVehicle();
        summaryPage.changeQuantity("2");

        checkServicesFront(summaryPage, "2016");
        checkServicesFront(summaryPage, "1998-2016");
        checkServicesFront(summaryPage, "1980 - 2018");
    }


    private static synchronized void setVehicle(WebDriver driver, String year, String make, String model, String trim){

        CommonWidgetMethods.setYear(driver, year);
        CommonWidgetMethods.setMake(driver, make);
        CommonWidgetMethods.setModel(driver, model);
        CommonWidgetMethods.setTrim(driver, trim);
    }

    private void getCarTireId(String year, String make, String model, String trim){

        TireSizesRequest tireSizesRequest = new Api().v2().vehicleTireSizes().tireSizes(apiKey, year, make, model, trim);
        carTireId = tireSizesRequest.getResponse().getRandomCarTire().getId();
    }

    private void setTire(String width, String height, String rim) {

        setTire(new TireSize(width, height, rim));
    }

    private void setTire(TireSize tireSize) {

        tire = new Api().v2().tireSearchBySize().bySize(apiKey, tireSize).getResponse().getRandomTireWithQuantity(2);
    }

    private void quoteDisplayRequestToApi(){

        display = new Api().v2().quoteDisplay().display(apiKey, quoteRequestParameters);
    }

    private void parametersForRequest(String quantity){

        quoteRequestParameters = new QuoteRequestParameters();
        quoteRequestParameters.setVehicleCarTireId(carTireId);
        quoteRequestParameters.addTireId(tire.getId());
        quoteRequestParameters.addQuantity(quantity);
    }

    private void checkServicesFront(SummaryPage summaryPage, String partTextOfServices){

        Assert.assertTrue(summaryPage.getTextAnyServicesFromSummaryPage(partTextOfServices).contains(partTextOfServices));
    }

    private void checkServicesFromApi(DisplayQuoteRequest display, int numberOfServicesFromApi, String summService, String nameAndDescription){

        Assert.assertEquals(summService, display.getResponse().getData().getServices().get(numberOfServicesFromApi).getTotal_price().toString());
        Assert.assertEquals(nameAndDescription, display.getResponse().getData().getServices().get(numberOfServicesFromApi).getName());
        Assert.assertEquals(nameAndDescription, display.getResponse().getData().getServices().get(numberOfServicesFromApi).getDescription());
    }

}
