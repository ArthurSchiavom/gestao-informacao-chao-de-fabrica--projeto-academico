/*
 * Copyright (c) 2013-2019 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eapli.base.app.backoffice.console.presentation;

import eapli.base.Application;
import eapli.base.app.backoffice.console.presentation.authz.AddUserUI;
import eapli.base.app.backoffice.console.presentation.authz.DeactivateUserAction;
import eapli.base.app.backoffice.console.presentation.authz.ListUsersAction;
import eapli.base.app.backoffice.console.presentation.clientuser.AcceptRefuseSignupRequestAction;
import eapli.base.app.backoffice.console.presentation.gestaochaofabrica.ordemproducao.ConsultarOrdemProducaoByEncomendaUI;
import eapli.base.app.backoffice.console.presentation.gestaochaofabrica.ordemproducao.ConsultarOrdemProducaoByEstadoUI;
import eapli.base.app.backoffice.console.presentation.gestaochaofabrica.ordemproducao.ImportarOrdensProducaoUI;
import eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaodepositos.especificacao.EspecificarDepositoUI;
import eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaolinhaproducao.especificacao.EspecificarLinhaProducaoUI;
import eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaomaquina.especificacao.EspecificarMaquinaAction;
import eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaomaterial.especificacao.EspecificarCategoriaMaterialAction;
import eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaomaterial.especificacao.EspecificarMaterialAction;
import eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaoproduto.consulta.ConsultarProdutosSemFichaDeProducaoAction;
import eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaoproduto.especificacao.EspecificarFichaDeProducaoAction;
import eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaoproduto.especificacao.EspecificarProdutoAction;
import eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaoproduto.especificacao.ImportarCatalogoProdutosAction;
import eapli.base.app.common.console.presentation.authz.MyUserMenu;
import eapli.base.gestaoproducao.exportacao.application.xml.ExportacaoFicheiroXMLChaoDeFabricaUI;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.actions.Actions;
import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ExitWithMessageAction;
import eapli.framework.presentation.console.ShowMessageAction;
import eapli.framework.presentation.console.menu.HorizontalMenuRenderer;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;

/**
 * TODO split this class in more specialized classes for each menu
 *
 * @author Paulo Gandra Sousa
 */
public class MainMenu extends AbstractUI {

	private static final String RETURN_LABEL = "Return ";

	private static final int EXIT_OPTION = 0;

	// USERS
	private static final int ADD_USER_OPTION = 1;
	private static final int LIST_USERS_OPTION = 2;
	private static final int DEACTIVATE_USER_OPTION = 3;
	private static final int ACCEPT_REFUSE_SIGNUP_REQUEST_OPTION = 4;

	// GESTOR PRODUCAO
	private static final int REGISTAR_PRODUTO = 1;
	private static final int REGISTAR_CATEGORIA = 2;
	private static final int REGISTAR_MATERIAL = 3;
	private static final int CONSULTAR_PRODUTOS_SEM_FICHA_DE_PRODUCAO = 4;
	private static final int REGISTAR_FICHA_DE_PRODUCAO = 5;
	private static final int REGISTAR_PRODUTO_CATALOGO=6;
	private static final int IMPORTAR_ORDENS_PRODUCAO = 7;
	private static final int CONSULTAR_ORDEM_PRODUCAO_ENCOMENDA = 8;
	private static final int CONSULTAR_ORDEM_PRODUCAO_ESTADO = 9;


	// GESTOR CHAO DE FÁBRICA
	private static final int REGISTAR_MAQUINA = 1;
	private static final int REGISTAR_DEPOSITO = 2;
	private static final int REGISTAR_LINHAPRODUCAO = 3;
	private static final int EXPORTAR_XML = 4;


	// SETTINGS
	private static final int SET_KITCHEN_ALERT_LIMIT_OPTION = 1;

	// MAIN MENU
	private static final int MY_USER_OPTION = 1;
	private static final int USERS_OPTION = 2;
	private static final int PRODUCAO_OPTION = 3;
	private static final int FABRICA_OPTION = 4;
	private static final int SETTINGS_OPTION = 5;
	private static final int TRACEABILITY_OPTION = 6;
	private static final int MEALS_OPTION = 7;
	private static final int REPORTING_DISHES_OPTION = 8;

	private static final String SEPARATOR_LABEL = "--------------";

	private final AuthorizationService authz = AuthzRegistry.authorizationService();

	@Override
	public boolean show() {
		drawFormTitle();
		return doShow();
	}

	/**
	 * @return true if the user selected the exit option
	 */
	@Override
	public boolean doShow() {
		final Menu menu = buildMainMenu();
		final MenuRenderer renderer;
		if (Application.settings().isMenuLayoutHorizontal()) {
			renderer = new HorizontalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
		} else {
			renderer = new VerticalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
		}
		return renderer.render();
	}

