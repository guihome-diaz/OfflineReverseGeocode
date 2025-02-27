package eu.daxiongmao.geocode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Unit tests for reverse-geocoding library
 * @author gdi
 * @since PORTAL R10 (2019-07)
 */
public class ReverseGeoCodeTest {

	private static final Set<String> COUNTRIES_TO_KEEP = new HashSet<String>(Arrays.asList(new String[] {"DE", "FR", "LU"}));

	private static final int CITIES_LUXEMBOURG_JULY_2019 = 1210;

	@Test
	public void testLoadLuTxtFileAll() throws URISyntaxException, FileNotFoundException, IOException {
		// Get input file
		final File inputTxtFile = new File(ReverseGeoCode.class.getClassLoader().getResource("LU.txt").toURI());
		Assertions.assertNotNull(inputTxtFile);
		Assertions.assertTrue(inputTxtFile.exists());
		Assertions.assertTrue(inputTxtFile.isFile());

		// Init DB
		final ReverseGeoCode reverseGeoCodeUtil = new ReverseGeoCode(inputTxtFile.toPath(), false, COUNTRIES_TO_KEEP);
		Assertions.assertNotNull(reverseGeoCodeUtil);
		Assertions.assertNotNull(reverseGeoCodeUtil.kdTree);
		Assertions.assertTrue(reverseGeoCodeUtil.nbCitiesLoaded > 1);
		System.out.println("Luxembourg LU.txt file => " + reverseGeoCodeUtil.nbCitiesLoaded + " cities loaded in-memory DB");
	}

	@Test
	public void testLoadLuTxtFileMajorCountryFilter() throws URISyntaxException, FileNotFoundException, IOException {
		// Get input file
		final File inputTxtFile = new File(ReverseGeoCode.class.getClassLoader().getResource("LU.txt").toURI());
		Assertions.assertNotNull(inputTxtFile);
		Assertions.assertTrue(inputTxtFile.exists());
		Assertions.assertTrue(inputTxtFile.isFile());

		// Init DB
		final ReverseGeoCode reverseGeoCodeUtil = new ReverseGeoCode(inputTxtFile.toPath(), true, COUNTRIES_TO_KEEP);
		Assertions.assertNotNull(reverseGeoCodeUtil);
		Assertions.assertNotNull(reverseGeoCodeUtil.kdTree);
		Assertions.assertTrue(reverseGeoCodeUtil.nbCitiesLoaded > 1);
		// Expect all cities because of the country filter
		System.out.println("Luxembourg LU.txt file => " + reverseGeoCodeUtil.nbCitiesLoaded + " cities loaded in-memory DB");
	}

	@Test
	public void testLoadLuTxtFileMajorNoCountry() throws URISyntaxException, FileNotFoundException, IOException {
		// Get input file
		final File inputTxtFile = new File(ReverseGeoCode.class.getClassLoader().getResource("LU.txt").toURI());
		Assertions.assertNotNull(inputTxtFile);
		Assertions.assertTrue(inputTxtFile.exists());
		Assertions.assertTrue(inputTxtFile.isFile());

		// Init DB
		final ReverseGeoCode reverseGeoCodeUtil = new ReverseGeoCode(inputTxtFile.toPath(), true, new HashSet<String>());
		Assertions.assertNotNull(reverseGeoCodeUtil);
		Assertions.assertNotNull(reverseGeoCodeUtil.kdTree);
		Assertions.assertTrue(reverseGeoCodeUtil.nbCitiesLoaded > 1);
		// Expect all cities because of the country filter
		Assertions.assertTrue(CITIES_LUXEMBOURG_JULY_2019 > reverseGeoCodeUtil.nbCitiesLoaded);
		System.out.println("Luxembourg LU.txt # majors cities only # file => " + reverseGeoCodeUtil.nbCitiesLoaded + " cities loaded in-memory DB");
	}

	@Test
	public void testLoadLuZipFile() throws URISyntaxException, FileNotFoundException, IOException {
		// Get input file
		final File inputZipFile = new File(ReverseGeoCode.class.getClassLoader().getResource("LU.zip").toURI());
		Assertions.assertNotNull(inputZipFile);
		Assertions.assertTrue(inputZipFile.exists());
		Assertions.assertTrue(inputZipFile.isFile());

		// Init DB
		final ReverseGeoCode reverseGeoCodeUtil = new ReverseGeoCode(inputZipFile.toPath(), false, COUNTRIES_TO_KEEP);
		Assertions.assertNotNull(reverseGeoCodeUtil);
		Assertions.assertNotNull(reverseGeoCodeUtil.kdTree);
		Assertions.assertTrue(reverseGeoCodeUtil.nbCitiesLoaded > 1);
		System.out.println("Luxembourg LU.zip file => " + reverseGeoCodeUtil.nbCitiesLoaded + " cities loaded in-memory DB");
	}

	@Test
	public void testLoadCities1000MajorCities() throws URISyntaxException, FileNotFoundException, IOException {
		// Get input file
		final File inputZipFile = new File(ReverseGeoCode.class.getClassLoader().getResource("cities1000.zip").toURI());
		Assertions.assertNotNull(inputZipFile);
		Assertions.assertTrue(inputZipFile.exists());
		Assertions.assertTrue(inputZipFile.isFile());

		// Init DB
		final ReverseGeoCode reverseGeoCodeUtil = new ReverseGeoCode(inputZipFile.toPath(), true, COUNTRIES_TO_KEEP);
		Assertions.assertNotNull(reverseGeoCodeUtil);
		Assertions.assertNotNull(reverseGeoCodeUtil.kdTree);
		Assertions.assertTrue(reverseGeoCodeUtil.nbCitiesLoaded > 1);
		System.out.println("Cities1000.zip # majors cities only # file => " + reverseGeoCodeUtil.nbCitiesLoaded + " cities loaded in-memory DB");
	}

