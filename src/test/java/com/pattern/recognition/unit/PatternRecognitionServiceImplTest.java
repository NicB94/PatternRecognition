package com.pattern.recognition.unit;

import com.pattern.recognition.data.Point;
import com.pattern.recognition.exception.InvalidParametersException;
import com.pattern.recognition.service.impl.PatternRecognitionServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PatternRecognitionServiceImplTest {

    Point x;
    Point y;
    Point z;
    @InjectMocks
    PatternRecognitionServiceImpl patternRecognitionService;

    private void init() throws IOException {

        patternRecognitionService = new PatternRecognitionServiceImpl();
        assertNotNull(patternRecognitionService);
    }

    @BeforeEach
    void setUp() throws IOException {
        init();

        x = new Point(1, 1);
        y = new Point(1, 2);
        z = new Point(1, 3);
        ReflectionTestUtils.setField(
                PatternRecognitionServiceImpl.class, "plane", new ArrayList<>());
    }

    @Test
    @DisplayName("whenDeleteIsCalled_expectThatPlaneIsEmpty")
    void whenDeleteIsCalled_expectThatPlaneIsEmpty(){

        List<Point> plane = new ArrayList<>();
        plane.add(x);
        ReflectionTestUtils.setField(
                PatternRecognitionServiceImpl.class, "plane", plane);
        patternRecognitionService.delete();
        Assertions.assertTrue(plane.isEmpty());
    }

    @Test
    @DisplayName("givenValidCoordinatesValue_thenInsertPointIntoThePlane")
    void whenValidCoordinatesAreProvided_thenInsertPointIntoThePlane(){

        patternRecognitionService.addPoint(x);
        List<Point> plane = (List<Point>) ReflectionTestUtils.getField(PatternRecognitionServiceImpl.class, "plane");
        Assertions.assertTrue(plane.contains(x));
    }

    @Test
    @DisplayName("givenInvalidCoordinatesValue_thenThrowsInvalidParametersException")
    void whenInvalidCoordinatesAreProvided_thenThrowsInvalidParametersException(){

        Point invalidPoint = new Point(null, -1);
        Assertions.assertThrows(InvalidParametersException.class, () -> patternRecognitionService.addPoint(invalidPoint));
    }


    @Test
    @DisplayName("whenGetPlaneIsCalled_thenAllCoordinatesAreReturned")
    void whenGetPlaneIsCalled_thenAllCoordinatesAreReturned(){

        List<Point> plane = new ArrayList<>();
        plane.add(x);
        plane.add(y);
        plane.add(z);

        ReflectionTestUtils.setField(
                PatternRecognitionServiceImpl.class, "plane", plane);

        List<Point> pointList = patternRecognitionService.getPoints();
        Assertions.assertTrue(pointList.size() == 3);
    }

    @Test
    @DisplayName("givenAValidNValue_thenReturnPointsRelatedToLinesWithLengthGreaterOrEqualsThanN")
    void whenNIsValidAndTherAreThreeCollinearPoints_thenReturnPointsRelatedToLinesWithLengthGreaterOrEqualsThanN(){
        List<Point> plane = new ArrayList<>();
        plane.add(x);
        plane.add(y);
        plane.add(z);

        ReflectionTestUtils.setField(
                PatternRecognitionServiceImpl.class, "plane", plane);

        List<List<Point>> lines = patternRecognitionService.getLinesWithLengthEqualsOrGreaterThanN(2);
        Assertions.assertFalse(lines.isEmpty());
    }

    @Test
    @DisplayName("givenAnInvalidNValue_thenThrowsInvalidParametersException")
    void givenAnInvalidNValue_thenThrowsInvalidParametersException(){
        Integer n = -1;
        Assertions.assertThrows(InvalidParametersException.class, () -> patternRecognitionService.getLinesWithLengthEqualsOrGreaterThanN(n));
    }
}
