package main.implementacao;

import java.util.ArrayList;

public class Baixo extends Instrumento {
    private static final Tecla PALHETA_KEY = new Tecla(257, false);

    public Baixo() {
        super("baixo");
    }

    public boolean tocar(ArrayList<Tecla> teclas) {
        
        boolean estaTocando = false;
        boolean palhetaFoiPressionada=teclas.contains(PALHETA_KEY) && !teclasFrameAnterior.contains(PALHETA_KEY);
        
        if (palhetaFoiPressionada){
            for (Tecla tecla : teclas) {
                if (notas.containsKey(tecla)) {
                    notas.get(tecla).pararNota();
                    notas.get(tecla).tocarNota();
                    estaTocando = true;
                }
            }
        }

        for (Tecla tecla : teclasFrameAnterior) {

            boolean teclaFoiDespressionada=!teclas.contains(tecla) && notas.containsKey(tecla);
            
            if (teclaFoiDespressionada) {
                notas.get(tecla).pararNota();
            }
        }

        teclasFrameAnterior = teclas;
        return estaTocando;
    }


    public Baixo baixoCopy() {
        this.notas = loadNotas("baixo");
        this.teclasFrameAnterior = new ArrayList<Tecla>();
        return this;
    }
}
