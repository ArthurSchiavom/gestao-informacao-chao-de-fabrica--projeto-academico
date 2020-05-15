package eapli.base.infrastructure.bootstrapers;

import eapli.base.definircategoriamaterial.application.RegistarCategoriaMaterialController;
import eapli.base.definircategoriamaterial.domain.Categoria;
import eapli.base.definircategoriamaterial.domain.CodigoAlfanumerico;
import eapli.base.gestaomateriasprimas.application.AdicionarMaterialCatalogoController;
import eapli.framework.actions.Action;

import java.io.IOException;

public class MaterialBootstrapper implements Action {

    AdicionarMaterialCatalogoController adicionarMaterialCatalogoController=new AdicionarMaterialCatalogoController();
    RegistarCategoriaMaterialController registarCategoriaMaterialController=new RegistarCategoriaMaterialController();

    @Override
    public boolean execute() {
            Categoria categoria1=registarCategoriaMaterialController.registarCategoriaMaterial("123456","metais");
            Categoria categoria2=registarCategoriaMaterialController.registarCategoriaMaterial("1234567","metais");
            Categoria categoria3=registarCategoriaMaterialController.registarCategoriaMaterial("1212344", "metais");
        try {
            adicionarMaterialCatalogoController.registarMaterial("kg",
                    " É um elemento químico, símbolo Fe, de número atômico 26",
                    "Ferro",
                    "test_material/outputPDF",
                    "É um metal maleável, tenaz, de coloração cinza prateado apresentando propriedades magnéticas." +
                            "É ferromagnético a temperatura ambiente, assim como o Níquel e o Cobalto.É encontrado na natureza fazendo parte da composição de diversos minerais, entre eles muitos óxidos, como o FeO (óxido de ferro II, ou óxido ferroso) ou como Fe2O3 (óxido de ferro III, ou óxido férrico). " +
                            "Os números que acompanham o íon ferro dizem respeito aos estados de oxidação apresentados pelo ferro, que são +2 e +3, e ele é raramente encontrado livre. " +
                            "Para obter-se ferro no estado elementar, os óxidos são reduzidos com carbono e imediatamente submetidos a um processo de refinação para retirar as impurezas presentes.",
                    "100",
                    categoria1);
            adicionarMaterialCatalogoController.registarMaterial("kg",
                    " O aço é uma liga metálica formada essencialmente por ferro e carbono, com percentagens deste último variando entre 0,008 e 2,11%",
                    "Aco",
                    "test_material/outputPDF",
                    "No aço comum o teor de impurezas (elementos além do ferro e do carbono) estará sempre abaixo dos 2%. Acima dos 2 até 5% de outros elementos já pode ser considerado aço de baixa-liga, acima de 5% é considerado de alta-liga. " +
                            "O enxofre e o fósforo são elementos prejudicais ao aço pois acabam por intervir nas suas propriedades físicas, deixando-o quebradiço. Dependendo das exigências cobradas, o controle sobre as impurezas pode ser menos rigoroso ou então podem pedir o uso de um antissulfurante como o magnésio e outros elementos de liga benéficos. " +
                            "Existe uma classe de aços carbono, conhecida como aços de fácil usinabilidade, que contém teores mínimos de fósforo e enxofre. Estes dois elementos proporcionam um melhor corte das ferramentas de usinagem, promovendo a quebra do cavaco e evitando a aderência do mesmo na ferramenta." +
                            " Estes aços são utilizados quando as propriedades de usinabilidade são prioritárias, em relação as propriedades mecânicas e microestruturais, (peças de baixa responsabilidade).",
                    "101", categoria2);
            ;

            adicionarMaterialCatalogoController.registarMaterial("kg",
                    " O cobre é um elemento químico de símbolo Cu (do latim cuprum), número atômico 29 (29 prótons e 29 elétrons) e de massa atómica 63,54 u.",
                    "Cobre",
                    "test_material/outputPDF",
                    "O cobre é um metal de transição avermelhado, que apresenta alta condutibilidade elétrica e térmica, só superada pela da prata. " +
                            "É possível que o cobre tenha sido o metal mais antigo a ser utilizado, pois se têm encontrado objetos de cobre de 8700 a.C. " +
                            "Pode ser encontrado em diversos minerais e pode ser encontrado nativo, na forma metálica, em alguns lugares. Fenícios importaram o cobre da Grécia, não tardando em explorar as minas do seu território, como atestam os nomes das cidades Calce, Calcis e Calcitis (de χαλκος, bronze), ainda que tenha sido Chipre, a meio caminho entre Grécia e Egito, por muito tempo o país do cobre por excelência, ao ponto de os romanos chamarem o metal de aes cyprium ou simplesmente cyprium e cuprum, donde provém o seu nome. " +
                            "Além disso, o cobre foi representado com o mesmo signo que Vênus (a afrodite grega), pois Chipre estava consagrada a deusa da beleza e os espelhos eram fabricados com este metal. O símbolo, espelho de Vênus da mitologia e da alquimia, modificação do egípcio Ankh, foi posteriormente adotado por Carl Linné para simbolizar o gênero feminino(♀).",
                    "102",
                    categoria3);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
