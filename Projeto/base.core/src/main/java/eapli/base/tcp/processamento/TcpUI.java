package eapli.base.tcp.processamento;

import eapli.base.tcp.TcpSrvRecolherMensagensGeradasPelasMaquinas;
import eapli.framework.presentation.console.AbstractUI;

public class TcpUI  extends AbstractUI {
    @Override
    protected boolean doShow() {
        try {
            TcpSrvRecolherMensagensGeradasPelasMaquinas.server();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public String headline() {
        return "uhm";
    }
}
