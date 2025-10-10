import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculadoraGUI extends JFrame implements ActionListener{
    private JTextField display;
    private String operador = "";
    private double num1 = 0, num2 = 0;

    public CalculadoraGUI() {
        setTitle("Calculadora");
        setSize(300, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        display = new JTextField();
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4));

        String[] botoes = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };

        for (String txt : botoes) {
            JButton btn = new JButton(txt);
            btn.addActionListener(this);
            panel.add(btn);
        }

        add(panel, BorderLayout.CENTER);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if("0123456789".contains(cmd)) {
            display.setText(display.getText() + cmd);
        } else if("+-*/".contains(cmd)) {
            num1 = Double.parseDouble(display.getText());
            operador = cmd;
            display.setText("");
        } else if ("=".equals(cmd)) {
            num2 = Double.parseDouble(display.getText());
            double resultado = 0;

            switch(operador) {
                case "+": resultado = num1 + num2; break;
                case "-": resultado = num1 - num2; break;
                case "*": resultado = num1 * num2; break;
                case "/": resultado = num2 == 0 ? Double.NaN : num1 / num2; break;
            }

            display.setText("" + resultado);
        } else if ("C".equals(cmd)) {
            display.setText("");
            num1 = num2 = 0;
            operador = "";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculadoraGUI().setVisible(true));
    }

}