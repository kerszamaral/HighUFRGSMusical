package main.interfaceGrafica;

import java.util.ArrayList;

import main.implementacao.Metronomo;
import main.implementacao.Tecla;
import main.programaPrincipal.ProgramaPrincipal;

import com.raylib.Jaylib;
import com.raylib.Jaylib.Vector2;

public class SliderMetronomo {

    // Path
    private final String pathUI = "./assets/ui/";
    private final String pathModoClaro = pathUI + "modo_claro/slider_volume/";
    private final String pathModoEscuro = pathUI + "modo_escuro/slider_volume/";

    // Texturas
    private Jaylib.Texture2D barraClaro;
    private Jaylib.Texture2D barraEscuro;
    private Jaylib.Texture2D bolaClaro;
    private Jaylib.Texture2D bolaEscuro;

    // Teclas de controle
    private final Tecla teclaAumentaVel = new Tecla(Jaylib.KEY_RIGHT, false);
    private final Tecla teclaDiminuiVel = new Tecla(Jaylib.KEY_LEFT, false);
    private final Tecla teclaParaComeca = new Tecla(Jaylib.KEY_BACKSPACE, false);
    private final Tecla teclaReinicia = new Tecla(Jaylib.KEY_DELETE, false);
    private boolean soltouTeclaPC;

    // Funcionalidades de metronomo
    private final int bpmInicial = 120;
    private int bpmAtual;
    private final int bpmMaximo = 200;
    private final int bpmMinimo = 40;
    private final Metronomo metronomo;

    // Posições
    private Vector2 posicaoBola;
    private final int offsetBarraX = 4000;
    private final int offsetBarraY = 80;
    private final Vector2 POSICAO_BARRA = Vector2Escalado(offsetBarraX, offsetBarraY);
    private final int offsetBolaX = 4000 - 10;
    private final int offsetBolaY = 585;
    private final float rateScaler = 9.3f;
    private final float rateOfChange = ((bpmInicial) / (float) (bpmMaximo - bpmMinimo)) * rateScaler;

    public SliderMetronomo() {
        this.posicaoBola = Vector2Escalado(offsetBolaX, offsetBolaY);

        this.barraClaro = Jaylib.LoadTexture(pathModoClaro + "barra_volume.png");
        this.bolaClaro = Jaylib.LoadTexture(pathModoClaro + "bola_claro.png");
        this.barraEscuro = Jaylib.LoadTexture(pathModoEscuro + "barra_volume.png");
        this.bolaEscuro = Jaylib.LoadTexture(pathModoEscuro + "bola_escuro.png");

        soltouTeclaPC = true;

        this.bpmAtual = bpmInicial;
        this.metronomo = new Metronomo(bpmAtual);
    }

    private Vector2 Vector2Escalado(float x, float y) {
        return new Jaylib.Vector2(x * ProgramaPrincipal.ESCALA, y * ProgramaPrincipal.ESCALA);
    }

    private void desenhaTextura(Jaylib.Texture2D textura, Vector2 posicao) {
        Jaylib.DrawTextureEx(textura, posicao, 0, ProgramaPrincipal.ESCALA, Jaylib.WHITE);
    }

    private void desenhaTexto(String texto, Vector2 posicao, Jaylib.Color cor) {
        final float tamanhoFonte = 80 * ProgramaPrincipal.ESCALA;
        Jaylib.DrawTextEx(InterfaceGrafica.getFonte(), texto, posicao, tamanhoFonte, 0, cor);
    }

    public void desenha(boolean modoEscuro) {
        final Jaylib.Texture2D barra = modoEscuro ? barraEscuro : barraClaro;
        desenhaTextura(barra, POSICAO_BARRA);

        final Jaylib.Texture2D bola = modoEscuro ? bolaEscuro : bolaClaro;
        desenhaTextura(bola, posicaoBola);

        final String texto = (int) (bpmAtual) + " BPM";
        final Jaylib.Color corTexto = metronomo.estaTocando() ? Jaylib.GREEN : Jaylib.RED;
        final Vector2 posicaoTexto = Vector2Escalado(offsetBarraX - 30, offsetBarraY + 1250);
        desenhaTexto(texto, posicaoTexto, corTexto);

        metronomo.update();
    }

    private void reiniciaBPM() {
        this.bpmAtual = bpmInicial;
        metronomo.setBpm(bpmAtual);
        this.posicaoBola = Vector2Escalado(offsetBolaX, offsetBolaY);
    }

    private void mudaBPM(int change) {
        this.bpmAtual += change;

        if (bpmAtual > bpmMaximo) {
            bpmAtual = bpmMaximo;
        } else if (bpmAtual < bpmMinimo) {
            bpmAtual = bpmMinimo;
        }

        metronomo.setBpm(bpmAtual);

        final boolean isExtreme = bpmAtual == bpmMaximo || bpmAtual == bpmMinimo;
        if (isExtreme) {
            return;
        }

        final float offset = -rateOfChange * change * ProgramaPrincipal.ESCALA;
        this.posicaoBola.y(posicaoBola.y() + offset);
    }

    private void mudaEstado() {
        if (soltouTeclaPC) {
            if (metronomo.estaTocando()) {
                metronomo.parar();
            } else {
                metronomo.começar();
            }
            soltouTeclaPC = false;
        }
    }

    public void alterouBPM(ArrayList<Tecla> teclas) {
        boolean detectouTeclaPC = false;

        for (Tecla tecla : teclas) {
            if (tecla.equals(teclaAumentaVel)) {
                mudaBPM(1);
            } else if (tecla.equals(teclaDiminuiVel)) {
                mudaBPM(-1);
            } else if (tecla.equals(teclaParaComeca)) {
                mudaEstado();
                detectouTeclaPC = true;
            } else if (tecla.equals(teclaReinicia)) {
                reiniciaBPM();
            }
        }

        if (!detectouTeclaPC) {
            soltouTeclaPC = true;
        }
    }
}
