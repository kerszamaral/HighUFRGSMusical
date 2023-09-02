package tests;

import main.implementacao.Metronomo;
import main.programaPrincipal.ProgramaPrincipal;
import static org.junit.Assert.*;
import org.junit.*;

public class MetronomoTests {
    private Metronomo metronomo;
    private final int DEFAULT_BPM = 120;

    @Before
    public void init() {
        this.metronomo = new Metronomo(DEFAULT_BPM);
    }

    @Test
    public void testMetronomo() {
        assertNotNull(metronomo);
    }

    @Test
    public void começarTest() {
        metronomo.começar();
        assertEquals(true, metronomo.estaTocando());
    }

    @Test
    public void pararTest() {
        metronomo.começar();
        metronomo.parar();
        assertEquals(false, metronomo.estaTocando());
    }

    @Test
    public void setBpmTest() {
        final int NEW_BPM = 60;
        assertTrue(metronomo.setBpm(NEW_BPM));
        assertEquals(NEW_BPM, metronomo.getBpm());
    }

    @Test
    public void setBpmTest2() {
        assertFalse(metronomo.setBpm(-1));
        assertEquals(DEFAULT_BPM, metronomo.getBpm());
    }

    @Test
    public void getBpmTest() {
        assertEquals(DEFAULT_BPM, metronomo.getBpm());
    }

    private void tocarNTimes(int n) {
        for (int i = 0; i < n; i++) {
            metronomo.update();
        }
    }

    private void tocarHalfSecond() {
        tocarNTimes(ProgramaPrincipal.TARGET_FPS / 2);
    }

    private void tocar1Second() {
        tocarHalfSecond();
        tocarHalfSecond();
    }

    @Test
    public void tocarTest() {
        metronomo.começar();
        metronomo.update();
        assertFalse(metronomo.update());
    }

    @Test
    public void tocarTest2() {
        metronomo.setBpm(60);
        metronomo.começar();
        tocarNTimes(ProgramaPrincipal.TARGET_FPS - 1);
        assertTrue(metronomo.update());
    }

    @Test
    public void tocarTest3() {
        metronomo.setBpm(30);
        metronomo.começar();
        tocar1Second();
        assertFalse(metronomo.update());
    }
}
