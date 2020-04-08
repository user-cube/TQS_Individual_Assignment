package pt.ua.tqs.airquality.tools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = CityInfo.class)
class CityInfoTest {

    private CityInfo cityInfo;
    private static final String cityURL = "http://api.ipma.pt/open-data/distrits-islands";

    @BeforeEach
    public void setUp() {
        cityInfo = new CityInfo();
    }

    @Test
    public void coordinatesFromGoodCityTest() {
        String [] coordinates = new String[2];
        coordinates[0] = "40.6405";
        coordinates[1] = "-8.6538";
        assertArrayEquals(coordinates, cityInfo.coordinatesFromName("Aveiro"));
    }

    @Test
    public void coordinatesFromBadCityTest() {
        assertArrayEquals(null, cityInfo.coordinatesFromName("Aveiras"));
    }

    @Test
    public void getCodeFromGoodCityTest() {
        assertEquals("1010500", cityInfo.getCityCode("Aveiro", cityURL));
    }

    @Test
    public void getCodeFromBadCityTest() {
        assertEquals(null, cityInfo.getCityCode("Aveiras", cityURL));
    }
}