package ohtu.ohtuvarasto;

import org.junit.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class VarastoTest {

    Varasto varasto;
    double vertailuTarkkuus = 0.0001;

    public void setUp() {
        varasto = new Varasto(10);
    }

    public void setUp(double tilavuus) {
        varasto = new Varasto(tilavuus);
    }

    public void setUp(double tilavuus, double alkuSaldo) {
        varasto = new Varasto(tilavuus, alkuSaldo);
    }

    @Test
    public void konstruktoriLuoTyhjanVarastonOikeallaTilavuudella() {
        setUp();
        assertEquals(10, varasto.getTilavuus(), vertailuTarkkuus);
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void konstruktoriAlkuSaldollaLuoVarastonOikeallaTilavuudellaJaSaldolla() {
        setUp(10, 8);
        assertEquals(10, varasto.getTilavuus(), vertailuTarkkuus);
        assertEquals(8, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void uudenVarastonNegatiivinenTilavuusNollautuu() {
        setUp(-0.0001);
        assertEquals(0, varasto.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void uudenAlkuSaldollaLuodunVarastonNegatiivinenTilavuusNollaaTilavuudenJaSaldon() {
        setUp(-0.0001, 8);
        assertEquals(0, varasto.getTilavuus(), vertailuTarkkuus);
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void uudenVarastoneNegatiivinenAlkuSaldoNollautuu() {
        setUp(10, -0.0001);
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void uudenVarastonAlkuSaldoEiVoiYlittaaTilavuutta() {
        setUp(10, 10.0001);
        assertEquals(10, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void lisaysKasvattaaSaldoaAnnetunMaaranKunTilaEiLopu() {
        setUp();
        varasto.lisaaVarastoon(8);

        // saldon pitäisi olla sama kun lisätty määrä
        assertEquals(8, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void lisaysTayttaaVarastonJosTilaLoppuu() {
        setUp();
        varasto.lisaaVarastoon(10.0001);

        // saldon pitäisi olla tilavuus
        assertEquals(10, varasto.getSaldo(), vertailuTarkkuus);
        assertEquals(varasto.getSaldo(), varasto.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void negatiivisenMaaranLisaysEiKasvataSaldoa() {
        setUp();
        varasto.lisaaVarastoon(-0.0001);
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaPienentaaVapaataTilaa() {
        setUp(10, 8);

        // vapaata tilaa pitäisi vielä olla tilavuus-lisättävä määrä eli 2
        assertEquals(2, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void ottaminenPalauttaaOikeanMaaran() {
        setUp(10, 8);
        double saatuMaara = varasto.otaVarastosta(2);
        assertEquals(2, saatuMaara, vertailuTarkkuus);
    }

    @Test
    public void ottaminenYliSaldonPalauttaaKaikenSaldon() {
        setUp(10, 8);
        double saatuMaara = varasto.otaVarastosta(8.0001);
        assertEquals(8, saatuMaara, vertailuTarkkuus);
        assertEquals(varasto.getTilavuus(), varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void negatiivisenMaaranOttaminenPalauttaaNollamaaran() {
        setUp(10, 8);
        double saatuMaara = varasto.otaVarastosta(-0.0001);
        assertEquals(0, saatuMaara, vertailuTarkkuus);
    }

    @Test
    public void ottaminenLisääTilaa() {
        setUp();
        varasto.lisaaVarastoon(8);
        varasto.otaVarastosta(2);

        // varastossa pitäisi olla tilaa 10 - 8 + 2 eli 4
        assertEquals(4, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void varastonToStringToimii() {
        double tilavuus = 10;
        double alkuSaldo = 8;
        setUp(tilavuus, alkuSaldo);
        String varastoString = "saldo = " + alkuSaldo + ", vielä tilaa " + (tilavuus - alkuSaldo);
        assertEquals(varasto.toString(), varastoString);
    }
}
