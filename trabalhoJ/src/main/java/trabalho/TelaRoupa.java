package trabalho;


import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import static javax.swing.JOptionPane.showMessageDialog;


public class TelaRoupa extends JFrame {
    private static final String CAMPO_1 = "Tipo de roupa";
    private static final String CAMPO_2 = "Marca";
    private static final String CAMPO_3 = "Tamanho ";
    private static final String CAMPO_4 = "Cor";
    private static final String CAMPO_5 = "Telefone";

    // variaveis do campo. campoV siginifca campo valor;
    private JTextField campoV1;
    private JTextField campoV2;
    private JTextField campoV3;
    private JTextField campoV4;
    private JTextField campoV5;
    private JButton botaoEnviar;
    private JButton botaoLimpar;
    private Boolean falha = Boolean.FALSE;

    public TelaRoupa() {
        setTitle("Tela de montagem de roupa");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        var labelValor1 = new JLabel(CAMPO_1);
        constraints.gridx = 0;
        constraints.gridy = 0;
        painel.add(labelValor1, constraints);

        campoV1 = new JTextField(10);
        constraints.gridx = 1;
        constraints.gridy = 0;
        painel.add(campoV1, constraints);

        var labelValor2 = new JLabel(CAMPO_2);
        constraints.gridx = 0;
        constraints.gridy = 1;
        painel.add(labelValor2, constraints);

        campoV2 = new JTextField(10);
        constraints.gridx = 1;
        constraints.gridy = 1;
        painel.add(campoV2, constraints);

        var labelValor3 = new JLabel(CAMPO_3);
        constraints.gridx = 0;
        constraints.gridy = 2;
        painel.add(labelValor3, constraints);

        campoV3 = new JTextField(10);
        constraints.gridx = 1;
        constraints.gridy = 2;
        painel.add(campoV3, constraints);

        var labelValor4 = new JLabel(CAMPO_4);
        constraints.gridx = 0;
        constraints.gridy = 3;
        painel.add(labelValor4, constraints);

        campoV4 = new JTextField(10);
        constraints.gridx = 1;
        constraints.gridy = 3;
        painel.add(campoV4, constraints);

        var labelValor5 = new JLabel(CAMPO_5);
        constraints.gridx = 0;
        constraints.gridy = 4;
        painel.add(labelValor5, constraints);

        campoV5 = new JTextField(10);
        constraints.gridx = 1;
        constraints.gridy = 4;
        painel.add(campoV5, constraints);

        botaoEnviar = new JButton("Cadastrar");
        botaoEnviar.addActionListener(e -> cadastro());
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 4;
        painel.add(botaoEnviar, constraints);

        botaoLimpar = new JButton("Limpar");
        botaoLimpar.addActionListener(e -> limparButton());
        constraints.gridx = 2;
        constraints.gridy = 5;
        constraints.gridwidth = 4;
        painel.add(botaoLimpar, constraints);


        add(painel);
        setLocationRelativeTo(null);

    }


    private boolean verificarString(String valorStr) {
        return valorStr.matches("[a-zA-Z\\s]+");
    }

    public String verificarVazio(String valorStr, String campo) {
        try {
            if (valorStr.isEmpty()) throw new RuntimeException("O valor do campo " + campo + " não pode ser vazio!");
            if (valorStr.isBlank())
                throw new RuntimeException("O valor do " + campo + " não pode ser espaços vazios no campo");
            if (!verificarString(valorStr))
                throw new RuntimeException("O valor do campo " + campo + " não é válido. Informe apenas texto.");

            return valorStr;
        } catch (RuntimeException e) {
            showMessageDialog(this, e.getMessage());
            this.falha = Boolean.TRUE;
        }
        return valorStr;
    }

    public Integer converter(String valorStr, String campo) {
        try {
            if (valorStr.isEmpty()) throw new RuntimeException("telefone " + campo + " não pode ser vazio!");
            if (valorStr.isBlank())
                throw new RuntimeException("O valor do " + campo + " não pode conter espaços vazios");

            return Integer.parseInt(valorStr);
        } catch (NumberFormatException n) {
            showMessageDialog(this, "Campo " + campo + " não é valido, informar números inteiros", "erro", JOptionPane.ERROR_MESSAGE);
            this.falha = Boolean.TRUE;
            return 0;
        }
    }

    private void cadastro() {
        try {
            var campo1 = verificarVazio(campoV1.getText(), CAMPO_1);
            var marca = verificarVazio(campoV2.getText(), CAMPO_2);
            var tamanho = verificarVazio(campoV3.getText(), CAMPO_3);
            var cor = verificarVazio(campoV4.getText(), CAMPO_4);

            if (this.falha) {
                this.falha = Boolean.FALSE;
                return;
            }
            var telefone = converter(campoV5.getText(), CAMPO_5);

            if (this.falha) {
                this.falha = Boolean.FALSE;
                return;
            }

            var resultado = "Tipo: " + campo1 +
                    ", Marca: " + marca +
                    ", Tamanho: " + tamanho + "Cor: " + cor + ", Telefone: " + telefone;


            String message = "Deseja cadastrar?";
            String title = "Confirmação";
            int mostrarOption = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);

            if (mostrarOption == JOptionPane.YES_OPTION) {
                salvar(resultado);
                showMessageDialog(this, "Cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limpar();
            } else {
                showMessageDialog(this, "Erro ao cadastrar!", "Erro", JOptionPane.ERROR_MESSAGE);
                limpar();
            }
        } catch (RuntimeException e) {
            showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpar() {
        campoV1.setText("");
        campoV2.setText("");
        campoV3.setText("");
        campoV4.setText("");
        campoV5.setText("");
    }

    private void limparButton() {
        campoV1.setText("");
        campoV2.setText("");
        campoV3.setText("");
        campoV4.setText("");
        campoV5.setText("");
        showMessageDialog(this, "Limpo !!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void salvar(String valor) {
        var diretorioProjeto = System.getProperty("user.dir");
        var nomeArquivo = "//Cadastro.txt";
        var arquivo = new File(diretorioProjeto, nomeArquivo);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
            writer.newLine();
            writer.write(valor);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}


