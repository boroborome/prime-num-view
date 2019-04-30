package com.boroborome.prime;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@SpringBootApplication
public class PrimeNumApplication extends JFrame {
    private PrimePane primePane = new PrimePane();
    private JTextField txtR;
    private JTextField txtArc;

    public PrimeNumApplication() {
        initUI();
    }

    private void initUI() {
        rootPane.setLayout(new GridBagLayout());

        rootPane.add(createButtonPane(), new GridBagConstraints(0, 0,
            1, 1,
            1, 0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
            new Insets(8, 8, 0, 8),
            0, 0));

        rootPane.add(primePane, new GridBagConstraints(0, 1,
            1, 1,
            1, 1,
            GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
            new Insets(8, 8, 8, 8),
            0, 0));

        setTitle("Quit button");
        setSize(300, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private JPanel createButtonPane() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        int column = 0;

        panel.add(new JLabel("R:"), new GridBagConstraints(column++, 0,
            1, 1,
            0, 0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
            new Insets(0, 0, 0, 8),
            0, 0));
        panel.add(getTxtR(), new GridBagConstraints(column++, 0,
            1, 1,
            1, 0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
            new Insets(0, 0, 0, 8),
            0, 0));

        panel.add(new JLabel("Arc:"), new GridBagConstraints(column++, 0,
            1, 1,
            0, 0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
            new Insets(0, 0, 0, 8),
            0, 0));
        panel.add(getTxtArc(), new GridBagConstraints(column++, 0,
            1, 1,
            1, 0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
            new Insets(0, 0, 0, 0),
            0, 0));
        return panel;
    }

    private JTextField getTxtR() {
        if (txtR == null) {
            txtR = new JTextField();
            txtR.setText("1");
            txtR.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        primePane.setR(Double.parseDouble(txtR.getText()));
                        primePane.repaint();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });
        }
        return txtR;
    }

    private JTextField getTxtArc() {
        if (txtArc == null) {
            txtArc = new JTextField();
            txtArc.setText("1");
            txtArc.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        primePane.setArc(Math.PI * Double.parseDouble(txtArc.getText()));
                        primePane.repaint();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });
        }
        return txtArc;
    }

    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(PrimeNumApplication.class)
            .headless(false).run(args);

        EventQueue.invokeLater(() -> {
            PrimeNumApplication ex = ctx.getBean(PrimeNumApplication.class);
            ex.setVisible(true);
        });
    }

}
