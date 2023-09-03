package main.implementacao;

import main.programaPrincipal.ProgramaPrincipal;

public class Metronomo {
    private Nota low;
    private Nota high;
    private int bpm;
    private int tempo;
    private int contagem;
    private boolean estaTocando;

    public Metronomo(int bpm) {
        this.low = new Nota("low", "metronomo");
        this.high = new Nota("high", "metronomo");
        this.bpm = bpm;
        this.tempo = 0;
        this.estaTocando = false;
        this.contagem = 0;
    }

    public boolean update() {
        if (!this.estaTocando) {
            return false;
        }

        final int MODULO = ProgramaPrincipal.TARGET_FPS * 60 / this.bpm;
        this.tempo = (tempo + 1) % MODULO;
        if (this.tempo != 0) {
            return false;
        }

        if (this.contagem == 0) {
            this.high.tocarNota();
        } else {
            this.low.tocarNota();
        }
        this.contagem = (this.contagem + 1) % 4;

        return true;
    }

    public void começar() {
        this.tempo = 0;
        this.estaTocando = true;
        this.contagem = 0;
    }

    public void parar() {
        this.estaTocando = false;
    }

    public boolean setBpm(int bpm) {
        if (bpm < 0) {
            return false;
        }
        this.bpm = bpm;
        return true;
    }

    public int getBpm() {
        return this.bpm;
    }

    public boolean estaTocando() {
        return estaTocando;
    }
}
