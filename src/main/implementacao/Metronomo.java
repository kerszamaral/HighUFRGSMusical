package main.implementacao;

import main.programaPrincipal.ProgramaPrincipal;

public class Metronomo {
    private Nota low;
    private Nota high;
    private int bpm;
    private int tempo;
    private boolean estaTocando;
    private boolean tocarHigh;

    public Metronomo(int bpm) {
        this.low = new Nota("low", "metronomo");
        this.high = new Nota("high", "metronomo");
        this.bpm = bpm;
        this.tempo = 0;
        this.estaTocando = false;
    }

    public boolean update() {
        if (!this.estaTocando) {
            return false;
        }

        final int modulo = ProgramaPrincipal.TARGET_FPS * 60 / this.bpm;
        this.tempo = (tempo + 1) % modulo;
        if (this.tempo != 0) {
            return false;
        }

        this.tocarHigh = !this.tocarHigh;

        if (this.tocarHigh) {
            this.high.tocarNota();
        } else {
            this.low.tocarNota();
        }

        return true;
    }

    public void começar() {
        this.tempo = 0;
        this.estaTocando = true;
        this.tocarHigh = false;
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
