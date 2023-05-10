package id.ac.ui.cs.advprog.gamesappsstore.controller.app;

import id.ac.ui.cs.advprog.gamesappsstore.controller.app.SearchController;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.enums.VerificationStatus;
import id.ac.ui.cs.advprog.gamesappsstore.repository.app.AppDataRepository;
import id.ac.ui.cs.advprog.gamesappsstore.service.app.SearchServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class SearchControllerTest {
    @Mock
    private SearchServiceImpl searchService;

    @InjectMocks
    private SearchController searchController;
    @Mock
    AppDataRepository appDataRepository;

    AppData appData1;
    AppData appData2;
    AppData appData3;

    @BeforeEach
    void setup() {
        appData1 = AppData.builder()
                .id(1L)
                .name("App 1")
                .imageUrl("https://image.com/app1")
                .installerUrl("https://storage.com/app1")
                .description("The first app")
                .version("1.0.0")
                .price(10000d)
                .verificationStatus(VerificationStatus.UNVERIFIED)
                .verificationAdminId(null)
                .verificationDate(null)
                .build();
        appData2 = AppData.builder()
                .id(2L)
                .name("App 2")
                .imageUrl("https://image.com/app2")
                .installerUrl("https://storage.com/app2")
                .description("The second app")
                .version("1.0.0")
                .price(10000d)
                .verificationStatus(VerificationStatus.VERIFIED)
                .verificationAdminId(1)
                .verificationDate(new Date())
                .build();
        appData3 = AppData.builder()
                .id(3L)
                .name("App 3")
                .imageUrl("https://image.com/app3")
                .installerUrl("https://storage.com/app3")
                .description("The third app")
                .version("1.0.0")
                .price(10000d)
                .verificationStatus(VerificationStatus.REJECTED)
                .verificationAdminId(1)
                .verificationDate(new Date())
                .build();
        appDataRepository.save(appData1);
        appDataRepository.save(appData2);
        appDataRepository.save(appData3);
    }

    @Test
    void getAppsByKeyword() {
        List<AppData> expectedList = new ArrayList<>();
        expectedList.add(appData1);
        expectedList.add(appData2);
        expectedList.add(appData3);

        Mockito
                .when(searchService.searchAppsByKeyword("app"))
                .thenReturn(expectedList);
        ResponseEntity<List<AppData>> response = searchController.getAppsByKeyword("app");
        List<AppData> appList = response.getBody();
        Assertions.assertEquals(expectedList, appList);
    }

}
