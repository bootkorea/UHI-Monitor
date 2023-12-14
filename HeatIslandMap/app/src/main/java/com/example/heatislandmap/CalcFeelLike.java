package com.example.heatislandmap;

public class CalcFeelLike {
    public static double summer(double temp, double humid) {
        double tw = temp * Math.atan(0.151977*Math.pow((humid+8.313659), 0.5)) + Math.atan(temp + humid) - Math.atan(humid-1.67633) + 0.00391838*Math.pow(humid, 1.5)*Math.atan(0.023101*humid) - 4.686035;
        double res = -0.2442 + (0.55399*tw) + (0.45535*temp) - (0.0022*Math.pow(tw,2)) + (0.00278*tw*temp) + 3.0;
        return res;
    }

    public static double winter(double temp, double wind) {
        wind *= 3.6;
        double res = 13.12 + (0.6215 * temp) - (11.37 * Math.pow(wind,0.16)) + (0.3965 * Math.pow(wind, 0.16) * temp);
        return res;
    }
}
