package com.boroborome.prime;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class PrimePane extends JPanel {


    private double r = 1;
    private double arc = 1;

    @Override
    public void paint(Graphics g) {
        Dimension size = this.getSize();

        int centerX = size.width / 2;
        int centerY = size.height / 2;
        double sum = 0;

        for (int i = 1; i < 104743; i++) {
            sum += 1/(double) i;
            double realR = r * sum;
            double realArc = arc * sum;

            double pointY = Math.sin(realArc) * realR + centerY;
            double pointX = Math.cos(realArc) * realR + centerX;

            g.setColor(isPrime(i) ? Color.RED : Color.GREEN);
            g.fillOval((int) pointX, (int) pointY, 2, 2);
        }
    }


    private static boolean isPrime(int src) {
        if (src < 2) {
            return false;
        }
        if (src == 2 || src == 3) {
            return true;
        }
//        if ((src & 1) == 0) {// 先判断是否为偶数，若偶数就直接结束程序
        if (src % 2 == 0) {// 先判断是否为偶数，若偶数就直接结束程序
            return false;
        }
        double sqrt = Math.sqrt(src);
        for (int i = 3; i <= sqrt; i+=2) {
            if (src % i == 0) {
                return false;
            }
        }
        return true;
    }
}
