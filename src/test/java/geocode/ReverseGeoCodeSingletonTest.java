package geocode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class ReverseGeoCodeSingletonTest {

	private static final Set<String> COUNTRIES_TO_KEEP = new HashSet<String>(Arrays.asList(new String[] {"BE", "DE", "FR", "LU"}));

	/** Init in-memory DB */
	private static ReverseGeoCode reverseGeoCodeUtil;

	@BeforeClass
	public static void setup() throws FileNotFoundException, URISyntaxException, Exception {
		final File cities1000 = new File(ReverseGeoCode.class.getClassLoader().getResource("cities1000.zip").toURI());
		reverseGeoCodeUtil = new ReverseGeoCode(cities1000.toPath(), false, COUNTRIES_TO_KEEP);
	}

	@Test
	public void testLoadCities1000() throws URISyntaxException, FileNotFoundException, IOException {
		// Check DB
		Assert.assertNotNull(reverseGeoCodeUtil);
		Assert.assertNotNull(reverseGeoCodeUtil.kdTree);
		Assert.assertTrue(reverseGeoCodeUtil.nbCitiesLoaded > 1);
		System.out.println("Cities1000.zip # majors cities only # file => " + reverseGeoCodeUtil.nbCitiesLoaded + " cities loaded in-memory DB");
	}

	@Ignore
	@Test
	public void testReverseGeoLocation() {
		// Following coordinate are based on LuxTrust headquarters on 2019-07-22
		// data is coming from LuxTrust mobile app Android v3.0.0
		// TODO
	}
}
