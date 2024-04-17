package trabalho;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()-> {
            var tela = new TelaRoupa();
            tela.setVisible(true);
        });
    }
}