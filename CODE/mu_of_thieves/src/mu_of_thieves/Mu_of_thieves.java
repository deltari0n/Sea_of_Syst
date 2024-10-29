/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mu_of_thieves;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author vruche
 */
public class Mu_of_thieves {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[] p1,p2,p3;
        p1 = new int[2];
        p1[0] = 15;
        p1[1] = 15;
        p3 = new int[2];
        p3[0] = 560;
        p3[1] = 70;
        p2 = new int[2];
        p2[0] = (p1[0]+p3[0])/2;
        p2[1] = 250;
        
        Boulet_2_canon boulet = new Boulet_2_canon();
        double[] poly = new double[3];
        poly = boulet.creerPolynome(p1,p2,p3);
        System.out.println(Arrays.toString(poly));
        List<int[]> pos = new ArrayList<>();
        pos = boulet.genererPoints(poly[0], poly[1], poly[2], 0 , 700, 5);
        System.out.println(boulet.toString(pos));
    }
    
}
