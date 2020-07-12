import GUI.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception exception) {
            System.err.println("Cannot make it prettier");
        }
        GUI gui = new GUI();

    }
}
