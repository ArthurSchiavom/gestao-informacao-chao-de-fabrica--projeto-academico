package eapli.base.infrastructure.bootstrapers;

import java.util.Calendar;

import eapli.framework.time.util.Calendars;

public final class TestDataConstants {

	public static final String DISH_TYPE_MEAT = "meat";
	public static final String DISH_TYPE_FISH = "fish";
	public static final String DISH_TYPE_VEGIE = "vegie";

	public static final String ALLERGEN_CRUSTACEOS = "crustaceos";
	public static final String ALLERGEN_PEIXES = "peixes";
	public static final String ALLERGEN_GLUTEN = "gluten";

	public static final String DISH_NAME_COSTELETA_A_SALSICHEIRO = "costeleta à salsicheiro";
	public static final String DISH_NAME_PICANHA = "picanha";
	public static final String DISH_NAME_LAGOSTA_SUADA = "lagosta suada";
	public static final String DISH_NAME_BACALHAU_A_BRAZ = "bacalhau à braz";
	public static final String DISH_NAME_LENTILHAS_SALTEADAS = "lentilhas salteadas";
	public static final String DISH_NAME_TOFU_GRELHADO = "tofu grelhado";

	public static final String USER_TEST1 = "user1";

	@SuppressWarnings("squid:S2068")
	public static final String PASSWORD1 = "Password1";

	@SuppressWarnings("squid:S2885")
	public static final Calendar DATE_TO_BOOK = Calendars.of(2017, 12, 01);

	private TestDataConstants() {
		// ensure utility
	}
}
