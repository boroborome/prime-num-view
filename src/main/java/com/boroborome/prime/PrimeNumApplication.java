package com.boroborome.prime;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@SpringBootApplication
public class PrimeNumApplication extends JFrame {
    private PrimePane primePane = new PrimePane();
    private JTextField txtR;
    private JTextField txtArc;
    private JTextField txtArcTo;
    private JTextField txtArcStep;
    private JLabel lblStatus = new JLabel();

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
            new Insets(8, 8, 0, 8),
            0, 0));

        rootPane.add(lblStatus, new GridBagConstraints(0, 2,
            1, 1,
            1, 0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
            new Insets(8, 8, 0, 8),
            0, 0));

        setTitle("Quit button");
        setSize(1000, 1000);
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

        panel.add(new JLabel("To:"), new GridBagConstraints(column++, 0,
            1, 1,
            0, 0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
            new Insets(0, 0, 0, 8),
            0, 0));
        panel.add(getTxtArcTo(), new GridBagConstraints(column++, 0,
            1, 1,
            1, 0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
            new Insets(0, 0, 0, 0),
            0, 0));

        panel.add(new JLabel("Step:"), new GridBagConstraints(column++, 0,
            1, 1,
            0, 0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
            new Insets(0, 0, 0, 8),
            0, 0));
        panel.add(getTxtArcStep(), new GridBagConstraints(column++, 0,
            1, 1,
            1, 0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
            new Insets(0, 0, 0, 0),
            0, 0));

        panel.add(createBtnArcRun(), new GridBagConstraints(column++, 0,
            1, 1,
            0, 0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
            new Insets(0, 0, 0, 8),
            0, 0));
        return panel;
    }

    private JButton createBtnArcRun() {
        JButton btnArcRun = new JButton("Run");
        btnArcRun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double start = Double.parseDouble(txtArc.getText());
                double end = Double.parseDouble(txtArcTo.getText());
                double step = Double.parseDouble(txtArcStep.getText());

                if (step == 0
                    || step > 0 && start > end
                    || step < 0 && start < end) {
                    return;
                }
                Thread thread = new Thread(new AnimateThread(start, end, step));
                thread.start();
            }
        });
        return btnArcRun;
    }

    @AllArgsConstructor
    private class AnimateThread implements Runnable {
        private double start;
        private double end;
        private double step;

        @Override
        public void run() {
            for (double arc = start; canRun(arc); arc += step) {
                try {
                    EventQueue.invokeAndWait(new ArcChanger(arc));
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private boolean canRun(double arc) {
            return step > 0 && arc < end
                || step < 0 && arc > end;
        }
    }

    @AllArgsConstructor
    private class ArcChanger implements Runnable {
        private double arc;
        @Override
        public void run() {
            lblStatus.setText(String.valueOf(arc));
            primePane.setArc(arc);
            primePane.repaint();
        }
    }

    private JTextField getTxtArcStep() {
        if (txtArcStep == null) {
            txtArcStep = new JTextField();
            txtArcStep.setText("1");
        }
        return txtArcStep;
    }

    private JTextField getTxtArcTo() {
        if (txtArcTo == null) {
            txtArcTo = new JTextField();
            txtArcTo.setText("1");
        }
        return txtArcTo;
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