	@Override
	public String headline() {

		return authz.session().map(s -> "Base [ @" + s.authenticatedUser().identity() + " ]")
				.orElse("Base [ ==Anonymous== ]");
	}

	private Menu buildMainMenu() {
		final Menu mainMenu = new Menu();

		final Menu myUserMenu = new MyUserMenu();
		mainMenu.addSubMenu(MY_USER_OPTION, myUserMenu);

		if (!Application.settings().isMenuLayoutHorizontal()) {
			mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));
		}

		if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.POWER_USER, BaseRoles.ADMIN)) {
			final Menu usersMenu = buildUsersMenu();
			mainMenu.addSubMenu(USERS_OPTION, usersMenu);
			final Menu producaoMenu = buildGestorProducaoMenu();
			mainMenu.addSubMenu(PRODUCAO_OPTION, producaoMenu);
			final Menu fabricaMenu = buildGestorChaoFabricaMenu();
			mainMenu.addSubMenu(FABRICA_OPTION, fabricaMenu);
			final Menu settingsMenu = buildAdminSettingsMenu();
			mainMenu.addSubMenu(SETTINGS_OPTION, settingsMenu);

		}

		if (!Application.settings().isMenuLayoutHorizontal()) {
			mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));
		}

		mainMenu.addItem(EXIT_OPTION, "Exit", new ExitWithMessageAction("Bye, Bye"));

		return mainMenu;
	}

	private Menu buildAdminSettingsMenu() {
		final Menu menu = new Menu("Settings >");

		menu.addItem(SET_KITCHEN_ALERT_LIMIT_OPTION, "Set kitchen alert limit",
				new ShowMessageAction("Not implemented yet"));
		menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

		return menu;
	}

	private Menu buildUsersMenu() {
		final Menu menu = new Menu("Users >");

		menu.addItem(ADD_USER_OPTION, "Add User", new AddUserUI()::show);
		menu.addItem(LIST_USERS_OPTION, "List all Users", new ListUsersAction());
		menu.addItem(DEACTIVATE_USER_OPTION, "Deactivate User", new DeactivateUserAction());
		menu.addItem(ACCEPT_REFUSE_SIGNUP_REQUEST_OPTION, "Accept/Refuse Signup Request",
				new AcceptRefuseSignupRequestAction());

		menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

		return menu;
	}

	/**
	 * Menu do gestor de produção
	 *
	 * @return
	 */
	private Menu buildGestorProducaoMenu() {
		final Menu menu = new Menu("Produção >");

		menu.addItem(REGISTAR_PRODUTO, "Carregar Catálogo de Produtos", new ImportarCatalogoProdutosAction());
		menu.addItem(REGISTAR_CATEGORIA, "Registar categoria de matéria prima", new EspecificarCategoriaMaterialAction());
		menu.addItem(REGISTAR_MATERIAL, "Registar material", new EspecificarMaterialAction());
		menu.addItem(CONSULTAR_PRODUTOS_SEM_FICHA_DE_PRODUCAO, "Produtos Sem Ficha de Produção", new ConsultarProdutosSemFichaDeProducaoAction());
		menu.addItem(REGISTAR_FICHA_DE_PRODUCAO, "Registar Ficha de Produção",
				new EspecificarFichaDeProducaoAction());
		menu.addItem(REGISTAR_PRODUTO_CATALOGO,"Adicionar novo produto no catalogo", new EspecificarProdutoAction());
		menu.addItem(IMPORTAR_ORDENS_PRODUCAO,"Importar ordens de produção", new ImportarOrdensProducaoUI()::show);
		menu.addItem(CONSULTAR_ORDEM_PRODUCAO_ENCOMENDA,"Consultar ordens de produção pelo identificador da encomenda", new ConsultarOrdemProducaoByEncomendaUI()::show);
		menu.addItem(CONSULTAR_ORDEM_PRODUCAO_ESTADO,"Consultar ordens de produção pelo estado", new ConsultarOrdemProducaoByEstadoUI()::show);
		menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

		return menu;
	}

	/**
	 * Menu do gestor de chao de fabrica
	 *
	 * @return
	 */
	private Menu buildGestorChaoFabricaMenu() {
		final Menu menu = new Menu("Chao Fabrica >"); // nao tem acentos, por causa do UTF-8 no terminal

		menu.addItem(REGISTAR_MAQUINA, "Registar Máquina", new EspecificarMaquinaAction());
		menu.addItem(REGISTAR_DEPOSITO, "Registar Depósito", new EspecificarDepositoUI()::show);
		menu.addItem(REGISTAR_LINHAPRODUCAO, "Registar Linha de Produção", new EspecificarLinhaProducaoUI()::show);
		menu.addItem(EXPORTAR_XML, "Exportar XML do Chão de Fábrica", new ExportacaoFicheiroXMLChaoDeFabricaUI()::show);
		menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

		return menu;
	}
}
