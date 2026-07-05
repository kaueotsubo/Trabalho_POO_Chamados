package view;

import control.Controller;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        HelpDeskView view = new HelpDeskView(new Controller());
        view.iniciar();
    }
}