	@Test
	public void testLoadCities1000AllCities() throws URISyntaxException, FileNotFoundException, IOException {
		// Get input file
		final String inputZipFilePath = new File(ReverseGeoCode.class.getClassLoader().getResource("cities1000.zip").toURI()).toString();
		final Path inputZipFile = Paths.get(inputZipFilePath);
		Assertions.assertNotNull(inputZipFile);
		Assertions.assertTrue(Files.exists(inputZipFile));
		Assertions.assertFalse(Files.isDirectory(inputZipFile));

		// Init DB
		final ReverseGeoCode reverseGeoCodeUtil = new ReverseGeoCode(inputZipFile, false, COUNTRIES_TO_KEEP);
		Assertions.assertNotNull(reverseGeoCodeUtil);
		Assertions.assertNotNull(reverseGeoCodeUtil.kdTree);
		Assertions.assertTrue(reverseGeoCodeUtil.nbCitiesLoaded > 1);
		System.out.println("Cities1000.zip # all cities # file => " + reverseGeoCodeUtil.nbCitiesLoaded + " cities loaded in-memory DB");
	}

	@Test
	public void testLoadCities1000AndLuxembourgAllCities() throws URISyntaxException, FileNotFoundException, IOException {
		// Get input file
		final File cities1000 = new File(ReverseGeoCode.class.getClassLoader().getResource("cities1000.zip").toURI());
		final File luxembourg = new File(ReverseGeoCode.class.getClassLoader().getResource("LU.zip").toURI());
		final List<Path> files = Arrays.asList(cities1000.toPath(), luxembourg.toPath());

		// Init DB
		final ReverseGeoCode reverseGeoCodeUtil = new ReverseGeoCode(files, false, COUNTRIES_TO_KEEP);
		Assertions.assertNotNull(reverseGeoCodeUtil);
		Assertions.assertNotNull(reverseGeoCodeUtil.kdTree);
		Assertions.assertTrue(reverseGeoCodeUtil.nbCitiesLoaded > 1);
		System.out.println("Cities1000.zip + LU.zip # all cities # 2 files => " + reverseGeoCodeUtil.nbCitiesLoaded + " cities loaded in-memory DB");
	}

	@Test
	public void testLoadCities1000AndLuxembourgAllCitiesAndBEmajors() throws URISyntaxException, FileNotFoundException, IOException {
		// Get input file
		final File cities1000 = new File(ReverseGeoCode.class.getClassLoader().getResource("cities1000.zip").toURI());
		final File luxembourg = new File(ReverseGeoCode.class.getClassLoader().getResource("LU.zip").toURI());
		final File belgium = new File(ReverseGeoCode.class.getClassLoader().getResource("BE.zip").toURI());
		final List<Path> files = Arrays.asList(cities1000.toPath(), luxembourg.toPath(), belgium.toPath());

		// Init DB
		final ReverseGeoCode reverseGeoCodeUtil = new ReverseGeoCode(files, true, COUNTRIES_TO_KEEP);
		Assertions.assertNotNull(reverseGeoCodeUtil);
		Assertions.assertNotNull(reverseGeoCodeUtil.kdTree);
		Assertions.assertTrue(reverseGeoCodeUtil.nbCitiesLoaded > 1);
		System.out.println("Cities1000.zip + LU.zip + BE.zip # all cities # 3 files => " + reverseGeoCodeUtil.nbCitiesLoaded + " cities loaded in-memory DB");
	}


	@Test
	public void testReverseGeoLocation() throws URISyntaxException, IOException {
		// Get input file
		final File cities1000 = new File(ReverseGeoCode.class.getClassLoader().getResource("cities1000.zip").toURI());
		final List<Path> files = Arrays.asList(cities1000.toPath());

		// Init DB
		final ReverseGeoCode reverseGeoCodeUtil = new ReverseGeoCode(files, false, COUNTRIES_TO_KEEP);
		Assertions.assertNotNull(reverseGeoCodeUtil);
		Assertions.assertNotNull(reverseGeoCodeUtil.kdTree);
		Assertions.assertTrue(reverseGeoCodeUtil.nbCitiesLoaded > 1);

		// expect: Luxembourg [LU]
		double latitude = 49.615267;
		double longitude = 6.120112;
		GeoName closestCity = reverseGeoCodeUtil.nearestPlace(latitude, longitude);
		Assertions.assertNotNull(closestCity);
		Assertions.assertEquals("Luxembourg [LU]", closestCity.toString());

		// expect: Capellen [LU]
		latitude = 49.642314;
		longitude = 6.007225;
		closestCity = reverseGeoCodeUtil.nearestPlace(latitude, longitude);
		Assertions.assertNotNull(closestCity);
		Assertions.assertEquals("Capellen [LU]", closestCity.toString());

		// Following coordinates are in France
		latitude = 46.033934;
		longitude = 3.917356;
		closestCity = reverseGeoCodeUtil.nearestPlace(latitude, longitude);
		Assertions.assertNotNull(closestCity);
		Assertions.assertEquals("Renaison [FR]", closestCity.toString());
	}
}
