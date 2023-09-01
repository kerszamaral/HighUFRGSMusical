package main.implementacao;

import java.util.ArrayList;

public class Piano extends Instrumento {

    public Piano() {
        super("piano");
    }

    public boolean tocar(ArrayList<Tecla> teclas) {

        boolean estaTocando = false;
        for (Tecla tecla : teclas) {
            boolean teclaFoiPressionada = notas.containsKey(tecla);
            if (teclaFoiPressionada) {
                if (!teclasFrameAnterior.contains(tecla)){
                    notas.get(tecla).tocarNota();
                    estaTocando = true;
                }
            }
        }
        for (Tecla tecla : teclasFrameAnterior) {

            boolean teclaPressionadaFrameAnterior = !teclas.contains(tecla) && notas.containsKey(tecla);

            if (teclaPressionadaFrameAnterior) {
                notas.get(tecla).pararNota();
            }
        }
        teclasFrameAnterior = teclas;
        return estaTocando;
    }

    public Piano PianoCopy() {
        this.notas = loadNotas("piano");
        this.teclasFrameAnterior = new ArrayList<Tecla>();
        return this;

    }
}
